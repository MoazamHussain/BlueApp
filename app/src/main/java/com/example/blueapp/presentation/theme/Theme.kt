package com.example.blueapp.presentation.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define your colors
private val DarkBlue = Color(0xFF003366)
private val LightBlue = Color(0xFF6699FF)
private val LightGray = Color(0xFFF0F0F0)

// Define the Theme
@Composable
fun TemplateKotlinMVVMArchitectureTheme(
    content: @Composable () -> Unit
) {
    // Define your Material3 colors
    val colorScheme = lightColorScheme(
        primary = DarkBlue,
        secondary = LightBlue,
        background = LightGray,
        surface = Color.White
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Use default Typography or customize
        content = content
    )
}
