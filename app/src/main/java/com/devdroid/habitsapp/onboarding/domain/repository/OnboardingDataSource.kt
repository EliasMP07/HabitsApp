package com.devdroid.habitsapp.onboarding.domain.repository

interface OnboardingDataSource {
    fun hasSeeOnBoarding () : Boolean
    fun completeOnBoarding()
}