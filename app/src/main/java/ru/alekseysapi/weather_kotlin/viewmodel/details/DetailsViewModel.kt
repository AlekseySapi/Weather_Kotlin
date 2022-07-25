package ru.alekseysapi.weather_kotlin.viewmodel.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.alekseysapi.weather_kotlin.model.*
import ru.alekseysapi.weather_kotlin.model.dto.WeatherDTO
import ru.alekseysapi.weather_kotlin.model.retrofit.RepositoryDetailsRetrofitImpl
import java.io.IOException
import ru.alekseysapi.weather_kotlin.MyApp
import ru.alekseysapi.weather_kotlin.utils.SP_DB_NAME_IS_RUSSIAN

class DetailsViewModel(private val liveData: MutableLiveData<DetailsFragmentAppState> = MutableLiveData<DetailsFragmentAppState>()) :
    ViewModel() {

    lateinit var repository: RepositoryDetails

    fun getLiveData(): MutableLiveData<DetailsFragmentAppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        val sp = MyApp.getMyApp().getSharedPreferences("erhrth", Context.MODE_PRIVATE)
        repository = when (sp.getInt("rturtu", 2)) {
            1 -> {
                RepositoryDetailsOkHttpImpl()
            }
            2 -> {
                RepositoryDetailsRetrofitImpl()
            }
            3 -> {
                RepositoryDetailsWeatherLoaderImpl()
            }
            else -> {
                RepositoryDetailsLocalImpl()
            }
        }
    }


    fun getWeather(lat: Double, lon: Double) {


        choiceRepository()
        liveData.value = DetailsFragmentAppState.Loading
        repository.getWeather(lat, lon, callback)
    }

    private val callback = object : MyLargeSuperCallback {
        override fun onResponse(weatherDTO: WeatherDTO) {
            /*Handler(Looper.getMainLooper()).post {
            }*/
            liveData.postValue(DetailsFragmentAppState.Success(weatherDTO))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(DetailsFragmentAppState.Error(e))
        }
    }

    private fun isConnection(): Boolean {
        return false
    }

    override fun onCleared() { // TODO HW ***
        super.onCleared()
    }
}