package com.devdroid.habitsapp.authentication.domain.repository

interface AuthenticationDataSource {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun signup(email: String, password: String): Result<Unit>
    suspend fun loginWithGoogle(idToken: String): Result<Unit>
    fun getUserId(): String?
    suspend fun logout()
}