package com.example.weather.data.repository

import com.example.weather.data.db.AlarmDao
import com.example.weather.data.model.Alarm
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Alarm repository.
 *
 * @property alarmDao
 * @constructor Create [AlarmRepository]
 */
@Singleton
class AlarmRepository@Inject constructor(
    private val alarmDao: AlarmDao,
)  {

    /**
     * Insert alarm.
     *
     * @param alarm Alarm
     * @return
     */
    suspend fun insertAlarm(alarm:Alarm) = alarmDao.insertAlarm(alarm)

    /**
     * Delete alarm.
     *
     * @param alarmId Alarm id
     * @return
     */
    suspend fun deleteAlarm(alarmId:Int) = alarmDao.deleteAlarm(alarmId)

    /**
     * Get alarm list.
     *
     * @return alarm list
     */
    suspend fun getAlarmList() = alarmDao.getAlarmList()
}