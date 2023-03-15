package com.devdroid.habitsapp.home.presentation.data.repository

import com.devdroid.habitsapp.home.presentation.domain.models.Habit
import com.devdroid.habitsapp.home.presentation.domain.repository.HomeDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

class HomeLocalSource : HomeDataSource{



    private val mockHabits = (1..30).map {
        val dates = mutableListOf<LocalDate>()
        if (it % 2 == 0){
            dates.add(LocalDate.now())
        }
        Habit(
            id = it.toString(),
            name = "Habito $it",
            frequency = listOf(),
            completeDates = dates,
            reminder = LocalTime.now(),
            startDate = ZonedDateTime.now()
        )
    }.toMutableList()

    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        return flowOf(mockHabits)
    }

    override suspend fun insertHabit(habit: Habit) {
        val index = mockHabits.indexOfFirst { it.id == habit.id }
        mockHabits.removeAt(index)
        mockHabits.add(index, habit)
    }

}