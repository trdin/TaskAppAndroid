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


// TODO onClick events
// TODO change inputs
open class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var tasks = Tasks()

    private val getInputResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                addTaskActivity(it)
            }
        }

    private val getQrCodeResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intentResult = IntentIntegrator.parseActivityResult(it.resultCode, it.data)

            if (intentResult.contents != null) {
                Toast.makeText(this, intentResult.contents, Toast.LENGTH_LONG).show()
                addTaskJson(intentResult.contents.toString())
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun addTaskActivity(it: ActivityResult) {
        val taskTitle = it.data?.getStringExtra("taskTitle").toString()
        val doDate = it.data?.getStringExtra("doDate").toString()
        val taskDone = it.data?.getStringExtra("taskDone").toBoolean()
        val taskContent = it.data?.getStringExtra("taskContent").toString()
        val taskPriority = it.data?.getStringExtra("taskPriority")?.toInt()

        tasks.push(Task(taskDone, taskTitle, taskContent, taskPriority!!))

        Log.d(null, "$taskTitle, $doDate, $taskContent, $taskDone, $taskPriority")

        binding.taskDisplay.text = tasks.toString()

    }

    private fun addTaskJson(resJson: String) {
        try {
            val listOfStrings = Gson().fromJson(resJson, mutableMapOf<String, Any>().javaClass)
            if (listOfStrings.containsKey("taskDone") &&
                listOfStrings.containsKey("taskTitle") &&
                listOfStrings.containsKey("taskContent") &&
                listOfStrings.containsKey("taskPriority")
            ) {
                tasks.push(
                    Task(
                        listOfStrings["taskDone"].toString().toBoolean(),
                        listOfStrings["taskTitle"].toString(),
                        listOfStrings["taskContent"].toString(),
                        listOfStrings["taskPriority"].toString().toDouble().toInt()
                    )
                )
                binding.taskDisplay.text = tasks.toString()
            } else {
                Toast.makeText(this, "not correct contents", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "not JSON format", Toast.LENGTH_LONG).show()
        }
    }


    fun inputTask(view: View) {
        val intent = Intent(this, InputTaskActivity::class.java);
        getInputResult.launch(intent)
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
        //scanner.initiateScan()
        getQrCodeResult.launch(scanner.createScanIntent())
    }

    fun exit(view: View) {
        finish()
        exitProcess(0)
    }
}


