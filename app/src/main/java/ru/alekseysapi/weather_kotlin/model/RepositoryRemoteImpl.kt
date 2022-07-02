package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.domain.Weather
import ru.alekseysapi.weather_kotlin.domain.getWorldCities
import ru.alekseysapi.weather_kotlin.domain.getRussianCities

import ru.alekseysapi.weather_kotlin.viewmodel.AppState

class RepositoryRemoteImpl:RepositoryOne {

    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            Thread.sleep(300L)
        }.start()
        return Weather()
    }
}