package com.devdroid.habitsapp.authentication.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid.habitsapp.authentication.domain.usecases.PasswordResult
import com.devdroid.habitsapp.authentication.domain.usecases.SignUpUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCases: SignUpUseCases
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

            SignUpEvent.SignUp -> {
                signUp()

            }

            SignUpEvent.LogIn ->
                state = state.copy(
                logIn = true
            )
        }
    }
    private fun signUp(){
        clearErrorMessage()
        if (signUpUseCases.validateEmailUseCase(state.email)){
            state = state.copy(
                emailError = "El email no es valido"
            )
        }
        val passwordResult = signUpUseCases.validatePasswordUseCase(state.password)
        if (passwordResult is PasswordResult.Invalid){
            state = state.copy(
                passwordError = passwordResult.erroMessage
            )
        }
        if (state.emailError == null && state.passwordError  == null){
            state = state.copy(
                isLoading = true
            )
            viewModelScope.launch {
                signUpUseCases.signUpWithEmailUseCase(state.email, state.password).onSuccess {
                    state = state.copy(
                        isSignedIn = true
                    )
                }.onFailure {
                    val error = it.message
                    println("No funcion, $error")
                }

                state = state.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun clearErrorMessage(){
        state = state.copy(
            emailError = null,
            passwordError = null
        )
    }


}