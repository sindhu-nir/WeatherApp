package com.elahee.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elahee.weatherapp.screens.about.AboutScreen
import com.elahee.weatherapp.screens.favourite.FavouriteScreen
import com.elahee.weatherapp.screens.main.MainScreen
import com.elahee.weatherapp.screens.main.MainViewModel
import com.elahee.weatherapp.screens.search.SearchScreen
import com.elahee.weatherapp.screens.settings.SettingsScreen
import com.elahee.weatherapp.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {

        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable(
            "$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("city").let { city->
                val viewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, viewModel,city=city)
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            //   val viewModel= hiltViewModel<MainViewModel>()
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
        composable(WeatherScreens.FavouriteScreen.name) {
            FavouriteScreen(navController = navController)
        }
    }
}