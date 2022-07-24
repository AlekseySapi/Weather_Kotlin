package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.BuildConfig
import ru.alekseysapi.weather_kotlin.model.dto.WeatherDTO
import ru.alekseysapi.weather_kotlin.utils.YANDEX_API_KEY
import ru.alekseysapi.weather_kotlin.utils.getLines
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RepositoryDetailsWeatherLoaderImpl: RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callback: MyLargeSuperCallback) {
        Thread {
            val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
            var myConnection: HttpsURLConnection? = null
            myConnection = uri.openConnection() as HttpsURLConnection
            try {
                myConnection.readTimeout = 5000
                myConnection.addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)

                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                callback.onResponse(weatherDTO)
            }catch (e:IOException){
                callback.onFailure(e)
            }finally {
                myConnection.disconnect()
            }
        }.start()
    }

}