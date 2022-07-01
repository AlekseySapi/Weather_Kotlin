package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.domain.Weather

interface Repository {
    fun getListWeather():List<Weather>
    fun getWeather( lat: Double, lon: Double):Weather
}