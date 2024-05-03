package com.devdroid.habitsapp.home.domain.home.usecases

import com.devdroid.habitsapp.home.domain.models.Habit
import com.devdroid.habitsapp.home.domain.repository.HomeDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import javax.inject.Inject

class GetHabitsForDate @Inject constructor(
    private val repository: HomeDataSource
) {
    operator fun invoke(date: ZonedDateTime): Flow<List<Habit>> {
        return repository.getAllHabitsForSelectedDate(date).map {
            it.filter { it.frequency.contains(date.dayOfWeek) }
        }.distinctUntilChanged()
    }
}