package com.rupeedesk7.smsapp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val TAG = "SmsWorker"
private const val CHANNEL_ID = "rupeedesk_sms_channel"

class SmsWorker(val appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun doWork(): Result {
        try {
            setForeground(createForegroundInfo("Sending SMS", "Sending pending SMS..."))
        } catch (t: Throwable) {
            Log.w(TAG, "Could not set foreground: $t")
        }

        return try {
            val snap = db.collection("inventory").whereEqualTo("sent", false).limit(1).get().await()
            if (snap.isEmpty) return Result.success()
            val doc = snap.documents[0]
            val data = doc.data ?: return Result.success()
            val target = data["target"] as? String ?: return Result.success()
            val message = data["message"] as? String ?: ""
            val price = (data["price"] as? Number)?.toDouble() ?: 0.0
            val subscriptionId = (data["subscriptionId"] as? Number)?.toInt() ?: run {
                val userId = (data["userId"] as? String) ?: target
                val userSnap = db.collection("users").document(userId.toString()).get().await()
                (userSnap.getLong("simId") ?: -1L).toInt()
            }

            val sentOk = sendTextSms(target, message, subscriptionId)

            if (sentOk) {
                db.collection("inventory").document(doc.id)
                    .update("sent", true, "sentAt", Timestamp.now())
                    .await()

                val userId = (data["userId"] as? String) ?: target
                val userRef = db.collection("users").document(userId.toString())
                val userSnap = userRef.get().await()
                if (userSnap.exists()) {
                    val prevBalance = userSnap.getDouble("balance") ?: 0.0
                    val prevDaily = userSnap.getLong("dailySent") ?: 0L
                    userRef.update("balance", prevBalance - price, "dailySent", prevDaily + 1).await()
                }
                Result.success()
            } else {
                Result.retry()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Worker failed", ex)
            Result.retry()
        }
    }

    private fun sendTextSms(target: String, message: String, subscriptionId: Int): Boolean {
        return try {
            val smsManager: SmsManager = if (subscriptionId >= 0) {
                try {
                    SmsManager.getSmsManagerForSubscriptionId(subscriptionId)
                } catch (e: Exception) {
                    Log.w(TAG, "getSmsManagerForSubscriptionId failed, using default", e)
                    SmsManager.getDefault()
                }
            } else {
                SmsManager.getDefault()
            }
            smsManager.sendTextMessage(target, null, message, null, null)
            Log.i(TAG, "SMS sent to $target via subId=$subscriptionId")
            true
        } catch (e: Exception) {
            Log.e(TAG, "SMS send failed", e)
            false
        }
    }

    private fun createForegroundInfo(title: String, text: String): ForegroundInfo {
        // create channel
        val nm = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (nm.getNotificationChannel(CHANNEL_ID) == null) {
            val chan = NotificationChannel(CHANNEL_ID, "SMS sending", NotificationManager.IMPORTANCE_LOW)
            nm.createNotificationChannel(chan)
        }
        val notif = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_notify_sync) // replace with your app icon resource if available
            .setContentTitle(title)
            .setContentText(text)
            .setOngoing(true)
            .build()
        return ForegroundInfo(42, notif)
    }
}