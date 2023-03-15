package com.devdroid.habitsapp.authentication.domain.usecases

import com.devdroid.habitsapp.authentication.domain.repository.AuthenticationDataSource
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource
) {
    operator fun invoke(): String? = authenticationDataSource.getUserId()
}