package com.example.neoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.neoapplication.mainScreen.activity.MainScreenActivity

class MainActivityXML : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            //for jetpack
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            //for xml
//            val intent = Intent(this, MainScreenActivity::class.java)
//            startActivity(intent)
            finish()
        }, 3000) // 3000 milliseconds delay

    }
}