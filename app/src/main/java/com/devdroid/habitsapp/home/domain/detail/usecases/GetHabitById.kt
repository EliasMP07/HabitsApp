package com.devdroid.habitsapp.home.domain.detail.usecases

import com.devdroid.habitsapp.home.domain.models.Habit
import com.devdroid.habitsapp.home.domain.repository.HomeDataSource

class GetHabitById(
    private val repository: HomeDataSource
){
    suspend operator fun invoke(id: String): Habit{
        return repository.getHabitById(id)
    }
}