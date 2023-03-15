package com.devdroid.habitsapp.home.domain.usecases

data class HomeUseCases(
    val completeHabitUseCase: CompleteHabitUseCase,
    val getAllHabitsForSelectedDateUseCase: GetAllHabitsForSelectedDateUseCase
)