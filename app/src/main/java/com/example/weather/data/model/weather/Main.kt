package com.example.weather.data.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Main.
 *
 * @property temp
 * @constructor Create [Main]
 */
@Parcelize
data class Main(
    val temp: Float = 0F,
) : Parcelable {
}