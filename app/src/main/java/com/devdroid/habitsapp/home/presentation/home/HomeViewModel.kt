package com.devdroid.habitsapp.home.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid.habitsapp.home.domain.usecases.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
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
    }

    fun onEvent(
        event: HomeEvent
    ){
        when(event){
            is HomeEvent.ChangeDate -> {

                state = state.copy(
                    selectedDate = event.date
                )
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
            homeUseCases.getAllHabitsForSelectedDateUseCase(state.selectedDate).collect{
                state = state.copy(
                    habits = it
                )
            }
        }
    }
}