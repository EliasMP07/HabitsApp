package com.devdroid.habitsapp.authentication.presentation.signup

sealed interface SignUpEvent {
    data class EmailChange(val email: String): SignUpEvent
    data class PasswordChange(val password: String): SignUpEvent

    object SignUp: SignUpEvent
}