package ru.alekseysapi.weather_kotlin.model

import ru.alekseysapi.weather_kotlin.domain.Weather
import ru.alekseysapi.weather_kotlin.domain.getDefaultCity
import ru.alekseysapi.weather_kotlin.domain.getRussianCities
import ru.alekseysapi.weather_kotlin.domain.getWorldCities
import ru.alekseysapi.weather_kotlin.model.dto.Fact
import ru.alekseysapi.weather_kotlin.model.dto.WeatherDTO

class RepositoryDetailsLocalImpl : RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callback: MyLargeSuperCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getRussianCities())
        val response = list.filter { it.city.lat == lat && it.city.lon == lon }
        callback.onResponse(convertModelToDto(response.first()))
    }

    private fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
        val fact: Fact = weatherDTO.fact
        return (Weather(getDefaultCity(), fact.temp, fact.feelsLike))
    }

    private fun convertModelToDto(weather: Weather): WeatherDTO {
        val fact: Fact = Fact(weather.feelsLike, weather.temperature)
        return WeatherDTO(fact)
    }

}