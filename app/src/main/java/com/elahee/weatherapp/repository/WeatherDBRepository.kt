package com.elahee.weatherapp.repository

import com.elahee.weatherapp.data.WeatherDao
import com.elahee.weatherapp.model.Favourite
import com.elahee.weatherapp.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavourites(): Flow<List<Favourite>> = weatherDao.getFavourites()
    suspend fun insertFavourites(favourite: Favourite)= weatherDao.insertFavourite(favourite)
    suspend fun updateFavourites(favourite: Favourite)= weatherDao.updateFavourite(favourite)
    suspend fun deleteAllFav()= weatherDao.deleteFavourites()
    suspend fun deleteFavourite(favourite: Favourite)=weatherDao.deleteFavourites(favourite)
    suspend fun getFavById(city:String):Favourite= weatherDao.getFavById(city)

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit)= weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit)= weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits()= weatherDao.deleteAllUnits()
    suspend fun deleteUnit(unit: Unit)= weatherDao.deleteUnit(unit)




}