package com.devdroid.habitsapp.onboarding.data.repository

import android.content.SharedPreferences
import com.devdroid.habitsapp.onboarding.domain.repository.OnboardingDataSource
import javax.inject.Inject

class OnboardingSharedPreferenceSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): OnboardingDataSource {

    companion object{
        private const val HAS_SEEN_ONBOARDING = "has_seen_onboarding"
    }
    override fun hasSeeOnBoarding(): Boolean {
        return sharedPreferences.getBoolean(HAS_SEEN_ONBOARDING, false)
    }

    override fun completeOnBoarding() {
        sharedPreferences.edit().putBoolean(HAS_SEEN_ONBOARDING, true).apply()
    }



}