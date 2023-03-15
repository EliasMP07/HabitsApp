package com.devdroid.habitsapp.home.data.remote.api

import com.devdroid.habitsapp.home.data.remote.dto.HabitDto
import com.devdroid.habitsapp.home.data.remote.dto.HabitResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface HomeApi {
    companion object{
        const val BASE_URL = "https://habitsapp-52567-default-rtdb.firebaseio.com/"
    }
    @GET("habits.json")
    suspend fun getAllHabits(): HabitResponse

    @PATCH("habits.json")
    suspend fun insertHabit(@Body habit:HabitResponse)
}