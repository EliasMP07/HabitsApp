package com.devdroid.habitsapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devdroid.habitsapp.authentication.presentation.login.LoginScreen
import com.devdroid.habitsapp.authentication.presentation.signup.SignUpScreen
import com.devdroid.habitsapp.onboarding.presentation.OnboardingScreen

@Composable
fun  NavigationHost(
    navController: NavHostController,
    startDestination: NavigationRoute
){
    NavHost(
        navController = navController,
        startDestination = startDestination.route){

        composable(NavigationRoute.Onboarding.route){
            OnboardingScreen{
                navController.popBackStack()
                navController.navigate(NavigationRoute.Login.route)
            }
        }
        composable(NavigationRoute.Login.route){
            LoginScreen(
                onLogin = {
                    navController.popBackStack()
                    navController.navigate(NavigationRoute.Home.route)
                },
                onSignUp = {
                    navController.navigate(NavigationRoute.SignUp.route)
                }
            )
        }
        composable(NavigationRoute.SignUp.route){
            SignUpScreen()
        }
        composable(NavigationRoute.Home.route){
            Text(text = "Home")
        }
    }
}