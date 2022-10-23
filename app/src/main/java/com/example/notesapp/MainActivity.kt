package com.example.notesapp

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.tasks.Task
import com.example.tasks.Tasks
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import kotlin.system.exitProcess

//import com.google.zxing.integration.android.IntentIntegrator

// TODO onClick events
// TODO change inputs
open class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var tasks = Tasks()

    /* private val getInputResult =
         registerForActivityResult(
             ActivityResultContracts.StartActivityForResult()) {
             if(it.resultCode == Activity.RESULT_OK){
                 addTask(it)
             }
         }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun addTask(it: ActivityResult) {
        val taskTitle = it.data?.getStringExtra("taskTitle").toString()
        val doDate = it.data?.getStringExtra("doDate").toString()
        val taskDone = it.data?.getStringExtra("taskDone").toBoolean()
        val taskContent = it.data?.getStringExtra("taskContent").toString()
        val taskPriority = it.data?.getStringExtra("taskPriority")?.toInt()

        tasks.push(Task(taskDone, taskTitle, taskContent, taskPriority!!))

        Log.d(null, "$taskTitle, $doDate, $taskContent, $taskDone, $taskPriority")

        binding.taskDisplay.text = tasks.toString()

    }

    fun inputTask(view: View) {
        val intent = Intent(this, InputTaskActivity::class.java);
        //getInputResult.launch(intent)
    }

    fun aboutApp(view: View) {
        val intent = Intent(this, AboutActivity::class.java);
        startActivity(intent)
    }

    fun import(view: View) {
        val scanner = IntentIntegrator(this)
        scanner.setOrientationLocked(true)
        scanner.setPrompt("Scan a barcode.")
        scanner.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        scanner.setBarcodeImageEnabled(true)
        scanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var resultJSON = "";
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                    resultJSON = result.contents.toString()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
        if (resultJSON != "") {
            val listOfStrings = Gson().fromJson(resultJSON, mutableMapOf<String, Any>().javaClass)
            Toast.makeText(this, "Scanned: " + listOfStrings.toString(), Toast.LENGTH_LONG).show()
            println(listOfStrings.toString())

            for (key in listOfStrings.keys) {
                println("Key = ${key}, Value = ${listOfStrings[key]}")
            }

            tasks.push(
                Task(
                    listOfStrings["taskDone"].toString().toBoolean(),
                    listOfStrings["taskTitle"].toString(),
                    listOfStrings["taskContent"].toString(),
                    listOfStrings["taskPriority"].toString().toDouble().toInt()
                )
            )
            binding.taskDisplay.text = tasks.toString()
        }
    }

    fun exit(view: View) {
        finish()
        exitProcess(0)
    }
}


