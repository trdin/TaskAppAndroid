package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import com.example.tasks.Task
import com.example.tasks.Tasks
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tasks = Tasks()

        val addButton: Button = findViewById(R.id.addButton)

        addButton.setOnClickListener { view ->
            val inputTaskTitle: EditText = findViewById(R.id.taskTitle)
            val taskTitle = inputTaskTitle.text.toString()

            val inputDoDate: EditText = findViewById(R.id.editDate)
            val doDate = inputDoDate.text.toString()


            val inputTaskContent: EditText = findViewById(R.id.taskContent)
            val taskContent = inputTaskContent.text.toString()
            println(taskContent)

            val inputTaskPriority: EditText = findViewById(R.id.taskPriority)
            var taskPriority = 0;
            if (inputTaskPriority.text.toString() != "") {
                taskPriority = inputTaskPriority.text.toString().toInt()
            }
            val switchTaskDone: Switch = findViewById(R.id.doneSwitch)
            val taskDone: Boolean = switchTaskDone.isChecked

            tasks.push(Task(taskDone, taskTitle, taskContent,taskPriority ))

            val inputs: MutableList<EditText> =
                mutableListOf(inputTaskTitle, inputDoDate, inputTaskContent, inputTaskPriority)
            for (input in inputs) {
                input.text.clear()
            }
            switchTaskDone.isChecked = false

            Log.d(null, "$taskTitle, $doDate, $taskContent, $taskDone, $taskPriority")

        }

        val infoButton: Button = findViewById(R.id.infoButton)
        infoButton.setOnClickListener {
            Log.d(null, "number of tasks: ${tasks.taskList.size}")
            for (task in tasks.taskList) {
                Log.d(null, task.toString())
            }
        }

        val exitButton:ImageButton = findViewById(R.id.exitButton)
        exitButton.setOnClickListener {
            finish()
            exitProcess(0)
        }
    }


}
