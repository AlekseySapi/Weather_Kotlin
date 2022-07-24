package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.domain.Weather
import ru.alekseysapi.weather_kotlin.domain.getDefaultCity

class RepositoryDetailsRetrofitImpl:RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather(getDefaultCity())
    }
}