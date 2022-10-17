package com.example.tasks

import java.text.SimpleDateFormat
import java.util.*

class UrgentTask(
    done: Boolean,
    title: String,
    contents: String,
    priority: Int,
    var doDate: SimpleDateFormat,
) : Task(done, title, contents, priority) {

    override fun toString(): String {
        var string = "$title\n"
        string += "done: " + if (done) "true" else "false"
        string += "\n"
        string += "$contents\n"
        string += "Do date: ${doDate.format(Date())}\n"
        string += "priority: $priority\n"
        return string
    }

}