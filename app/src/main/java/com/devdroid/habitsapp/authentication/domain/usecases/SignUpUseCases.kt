package com.devdroid.habitsapp.authentication.domain.usecases

data class SignUpUseCases(
    val signUpWithEmailUseCase: SignUpWithEmailUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase
)