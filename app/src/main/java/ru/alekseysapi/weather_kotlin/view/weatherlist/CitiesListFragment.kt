package ru.alekseysapi.weather_kotlin.view.weatherlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.alekseysapi.weather_kotlin.R
import ru.alekseysapi.weather_kotlin.databinding.FragmentCitiesListBinding
import ru.alekseysapi.weather_kotlin.domain.Weather
import ru.alekseysapi.weather_kotlin.view.details.DetailsFragment
import ru.alekseysapi.weather_kotlin.view.details.OnItemClick
import ru.alekseysapi.weather_kotlin.viewmodel.citieslist.CitiesListViewModel
import ru.alekseysapi.weather_kotlin.viewmodel.citieslist.CityListFragmentAppState
import ru.alekseysapi.weather_kotlin.utils.SP_DB_NAME_IS_RUSSIAN
import ru.alekseysapi.weather_kotlin.utils.SP_KEY_IS_RUSSIAN

class CitiesListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = CitiesListFragment()
    }

    var isRussian = true

    private var _binding: FragmentCitiesListBinding? = null
    private val binding: FragmentCitiesListBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    lateinit var viewModel: CitiesListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CitiesListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }

        binding.weatherListFragmentFAB.setOnClickListener {
            isRussian = !isRussian
            if (isRussian) {
                viewModel.getWeatherListForRussia()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_russia)
            } else {
                viewModel.getWeatherListForWorld()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
            }
            val sp = requireActivity().getSharedPreferences(SP_DB_NAME_IS_RUSSIAN,Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putBoolean(SP_KEY_IS_RUSSIAN,isRussian)
            editor.commit()
            editor.apply()
        }
        viewModel.getWeatherListForRussia()
    }


    private fun renderData(cityListFragmentAppState: CityListFragmentAppState) {
        when (cityListFragmentAppState) {
            is CityListFragmentAppState.Error -> {
                binding.showResult()
            }
            CityListFragmentAppState.Loading -> {
                binding.loading()
            }
            is CityListFragmentAppState.SuccessOne -> {
                binding.showResult()
                val result = cityListFragmentAppState.weatherData
            }
            is CityListFragmentAppState.SuccessMulti -> {
                binding.showResult()
                binding.mainFragmentRecyclerView.adapter =
                    CitiesListAdapter(cityListFragmentAppState.weatherList, this)
            }
        }
    }

    fun FragmentCitiesListBinding.loading() {
        this.mainFragmentLoadingLayout.visibility = View.VISIBLE
        this.weatherListFragmentFAB.visibility = View.GONE
    }

    fun FragmentCitiesListBinding.showResult() {
        this.mainFragmentLoadingLayout.visibility = View.GONE
        this.weatherListFragmentFAB.visibility = View.VISIBLE
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }


}