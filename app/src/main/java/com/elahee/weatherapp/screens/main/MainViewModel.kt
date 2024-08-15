package com.elahee.weatherapp.screens.main

import androidx.lifecycle.ViewModel
import com.elahee.weatherapp.data.DataOrException
import com.elahee.weatherapp.model.WeatherResponse
import com.elahee.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    suspend fun getWeather(city: String, units: String):
            DataOrException<WeatherResponse, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units= units)

    }
}