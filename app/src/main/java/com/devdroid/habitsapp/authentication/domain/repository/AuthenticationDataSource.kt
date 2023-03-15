package com.devdroid.habitsapp.authentication.domain.repository

interface AuthenticationDataSource {
    suspend fun login(email: String, password: String): Result<Unit>
}