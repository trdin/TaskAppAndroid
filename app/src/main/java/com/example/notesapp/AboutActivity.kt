package com.example.notesapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.AboutActivityBinding

class AboutActivity: AppCompatActivity() {
    private lateinit var binding: AboutActivityBinding
    lateinit var app: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MyApplication
        binding = AboutActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.userId.text = app.userUuid.toString()

        app.aboutVisits++
        app.saveAbout()

        //main activity is on pause
        app.appBackground--
        app.saveAppBackground()
    }

    fun goBack(view: View){
        finish();
    }

    override fun onPause() {
        super.onPause()
        app.appBackground++
        app.saveAppBackground()
    }
}