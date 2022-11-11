package com.example.notesapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.TasksListActivityBinding
import timber.log.Timber


class TasksListActivity : AppCompatActivity() {
    lateinit var app: MyApplication
    private lateinit var binding: TasksListActivityBinding

    lateinit var adapter: TasksAdapter
    private val getUpdateResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Task Succesfull updated", Toast.LENGTH_LONG).show()
                adapter.notifyDataSetChanged()
                app.saveToFile()
            } else if (it.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Update canceled", Toast.LENGTH_LONG).show()
            } else if (it.resultCode == 404) {
                Toast.makeText(this, "Task not found", Toast.LENGTH_LONG).show()
            } else if (it.resultCode == 504) {
                Toast.makeText(this, "Task not updated error", Toast.LENGTH_LONG).show()
            }

        }

    private val getAddResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Task added", Toast.LENGTH_LONG).show()
            }else if ( it.resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "input canceled", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MyApplication
        binding = TasksListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = TasksAdapter(app.data, object : TasksAdapter.MyOnClick {
            override fun onClick(p0: View?, pos: Int) {
                Timber.d("Here code comes ${pos}.")
                /*val data = intent
                data.putExtra("SELECTED_ID", app.data.taskList[pos].uuid)
                setResult(RESULT_OK, data)*/

                val intent = Intent(this@TasksListActivity, InputTaskActivity::class.java);
                intent.putExtra("updateId", app.data.taskList[pos].uuid)
                getUpdateResult.launch(intent)
                //finish()
            }
        })
        binding.recyclerview.adapter = adapter
        //adapter.notifyDataSetChanged();
        Timber.d("Items ${app.data.taskList.size}")

        adapter.onLongClickObject = object : TasksAdapter.MyOnClick {
            override fun onClick(p0: View?, pos: Int) {
                Timber.d("Here code comes ${pos}.")
                val builder =
                    AlertDialog.Builder(this@TasksListActivity) //access context from inner class
                //set title for alert dialog
                builder.setTitle("Delete")
                builder.setMessage(app.data.taskList[pos].toString())
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                var buttonFuntColor = "#FFFFFF"
                if(app.getThemeMode()=="light"){
                    buttonFuntColor = "#000000"
                }
                builder.setPositiveButton(Html.fromHtml("<font color='$buttonFuntColor'>Yes</font>")) { dialogInterface, which -> //performing positive action
                    Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
                    app.data.taskList.removeAt(pos)
                    adapter.notifyDataSetChanged()
                    app.saveToFile()
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
        }

       /* fun inputTask() {
            val intent = Intent(this, InputTaskActivity::class.java);
            getInputResult.launch(intent)
            //startActivity(intent)

        }*/

        binding.tasks100.setOnClickListener{
            app.generateTasks(100)
            adapter.notifyDataSetChanged()
        }


    }

    fun addTaskInput(view: View) {

        val intent = Intent(this, InputTaskActivity::class.java);
        intent.putExtra("updateId", "")
        getAddResult.launch(intent)
    }

    fun cancel(view: View) {
        finish();
    }


}
