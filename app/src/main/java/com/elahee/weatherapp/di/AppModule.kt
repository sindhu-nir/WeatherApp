package com.elahee.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.elahee.weatherapp.data.WeatherDB
import com.elahee.weatherapp.data.WeatherDao
import com.elahee.weatherapp.network.WeatherApi
import com.elahee.weatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesWeatherDao(weatherDB: WeatherDB): WeatherDao = weatherDB.weatherDao()

    @Singleton
    @Provides
    fun providesAppDB(@ApplicationContext context: Context): WeatherDB = Room.databaseBuilder(
        context = context,
        WeatherDB::class.java,
        name = "weather_db"
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesRetrofitApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}