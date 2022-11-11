package com.example.notesapp

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.InputActivityBinding
import com.example.tasks.Task
import kotlin.properties.Delegates


class InputTaskActivity : AppCompatActivity() {
    private lateinit var binding: InputActivityBinding
    lateinit var app: MyApplication
    lateinit var updateUuid: String
    var update = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MyApplication
        binding = InputActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app.inputVisits++
        app.saveInput()

        updateUuid = intent.getStringExtra("updateId").toString()
        update = updateUuid != ""
        if (update) {
            var updateTask = app.findById(updateUuid)
            if (updateTask != null) {
                binding.taskTitle.setText(updateTask.title);
                binding.taskContent.setText(updateTask.contents)
                binding.taskPriority.setText(updateTask.priority.toString())
                binding.doneSwitch.isChecked = updateTask.done

            } else {
                setResult(404);
                finish();
            }

        }

    }


    fun addTask(view: View) {

        if (binding.taskTitle.text.isNotEmpty() &&
            binding.taskContent.text.isNotEmpty() &&
            binding.taskPriority.text.isNotEmpty()
        ) {
            /*val resultIntent = Intent();
            resultIntent.putExtra("taskTitle", binding.taskTitle.text.toString());
            resultIntent.putExtra("doDate", binding.editDate.text.toString());
            resultIntent.putExtra("taskContent", binding.taskContent.text.toString());
            resultIntent.putExtra("taskPriority", binding.taskPriority.text.toString());
            resultIntent.putExtra("taskDone", binding.doneSwitch.isChecked.toString());
            */
            if (update) {
                var result = app.modifyTask(
                    updateUuid,
                    Task(
                        binding.doneSwitch.isChecked,
                        binding.taskTitle.text.toString(),
                        binding.taskContent.text.toString(),
                        binding.taskPriority.text.toString().toInt()
                    )
                )


                if(result){
                    setResult(Activity.RESULT_OK);
                }else{
                    setResult(504);
                }
            } else {
                app.data.push(
                    Task(
                        binding.doneSwitch.isChecked,
                        binding.taskTitle.text.toString(),
                        binding.taskContent.text.toString(),
                        binding.taskPriority.text.toString().toInt()
                    )
                )
                setResult(Activity.RESULT_OK);
            }

            app.saveSortData()
            finish();
        } else {
            binding.textError.setTextColor(Color.rgb(200, 0, 0));
            binding.textError.text = "Error, all fields must be filled"
        }
    }

    fun cancel(view: View) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}