package com.devdroid.habitsapp.onboarding.domain.usecases

import com.devdroid.habitsapp.onboarding.domain.repository.OnboardingDataSource

class CompleteOnboardingUseCase(
    private val repository: OnboardingDataSource
) {
    operator fun invoke() = repository.completeOnBoarding()
}