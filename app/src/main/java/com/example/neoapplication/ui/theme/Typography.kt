package com.example.neoapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.neoapplication.R

// Define your fonts
val rubikFontFamily = FontFamily(
    Font(R.font.rubik_regular),
    Font(R.font.rubik_semibold, FontWeight.Medium),
)

// Define your Typography
val Typography = Typography(
    // Define default text styles
    bodyLarge = TextStyle(
        fontFamily = com.example.neoapplication.presentation.theme.rubikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = com.example.neoapplication.presentation.theme.rubikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = com.example.neoapplication.presentation.theme.rubikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = com.example.neoapplication.presentation.theme.rubikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = com.example.neoapplication.presentation.theme.rubikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = com.example.neoapplication.presentation.theme.rubikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    // Customize other text styles if needed
)
