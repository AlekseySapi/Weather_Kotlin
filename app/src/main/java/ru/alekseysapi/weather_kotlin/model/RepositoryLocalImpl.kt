package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.domain.City
import ru.alekseysapi.weather_kotlin.domain.getRussianCities
import ru.alekseysapi.weather_kotlin.domain.getWorldCities

class RepositoryLocalImpl:RepositoryWeatherByCity {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getRussianCities())
        val response = list.filter { it.city.lat==city.lat&&it.city.lon==city.lon  }
        callback.onResponse((response.first()))
    }
}