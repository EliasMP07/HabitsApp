package com.devdroid.habitsapp.home.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.devdroid.habitsapp.home.data.alarm.AlarmHandlerAndroid
import com.devdroid.habitsapp.home.data.local.dao.HomeDao
import com.devdroid.habitsapp.home.data.local.database.HomeDatabase
import com.devdroid.habitsapp.home.data.local.typeConverter.HomeTypeConverter
import com.devdroid.habitsapp.home.data.remote.api.HomeApi
import com.devdroid.habitsapp.home.data.repository.HomeLocalSource
import com.devdroid.habitsapp.home.domain.alarm.AlarmHandler
import com.devdroid.habitsapp.home.domain.detail.usecases.DetailUseCases
import com.devdroid.habitsapp.home.domain.detail.usecases.GetHabitById
import com.devdroid.habitsapp.home.domain.detail.usecases.InsertHabit
import com.devdroid.habitsapp.home.domain.home.usecases.CompleteHabitUseCase
import com.devdroid.habitsapp.home.domain.home.usecases.GetHabitsForDate
import com.devdroid.habitsapp.home.domain.home.usecases.HomeUseCases
import com.devdroid.habitsapp.home.domain.home.usecases.SyncHabits
import com.devdroid.habitsapp.home.domain.repository.HomeDataSource
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideOkHttpCliente(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    @Singleton
    @Provides
    fun provideHomeApi(okHttpClient: OkHttpClient): HomeApi {
        return Retrofit.Builder()
            .baseUrl(HomeApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build().create(HomeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHabitDao(@ApplicationContext context: Context, moshi: Moshi): HomeDao {
        return Room.databaseBuilder(
            context,
            HomeDatabase::class.java,
            "habit_db"
        ).addTypeConverter(HomeTypeConverter(moshi)).build().dao
    }

    @Provides
    @Singleton
    fun provideAlarmHandler(@ApplicationContext context: Context): AlarmHandler {
        return AlarmHandlerAndroid(context)
    }

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager{
        return WorkManager.getInstance(context)
    }
    @Provides
    @Singleton
    fun provideHomeDataSource(dao: HomeDao, api: HomeApi, alarmHandler: AlarmHandler, workManager: WorkManager): HomeDataSource = HomeLocalSource(dao, api,alarmHandler, workManager)

    @Provides
    @Singleton
    fun provideHomeUseCases(repository: HomeDataSource): HomeUseCases = HomeUseCases(
        completeHabitUseCase = CompleteHabitUseCase(repository),
        getHabitsForDate = GetHabitsForDate(repository),
        syncHabits = SyncHabits(repository)
    )

    @Provides
    @Singleton
    fun provideDetailUseCase(repository: HomeDataSource): DetailUseCases = DetailUseCases(
        getHabitById = GetHabitById(repository),
        insertHabit = InsertHabit(repository)
    )

    @Provides
    @Singleton
    fun provideMoshi(): Moshi{
        return Moshi.Builder().build()
    }


}