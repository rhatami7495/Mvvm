package com.example.weather.data.api

import net.instami.publisher.data.model.Weather
import retrofit2.http.*


/**
 * Api.
 */
interface Api {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Long,
        @Query("lon") lon: Long,
        @Query("appid") appid: String
    ): ApiResult<Weather>

}