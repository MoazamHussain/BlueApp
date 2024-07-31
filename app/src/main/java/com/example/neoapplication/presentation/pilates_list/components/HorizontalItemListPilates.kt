package com.example.neoapplication.presentation.pilates_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.neoapplication.data.remote.dto.PilatesItems

@Composable
fun HorizontalItemListPilates(pilatesItems: List<PilatesItems>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(pilatesItems.size) { item ->
            ItemViewPilates(pilatesItem = pilatesItems[item])
        }
    }
}