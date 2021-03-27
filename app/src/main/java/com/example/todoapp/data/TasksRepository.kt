package com.example.todoapp.data

import com.example.todoapp.model.Task
import com.example.todoapp.util.DateUtil

/**
 * Created by Yasuhiro Suzuki on 2017/06/18.
 */
object TasksRepository {

    private val sampleDate = arrayOf(
            Task(id = 1, title = "TASK1", description = "description", date = DateUtil.currentDate),
            Task(id = 2, title = "TASK2", description = "description", date = DateUtil.currentDate),
            Task(id = 3, title = "TASK3", description = "description", date = DateUtil.currentDate),
            Task(id = 4, title = "TASK4", description = "description", date = DateUtil.currentDate),
            Task(id = 5, title = "TASK5", description = "description", date = DateUtil.currentDate),
            Task(id = 6, title = "TASK6", description = "description", date = DateUtil.currentDate),
            Task(id = 7, title = "TASK7", description = "description", date = DateUtil.currentDate),
            Task(id = 8, title = "TASK8", description = "description", date = DateUtil.currentDate),
            Task(id = 9, title = "TASK9", description = "description", date = DateUtil.currentDate)
    )

    private var tasks: ArrayList<Task> = arrayListOf()

    init {
        tasks.addAll(sampleDate)
    }

    fun getTasks(): List<Task> {
        return tasks
    }

}