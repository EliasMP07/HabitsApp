package com.devdroid.habitsapp.home.domain.detail.usecases

import com.devdroid.habitsapp.home.domain.models.Habit
import com.devdroid.habitsapp.home.domain.repository.HomeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHabitById(
    private val repository: HomeDataSource
){
    suspend operator fun invoke(id: String): Habit{
        return withContext(Dispatchers.IO){
            repository.getHabitById(id)
        }
    }
}