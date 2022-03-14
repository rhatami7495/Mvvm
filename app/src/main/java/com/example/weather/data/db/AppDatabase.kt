package com.example.weather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather.data.model.Alarm

@Database(
    entities = [
        Alarm::class
    ],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}