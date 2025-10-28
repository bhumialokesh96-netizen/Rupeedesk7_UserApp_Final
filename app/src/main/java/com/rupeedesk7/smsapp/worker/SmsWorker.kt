package com.rupeedesk7.smsapp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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

class SmsWorker(val appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun doWork(): Result {
        try {
            // Set as foreground worker (for stability)
            setForeground(createForegroundInfo("Sending SMS", "Sending pending messages..."))
        } catch (e: Exception) {
            Log.w(TAG, "Foreground start failed: $e")
        }

        return try {
            // Fetch one unsent SMS task from Firestore
            val snap = db.collection("inventory")
                .whereEqualTo("sent", false)
                .limit(1)
                .get()
                .await()

            if (snap.isEmpty) {
                Log.i(TAG, "No pending messages found.")
                return Result.success()
            }

            val doc = snap.documents[0]
            val data = doc.data ?: return Result.success()
            val target = data["target"] as? String ?: return Result.success()
            val message = data["message"] as? String ?: ""
            val price = (data["price"] as? Number)?.toDouble() ?: 0.0

            // Get SIM id from document or user record
            val subscriptionId = (data["subscriptionId"] as? Number)?.toInt() ?: run {
                val userId = (data["userId"] as? String) ?: target
                val userSnap = db.collection("users").document(userId).get().await()
                (userSnap.getLong("simId") ?: -1L).toInt()
            }

            val sentOk = sendTextSms(target, message, subscriptionId)

            if (sentOk) {
                // Update Firestore
                db.collection("inventory").document(doc.id)
                    .update(
                        mapOf(
                            "sent" to true,
                            "sentAt" to Timestamp.now()
                        )
                    )
                    .await()

                val userId = (data["userId"] as? String) ?: target
                val userRef = db.collection("users").document(userId)
                val userSnap = userRef.get().await()

                if (userSnap.exists()) {
                    val prevBalance = userSnap.getDouble("balance") ?: 0.0
                    val prevDaily = userSnap.getLong("dailySent") ?: 0L
                    userRef.update(
                        mapOf(
                            "balance" to prevBalance - price,
                            "dailySent" to prevDaily + 1
                        )
                    ).await()
                }

                Log.i(TAG, "SMS sent successfully to $target")
                Result.success()
            } else {
                Log.w(TAG, "SMS failed to send to $target")
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
                    Log.w(TAG, "Subscription ID failed, using default", e)
                    SmsManager.getDefault()
                }
            } else {
                SmsManager.getDefault()
            }

            smsManager.sendTextMessage(target, null, message, null, null)
            Log.i(TAG, "SMS sent to $target (subId=$subscriptionId)")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error sending SMS", e)
            false
        }
    }

    private fun createForegroundInfo(title: String, message: String): ForegroundInfo {
        val channelId = "sms_worker_channel"
        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification channel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "SMS Worker",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(appContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.sym_action_email) // ✅ fixed icon
            .setOngoing(true)
            .build()

        return ForegroundInfo(42, notification)
    }
}
