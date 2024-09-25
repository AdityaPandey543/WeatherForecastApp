package com.example.weatherforecastapp.repository

import android.util.Log
import com.example.weatherforecastapp.data.DataOrException
import com.example.weatherforecastapp.model.Weather
import com.example.weatherforecastapp.model.WeatherObject
import com.example.weatherforecastapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception> {
        //In Kotlin, try and catch are used for handling exceptions, which are runtime errors that can occur during program execution. The try block contains the code that might throw an exception, while the catch block is used to handle that exception if it occurs. This helps prevent the app from crashing and allows you to respond to errors gracefully.
        val response = try {
            api.getWeather(query = cityQuery)
        }catch (e:Exception){
            return DataOrException(e=e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)
    }

}