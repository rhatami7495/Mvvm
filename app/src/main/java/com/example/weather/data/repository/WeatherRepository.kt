package com.example.weather.data.repository

import com.example.weather.application.APP_ID_WEATHER
import com.example.weather.data.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val api: Api
) {

    /**
     * Get weather.
     *
     * @param lat Lat
     * @param lon Lon
     * @return
     */
    suspend fun getWeather(
        lat: Long,
        lon: Long

    ) = api.getWeather(
        lat = lat,
        lon = lon,
        appid = APP_ID_WEATHER
    )

}