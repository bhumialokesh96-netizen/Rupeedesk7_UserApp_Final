package com.rupeedesk7.smsapp.worker
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
fun createNotification(context: Context, title:String, text:String): Notification {
    val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "rupeedesk7_channel_1"
    if (nm.getNotificationChannel(channelId) == null) {
        val ch = NotificationChannel(channelId, "Rupeedesk7", NotificationManager.IMPORTANCE_LOW)
        nm.createNotificationChannel(ch)
    }
    return NotificationCompat.Builder(context, channelId).setContentTitle(title).setContentText(text).setSmallIcon(android.R.drawable.ic_dialog_info).build()
}
