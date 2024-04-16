package com.devdroid.habitsapp.onboarding.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.devdroid.habitsapp.R
import com.devdroid.habitsapp.onboarding.presentation.components.OnboardingPager

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onFinish :() ->Unit
) {
    LaunchedEffect(key1 = viewModel.hasSeenOnboarding) {
       if (viewModel.hasSeenOnboarding) {
           onFinish()
       }
    }
    val pages = listOf(
        OnBoardingPagerInformation(
            title = R.string.titleOnboarding1,
            subtitle = R.string.subtitleOnboarding1,
            image = R.drawable.onboarding1
        ),
        OnBoardingPagerInformation(
            title = R.string.titleOnboarding2,
            subtitle = R.string.subtitleOnboarding2,
            image = R.drawable.onboarding2
        ),
        OnBoardingPagerInformation(
            title = R.string.titleOnboarding3,
            subtitle = R.string.subtitleOnboarding3,
            image = R.drawable.onboarding3
        ),
        OnBoardingPagerInformation(
            title = R.string.titleOnboarding4,
            subtitle = R.string.subtitleOnboarding4,
            image = R.drawable.onboarding4
        )
    )
    OnboardingPager(
        pages = pages ,
        onFinish = {
            viewModel.completeOnboarding()
        })
}