package ru.alekseysapi.weather_kotlin.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.alekseysapi.weather_kotlin.databinding.FragmentDetailsBinding
import ru.alekseysapi.weather_kotlin.domain.Weather
import ru.alekseysapi.weather_kotlin.model.dto.WeatherDTO
import ru.alekseysapi.weather_kotlin.utils.WeatherLoader
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.alekseysapi.weather_kotlin.utils.BUNDLE_CITY_KEY
import ru.alekseysapi.weather_kotlin.utils.BUNDLE_WEATHER_DTO_KEY
import ru.alekseysapi.weather_kotlin.utils.WAVE


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            Log.d("@@@", "onReceive ${binding.root}")
            intent?.let {
                it.getParcelableExtra<WeatherDTO>(BUNDLE_WEATHER_DTO_KEY)
                    ?.let { watherDTO ->
                        bindWeatherLocalWithWeatherDTO(weatherLocal, watherDTO)
                    }
            }
        }
    }

    lateinit var weatherLocal: Weather

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }


    // TODO 5 HW  создать DetailsListViewModel + RepositoryRemoteImpl
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val weather = arguments?.let { arg ->
            arg.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)
        }

        weather?.let { weatherLocal ->

            this.weatherLocal = weatherLocal
            WeatherLoader.requestFirstVariant(
                weatherLocal.city.lat,
                weatherLocal.city.lon,
                object : OnResponse {
                    override fun onResponse(weather: WeatherDTO) {

                    }
                }
            )

            LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                receiver,
                IntentFilter(WAVE)
            )

            requireActivity().startService(
                Intent(
                    requireContext(),
                    DetailsServiceIntent::class.java
                ).apply {
                    putExtra(BUNDLE_CITY_KEY, weatherLocal.city)
                })

        }


    }

    private fun bindWeatherLocalWithWeatherDTO(
        weatherLocal: Weather,
        weatherDTO: WeatherDTO
    ) {
        renderData(weatherLocal.apply {
            weatherLocal.feelsLike = weatherDTO.fact.feelsLike
            weatherLocal.temperature = weatherDTO.fact.temp
        })
    }

    // FIXME диссонанс this - как бы приемник?
    private fun renderData(weather: Weather) {

        with(binding) {
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"
        }
    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "sgrrdfge"
        fun newInstance(weather: Weather): DetailsFragment {
            val fr = DetailsFragment()

            fr.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }
            fr.arguments = Bundle().also {
                it.putParcelable(BUNDLE_WEATHER_EXTRA, weather)
                it.putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }

            return fr
        }
    }

}