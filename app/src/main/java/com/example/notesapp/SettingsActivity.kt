package com.example.notesapp

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.notesapp.databinding.SettingsActivityBinding

class SettingsActivity: AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding
    lateinit var app: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MyApplication
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.darkModeSwitch.isChecked = app.getThemeMode()=="dark"


        binding.darkModeSwitch.setOnClickListener {
            if (app.getThemeMode()=="dark") {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                app.saveTheme("light")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                app.saveTheme("dark")
            }
        }

        binding.backButtoSettings.setOnClickListener{
            setResult(Activity.RESULT_OK);
            finish();
        }

        binding.prioritySwitch.isChecked = app.getOrder() == "ASC"
        binding.prioritySwitch.setOnClickListener{
            if(binding.prioritySwitch.isChecked){
                app.saveOrder("ASC")
            }else{
                app.saveOrder("DESC")
            }
            app.saveSortData();
        }

        app.settingsVisits++
        app.saveSettings()


        binding.appBackground.text = app.appBackground.toString()
        binding.appOpened.text = app.appOpened.toString()

        binding.aboutVisits.text = app.aboutVisits.toString()
        binding.inputVisits.text = app.inputVisits.toString()
        binding.mainVisits.text = app.mainVisits.toString()
        binding.settingsVisits.text = app.settingsVisits.toString()
    }


}