package ru.alekseysapi.weather_kotlin.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO(
    val fact: Fact
): Parcelable