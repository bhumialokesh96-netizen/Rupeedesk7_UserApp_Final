package com.rupeedesk7.smsapp.worker
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SmsDeliveredReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("SmsDeliveredReceiver", "SMS delivered intent received")
    }
}
