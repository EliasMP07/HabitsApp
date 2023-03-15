package com.devdroid.habitsapp.home.presentation.home

import com.devdroid.habitsapp.home.presentation.domain.models.Habit
import java.time.ZonedDateTime

sealed interface HomeEvent {
    data class ChangeDate(val date: ZonedDateTime): HomeEvent
    data class CompleteHabit(val habit: Habit): HomeEvent

}