package com.devdroid.habitsapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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

            }
        }
    }
}