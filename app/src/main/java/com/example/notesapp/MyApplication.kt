package com.example.notesapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.tasks.Task
import com.example.tasks.Tasks
import com.google.gson.Gson
import org.apache.commons.io.FileUtils
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*


const val MY_FILE_NAME = "mydata.json"
const val MY_SP_FILE_NAME = "myshared.data"  //pred razredom

class MyApplication : Application() {
    lateinit var data: Tasks
    lateinit var userUuid: String;

    private lateinit var gson: Gson
    private lateinit var file: File

    lateinit var sharedPref: SharedPreferences;

    override fun onCreate() {
        super.onCreate()
        data = Tasks()

        gson = Gson()
        file = File(filesDir, MY_FILE_NAME)
        sharedPref = getSharedPreferences(MY_SP_FILE_NAME, Context.MODE_PRIVATE);

        if (!sharedPref.contains("ID")) {
            saveID(UUID.randomUUID().toString().replace("-", ""));
        }

        userUuid = getID()!!;
    }

    fun saveToFile() {
        try {
            //for FileUtils import org.apache.commons.io.FileUtils
            //in gradle implementation 'org.apache.commons:commons-io:1.3.2'
            FileUtils.writeStringToFile(file, gson.toJson(data))
            Timber.d("Save to file.")
        } catch (e: IOException) {
            Timber.d("Can't save %s", file.path)
        }
    }

    fun initData() {
        data = try { //www
            Timber.d("My file data:${FileUtils.readFileToString(file)}")
            gson.fromJson(FileUtils.readFileToString(file), Tasks::class.java)
        } catch (e: IOException) {
            Timber.d("No file init data.")
            Tasks()
        }
    }

    fun saveID(id: String) {
        with(sharedPref.edit()) {
            putString("ID", id)
            apply()
        }
    }

    fun getID(): String? {
        return sharedPref.getString("ID", "DefaultNoData")
    }


    fun deleteByID(id: String): Boolean {
        return data.deleteByID(id)
    }

    fun modifyTask(taskNew: Task): Boolean {
        return data.modifyTask(taskNew)
    }


}