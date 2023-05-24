package com.example.coursework.screens.adding

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.util.Calendar

class AddedTrainDetails : Fragment() {
    private lateinit var binding: AddedTrainDetailsBinding
    private lateinit var notificationManager: NotificationManagerCompat

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddedTrainDetailsBinding.inflate(layoutInflater, container, false)
        notificationManager = NotificationManagerCompat.from(requireContext())

        // Создание канала уведомлений
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // Настройка атрибутов канала уведомлений
            channel.description = "Channel Description"
            // Другие настройки канала уведомлений, такие как звук и вибрация, могут быть установлены здесь

            // Регистрация канала уведомлений в NotificationManager
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
                        // Сохраняем выбранную дату и время
                        val selectedCalendar = Calendar.getInstance()
                        selectedCalendar.set(
                            selectedYear,
                            monthOfYear,
                            dayOfMonth,
                            hourOfDay,
                            minute
                        )

                        // Назначаем уведомление на выбранную дату и время
                        setNotification(selectedCalendar.timeInMillis)
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

    @SuppressLint("MissingPermission")
    private fun setNotification(timeInMillis: Long) {
        val notificationTitle = "Напоминание"
        val notificationText = "Уведомление"

        // Создаем намерение для открытия активности при нажатии на уведомление
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE // Используйте FLAG_IMMUTABLE или FLAG_MUTABLE здесь
        )

        // Создаем уведомление
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Устанавливаем время для уведомления
        val notificationTime = Calendar.getInstance()
        notificationTime.timeInMillis = timeInMillis

        // Запланировать уведомление
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_ID = "MyNotificationChannel"
        private const val NOTIFICATION_ID = 1
        private const val REQUEST_CODE = 121
    }
}
