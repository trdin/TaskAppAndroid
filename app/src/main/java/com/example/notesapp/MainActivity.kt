package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.tasks.Task
import com.example.tasks.Tasks
import kotlin.system.exitProcess

// TODO onClick events
// TODO change inputs
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var tasks = Tasks()

    private val getInputResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                addTask(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    private fun addTask(it: ActivityResult){
        val taskTitle = it.data?.getStringExtra("taskTitle").toString()
        val doDate = it.data?.getStringExtra("doDate").toString()
        val taskDone = it.data?.getStringExtra("taskDone").toBoolean()
        val taskContent = it.data?.getStringExtra("taskContent").toString()
        val taskPriority = it.data?.getStringExtra("taskPriority")?.toInt()

        tasks.push(Task(taskDone, taskTitle, taskContent,taskPriority!!))

        Log.d(null, "$taskTitle, $doDate, $taskContent, $taskDone, $taskPriority")

        binding.taskDisplay.text = tasks.toString()

    }

    fun inputTask(view: View){
        val intent = Intent(this, InputTaskActivity::class.java);
        getInputResult.launch(intent)
    }

    fun aboutApp(view: View){
        val intent = Intent(this, AboutActivity::class.java);
        startActivity(intent)
    }

    fun exit(view: View){
        finish()
        exitProcess(0)
    }

}
