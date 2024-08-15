package com.elahee.weatherapp.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elahee.weatherapp.model.Favourite
import com.elahee.weatherapp.model.Unit
import com.elahee.weatherapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel@Inject constructor(private val weatherDBRepository: WeatherDBRepository): ViewModel() {
    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList= _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDBRepository.getUnits().distinctUntilChanged()
                .collect{ listOfUnits->
                    if (listOfUnits.isNullOrEmpty()) {
                        Log.d("SettingsViewModel", "Empty favs: ")
                    }else{
                        _unitList.value=listOfUnits
                        Log.d("SettingsViewModel", "${unitList.value} ")

                    }
                }
        }
    }

    fun insertUnit(unit: Unit)= viewModelScope.launch {
        weatherDBRepository.insertUnit(unit)
    }
    fun updateUnit(unit: Unit)= viewModelScope.launch {
        weatherDBRepository.updateUnit(unit)
    }

    fun deleteUnit(unit: Unit)= viewModelScope.launch {
        weatherDBRepository.deleteUnit(unit)
    }
    fun deleteAllUnit()= viewModelScope.launch {
        weatherDBRepository.deleteAllUnits()
    }
}