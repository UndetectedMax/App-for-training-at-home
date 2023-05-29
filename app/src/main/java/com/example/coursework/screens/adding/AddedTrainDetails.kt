package com.example.coursework.screens.adding

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.coursework.MainActivity
import com.example.coursework.R
import com.example.coursework.databinding.AddedTrainDetailsBinding
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddedTrainDetails : Fragment() {
    private lateinit var binding: AddedTrainDetailsBinding
    private lateinit var notificationManager: NotificationManagerCompat
    private var selectedTimeInMillis: Long = 0
    private var selectedCalendar: Calendar? = null

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddedTrainDetailsBinding.inflate(layoutInflater, container, false)
        notificationManager = NotificationManagerCompat.from(requireContext())

        // Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // Configure notification channel attributes
            channel.description = "Channel Description"
            // Other notification channel settings such as sound and vibration can be set here

            // Register the notification channel with the NotificationManager
            val notificationManager =
                requireContext().getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }

        binding.setDate.setOnClickListener {
            showDateTimePickerDialog()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val trainCode = arguments?.getString("trainCode")
        val trainReference =
            FirebaseDatabase.getInstance().getReference("trainings").orderByChild("trainCode")
                .equalTo(trainCode)
        trainReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val train = childSnapshot.getValue(OwnTrainInfo::class.java)
                    train?.let {
                        binding.addedTrainName.text = it.trainName
                        binding.addedTrainDesc.text = it.trainDescription
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDateTimePickerDialog() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, monthOfYear, dayOfMonth ->
                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        selectedCalendar = Calendar.getInstance()
                        selectedCalendar?.set(
                            selectedYear,
                            monthOfYear,
                            dayOfMonth,
                            hourOfDay,
                            minute
                        )
                        selectedCalendar?.let {
                            selectedTimeInMillis = it.timeInMillis
                            // Set the notification for the selected date and time
                            setNotification(selectedTimeInMillis)
                        }
                    },
                    currentDate.get(Calendar.HOUR_OF_DAY),
                    currentDate.get(Calendar.MINUTE),
                    true
                )

                timePickerDialog.show()
            }, year, month, day
        )

        datePickerDialog.datePicker.minDate = currentDate.timeInMillis
        datePickerDialog.show()
    }

    @SuppressLint("MissingPermission", "ObsoleteSdkInt")
    private fun setNotification(timeInMillis: Long) {
        val notificationTitle = "You have a planned train"
        val trainCode = arguments?.getString("trainCode")
        val notificationText =
            String.format("Train Code: %s\nDate: %s", trainCode, formatDate(timeInMillis))

        // Check if notifications are enabled for the app
        if (notificationManager.areNotificationsEnabled()) {
            createNotification(timeInMillis, notificationTitle, notificationText)
        } else {
            showEnableNotificationsDialog()
        }
    }

    private fun showEnableNotificationsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Notifications Disabled")
            .setMessage("Please enable notifications for this app to receive reminders.")
            .setPositiveButton("Enable") { _, _ ->
                // app settings notifications enabled
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, requireActivity().packageName)
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun createNotification(timeInMillis: Long, title: String, text: String) {
        // Create an intent to open the activity when the notification is clicked
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE // Use FLAG_IMMUTABLE or FLAG_MUTABLE here
        )

        // Create the notification
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Schedule the notification using AlarmManager
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(requireContext(), NotificationBroadcastReceiver::class.java)
        notificationIntent.putExtra(KEY_NOTIFICATION, notification)
        val pendingNotificationIntent = PendingIntent.getBroadcast(
            requireContext(),
            NOTIFICATION_ID,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,timeInMillis,pendingNotificationIntent)
    }

    private fun formatDate(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis

        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    companion object {
        private const val CHANNEL_ID = "MyNotificationChannel"
        private const val NOTIFICATION_ID = 1
        private const val REQUEST_CODE = 121
        private const val KEY_NOTIFICATION = "notification"
    }
}
