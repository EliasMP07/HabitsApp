package com.devdroid.habitsapp.home.presentation.di

import com.devdroid.habitsapp.home.presentation.data.repository.HomeLocalSource
import com.devdroid.habitsapp.home.presentation.domain.home.usecases.CompleteHabitUseCase
import com.devdroid.habitsapp.home.presentation.domain.home.usecases.GetAllHabitsForSelectedDateUseCase
import com.devdroid.habitsapp.home.presentation.domain.home.usecases.HomeUseCases
import com.devdroid.habitsapp.home.presentation.domain.repository.HomeDataSource
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
    fun provideHomeUseCases(repository: HomeDataSource):HomeUseCases = HomeUseCases(
        completeHabitUseCase = CompleteHabitUseCase(repository),
        getAllHabitsForSelectedDateUseCase = GetAllHabitsForSelectedDateUseCase(repository)
    )
}