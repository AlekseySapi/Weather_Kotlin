package ru.alekseysapi.weather_kotlin.view.details

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.alekseysapi.weather_kotlin.BuildConfig
import ru.alekseysapi.weather_kotlin.domain.City
import ru.alekseysapi.weather_kotlin.model.dto.WeatherDTO
import ru.alekseysapi.weather_kotlin.utils.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.google.gson.JsonSyntaxException
import java.io.IOException
import java.net.MalformedURLException


class DetailsServiceIntent : IntentService("") {

    override fun onHandleIntent(intent: Intent?) {

        intent?.let {
            it.getParcelableExtra<City>(BUNDLE_CITY_KEY)?.let {

                try {
                    val uri =
                        URL("https://api.weather.yandex.ru/v2/informers?lat=${it.lat}&lon=${it.lon}")
                    Thread {
                        var myConnection: HttpsURLConnection? = null
                        myConnection = uri.openConnection() as HttpsURLConnection
                        try {
                            myConnection.readTimeout = 5000
                            myConnection.addRequestProperty(
                                YANDEX_API_KEY,
                                BuildConfig.WEATHER_API_KEY
                            )

                            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                            val weatherDTO =
                                Gson().fromJson(getLines(reader), WeatherDTO::class.java)

                            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {
                                putExtra(BUNDLE_WEATHER_DTO_KEY, weatherDTO)
                                action = WAVE
                            })

                        } catch (e: MalformedURLException) {

                        } catch (e: IOException) {

                        } catch (e: JsonSyntaxException) {

                        } finally {
                            myConnection.disconnect()
                        }
                    }.start()
                } catch (e: MalformedURLException) {

                }

            }

        }
    }

}