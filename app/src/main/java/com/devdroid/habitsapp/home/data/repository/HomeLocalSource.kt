package com.devdroid.habitsapp.home.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.devdroid.habitsapp.home.data.extension.toStartOfDateTimestamp
import com.devdroid.habitsapp.home.data.local.dao.HomeDao
import com.devdroid.habitsapp.home.data.local.entity.HabitSyncEntity
import com.devdroid.habitsapp.home.data.mapper.toDomain
import com.devdroid.habitsapp.home.data.mapper.toDto
import com.devdroid.habitsapp.home.data.mapper.toEntity
import com.devdroid.habitsapp.home.data.mapper.toSyncEntity
import com.devdroid.habitsapp.home.data.remote.api.HomeApi
import com.devdroid.habitsapp.home.data.remote.util.resultOf
import com.devdroid.habitsapp.home.data.sync.HabitSyncWorker
import com.devdroid.habitsapp.home.domain.alarm.AlarmHandler
import com.devdroid.habitsapp.home.domain.models.Habit
import com.devdroid.habitsapp.home.domain.repository.HomeDataSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import java.time.ZonedDateTime

class HomeLocalSource(
    private val dao: HomeDao,
    private val api: HomeApi,
    private val alarmHandler: AlarmHandler,
    private val workManager: WorkManager
) : HomeDataSource {


    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        val localFlow = dao.getAllHabitsForSelectedDate(date.toStartOfDateTimestamp())
            .map { it.map { it.toDomain() } }
        val apiFlow = getHabitsFromApi()

        return localFlow.combine(apiFlow) { db, _ ->
            db
        }
    }

    private fun getHabitsFromApi(): Flow<List<Habit>> {
        return flow {
            resultOf {
                val habits =  api.getAllHabits().toDomain()
                insertHabits(habits)
            }
            emit(emptyList<Habit>())
        }.onStart {
            emit(emptyList())
        }
    }

    private suspend fun insertHabits(habits: List<Habit>){
        habits.forEach {
            setHandleAlarm(it)
            dao.insertHabit(it.toEntity())
        }
    }

    override suspend fun insertHabit(habit: Habit) {
        setHandleAlarm(habit)
        dao.insertHabit(habit.toEntity())
        resultOf {
            api.insertHabit(habit.toDto())
        }.onFailure {
            dao.insertHabitSync(habit.toSyncEntity())
        }

    }

    private suspend fun setHandleAlarm(habit: Habit){
        try {
            val previous = dao.getHabitById(habit.id)
            alarmHandler.cancel(previous.toDomain())
        }catch (e:Exception){

        }
        alarmHandler.setRecurringAlarm(habit)
    }

    override suspend fun getHabitById(id: String): Habit {
        return dao.getHabitById(id).toDomain()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun syncHabits() {
        val worker = OneTimeWorkRequestBuilder<HabitSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()

        workManager.beginUniqueWork("sync_habit_id", ExistingWorkPolicy.REPLACE, worker).enqueue()
    }

}