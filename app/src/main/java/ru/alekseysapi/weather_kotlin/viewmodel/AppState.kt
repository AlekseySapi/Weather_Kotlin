package ru.alekseysapi.weather_kotlin.view


sealed class AppState {
    data class Success(val weatherData: Any) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

class  AppStateSecond:AppState(){

}