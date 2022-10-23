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


class InputTaskActivity : AppCompatActivity() {
    private lateinit var binding: InputActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InputActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun addTask(view: View) {

        if (binding.taskTitle.text.isNotEmpty() &&
            binding.editDate.text.isNotEmpty() &&
            binding.taskContent.text.isNotEmpty() &&
            binding.taskPriority.text.isNotEmpty()
                ){
            val resultIntent = Intent();
            resultIntent.putExtra("taskTitle", binding.taskTitle.text.toString());
            resultIntent.putExtra("doDate", binding.editDate.text.toString());
            resultIntent.putExtra("taskContent", binding.taskContent.text.toString());
            resultIntent.putExtra("taskPriority", binding.taskPriority.text.toString());
            resultIntent.putExtra("taskDone", binding.doneSwitch.isChecked.toString());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }else{
            binding.textError.setTextColor(Color.rgb(200,0,0));
            binding.textError.text = "Error, all fields must be filled"
        }
    }
}