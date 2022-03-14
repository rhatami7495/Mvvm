package com.example.weather.feature

import androidx.lifecycle.viewModelScope
import com.example.weather.common.BaseViewModel
import com.example.weather.data.model.Alarm
import com.example.weather.data.repository.AlarmRepository
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.instami.publisher.data.model.Weather
import javax.inject.Inject

/**
 * Main view model.
 *
 * @property weatherRepository
 * @constructor Create [MainViewModel]
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val alarmRepository: AlarmRepository
) : BaseViewModel() {

    private val _weather =
        MutableStateFlow<Resource<Weather>>(Resource.Initialize)
    val weather: Flow<Resource<Weather>> = _weather

    /**
     * Weather info.
     *
     * @param lat Lat
     * @param lon Lon
     * @return Weather
     */
    fun weatherInfo(lat: Long, lon: Long) = viewModelScope.launch {
        _weather.emit(Resource.Loading)
        try {
            val response = weatherRepository.getWeather(lat,lon)
            _weather.emit(Resource.Success(Weather(main = response.main!!,wind = response.wind!!)))

        } catch (e: Exception) {
            _weather.emit(Resource.Failure("fail"))
        }
    }

    private val _insertAlarm =
        MutableStateFlow<Resource<Boolean>>(Resource.Initialize)
    val insertAlarm: Flow<Resource<Boolean>> = _insertAlarm

    /**
     * Insert alarm.
     *
     * @param alarm Alarm
     * @return
     */
    fun insertAlarm(alarm:Alarm) = viewModelScope.launch {
        _insertAlarm.emit(Resource.Loading)
        try {
            val response = alarmRepository.insertAlarm(alarm)
            _insertAlarm.emit(Resource.Success(true))

        } catch (e: Exception) {
            _insertAlarm.emit(Resource.Failure("fail"))
        }
    }

    private val _deleteAlarm =
        MutableStateFlow<Resource<Boolean>>(Resource.Initialize)
    val deleteAlarm: Flow<Resource<Boolean>> = _deleteAlarm

    /**
     * Delete alarm.
     *
     * @param alarmId Alarm id
     * @return
     */
    fun deleteAlarm(alarmId:Int) = viewModelScope.launch {
        _deleteAlarm.emit(Resource.Loading)
        try {
            val response = alarmRepository.deleteAlarm(alarmId)
            _deleteAlarm.emit(Resource.Success(true))

        } catch (e: Exception) {
            _deleteAlarm.emit(Resource.Failure("fail"))
        }
    }

    private val _alarmList =
        MutableStateFlow<Resource<Flow<List<Alarm>>>>(Resource.Initialize)
    val alarmList: Flow<Resource<Flow<List<Alarm>>>> = _alarmList

    /**
     * Get alarm list.
     *
     * @return
     */
    fun getAlarmList() = viewModelScope.launch {
        _alarmList.emit(Resource.Loading)
        try {
            val response = alarmRepository.getAlarmList()
            _alarmList.emit(Resource.Success(response))

        } catch (e: Exception) {
            _alarmList.emit(Resource.Failure("fail"))
        }
    }

}