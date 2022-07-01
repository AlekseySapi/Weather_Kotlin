package ru.alekseysapi.weather_kotlin.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.alekseysapi.weather_kotlin.view.AppState
import java.lang.Thread.sleep

class WeatherListViewModel(val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()) :
    ViewModel() {

    fun sentRequest() {
        liveData.value = AppState.Loading
        Thread{
            sleep(2000L)
            liveData.postValue(AppState.Success(Any()))
        }.start()
    }
}