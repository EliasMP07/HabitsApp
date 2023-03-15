package com.devdroid.habitsapp.authentication.data.matcher

import android.util.Patterns
import com.devdroid.habitsapp.authentication.domain.matcher.EmailMatcher

class EmailMatcherAndroid: EmailMatcher{
    override fun isValid(email: String): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}