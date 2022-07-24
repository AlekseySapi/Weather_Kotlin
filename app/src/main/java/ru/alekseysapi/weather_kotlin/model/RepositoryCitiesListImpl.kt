package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.domain.Weather
import ru.alekseysapi.weather_kotlin.domain.getRussianCities
import ru.alekseysapi.weather_kotlin.domain.getWorldCities

class RepositoryCitiesListImpl : RepositoryCitiesList {
    override fun getListCities(location: Location): List<Weather> {
        return when (location) {
            Location.Russian -> {
                getRussianCities()
            }
            Location.World -> {
                getWorldCities()
            }
        }
    }
}