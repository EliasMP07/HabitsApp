package com.devdroid.habitsapp.authentication.di

import com.devdroid.habitsapp.authentication.data.matcher.EmailMatcherAndroid
import com.devdroid.habitsapp.authentication.data.repository.AuthenticationFirebaseSource
import com.devdroid.habitsapp.authentication.domain.matcher.EmailMatcher
import com.devdroid.habitsapp.authentication.domain.repository.AuthenticationDataSource
import com.devdroid.habitsapp.authentication.domain.usecases.LoginUseCases
import com.devdroid.habitsapp.authentication.domain.usecases.LoginWithEmailUseCase
import com.devdroid.habitsapp.authentication.domain.usecases.ValidateEmailUseCase
import com.devdroid.habitsapp.authentication.domain.usecases.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    @Provides
    @Singleton
    fun provideAuthenticationFirebaseSource(): AuthenticationDataSource = AuthenticationFirebaseSource()

    @Provides
    @Singleton
    fun provideEmailMatcherAndroid (): EmailMatcher = EmailMatcherAndroid()

    @Provides
    @Singleton
    fun provideLoginUseCases(repository: AuthenticationDataSource, emailMatcher: EmailMatcher): LoginUseCases = LoginUseCases(
        loginWithEmailUseCase = LoginWithEmailUseCase(repository),
        validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
        validatePasswordUseCase = ValidatePasswordUseCase()
    )
}