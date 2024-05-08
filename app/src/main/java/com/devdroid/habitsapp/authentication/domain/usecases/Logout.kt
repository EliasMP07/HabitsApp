package com.devdroid.habitsapp.authentication.domain.usecases

import com.devdroid.habitsapp.authentication.domain.repository.AuthenticationDataSource
import javax.inject.Inject

class Logout @Inject constructor(
    private val repository: AuthenticationDataSource
) {
    suspend operator fun invoke(){
        return repository.logout()
    }
}