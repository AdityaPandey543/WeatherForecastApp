package com.example.weatherforecastapp.screen.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapp.data.DataOrException
import com.example.weatherforecastapp.model.Weather
import com.example.weatherforecastapp.model.WeatherObject
import com.example.weatherforecastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//We are injecting WeatherRepository into the MainViewModel using Hilt,
// which is a dependency injection (DI) framework.
// Here's why we inject it:

@HiltViewModel
 class MainViewModel @Inject constructor(private val repository: WeatherRepository)
    :ViewModel()
{
 suspend fun getWeatherData ( city : String): DataOrException<Weather , Boolean , Exception>{
     return repository.getWeather(cityQuery = city)
 }


}