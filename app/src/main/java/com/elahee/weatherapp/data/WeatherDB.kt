package com.elahee.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elahee.weatherapp.model.Favourite
import com.elahee.weatherapp.model.Unit

@Database(entities = [Favourite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDB: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}