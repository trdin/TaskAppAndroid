package com.example.notesapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.InputActivityBinding
import com.example.tasks.Task


class InputTaskActivity : AppCompatActivity() {
    private lateinit var binding: InputActivityBinding
    lateinit var app: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MyApplication
        binding = InputActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app.inputVisits++
        app.saveInput()


    }


    fun addTask(view: View) {

        if (binding.taskTitle.text.isNotEmpty() &&
            binding.editDate.text.isNotEmpty() &&
            binding.taskContent.text.isNotEmpty() &&
            binding.taskPriority.text.isNotEmpty()
                ){
            /*val resultIntent = Intent();
            resultIntent.putExtra("taskTitle", binding.taskTitle.text.toString());
            resultIntent.putExtra("doDate", binding.editDate.text.toString());
            resultIntent.putExtra("taskContent", binding.taskContent.text.toString());
            resultIntent.putExtra("taskPriority", binding.taskPriority.text.toString());
            resultIntent.putExtra("taskDone", binding.doneSwitch.isChecked.toString());
            */
            app.data.push(Task(binding.doneSwitch.isChecked, binding.taskTitle.text.toString(), binding.taskContent.text.toString(),  binding.taskPriority.text.toString().toInt()))
            setResult(Activity.RESULT_OK);
            finish();
        }else{
            binding.textError.setTextColor(Color.rgb(200,0,0));
            binding.textError.text = "Error, all fields must be filled"
        }
    }

    fun cancel(view: View){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}