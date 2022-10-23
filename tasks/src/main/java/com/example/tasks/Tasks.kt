package com.example.tasks

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
}