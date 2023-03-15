package com.devdroid.habitsapp.authentication.domain.usecases

import com.devdroid.habitsapp.authentication.domain.repository.AuthenticationDataSource
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(
    private val repository: AuthenticationDataSource
){
    suspend operator fun invoke(email: String, password:String): Result<Unit> = repository.signup(email, password)
}