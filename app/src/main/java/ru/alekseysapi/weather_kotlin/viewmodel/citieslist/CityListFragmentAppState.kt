package ru.alekseysapi.weather_kotlin.viewmodel.citieslist

import ru.alekseysapi.weather_kotlin.model.dto.WeatherDTO

sealed class DetailsFragmentAppState {
    data class Success(val weatherData: WeatherDTO) : DetailsFragmentAppState()
    data class Error(val error: Throwable) : DetailsFragmentAppState()
    object Loading : DetailsFragmentAppState()
}