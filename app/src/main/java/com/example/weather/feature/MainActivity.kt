package com.example.weather.feature

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.format.DateFormat
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akexorcist.snaptimepicker.SnapTimePickerDialog
import com.akexorcist.snaptimepicker.TimeValue
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.helper.Resource
import dagger.hilt.android.AndroidEntryPoint
import net.instami.publisher.data.model.Weather
import java.text.DateFormatSymbols
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var mClockHandler: Handler?=null
    private var mCornometerHandler: Handler?=null
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var progressCornometer = 0
    private var checkStateCornometer = CornometerStatus.STOP
    object CornometerStatus {
        const val START = 0
        const val PAUSE = 1
        const val STOP = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClock()
        observe()
        getweather()
        onclick()
    }

    /**
     * Onclick.
     */
    private fun onclick() {
        binding.cornometerProgress.setOnClickListener{
            when(checkStateCornometer){
                CornometerStatus.START->{
                    checkStateCornometer =  CornometerStatus.PAUSE
                    changedCornometerState(checkStateCornometer)
                }
                CornometerStatus.STOP->{
                    setCornometerTime()
                }
                CornometerStatus.PAUSE->{
                    checkStateCornometer =  CornometerStatus.START
                    mCornometerHandler?.postDelayed(mUpdateCornometerTask, 1000)
                    changedCornometerState(checkStateCornometer)
                }
            }

        }
        binding.cornometerProgress.setOnLongClickListener(OnLongClickListener {
            checkStateCornometer =  CornometerStatus.STOP
            changedCornometerState(checkStateCornometer)
            false
        })

        binding.rlAddAlarm.setOnClickListener {
            //AlarmDialog(context = applicationContext)
        }

    }

    /**
     * Set cornometer time.
     */
    private fun setCornometerTime() {
        val mHour = 0
        val mMinute = 1
        SnapTimePickerDialog.Builder()
            .setTitle(R.string.set_time)
            .setThemeColor(R.color.purple_200)
            .setTitleColor(R.color.white)
            .setPositiveButtonText(R.string.accept)
            .setNegativeButtonText(R.string.reject)
            .setPreselectedTime(TimeValue(mHour, mMinute))
            .build().apply {
                setListener { hour, minute ->
                    val h = if (hour < 10) "0$hour" else "$hour"
                    val m = if (minute < 10) "0$minute" else "$minute"
                    //binding.txtCornometer.text = "$h:$m"
                    checkStateCornometer =  CornometerStatus.START
                    startCornometerTime(h.toInt(),m.toInt())
                }
            }.show(this@MainActivity.supportFragmentManager, "setTime")
    }

    /**
     * Set handler cornometer time.
     */
    private fun setHandlerCornometerTime(){
        progressCornometer=0
        mCornometerHandler = Handler()
        mCornometerHandler?.postDelayed(mUpdateCornometerTask, 1000)
    }

    private val mUpdateCornometerTask: Runnable = object : Runnable {
        override fun run() {
            progressCornometer++
            binding.cornometerProgress.progress = progressCornometer
            mCornometerHandler?.postDelayed(this, 1000)
        }
    }

    /**
     * Start cornometer time.
     *
     * @param h H
     * @param m M
     */
    private fun startCornometerTime(h: Int, m: Int) {
        binding.apply {
            cornometerProgress.max = (h*60+m)*60
        }
        setHandlerCornometerTime()
        changedCornometerState(checkStateCornometer)
    }

    private fun changedCornometerState(check: Int) {
        binding.apply {
            when(check){
                CornometerStatus.START->{
                    txtCornometer.text = "Started"
                }
                CornometerStatus.STOP->{
                    txtCornometer.text = "Stoped"
                    mCornometerHandler?.removeCallbacks(mUpdateCornometerTask)
                }
                CornometerStatus.PAUSE->{
                    txtCornometer.text = "Paused"
                    mCornometerHandler?.removeCallbacks(mUpdateCornometerTask)
                }
            }
        }

    }

    /**
     * Set clock.
     */
    private fun setClock() {
        mClockHandler = Handler()
        mClockHandler?.postDelayed(mUpdateClockTask, 1000)
    }

    /**
     * Observe.
     */
    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.weather.collect {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        hideLoading()
                        setWeatherInfo(it.data)
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        Toast.makeText(this@MainActivity,it.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    /**
     * Set weather info.
     *
     * @param data Data
     */
    private fun setWeatherInfo(data: Weather) {
        binding.apply {
            txtTemperature.text = data.main.temp.toString()
            txtWindSpeed.text = data.wind.speed.toString()
            txtDegreeOfWind.text = data.wind.deg.toString()
            windDirection.rotation = data.wind.deg
        }
    }


    /**
     * Get weather.
     */
    private fun getweather() {
        viewModel.weatherInfo(35.712114.toLong(), 51.383246.toLong())
    }


    /**
     * Show loading.
     */
    private fun showLoading()  {
        binding.apply {
            loadingView.loading.visibility= View.VISIBLE
        }
    }

    /**
     * Hide loading.
     *
     * @return
     */
    private fun hideLoading() {
        binding.apply {
            loadingView.loading.visibility= View.GONE
        }
    }

    private val mUpdateClockTask: Runnable = object : Runnable {
        override fun run() {
            setTime()
            mClockHandler?.postDelayed(this, 1000)
        }
    }

    /**
     * setTime
     */
    @SuppressLint("SetTextI18n")
    private fun setTime() {
        binding.apply {
            val cal: Calendar = Calendar.getInstance()
            val minutes: Int = cal.get(Calendar.MINUTE)
            if (DateFormat.is24HourFormat(this@MainActivity)) {
                val hours: Int = cal.get(Calendar.HOUR_OF_DAY)
                txtClock.text = (if (hours < 10) "0$hours" else hours).toString() + ":" + if (minutes < 10) "0$minutes" else minutes
            } else {
                val hours: Int = cal.get(Calendar.HOUR)
                txtClock.text = hours.toString() + ":" + (if (minutes < 10) "0$minutes" else minutes) + " " + DateFormatSymbols().amPmStrings[cal.get(Calendar.AM_PM)]
            }
        }
    }


}