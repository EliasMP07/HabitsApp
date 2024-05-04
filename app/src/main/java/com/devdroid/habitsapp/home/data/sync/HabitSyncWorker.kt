package com.devdroid.habitsapp.home.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.devdroid.habitsapp.home.data.local.dao.HomeDao
import com.devdroid.habitsapp.home.data.local.entity.HabitSyncEntity
import com.devdroid.habitsapp.home.data.mapper.toDomain
import com.devdroid.habitsapp.home.data.mapper.toDto
import com.devdroid.habitsapp.home.data.remote.api.HomeApi
import com.devdroid.habitsapp.home.data.remote.util.resultOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@HiltWorker
class HabitSyncWorker @AssistedInject constructor (
    @Assisted private val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val api: HomeApi,
    private val dao: HomeDao
): CoroutineWorker(context, workerParameters){
    override suspend fun doWork(): Result {
        val items = dao.getAllHabitsSync()
        if (runAttemptCount >= 3){
            return Result.failure()
        }
        return try {
            supervisorScope {
                val jobs = items.map {items -> launch {  sync(items) } }
                jobs.forEach { it.join() }
            }
            Result.success()
        }catch (e: Exception){
            Result.retry()
        }
    }

    private suspend fun sync(entity: HabitSyncEntity){
        val habit = dao.getHabitById(entity.id).toDomain().toDto()
        resultOf {
            api.insertHabit(habit)
        }.onSuccess {
            dao.deleteHabitSync(entity)
        }.onFailure {
            throw it
        }

    }
}