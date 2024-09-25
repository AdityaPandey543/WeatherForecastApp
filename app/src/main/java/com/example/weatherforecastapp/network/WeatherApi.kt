package com.example.weatherforecastapp.network

import com.example.weatherforecastapp.model.Weather
import com.example.weatherforecastapp.util.constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast")

    //This function will return all of the info in the site in the form of a weather object
    // that we have already created in the model package
    suspend fun getWeather(
        @Query("q") query : String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = API_KEY )  : Weather
}