package com.devdroid.habitsapp.home.data.mapper

import com.devdroid.habitsapp.home.data.extension.toStartOfDateTimestamp
import com.devdroid.habitsapp.home.data.extension.toTimeStamp
import com.devdroid.habitsapp.home.data.extension.toZonedDateTime
import com.devdroid.habitsapp.home.data.local.entity.HabitEntity
import com.devdroid.habitsapp.home.data.remote.dto.HabitDto
import com.devdroid.habitsapp.home.data.remote.dto.HabitResponse
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


fun Habit.toDto(): HabitResponse {
    val dto =  HabitDto(
        name = this.name,
        frequency = this.frequency.map { it.value },
        completeDates = this.completeDates.map { it.toZonedDateTime().toTimeStamp() },
        reminder = this.reminder.toZonedDateTime().toTimeStamp(),
        startDate = this.startDate.toStartOfDateTimestamp()
    )
    return mapOf(id to dto)
}


fun HabitResponse.toDomain(): List<Habit>{
    return this.entries.map {
        val id = it.key
        val dto = it.value
        Habit(
            id = id,
            name = dto.name,
            frequency =dto.frequency.map { DayOfWeek.of(it) },
            completeDates = dto.completeDates?.map { it.toZonedDateTime().toLocalDate() } ?: emptyList(),
            reminder = dto.reminder.toZonedDateTime().toLocalTime(),
            startDate = dto.startDate.toZonedDateTime()
        )
    }
}
