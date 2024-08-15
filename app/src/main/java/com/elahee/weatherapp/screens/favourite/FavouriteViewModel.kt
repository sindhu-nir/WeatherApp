package com.elahee.weatherapp.screens.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elahee.weatherapp.model.Favourite
import com.elahee.weatherapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val weatherDBRepository: WeatherDBRepository): ViewModel() {
    private val _favList = MutableStateFlow<List<Favourite>>(emptyList())
    val favList= _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDBRepository.getFavourites().distinctUntilChanged()
                .collect{ listOfFav->
                    if (listOfFav.isNullOrEmpty()) {
                        Log.d("FavouriteViewModel", "Empty favs: ")
                    }else{
                        _favList.value=listOfFav
                        Log.d("FavouriteViewModel", "${favList.value} ")

                    }
                }
        }
    }

    fun insertFavourite(favourite: Favourite)= viewModelScope.launch {
        weatherDBRepository.insertFavourites(favourite)
    }
    fun updateFavourite(favourite: Favourite)= viewModelScope.launch {
        weatherDBRepository.insertFavourites(favourite)
    }

    fun deleteFavourite(favourite: Favourite)= viewModelScope.launch {
        weatherDBRepository.deleteFavourite(favourite)
    }
}