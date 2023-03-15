package com.devdroid.habitsapp.home.domain.usecases

import com.devdroid.habitsapp.home.domain.models.Habit
import com.devdroid.habitsapp.home.domain.repository.HomeDataSource
import java.time.ZonedDateTime
import javax.inject.Inject

class CompleteHabitUseCase @Inject constructor(
    private val repository : HomeDataSource
) {
    suspend operator fun invoke(habit: Habit, date:ZonedDateTime ) {
        val newHabit  = if (habit.completeDates.contains(date.toLocalDate())){
            habit.copy(
                completeDates = habit.completeDates - date.toLocalDate()
            )
        }else{
            habit.copy(
                completeDates = habit.completeDates + date.toLocalDate()
            )
        }
        repository.insertHabit(newHabit)
    }
}