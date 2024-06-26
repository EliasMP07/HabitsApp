package com.devdroid.habitsapp.home.data.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.devdroid.habitsapp.R
import com.devdroid.habitsapp.home.data.extension.goAsync
import com.devdroid.habitsapp.home.domain.alarm.AlarmHandler
import com.devdroid.habitsapp.home.domain.models.Habit
import com.devdroid.habitsapp.home.domain.repository.HomeDataSource
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    companion object{
        const val HABIT_ID = "habit_id"
        const val CHANNEL_ID = "habits_channel"
    }
    @Inject
    lateinit var repository : HomeDataSource

    @Inject
    lateinit var alarmHandler: AlarmHandler

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (context == null || intent == null) return@goAsync
        val id = intent.getStringExtra(HABIT_ID)?: return@goAsync
        val habit = repository.getHabitById(id)
        createNotificationChannel(context)
        if (!habit.completeDates.contains(LocalDate.now())){
            showNotification(habit, context)
        }
        alarmHandler.setRecurringAlarm(habit)
    }

    private fun showNotification(habit: Habit, context: Context) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        ).setContentTitle(habit.name).setSmallIcon(R.drawable.notification_icon)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(habit.id.hashCode(),notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID,
                "habit_reminder",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Get your habits reminder!"
            val notificationManager =context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


}