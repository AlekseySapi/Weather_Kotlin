package ru.alekseysapi.weather_kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.alekseysapi.weather_kotlin.lesson6.ThreadsFragment
import ru.alekseysapi.weather_kotlin.databinding.ActivityMainBinding
import ru.alekseysapi.weather_kotlin.viewmodel.WeatherListFragment
import ru.alekseysapi.weather_kotlin.lesson6.BUNDLE_KEY
import ru.alekseysapi.weather_kotlin.lesson6.MyBroadCastReceiver
import ru.alekseysapi.weather_kotlin.lesson6.MyService
import ru.alekseysapi.weather_kotlin.view.weatherlist.CitiesListFragment

internal class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.activityRoot)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CitiesListFragment.newInstance()).commit()
        }


        startService(Intent(this,MyService::class.java).apply {
            putExtra(BUNDLE_KEY,"Hello")
        })

        val receiver=MyBroadCastReceiver()
        registerReceiver(receiver, IntentFilter("android.intent.action.AIRPLANE_MODE"))
        registerReceiver(receiver, IntentFilter("myaction"))



        LocalBroadcastManager.getInstance(this).registerReceiver(object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("@@@"," onReceive ${Thread.currentThread()}")
            }
        }, IntentFilter("answer"))

        sendBroadcast(Intent().apply {
            action = "myaction"
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_threads -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, ThreadsFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}