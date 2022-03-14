package com.example.weather.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather.data.model.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(user: Alarm)

    @Query("DELETE FROM tbl_alarm WHERE id=:id")
    fun deleteAlarm(id: Int)

    @Query("SELECT * FROM tbl_alarm")
    fun getAlarmList(): Flow<List<Alarm>>

}