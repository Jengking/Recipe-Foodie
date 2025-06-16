package com.fortitude.recipefoodie.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import coil3.util.DebugLogger
import com.fortitude.recipefoodie.models.MealItem

@Composable
fun ItemContainer(item: MealItem, onItemClicked: (MealItem) -> Unit = {}) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .logger(DebugLogger())
        .build()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .clickable {
                onItemClicked(item)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(item.strMealThumb + "/small")
                    .crossfade(true)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.stat_notify_error)
                    .build(),
                contentDescription = "Image for ${item.strMeal}",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp),
                imageLoader = imageLoader
            )

            Text(
                text = item.strMeal,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
fun NoImgItemContainer(item: MealItem, onItemClicked: (MealItem) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .clickable {
                onItemClicked(item)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.strMeal,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                fontWeight = FontWeight.Medium
            )
        }
    }
}






