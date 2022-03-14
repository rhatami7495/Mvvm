package net.instami.publisher.data.model

import android.os.Parcelable
import com.example.weather.data.model.weather.Main
import com.example.weather.data.model.weather.Wind
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * Weather.
 *
 * @property main
 * @property wind
 * @constructor Create [Weather]
 */
@Parcelize
data class Weather(
    val main: Main,
    val wind: Wind
) : Parcelable {
}
