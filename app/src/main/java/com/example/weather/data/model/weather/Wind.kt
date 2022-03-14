package com.example.weather.data.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Wind.
 *
 * @property speed
 * @property deg
 * @property gust
 * @constructor Create [Wind]
 */
@Parcelize
data class Wind(
    val speed: Float = 0F,
    val deg: Float = 0F,
    val gust: Float = 0F,
) : Parcelable {
}