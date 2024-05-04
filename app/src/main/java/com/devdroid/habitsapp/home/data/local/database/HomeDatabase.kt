package com.devdroid.habitsapp.home.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.devdroid.habitsapp.home.data.local.dao.HomeDao
import com.devdroid.habitsapp.home.data.local.entity.HabitEntity
import com.devdroid.habitsapp.home.data.local.entity.HabitSyncEntity
import com.devdroid.habitsapp.home.data.local.typeConverter.HomeTypeConverter

@Database(entities = [HabitEntity::class, HabitSyncEntity::class], version = 2, exportSchema = false)
@TypeConverters(
    HomeTypeConverter::class
)
abstract class HomeDatabase(): RoomDatabase() {
    abstract val dao: HomeDao
}