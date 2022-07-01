package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.domain.Weather

class RepositoryLocalImpl:Repository {
    override fun getListWeather(): List<Weather> {
        return listOf(Weather())
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}