package com.example.weatherforecastapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.model.Weather
import com.example.weatherforecastapp.model.WeatherItem
import com.example.weatherforecastapp.util.formatDate
import com.example.weatherforecastapp.util.formatDateTime
import com.example.weatherforecastapp.util.formatDecimals

@Composable
fun WeatherDetailRow(weather: WeatherItem, content: () -> Unit) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    Surface(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row (modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = formatDate(weather.dt).split(",")[0]+" , ${formatDateTime(weather.dt)}",
                modifier = Modifier.padding(start = 5.dp)
            )

            WeatherStateImage(imageUrl = imageUrl)

            Surface (modifier = Modifier.
            padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ){
                Text(text = weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold
                )
                ){
                    append(formatDecimals(weather.main.temp_max) +"°")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray,
                )
                ){
                    append(formatDecimals(weather.main.temp_min) +"°")
                }
            }
            )
        }
    }
}

@Composable
fun SunsetSunRiseRow(weather: Weather, content: () -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row {
            Image(painter = painterResource(id = R.drawable.sunrise), contentDescription = "Sunrise"
                ,modifier = Modifier.size(30.dp)
            )
            Text(text = formatDateTime(weather.city.sunrise),
                style = MaterialTheme.typography.labelLarge
            )

        }
        // Spacer(modifier = Modifier.width(210.dp))
        Row {
            Image(painter = painterResource(id = R.drawable.sunset), contentDescription = "Sunset"
                ,modifier = Modifier.size(30.dp)
            )
            Text(text = formatDateTime(weather.city.sunset),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun HumidityWindPressure(weather: Weather, content: () -> Unit) {
    Row (
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription ="humidity icon",
                modifier = Modifier.size(20.dp)
            )

            Text(text = "${weather.list[0].main.humidity}"+"%",
                style = MaterialTheme.typography.labelLarge
            )
        }

        Row {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription ="pressure icon",
                modifier = Modifier.size(20.dp)
            )

            Text(text = "${weather.list[0].main.pressure}"+" psi",
                style = MaterialTheme.typography.labelLarge
            )
        }

        Row {
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription ="wind icon",
                modifier = Modifier.size(20.dp)
            )

            Text(text = "${weather.list[0].wind.speed}"+" mph",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberImagePainter( imageUrl), contentDescription = "icon image"
        , modifier = Modifier.size(80.dp)
    )
}
