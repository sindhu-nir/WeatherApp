package com.elahee.weatherapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.elahee.weatherapp.R
import com.elahee.weatherapp.model.WeatherItem
import com.elahee.weatherapp.utils.formatDate
import com.elahee.weatherapp.utils.formatDateTime
import com.elahee.weatherapp.utils.formatDecimals

@Composable
fun ThisWeekRow(
    weatherItem: WeatherItem,
    onWeatherClick: (WeatherItem) -> Unit
) {
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem!!.weather[0].icon}.png"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onWeatherClick(weatherItem) },
        shape = RoundedCornerShape(
            topStartPercent = 50,
            bottomEndPercent = 50,
            bottomStartPercent = 50
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "${formatDate(weatherItem.dt).split(",")[0]}",
                modifier = Modifier.padding(start = 15.dp),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            WeatherStateImage(
                imageUrl = imageUrl, modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
            )

            Surface(
                modifier = Modifier
                    .padding(10.dp),
                shape = RoundedCornerShape(50.dp),
                color = Color(0xFFFFC400)
            ) {
                Text(
                    text = "${weatherItem.weather[0].description}",
                    modifier = Modifier.padding(4.dp),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Light,
                        color = Color.Blue,
                        fontSize = 17.sp
                    )
                ) {
                    append("${formatDecimals(weatherItem.temp.max)}°")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Light,
                        color = Color.DarkGray,
                        fontSize = 17.sp
                    )
                ) {
                    append("${formatDecimals(weatherItem.temp.min)}°")
                }
            }
            Text(
                text = annotatedString,
                modifier = Modifier.padding(6.dp)
            )
        }

    }


}

@Composable
fun SunsetSunriseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise Icon",
                modifier = Modifier.size(25.dp)
            )
            Text(
                text = "${formatDateTime(weather.sunrise)}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 5.dp)
            )
        }


        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Text(
                text = "${formatDateTime(weather.sunset)}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 5.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset Icon",
                modifier = Modifier.size(25.dp)
            )

        }

    }
}


@Composable
fun WeatherStateImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = "IconImage",
        modifier = modifier.size(80.dp)
    )
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.labelMedium
            )
        }
        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.pressure} psi",
                style = MaterialTheme.typography.labelMedium
            )
        }

        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${formatDecimals(weather.speed)} "+ if (isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.labelMedium
            )
        }

    }
}