package com.devdroid.habitsapp.onboarding.di

import android.content.Context
import android.content.SharedPreferences
import com.devdroid.habitsapp.onboarding.data.repository.OnboardingSharedPreferenceSource
import com.devdroid.habitsapp.onboarding.domain.repository.OnboardingDataSource
import com.devdroid.habitsapp.onboarding.domain.usecases.CompleteOnboardingUseCase
import com.devdroid.habitsapp.onboarding.domain.usecases.HasSeenOnboardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OnboardingModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("habits_onboarding_preferences", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideOnboardingDataSource(sharedPreferences: SharedPreferences): OnboardingDataSource = OnboardingSharedPreferenceSource(sharedPreferences)

    @Provides
    @Singleton
    fun provideHasSeenOnBoardingUseCase(repository: OnboardingDataSource): HasSeenOnboardingUseCase = HasSeenOnboardingUseCase(repository)

    @Provides
    @Singleton
    fun provideCompleteOnBoardingUseCase(repository: OnboardingDataSource): CompleteOnboardingUseCase = CompleteOnboardingUseCase(repository)
}