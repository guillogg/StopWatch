package com.example.stopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.stopwatch.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.StartButton.setOnClickListener { startStopTimer() }
        binding.ResetButton.setOnClickListener { resetTimer() }

        serviceIntent = Intent(applicationContext,TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_ACTUALIZADO))
    }
    private fun resetTimer(){
        stopTimer()
        time= 0.0
        binding.Time.text = getTimeStringFromDouble(time)
        binding.fondo.setBackgroundColor(getColor(R.color.white))
    }
    private fun startStopTimer(){
        if (timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA,time)
        startService(serviceIntent)
        binding.StartButton.text = "Stop"
        binding.StartButton.icon = getDrawable(R.drawable.baseline_pause_24)
        binding.fondo.setBackgroundColor(getColor(R.color.green))
        timerStarted = true

    }

    private fun stopTimer() {
        stopService(serviceIntent)
        binding.StartButton.text = "Play"
        binding.StartButton.icon = getDrawable(R.drawable.baseline_play_arrow_24)
        timerStarted = false
        binding.fondo.setBackgroundColor(getColor(R.color.red))
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA,0.0)
            binding.Time.text= getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val horas = resultInt %86400 /3600
        val minutos = resultInt %86400 %3600 /60
        val segundos = resultInt %86400 %3600 % 60
        
        return makeTimeString (horas,minutos,segundos)
    }

    private fun makeTimeString(horas: Int, minutos: Int, segundos: Int): String = String.format("%02d:%02d:%02d",horas,minutos,segundos)


}