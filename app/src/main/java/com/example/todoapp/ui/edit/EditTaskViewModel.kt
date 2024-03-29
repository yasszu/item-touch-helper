package com.example.todoapp.ui.edit

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.example.todoapp.data.TasksRepository
import com.example.todoapp.model.Task
import com.example.todoapp.util.DateUtil

/**
 * Created by Yasuhiro Suzuki on 2017/07/30.
 */

typealias OnSuccess = (task: Task) -> Unit
typealias OnError = (error: String) -> Unit

class EditTaskViewModel: ViewModel() {

    companion object {
        const val ERROR_TITLE = 1
        const val ERROR_CONTENT = 2
    }

    val title = ObservableField<String>()

    val content = ObservableField<String>()

    private val _task: Task
        get() = Task(DateUtil.timestamp, DateUtil.currentDate, title.get()!!, content.get()!!)

    fun save(onSuccess: OnSuccess, onError: OnError) = when (validate()) {
        ERROR_TITLE -> onError("No title!")
        ERROR_CONTENT -> onError("No content!")
        else -> {
            // Save task on repository here
            TasksRepository.setTask(_task)
            onSuccess(_task)
        }
    }

    private fun validate() = if (!validateTitle()) ERROR_TITLE else if (!validateContent()) ERROR_CONTENT else 0

    private fun validateTitle() = !title.get().isNullOrBlank()

    private fun validateContent() = !content.get().isNullOrBlank()

}