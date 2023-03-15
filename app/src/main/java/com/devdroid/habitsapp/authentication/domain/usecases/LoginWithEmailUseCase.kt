package com.devdroid.habitsapp.authentication.domain.usecases

import com.devdroid.habitsapp.authentication.domain.repository.AuthenticationDataSource
import javax.inject.Inject

class LoginWithEmailUseCase @Inject constructor(
    private val repository: AuthenticationDataSource
) {
    suspend operator fun invoke(email: String, password:String): Result<Unit>{
        return repository.login(email, password)
    }
}