package com.devdroid.habitsapp.home.data.repository

import com.devdroid.habitsapp.home.data.extension.toStartOfDateTimestamp
import com.devdroid.habitsapp.home.data.local.dao.HomeDao
import com.devdroid.habitsapp.home.data.mapper.toDomain
import com.devdroid.habitsapp.home.data.mapper.toEntity
import com.devdroid.habitsapp.home.domain.models.Habit
import com.devdroid.habitsapp.home.domain.repository.HomeDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime

class HomeLocalSource (
    private val dao: HomeDao
): HomeDataSource {


    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        return dao.getAllHabitForSelectedDate(date.toStartOfDateTimestamp()).map {
            it.map {
                it.toDomain()
            }
        }
    }

    override suspend fun insertHabit(habit: Habit) {
        dao.insertHabit(habit.toEntity())

    }

    override suspend fun getHabitById(id: String): Habit {
       return dao.getHabitById(id).toDomain()
    }

}