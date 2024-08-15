package com.elahee.weatherapp.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elahee.weatherapp.data.DataOrException
import com.elahee.weatherapp.model.WeatherItem
import com.elahee.weatherapp.model.WeatherResponse
import com.elahee.weatherapp.navigation.WeatherScreens
import com.elahee.weatherapp.screens.settings.SettingsViewModel
import com.elahee.weatherapp.utils.formatDate
import com.elahee.weatherapp.utils.formatDecimals
import com.elahee.weatherapp.widgets.HumidityWindPressureRow
import com.elahee.weatherapp.widgets.SunsetSunriseRow
import com.elahee.weatherapp.widgets.ThisWeekRow
import com.elahee.weatherapp.widgets.WeatherAppBar
import com.elahee.weatherapp.widgets.WeatherStateImage

@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel(),
               settingsViewModel: SettingsViewModel= hiltViewModel(),
               city: String?) {
    val curCity:String= if(city.isNullOrEmpty()) "Seattle" else city
    val unitFromDB= settingsViewModel.unitList.collectAsState().value
    var unit by remember{
        mutableStateOf("imperial")
    }

    var isImperial by remember{
        mutableStateOf(false)
    }
    if (!unitFromDB.isNullOrEmpty()){
        unit= unitFromDB[0].unit.split(" ")[0].lowercase()
        isImperial= unit== "imperial"
        val weatherData = produceState<DataOrException<WeatherResponse, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeather(city = curCity,
                units= unit)
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(weatherResponse = weatherData.data!!, navController, isImperial= isImperial)
        }
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(
    weatherResponse: WeatherResponse,
    navController: NavController,
    isImperial: Boolean
) {

    Scaffold(topBar = {
        Surface(shadowElevation = 5.dp) {
            WeatherAppBar(
                title = weatherResponse.city.name + ", ${weatherResponse.city.country}",
                isMainScreen = true,
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                },
                elevation = 5.dp
            ) {
                Log.d("BUttonCLicked", "MainScaffold: ButtonClicked")
            }
        }
    }) {
        MainContent(data = weatherResponse, isImperial= isImperial)

    }

}

@Composable
fun MainContent(data: WeatherResponse, isImperial: Boolean) {
    val imageUrl = "https://openweathermap.org/img/wn/${data!!.list[0].weather[0].icon}.png"
    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "${formatDate(data.list[0].dt)}",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl)
                Text(
                    text = "${formatDecimals(data.list[0].temp.day)}°",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "${data.list[0].weather[0].main}",
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(weather = data.list[0], isImperial= isImperial)
        Divider()
        SunsetSunriseRow(weather = data.list[0])
        Text(
            text = "This Week",
            style = TextStyle(
                color = Color.Black,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(14.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(2.dp),
                contentPadding = PaddingValues(5.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = data.list) { item: WeatherItem ->
                    ThisWeekRow(item) {

                    }
                }
            }
        }


    }

}


