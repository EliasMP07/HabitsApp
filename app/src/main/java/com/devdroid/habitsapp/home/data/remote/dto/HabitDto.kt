package com.devdroid.habitsapp.home.data.remote.dto

data class HabitDto(
    val name: String,
    val reminder: Long,
    val startDate: Long,
    val frequency : List<Int>,
    val completeDates: List<Long>?

)
