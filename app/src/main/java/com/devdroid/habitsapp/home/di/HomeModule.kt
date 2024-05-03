package com.devdroid.habitsapp.home.di

import com.devdroid.habitsapp.home.data.repository.HomeLocalSource
import com.devdroid.habitsapp.home.domain.usecases.CompleteHabitUseCase
import com.devdroid.habitsapp.home.domain.usecases.GetAllHabitsForSelectedDateUseCase
import com.devdroid.habitsapp.home.domain.usecases.HomeUseCases
import com.devdroid.habitsapp.home.domain.repository.HomeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule  {

    @Provides
    @Singleton
    fun provideHomeDataSource() : HomeDataSource = HomeLocalSource()
    @Provides
    @Singleton
    fun provideHomeUseCases(repository: HomeDataSource): HomeUseCases = HomeUseCases(
        completeHabitUseCase = CompleteHabitUseCase(repository),
        getAllHabitsForSelectedDateUseCase = GetAllHabitsForSelectedDateUseCase(repository)
    )
}