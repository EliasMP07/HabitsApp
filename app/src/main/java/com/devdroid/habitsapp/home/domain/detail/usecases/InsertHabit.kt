package com.devdroid.habitsapp.home.domain.detail.usecases

import com.devdroid.habitsapp.home.domain.models.Habit
import com.devdroid.habitsapp.home.domain.repository.HomeDataSource
import java.time.ZonedDateTime
import javax.inject.Inject

class InsertHabit @Inject constructor(
    private val repository : HomeDataSource
) {
    suspend operator fun invoke(habit: Habit) {
        repository.insertHabit(habit)
    }
}