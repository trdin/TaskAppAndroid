package com.example.tasks

import java.text.SimpleDateFormat
import java.util.Collections.sort

class Tasks {
    var taskList: MutableList<Task> = mutableListOf()

    fun push(task: Task){
        taskList.add(task)
    }

    override fun toString(): String {
        var string = ""
        for (task in taskList){
            string += "============================================\n"
            string += "$task"
        }
        return string
    }

    fun sortByPriority(){
        sort(taskList)
    }

    fun deleteByID(id:String):Boolean{
        for(task in taskList){
            if (task.uuid == id ){
                taskList.remove(task)
                return true;
            }
        }
        return false;
    }

    fun modifyTask(taskNew: Task): Boolean{
        for(task in taskList){
            if (task.uuid == taskNew.uuid ){
                task.done = taskNew.done
                task.priority = taskNew.priority
                task.contents = taskNew.contents
                task.title = task.title
                task.lastModified = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
                sortByPriority()
                return true;
            }
        }
        return false;
    }
}