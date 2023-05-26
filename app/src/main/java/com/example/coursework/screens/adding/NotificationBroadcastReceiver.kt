package com.example.coursework.screens.adding

import android.annotation.SuppressLint
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat

class NotificationBroadcastReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Train has planned!", Toast.LENGTH_SHORT).show()
        val notification = intent.getParcelableExtra<Notification>(KEY_NOTIFICATION)
        val notificationManager = NotificationManagerCompat.from(context)
        notification?.let { notificationManager.notify(NOTIFICATION_ID, it) }
    }

    companion object {
        private const val KEY_NOTIFICATION = "notification"
        private const val NOTIFICATION_ID = 1
    }
}