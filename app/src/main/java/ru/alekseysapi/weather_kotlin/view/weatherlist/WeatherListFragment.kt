package ru.alekseysapi.weather_kotlin.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.alekseysapi.weather_kotlin.databinding.FragmentWeatherListBinding
import ru.alekseysapi.weather_kotlin.viewmodel.AppState

class WeatherListFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    private var _binding: FragmentWeatherListBinding?= null
    private val binding: FragmentWeatherListBinding
        get(){
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    lateinit var viewModel: WeatherListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner,object : Observer<AppState>{
            override fun onChanged(t: AppState) {
                Toast.makeText(requireContext(),"РАБОТАЕТ $t",Toast.LENGTH_LONG).show()
                renderData(t)
            }
        })
        viewModel.sentRequest()
        viewModel.sentRequest()
    }

    private fun renderData(appState: AppState){
        when (appState){
            is AppState.Error -> {/*TODO HW*/ }
            AppState.Loading -> {/*TODO HW*/}
            is AppState.Success -> {
                val result = appState.weatherData
                binding.cityName.text = result.city.name
                binding.temperatureValue.text = result.temperature.toString()
                binding.feelsLikeValue.text = result.feelsLike.toString()
                binding.cityCoordinates.text = "${result.city.lat}/${result.city.lon}"
            }
        }
    }


}