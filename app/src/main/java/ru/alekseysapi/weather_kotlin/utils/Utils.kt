package ru.alekseysapi.weather_kotlin.utils

import java.io.BufferedReader
import java.util.stream.Collectors

fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}