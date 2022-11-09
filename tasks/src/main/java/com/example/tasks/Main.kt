package com.example.tasks

import io.github.serpro69.kfaker.Faker
import java.text.SimpleDateFormat
import java.util.*

fun generateTasks(size: Int): Tasks {
    val faker = Faker()
    var tasks = Tasks()

    for (i in 0..size) {
        var done: Boolean = faker.random.nextInt(0..1) == 1
        var title: String = faker.friends.characters()
        var contents: String = faker.internet.safeEmail()
        var priority: Int = faker.random.nextInt(0..10)
        if (i % 2 == 0) {
            tasks.push(Task(done, title, contents, priority))
        } else {
            var year = faker.random.nextInt(2022..2024)
            var month = faker.random.nextInt(1..12)
            var day = faker.random.nextInt(0..31)

            var doDate: SimpleDateFormat = SimpleDateFormat("$year-$month-$day")
            tasks.push(UrgentTask(done, title, contents, priority, doDate))
        }
    }

    return tasks
}

fun main() {
    var tasks = generateTasks(3)
    tasks.sortByPriority()
    println("$tasks")
    println("different order =========================================================================")
    tasks.sortByPriorityDescendig()
    println("$tasks")
}