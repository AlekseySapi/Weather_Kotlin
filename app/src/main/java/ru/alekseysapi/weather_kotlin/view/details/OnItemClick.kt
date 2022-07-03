package ru.alekseysapi.weather_kotlin.view.details

import ru.alekseysapi.weather_kotlin.domain.Weather

fun interface OnItemClick {
    fun onItemClick(weather: Weather)
}