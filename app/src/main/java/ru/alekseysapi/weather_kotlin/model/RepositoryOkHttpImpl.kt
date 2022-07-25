package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.BuildConfig
import ru.alekseysapi.weather_kotlin.domain.City
import ru.alekseysapi.weather_kotlin.model.dto.WeatherDTO
import ru.alekseysapi.weather_kotlin.utils.YANDEX_API_KEY
import ru.alekseysapi.weather_kotlin.utils.bindDTOWithCity
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryOkHttpImpl:RepositoryWeatherByCity {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {
        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
        val request: Request = builder.build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }
            override fun onResponse(call: Call, response: Response) {
                //if (response.isSuccessful) { }
                if (response.code in 200..299 && response.body != null) {
                    response.body?.let {
                        val responseString = it.string()
                        val weatherDTO =
                            Gson().fromJson((responseString), WeatherDTO::class.java)
                        callback.onResponse(bindDTOWithCity(weatherDTO,city))
                    }
                } else {
                    // TODO HW callback.on??? 403 404
                    callback.onFailure(IOException("403 404"))
                }
            }
        })
    }
}