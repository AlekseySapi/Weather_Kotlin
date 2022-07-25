package ru.alekseysapi.weather_kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.alekseysapi.weather_kotlin.databinding.ActivityMainBinding
import ru.alekseysapi.weather_kotlin.lesson6.BUNDLE_KEY
import ru.alekseysapi.weather_kotlin.lesson6.MyBroadCastReceiver
import ru.alekseysapi.weather_kotlin.lesson6.MyService
import ru.alekseysapi.weather_kotlin.lesson6.ThreadsFragment
import ru.alekseysapi.weather_kotlin.utils.SP_DB_NAME_IS_RUSSIAN
import ru.alekseysapi.weather_kotlin.utils.SP_KEY_IS_RUSSIAN
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

        val sp = getSharedPreferences(SP_DB_NAME_IS_RUSSIAN,Context.MODE_PRIVATE)
        Log.d("@@@", localClassName)
        val spActivity = getPreferences(Context.MODE_PRIVATE)// аналог getSharedPreferences("MainActivity.class",Context.MODE_PRIVATE)
        val spApp = getDefaultSharedPreferences(this)// аналог getSharedPreferences(getPackageName(),Context.MODE_PRIVATE)


        val isRussian = sp.getBoolean(SP_KEY_IS_RUSSIAN,true)
        val editor = sp.edit()
        editor.putBoolean(SP_KEY_IS_RUSSIAN,isRussian)
        editor.apply()

        sp.edit().apply {
            putBoolean(SP_KEY_IS_RUSSIAN, isRussian)
            apply()
        }

        val rows=  MyApp.getWeatherDatabase().weatherDao().getWeatherAll()

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