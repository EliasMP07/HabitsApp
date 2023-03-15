package com.devdroid.habitsapp.authentication.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.devdroid.habitsapp.R
import com.devdroid.habitsapp.authentication.presentation.signup.components.SignUpForm


@Composable
fun SignUpScreen(
    onLogin :()-> Unit,
    onSignIn :() -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(state.isSignedIn) {
        if (state.isSignedIn) {
            onSignIn()
        }
    }

    LaunchedEffect(state.logIn) {
        if (state.logIn) {
            onLogin()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Image(
            painter = painterResource(id = R.drawable.signup),
            contentDescription = ""
        )
        SignUpForm(state = state, onEvent = viewModel::onEvent, modifier = Modifier.fillMaxWidth())

    }
    if (state.isLoading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
}