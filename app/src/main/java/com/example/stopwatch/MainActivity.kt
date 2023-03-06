package com.example.stopwatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stopwatch.databinding.ActivityMainBinding

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
    }

    private fun resetTimer() {

    }

    private fun startStopTimer() {

    }
}