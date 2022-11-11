package com.example.notesapp

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
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

        binding.deleteTasks.setOnClickListener{
            val builder =
                AlertDialog.Builder(this) //access context from inner class
            //set title for alert dialog
            builder.setTitle("Delete all Tasks")
            builder.setMessage("Are you sure you want to delete all tasks? These action cannot be reversed")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            var buttonFuntColor = "#FFFFFF"
            if(app.getThemeMode()=="light"){
                buttonFuntColor = "#000000"
            }
            builder.setPositiveButton(Html.fromHtml("<font color='$buttonFuntColor'>Yes</font>")) { dialogInterface, which -> //performing positive action
                Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
                app.deleteAllTasks()
            }
            builder.setNeutralButton(Html.fromHtml("<font color='$buttonFuntColor'>Cancel</font>")) { dialogInterface, which -> //performing cancel action
                Toast.makeText(
                    applicationContext,
                    "clicked cancel\n operation cancel",
                    Toast.LENGTH_LONG
                ).show()
            }
            builder.setNegativeButton(Html.fromHtml("<font color='$buttonFuntColor'>No</font>")) { dialogInterface, which -> //performing negative action
                Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
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