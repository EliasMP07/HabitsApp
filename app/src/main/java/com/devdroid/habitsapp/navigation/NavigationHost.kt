package com.devdroid.habitsapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devdroid.habitsapp.authentication.presentation.login.LoginScreen
import com.devdroid.habitsapp.authentication.presentation.signup.SignUpScreen
import com.devdroid.habitsapp.home.presentation.detail.DetailScreen
import com.devdroid.habitsapp.home.presentation.home.HomeScreen
import com.devdroid.habitsapp.onboarding.presentation.OnboardingScreen
import com.devdroid.habitsapp.settings.presentation.SettingsScreen

@Composable
fun  NavigationHost(
    navController: NavHostController,
    startDestination: NavigationRoute,
    logout: () -> Unit
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
            SignUpScreen(
                onLogin = {
                   navController.popBackStack()
                },
                onSignIn = {

                    navController.navigate(NavigationRoute.Home.route){
                        popUpTo(navController.graph.id){
                            inclusive = true
                        }
                    }

                }
            )
        }
        composable(NavigationRoute.Home.route){
            HomeScreen(
                onSetting = {
                    navController.navigate(NavigationRoute.Settings.route)
                },
                onNewHabit = {
                    navController.navigate(NavigationRoute.Detail.route)
                },
                onEditHabit = {
                    navController.navigate(NavigationRoute.Detail.route + "?habitId=$it")
                }
            )
        }
        composable(NavigationRoute.Detail.route + "?habitId={habitId}", arguments = listOf(
            navArgument("habitId"){
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )){
            DetailScreen(onBack = { navController.navigateUp() }, onSave = { navController.navigateUp() })
        }

        composable(NavigationRoute.Settings.route) {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    logout()
                    navController.navigate(NavigationRoute.Login.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}