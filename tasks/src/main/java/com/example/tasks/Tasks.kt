package com.example.tasks

import java.text.SimpleDateFormat
import java.util.*
import java.util.Collections.sort

class Tasks {
    var taskList: MutableList<Task> = mutableListOf()

    fun push(task: Task) {
        taskList.add(task)
    }

    override fun toString(): String {
        var string = ""
        for (task in taskList) {
            string += "============================================\n"
            string += "$task"
        }
        return string
    }


    fun sortByPriority() {
        sort(taskList)
    }

    fun sortByPriorityDescendig() {
        taskList.sortDescending()
    }

    fun deleteByID(id: String): Boolean {
        for (task in taskList) {
            if (task.uuid == id) {
                taskList.remove(task)
                return true;
            }
        }
        return false;
    }

    fun modifyTask(id:String, taskNew: Task): Boolean {
        for (task in taskList) {
            if (task.uuid == id) {
                task.done = taskNew.done
                task.priority = taskNew.priority
                task.contents = taskNew.contents
                task.title = taskNew.title
                task.lastModified = Date()
                return true;
            }
        }
        return false;
    }

    fun findByID(id: String): Task? {
        for (task in taskList) {
            if (task.uuid == id) {
                return task;
            }
        }
        return null;
    }
}