package com.example.tasks
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

open class Task (
    var done: Boolean,
    var title: String,
    var contents: String,
    var priority: Int
        ): Comparable<Task>{


    var createdTime:Date  = Date()
    var lastModified = Date()

    var uuid = UUID.randomUUID().toString().replace("-", "");

    constructor(done: String, title: String, contents: String, priority: String):
            this(done.toBoolean(), title, contents, priority.toInt())

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