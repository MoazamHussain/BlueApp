package com.example.blueapp.presentation.yoga_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blueapp.data.remote.dto.YogaItems

@Composable
fun YogaAnalyticsData(yogaItems: List<YogaItems>) {
    val totalItems = yogaItems.size

    val firstThreeItems = yogaItems.take(3)
    val occurrences = firstThreeItems.flatMap { it.YName?.lowercase()?.toList() ?: emptyList() }
        .groupBy { it }
        .mapValues { it.value.size }
        .toList()
        .sortedByDescending { it.second }
        .take(3)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Total number of items: $totalItems",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Character occurences from top 3 items:",
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(16.dp))
        occurrences.forEach { (char, count) ->
            Text(
                text = "'$char' -> $count",
                style = MaterialTheme.typography.body1
            )
        }
    }
}