package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.domain.Weather

class RepositoryRemoteImpl:RepositoryOne {

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}