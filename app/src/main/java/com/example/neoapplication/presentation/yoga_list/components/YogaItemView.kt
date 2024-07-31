package com.example.neoapplication.presentation.yoga_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.neoapplication.R
import com.example.neoapplication.data.remote.dto.YogaItems

@Composable
fun YogaItemView(yogaItem: YogaItems) {
    // Display the card view with image and text
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lightgreen)),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image section
            AsyncImage(
                model = yogaItem.YImg,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Text section
            Column {
                Text(
                    text = yogaItem.YId.toString() ?: "No Name",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black
                )
                // Subtitle
                Text(
                    text = yogaItem.YName ?:"Subtitle here", // Replace with actual subtitle if available
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            }
        }
    }
}