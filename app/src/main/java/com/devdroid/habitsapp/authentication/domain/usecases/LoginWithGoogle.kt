package com.devdroid.habitsapp.authentication.domain.usecases

import com.devdroid.habitsapp.authentication.domain.repository.AuthenticationDataSource

class LoginWithGoogle (
    private val repository: AuthenticationDataSource
){
}