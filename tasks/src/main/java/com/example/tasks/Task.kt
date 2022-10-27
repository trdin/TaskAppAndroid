package com.example.tasks
import java.text.SimpleDateFormat
import java.util.*

open class Task (
    var done: Boolean,
    var title: String,
    var contents: String,
    var priority: Int
        ): Comparable<Task>{

    var createdTime = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
    var lastModified = SimpleDateFormat("yyyy-M-dd hh:mm:ss")

    override fun toString(): String {
        var string = "task: $title; "
        string += "done: " + if (done) "true" else "false"
        string += "; "
        string += "content: $contents; "
        string += "priority: $priority;\n"
        return string
    }


    override fun compareTo(other: Task): Int = compareValuesBy(this, other) { it.priority }
}