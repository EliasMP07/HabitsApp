package com.devdroid.habitsapp.authentication.di

import android.content.Context
import com.devdroid.habitsapp.authentication.data.matcher.EmailMatcherAndroid
import com.devdroid.habitsapp.authentication.data.repository.AuthenticationFirebaseSource
import com.devdroid.habitsapp.authentication.domain.matcher.EmailMatcher
import com.devdroid.habitsapp.authentication.domain.repository.AuthenticationDataSource
import com.devdroid.habitsapp.authentication.domain.usecases.GetUserIdUseCase
import com.devdroid.habitsapp.authentication.domain.usecases.LoginUseCases
import com.devdroid.habitsapp.authentication.domain.usecases.LoginWithEmailUseCase
import com.devdroid.habitsapp.authentication.domain.usecases.Logout
import com.devdroid.habitsapp.authentication.domain.usecases.SignUpUseCases
import com.devdroid.habitsapp.authentication.domain.usecases.SignUpWithEmailUseCase
import com.devdroid.habitsapp.authentication.domain.usecases.ValidateEmailUseCase
import com.devdroid.habitsapp.authentication.domain.usecases.ValidatePasswordUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
    @Provides
    @Singleton
    fun provideAuthenticationFirebaseSource(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth
    ): AuthenticationDataSource = AuthenticationFirebaseSource(firebaseAuth, context)

    @Provides
    @Singleton
    fun provideEmailMatcherAndroid (): EmailMatcher = EmailMatcherAndroid()


    @Provides
    @Singleton
    fun provideGetUserIdUseCase(repository: AuthenticationDataSource) : GetUserIdUseCase = GetUserIdUseCase(repository)
    @Provides
    @Singleton
    fun provideLoginUseCases(repository: AuthenticationDataSource, emailMatcher: EmailMatcher): LoginUseCases = LoginUseCases(
        loginWithEmailUseCase = LoginWithEmailUseCase(repository),
        validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
        validatePasswordUseCase = ValidatePasswordUseCase()
    )
    @Provides
    @Singleton
    fun provideSignUpUseCases(repository: AuthenticationDataSource, emailMatcher: EmailMatcher): SignUpUseCases = SignUpUseCases(
        signUpWithEmailUseCase = SignUpWithEmailUseCase(repository),
        validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
        validatePasswordUseCase = ValidatePasswordUseCase()
    )

    @Singleton
    @Provides
    fun provideLogout(repository: AuthenticationDataSource): Logout{
        return Logout(repository)
    }
}