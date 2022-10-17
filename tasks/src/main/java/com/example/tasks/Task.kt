package com.example.tasks
import java.text.SimpleDateFormat
import java.util.*

open class Task (
    var done: Boolean,
    var title: String,
    var contents: String,
    var priority: Int
        ) {

    var createdTime = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
    var lastModified = SimpleDateFormat("yyyy-M-dd hh:mm:ss")

    override fun toString(): String {
        var string = "$title\n"
        string += "done: " + if (done) "true" else "false"
        string += "\n"
        string += "$contents\n"
        string += "priority: $priority\n"
        return string
    }
}