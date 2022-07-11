package ru.alekseysapi.weather_kotlin.view.details

import ru.alekseysapi.domain.Weather
import ru.alekseysapi.model.dto.WeatherDTO

fun interface OnResponse {
    fun onResponse(weather: WeatherDTO)
}