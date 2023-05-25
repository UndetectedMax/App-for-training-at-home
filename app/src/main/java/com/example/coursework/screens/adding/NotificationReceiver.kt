package com.example.coursework.screens.adding

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Действия, которые должны произойти при получении уведомления
        Toast.makeText(context, "Train starts now!", Toast.LENGTH_SHORT).show()
    }
}