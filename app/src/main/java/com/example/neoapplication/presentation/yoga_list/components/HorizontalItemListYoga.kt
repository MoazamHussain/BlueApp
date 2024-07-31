package com.example.neoapplication.presentation.yoga_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.neoapplication.data.remote.dto.YogaItems

@Composable
fun HorizontalItemListYoga(yogaItems: List<YogaItems>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        items(yogaItems.size) { item ->
            ItemViewYoga(yogaItem = yogaItems[item])
        }
    }
}