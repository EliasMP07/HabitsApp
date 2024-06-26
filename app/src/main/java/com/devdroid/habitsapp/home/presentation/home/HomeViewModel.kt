package com.devdroid.habitsapp.home.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid.habitsapp.home.domain.home.usecases.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
): ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        getHabits()
        viewModelScope.launch{
            homeUseCases.syncHabits()
        }
    }

    fun onEvent(
        event: HomeEvent
    ){
        when(event){
            is HomeEvent.ChangeDate -> {
                state = state.copy(
                    selectedDate = event.date
                )
                getHabits()
            }
            is HomeEvent.CompleteHabit -> {
                viewModelScope.launch {
                    homeUseCases.completeHabitUseCase(event.habit, state.selectedDate)
                }
            }
        }
    }

    private fun getHabits(){
        viewModelScope.launch {
            homeUseCases.getHabitsForDate(state.selectedDate).collectLatest{
                state = state.copy(
                    habits = it
                )
            }
        }
    }
}