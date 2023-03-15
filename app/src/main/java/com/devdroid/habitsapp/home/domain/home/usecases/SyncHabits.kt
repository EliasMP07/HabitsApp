package com.devdroid.habitsapp.home.domain.home.usecases

import com.devdroid.habitsapp.home.domain.repository.HomeDataSource

class SyncHabits (
    private val repository : HomeDataSource
){
    suspend operator fun invoke(){
        repository.syncHabits()
    }
}