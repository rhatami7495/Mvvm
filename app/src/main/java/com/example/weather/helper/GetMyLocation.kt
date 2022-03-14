package com.example.weather.helper

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder

class GetMyLocation(private val mContext: Context) : Service(), LocationListener {

    internal var checkGPS = false

    internal var checkNetwork = false

    internal var canGetLocation = false

    internal var loc: Location? = null
    internal var latitude: Double = 0.toDouble()
    internal var longitude: Double = 0.toDouble()
    protected var locationManager: LocationManager? = null

    private// getting GPS status
    // getting network status
    // Toast.makeText(mContext, "No Service Provider Available", Toast.LENGTH_SHORT).show();
    // First get location from Network Provider
    //   Toast.makeText(mContext, "Network", Toast.LENGTH_SHORT).show();
    //  Log.d("Network", "Network");
    // if GPS Enabled get lat/long using GPS Services
    // Toast.makeText(mContext, "GPS", Toast.LENGTH_SHORT).show();
    // Log.d("GPS Enabled", "GPS Enabled");
    val location: Location?
        get() {

            try {
                locationManager = mContext
                    .getSystemService(Context.LOCATION_SERVICE) as LocationManager
                checkGPS = locationManager!!
                    .isProviderEnabled(LocationManager.GPS_PROVIDER)
                checkNetwork = locationManager!!
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (!checkGPS && !checkNetwork) {
                } else {
                    this.canGetLocation = true
                    if (checkNetwork) {

                        try {

                            locationManager!!.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)
                            if (locationManager != null) {
                                loc = locationManager!!
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                            }

                            if (loc != null) {
                                latitude = loc!!.latitude
                                longitude = loc!!.longitude
                            }
                        } catch (e: SecurityException) {

                        }

                    }
                }
                if (checkGPS) {
                    if (loc == null) {
                        try {
                            locationManager!!.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)
                            if (locationManager != null) {
                                loc = locationManager!!
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                if (loc != null) {
                                    latitude = loc!!.latitude
                                    longitude = loc!!.longitude
                                }
                            }
                        } catch (e: SecurityException) {

                        }

                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return loc
        }

    init {
        location
    }

    fun getLongitude(): Double {
        if (loc != null) {
            longitude = loc!!.longitude
        }
        return longitude
    }

    fun getLatitude(): Double {
        if (loc != null) {
            latitude = loc!!.latitude
        }
        return latitude
    }

    fun canGetLocation(): Boolean {
        return this.canGetLocation
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {

    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

    }

    override fun onProviderEnabled(s: String) {

    }

    override fun onProviderDisabled(s: String) {

    }

    companion object {

        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10

        private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong()
    }

}
