package com.devdroid.habitsapp.home.presentation.domain.home.usecases

import com.devdroid.habitsapp.home.presentation.domain.models.Habit
import com.devdroid.habitsapp.home.presentation.domain.repository.HomeDataSource
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime
import javax.inject.Inject

class GetAllHabitsForSelectedDateUseCase @Inject constructor(
    private val repository: HomeDataSource
){
    operator fun invoke(date: ZonedDateTime): Flow<List<Habit>> = repository.getAllHabitsForSelectedDate(date)
}