package ru.alekseysapi.weather_kotlin.viewmodel

import ru.alekseysapi.weather_kotlin.domain.Weather

sealed class AppState {
    data class SuccessOne(val weatherData: Weather) : AppState()
    data class SuccessMulti(val weatherList: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}