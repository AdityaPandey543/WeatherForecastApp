package com.example.weatherforecastapp.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforecastapp.data.DataOrException
import com.example.weatherforecastapp.model.Weather
import com.example.weatherforecastapp.model.WeatherItem
import com.example.weatherforecastapp.navigation.WeatherScreens
import com.example.weatherforecastapp.util.formatDate
import com.example.weatherforecastapp.util.formatDecimals
import com.example.weatherforecastapp.widgets.HumidityWindPressure
import com.example.weatherforecastapp.widgets.SunsetSunRiseRow
import com.example.weatherforecastapp.widgets.WeatherAppBar
import com.example.weatherforecastapp.widgets.WeatherDetailRow
import com.example.weatherforecastapp.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
){

    val weatherdata = produceState<DataOrException<Weather,Boolean,Exception>>(
        initialValue = DataOrException(loading = true)
    )
    {
        value = mainViewModel.getWeatherData(city = city.toString())
    }.value

    if(weatherdata.loading == true){
        CircularProgressIndicator()
    }
    else if (weatherdata.data!= null){
        MainScaffold(weather = weatherdata.data!! , navController)
    }
    else{
        Text(text = "Error") // Added for the error handling
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                WeatherAppBar(
                    title = "${weather.city.name} , ${weather.city.country}",
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    navController = navController , elevationVal = 5.dp ,
                    onAddActionClicked = {
                        navController.navigate(WeatherScreens.SearchScreen.name)
                    }
                )
                {

                }
            },

        ) {
            MainContent(data = weather, modifier = Modifier.padding(it))
        }
    }
}

@Composable
fun MainContent(data: Weather , modifier : Modifier) {

    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

    Column(modifier = modifier
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = formatDate( weatherItem.dt ),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
            )

        Surface (
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
            ){

            Column (verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                ){

                // Image (from the api)
                WeatherStateImage(imageUrl = imageUrl)

                Text(text = formatDecimals(weatherItem.main.temp)+"°" , style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold
                    )
                
                Text(text = weatherItem.weather[0].main , fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressure(weather = data){}
        // to provide the straight line
        HorizontalDivider()

        SunsetSunRiseRow(weather = data){}
        
        Text(text = "This Week",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
            )
        
       Surface(modifier = Modifier
           .fillMaxHeight()
           .fillMaxWidth(),
           color = Color(0xFFEEF1EF),
       shape = RoundedCornerShape(size = 14.dp)
       ) {
            LazyColumn (modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
                ){
                items(items = data.list){item: WeatherItem ->
                    WeatherDetailRow(weather = item) {
                    }
                }
            }
       }
    }

}

