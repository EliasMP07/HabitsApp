package com.devdroid.habitsapp.onboarding.domain.usecases

import com.devdroid.habitsapp.onboarding.domain.repository.OnboardingDataSource

class HasSeenOnboardingUseCase (
    private val repository : OnboardingDataSource
){
    operator fun invoke(): Boolean = repository.hasSeeOnBoarding()
}