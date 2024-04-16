package com.devdroid.habitsapp.onboarding.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingPagerInformation(
    @StringRes val title: Int,
    @StringRes val subtitle: Int,
    @DrawableRes val image: Int
)
