package com.devdroid.habitsapp.home.domain.alarm

import com.devdroid.habitsapp.home.domain.models.Habit

interface AlarmHandler {
    fun setRecurringAlarm(habit: Habit)
    fun cancel(habit: Habit)
}