package com.elahee.weatherapp.repository

import com.elahee.weatherapp.data.DataOrException
import com.elahee.weatherapp.model.WeatherResponse
import com.elahee.weatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getWeather(cityQuery: String, units: String):DataOrException<WeatherResponse,Boolean,Exception>{
        val response= try {
            weatherApi.getWeather(cityQuery, units = units)
        }catch (e:Exception){
            return DataOrException(e=e)
        }
        return DataOrException(data = response)
    }
}