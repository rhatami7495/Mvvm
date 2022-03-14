package com.example.weather.data.api

import com.example.weather.data.model.weather.Main
import com.example.weather.data.model.weather.Wind


/**
 * Api result.
 *
 * @param T
 * @property data
 * @property status
 * @constructor Create [ApiResult]
 */
data class ApiResult<T>(
    val data: T?,
    val main: Main?,
    val wind: Wind?,
    val status: String,
) {
}