package com.example.blueapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.blueapp.presentation.mainScreen.activity.MainScreenActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 milliseconds delay

    }
}