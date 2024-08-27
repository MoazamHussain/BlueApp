package com.example.blueapp.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.blueapp.R
import com.example.blueapp.presentation.splash_screen.SplashScreen

import com.example.blueapp.presentation.theme.TemplateKotlinMVVMArchitectureTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainCleanArchitectureActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            // Set the status bar color
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.isAppearanceLightStatusBars = true
            val statusBarColor = ContextCompat.getColor(this, R.color.white)
            window.statusBarColor = statusBarColor
            var isSplashScreenVisible = remember { mutableStateOf(true) }

            TemplateKotlinMVVMArchitectureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.white)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        if (isSplashScreenVisible.value) {
                            SplashScreen {
                                // Update the state to navigate to the main screen
                                isSplashScreenVisible.value = false
                            }
                        } else {
                            MainListScreen()
                        }

                    }
                }
            }
        }
    }
}








