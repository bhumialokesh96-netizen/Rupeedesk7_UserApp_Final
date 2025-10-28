package com.rupeedesk7.smsapp.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val TAG = "SmsWorker"

class SmsWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun doWork(): Result {
        // start foreground for reliability
        try {
            setForeground(createForegroundInfo("RupeeDesk SMS", "Preparing to send..."))
        } catch (e: Exception) {
            Log.w(TAG, "Foreground not allowed: $e")
        }

        // check SEND_SMS permission
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.SEND_SMS)
            != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(TAG, "SEND_SMS permission not granted, cannot send")
            return Result.failure()
        }

        return try {
            // fetch one unsent message
            val snap = db.collection("inventory")
                .whereEqualTo("sent", false)
                .limit(1)
                .get()
                .await()

            if (snap.isEmpty) {
                Log.i(TAG, "No pending SMS to send.")
                return Result.success()
            }

            val doc = snap.documents[0]
            val data = doc.data ?: return Result.success()
            val target = data["target"] as? String ?: return Result.success()
            val message = data["message"] as? String ?: "Hello from RupeeDesk"
            val price = (data["price"] as? Number)?.toDouble() ?: 0.0
            val userId = data["userId"] as? String ?: target

            // get user's sim id if available
            val userSnap = db.collection("users").document(userId).get().await()
            val subscriptionId = (userSnap.getLong("simId") ?: -1L).toInt()

            val sent = sendTextSms(target, message, subscriptionId)

            if (sent) {
                db.collection("inventory").document(doc.id)
                    .update("sent", true, "sentAt", Timestamp.now())
                    .await()
                val prevBalance = userSnap.getDouble("balance") ?: 0.0
                val prevDaily = userSnap.getLong("dailySent") ?: 0L
                db.collection("users").document(userId)
                    .update("balance", prevBalance - price, "dailySent", prevDaily + 1)
                    .await()
                Log.i(TAG, "SMS sent successfully to $target")
                Result.success()
            } else {
                Log.e(TAG, "SMS sending failed, will retry")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Worker failed", e)
            Result.retry()
        }
    }

    private fun sendTextSms(target: String, message: String, subscriptionId: Int): Boolean {
        return try {
            val smsManager: SmsManager = if (subscriptionId >= 0) {
                try {
                    SmsManager.getSmsManagerForSubscriptionId(subscriptionId)
                } catch (e: Exception) {
                    Log.w(TAG, "Fallback to default SMS manager", e)
                    SmsManager.getDefault()
                }
            } else SmsManager.getDefault()

            smsManager.sendTextMessage(target, null, message, null, null)
            Log.i(TAG, "SMS sent to $target using subId=$subscriptionId")
            true
        } catch (e: Exception) {
            Log.e(TAG, "SMS send failed", e)
            false
        }
    }

    private fun createForegroundInfo(title: String, message: String): ForegroundInfo {
        val channelId = "sms_worker_channel"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "SMS Worker",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.stat_sys_sending)
            .setOngoing(true)
            .build()

        return ForegroundInfo(42, notification)
    }
}
