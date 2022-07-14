package ru.alekseysapi.weather_kotlin.lesson6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadCastReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("@@@"," MyBroadCastReceiver ${intent!!.action}")
    }
}