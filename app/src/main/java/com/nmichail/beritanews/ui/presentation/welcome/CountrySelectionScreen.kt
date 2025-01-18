package com.nmichail.beritanews.ui.presentation.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nmichail.beritanews.R
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CountrySelectionScreen(onCountrySelected: (String) -> Unit) {
    val countries = listOf(
        "us" to "United States",
        "gb" to "Great Britain",
        "fr" to "France",
        "de" to "Germany",
        "in" to "India",
        "au" to "Australia",
        "ru" to "Russia"
    )

    val countryFlags = mapOf(
        "us" to R.drawable.flag_us,
        "gb" to R.drawable.flag_uk,
        "fr" to R.drawable.flag_fr,
        "de" to R.drawable.flag_de,
        "in" to R.drawable.flag_in,
        "au" to R.drawable.flag_au,
        "ru" to R.drawable.flag_ru
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Select Your Country",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 16.dp, start = 10.dp, bottom = 10.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(countries) { (code, name) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCountrySelected(code) }
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val flag = countryFlags[code]
                    if (flag != null) {
                        Image(
                            painter = painterResource(id = flag),
                            contentDescription = "Flag of $name",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(5.dp))
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}