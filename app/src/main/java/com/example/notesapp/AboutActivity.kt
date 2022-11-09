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
    }

    fun goBack(view: View){
        finish();
    }



}