package com.devdroid.habitsapp.authentication.presentation.signup

data class SignUpState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError:String? = null,
    val isLoading:Boolean = false,
    val logIn: Boolean = false,
    val isSignedIn: Boolean = false

)
