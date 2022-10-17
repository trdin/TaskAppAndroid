package com.example.tasks

class Tasks {
    var taskList: List<Task> = listOf()

    fun push(task: Task){
        taskList = taskList + task
    }

    override fun toString(): String {
        var string = ""
        for (task in taskList){
            string += "=======================\n"
            string += "$task"
        }
        return string
    }
}