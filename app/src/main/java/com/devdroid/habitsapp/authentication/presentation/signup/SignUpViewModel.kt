package com.devdroid.habitsapp.authentication.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SignUpViewModel @Inject constructor(

): ViewModel() {
    var state by mutableStateOf(SignUpState())
        private set

    fun onEvent(
        event: SignUpEvent
    ){
        when(event){
            is SignUpEvent.EmailChange -> {
                state = state.copy(
                    email = event.email
                )

            }
            is SignUpEvent.PasswordChange -> {
                state = state.copy(
                    password = event.password
                )

            }
            SignUpEvent.SignIn -> {
                state = state.copy(
                    signIn = true
                )

            }
            SignUpEvent.SignUp -> {
                signUp()

            }
        }
    }
    private fun signUp(){

    }

    private fun clearErrorMessage(){
        state = state.copy(
            emailError = null,
            passwordError = null
        )
    }


}