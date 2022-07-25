package ru.alekseysapi.weather_kotlin.view.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.alekseysapi.weather_kotlin.R
import ru.alekseysapi.weather_kotlin.databinding.FragmentWeatherHistoryListBinding
import ru.alekseysapi.weather_kotlin.domain.Weather
import ru.alekseysapi.weather_kotlin.view.details.DetailsFragment
import ru.alekseysapi.weather_kotlin.view.details.OnItemClick
import ru.alekseysapi.weather_kotlin.view.weatherlist.CitiesListAdapter
import ru.alekseysapi.weather_kotlin.viewmodel.weatherhistorylist.WeatherHistoryListFragmentAppState
import ru.alekseysapi.weather_kotlin.viewmodel.weatherhistorylist.WeatherHistoryListViewModel

class WeatherHistoryListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = WeatherHistoryListFragment()
    }

    var isRussian = true

    private var _binding: FragmentWeatherHistoryListBinding? = null
    private val binding: FragmentWeatherHistoryListBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    lateinit var viewModel: WeatherHistoryListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherHistoryListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherHistoryListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.getAllHistory()
    }


    private fun renderData(weatherHistoryListFragmentAppState: WeatherHistoryListFragmentAppState) {
        when (weatherHistoryListFragmentAppState) {
            is WeatherHistoryListFragmentAppState.Error -> {
            }
            WeatherHistoryListFragmentAppState.Loading -> {
            }
            is WeatherHistoryListFragmentAppState.SuccessMulti -> {
                binding.historyFragmentRecyclerView.adapter =
                    WeatherHistoryListAdapter(weatherHistoryListFragmentAppState.weatherList, this)
            }
        }
    }


    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }


}