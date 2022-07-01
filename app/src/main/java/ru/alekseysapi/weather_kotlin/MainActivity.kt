package ru.alekseysapi.weather_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.alekseysapi.weather_kotlin.databinding.ActivityMainBinding

internal class MainActivity: AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.activityRoot)
        binding.btn.text = "Change text"
    }
}