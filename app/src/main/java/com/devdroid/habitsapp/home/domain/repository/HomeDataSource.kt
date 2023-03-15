package com.devdroid.habitsapp.home.domain.repository

import com.devdroid.habitsapp.home.domain.models.Habit
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime


interface HomeDataSource {
    fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>>
    suspend fun insertHabit(habit: Habit)

}