package com.example.notesapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
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

class MyApplication : Application(), LifecycleObserver {
    lateinit var data: Tasks
    lateinit var userUuid: String;

    private lateinit var gson: Gson
    private lateinit var file: File

    lateinit var sharedPref: SharedPreferences;

    var aboutVisits = 0
    var inputVisits = 0
    var mainVisits = 0
    var settingsVisits = 0

    var appOpened = 0
    var appBackground = 0


    @SuppressLint("BinaryOperationInTimber")
    override fun onCreate() {
        super.onCreate()

        gson = Gson()
        file = File(filesDir, MY_FILE_NAME)
        sharedPref = getSharedPreferences(MY_SP_FILE_NAME, Context.MODE_PRIVATE);

        data = Tasks()
        initData()

        if (!sharedPref.contains("ID")) {
            saveID(UUID.randomUUID().toString().replace("-", ""));
        }

        userUuid = getID()!!;

        if(!sharedPref.contains("ThemeMode")){
            saveTheme("light")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }else {
            if(getThemeMode()=="light"){

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
        if(!sharedPref.contains("PriorityOrder")){
            saveOrder("ASC")
        }

        initAnalytics();

        appOpened++
        saveAppOpened()
    }

    /*@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        appBackground++
        saveAppBackground()
    }*/

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
            //Timber.d("My file data:${FileUtils.readFileToString(file)}")
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

    fun saveTheme(mode:String){
        with(sharedPref.edit()) {
            putString("ThemeMode", mode)
            apply()
        }
    }

    fun getThemeMode(): String? {
        return sharedPref.getString("ThemeMode", "DefaultNoData")
    }

    fun saveOrder(order:String){
        with(sharedPref.edit()) {
            putString("PriorityOrder", order)
            apply()
        }
    }
    fun getOrder():String?{
        return sharedPref.getString("PriorityOrder", "DefaultNoData")
    }

    fun sortData(){
        if(getOrder() =="ASC"){
            data.sortByPriority()
        }else{
            data.sortByPriorityDescendig()
        }
        saveToFile();
    }



    fun deleteByID(id: String): Boolean {
        return data.deleteByID(id)
    }

    fun modifyTask(taskNew: Task): Boolean {
        return data.modifyTask(taskNew)
    }

    fun initAnalytics(){

        if(!sharedPref.contains("aboutVisits")){
            saveAbout()
        }
        if(!sharedPref.contains("inputVisits")){
            saveInput()
        }
        if(!sharedPref.contains("mainVisits")){
            saveMain()
        }
        if(!sharedPref.contains("settingsVisits")){
            saveSettings()
        }

        if(!sharedPref.contains("appOpened")){
            saveAppOpened()
        }
        if(!sharedPref.contains("appBackground")){
            saveAppBackground()
        }

        aboutVisits = sharedPref.getString("aboutVisits", "DefaultNoData").toString().toInt()
        inputVisits = sharedPref.getString("inputVisits", "DefaultNoData").toString().toInt()
        mainVisits = sharedPref.getString("mainVisits", "DefaultNoData").toString().toInt()
        settingsVisits = sharedPref.getString("settingsVisits", "DefaultNoData").toString().toInt()

        appOpened = sharedPref.getString("appOpened", "DefaultNoData").toString().toInt()
        appBackground = sharedPref.getString("appBackground", "DefaultNoData").toString().toInt()
        appBackground = 0;
    }

    fun saveAppBackground(){
        with(sharedPref.edit()) {
            putString("appBackground", appBackground.toString())
            apply()
        }
    }

    fun saveAppOpened(){
        with(sharedPref.edit()) {
            putString("appOpened", appOpened.toString())
            apply()
        }
    }

    fun saveSettings(){
        with(sharedPref.edit()) {
            putString("settingsVisits", settingsVisits.toString())
            apply()
        }
    }
    fun saveMain(){
        with(sharedPref.edit()) {
            putString("mainVisits", mainVisits.toString())
            apply()
        }
    }
    fun saveInput(){
        with(sharedPref.edit()) {
            putString("inputVisits", inputVisits.toString())
            apply()
        }
    }

    fun saveAbout(){
        with(sharedPref.edit()) {
            putString("aboutVisits", aboutVisits.toString())
            apply()
        }
    }

}