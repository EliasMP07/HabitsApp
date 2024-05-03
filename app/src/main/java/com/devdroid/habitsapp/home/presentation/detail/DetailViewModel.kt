package com.devdroid.habitsapp.home.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid.habitsapp.home.domain.detail.usecases.DetailUseCases
import com.devdroid.habitsapp.home.domain.models.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val detailUseCases: DetailUseCases
) : ViewModel() {
    var state by mutableStateOf(DetailState())
        private set

    init {
        val id = savedStateHandle.get<String>("habitId")
        if (id != null) {
            viewModelScope.launch {
                val habit = detailUseCases.getHabitById(id)
                state = state.copy(
                    id = habit.id,
                    habitName = habit.name,
                    frequency = habit.frequency,
                    completeDates = habit.completeDates,
                    reminder = habit.reminder,
                    startDate = habit.startDate
                )
            }
        }
    }

    fun onEvent(
        event: DetailEvent
    ) {
        when (event) {
            is DetailEvent.FrecuencyChange -> {
                val frecuency = if (state.frequency.contains(event.dayOfWeek)) {
                    state.frequency - event.dayOfWeek
                } else {
                    state.frequency + event.dayOfWeek
                }
                state = state.copy(
                    frequency = frecuency
                )
            }

            DetailEvent.HabitSave -> {
                viewModelScope.launch {
                    val habit = Habit(
                        id = state.id ?: UUID.randomUUID().toString(),
                        name = state.habitName,
                        frequency = state.frequency,
                        completeDates = state.completeDates,
                        reminder = state.reminder,
                        startDate = state.startDate
                    )
                    detailUseCases.insertHabit(habit)
                }
                state = state.copy(
                    isSave = true
                )
            }

            is DetailEvent.NameChange -> {
                state = state.copy(
                    habitName = event.name
                )
            }

            is DetailEvent.ReminderChange -> {
                state = state.copy(
                    reminder = event.time
                )
            }
        }
    }
}