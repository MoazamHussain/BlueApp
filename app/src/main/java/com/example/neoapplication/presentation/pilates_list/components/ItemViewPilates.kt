package com.example.neoapplication.presentation.pilates_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.neoapplication.R
import com.example.neoapplication.data.remote.dto.PilatesItems

@Composable
fun ItemViewPilates(pilatesItem: PilatesItems) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lightgreen)),
        modifier = Modifier
            .padding(end = 8.dp)
            .wrapContentWidth().height(120.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = pilatesItem.PImg),
                contentDescription = pilatesItem.PName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = pilatesItem.PId.toString() ?: "",
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = pilatesItem.PName ?: "",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            }
        }
    }
}