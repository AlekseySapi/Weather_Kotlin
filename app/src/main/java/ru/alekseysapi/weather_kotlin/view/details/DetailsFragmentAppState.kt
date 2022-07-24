package ru.alekseysapi.weather_kotlin.view.details

import ru.alekseysapi.weather_kotlin.domain.Weather

sealed class DetailsFragmentAppState {
    data class Success(val weatherData: Weather) : DetailsFragmentAppState()
    data class Error(val error: Throwable) : DetailsFragmentAppState()
    object Loading : DetailsFragmentAppState()
}