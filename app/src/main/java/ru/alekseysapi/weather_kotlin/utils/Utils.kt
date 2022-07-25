package ru.alekseysapi.weather_kotlin.utils

import ru.alekseysapi.weather_kotlin.domain.City
import ru.alekseysapi.weather_kotlin.domain.Weather
import ru.alekseysapi.weather_kotlin.model.dto.Fact
import ru.alekseysapi.weather_kotlin.model.dto.WeatherDTO
import java.io.BufferedReader
import java.util.stream.Collectors

fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}

fun bindDTOWithCity(weatherDTO: WeatherDTO,city: City): Weather {
    val fact: Fact = weatherDTO.fact
    return (Weather(city, fact.temp, fact.feelsLike))
}

fun convertModelToDto(weather: Weather): WeatherDTO {
    val fact: Fact = Fact(weather.feelsLike, weather.temperature)
    return WeatherDTO(fact)
}