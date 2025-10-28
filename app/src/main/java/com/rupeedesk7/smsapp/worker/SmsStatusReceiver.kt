package com.rupeedesk7.smsapp.worker
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class SmsStatusReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("SmsStatusReceiver", "SMS sent intent received: ${'$'}{intent?.action}")
        // Optionally update Firestore here using data from the intent's extras (message id etc.) if you pass them
        // This is a placeholder to show where to handle SENT status.
        // Example: db.collection("inventory").document(id).update("sentStatus","SENT")
    }
}
