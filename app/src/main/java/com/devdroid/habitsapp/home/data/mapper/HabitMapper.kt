package com.devdroid.habitsapp.home.data.mapper

import com.devdroid.habitsapp.home.data.extension.toStartOfDateTimestamp
import com.devdroid.habitsapp.home.data.extension.toTimeStamp
import com.devdroid.habitsapp.home.data.extension.toZonedDateTime
import com.devdroid.habitsapp.home.data.local.entity.HabitEntity
import com.devdroid.habitsapp.home.domain.models.Habit
import java.time.DayOfWeek

fun HabitEntity.toDomain(): Habit {
    return Habit(
        id = this.id,
        name = this.name,
        frequency = this.frequency.map { DayOfWeek.of(it) },
        completeDates = this.completeDates.map { it.toZonedDateTime().toLocalDate() },
        reminder = this.reminder.toZonedDateTime().toLocalTime(),
        startDate = this.startDate.toZonedDateTime()
    )
}

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = this.id,
        name = this.name,
        frequency = this.frequency.map { it.value },
        completeDates = this.completeDates.map { it.toZonedDateTime().toTimeStamp() },
        reminder = this.reminder.toZonedDateTime().toTimeStamp(),
        startDate = this.startDate.toStartOfDateTimestamp()
    )
}
