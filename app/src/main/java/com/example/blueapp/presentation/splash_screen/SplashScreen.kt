package com.example.blueapp.presentation.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blueapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Use a Box or other layout to center your splash content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Blue App",
            style = TextStyle(fontSize = 24.sp, color = colorResource(id = R.color.blue), letterSpacing = 2.sp),
            modifier = Modifier.padding(16.dp)
        )
    }

    // Use LaunchedEffect to handle the timeout and navigate to the main screen
    LaunchedEffect(Unit) {
        delay(2000) // Adjust the delay as needed
        onTimeout() // Trigger the navigation or state change to show the main content
    }
}
