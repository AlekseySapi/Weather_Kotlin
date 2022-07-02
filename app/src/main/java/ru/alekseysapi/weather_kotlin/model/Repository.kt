package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.domain.Weather

fun interface RepositoryOne {
    fun getWeather( lat: Double, lon: Double):Weather
}
fun interface RepositoryMany {
    fun getListWeather(location:Location):List<Weather>
}

sealed class Location{
    object Russian:Location()
    object World:Location()
}