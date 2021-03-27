package com.example.todoapp.ui.edit

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.example.todoapp.model.Task
import com.example.todoapp.util.DateUtil

/**
 * Created by Yasuhiro Suzuki on 2017/07/30.
 */

typealias OnSuccess = (task: Task) -> Unit
typealias OnError = (error: String) -> Unit

class EditTaskViewModel: ViewModel() {

    val ERROR_TITLE = 1

    val ERROR_CONTENT = 2

    val title = ObservableField<String>()

    val content = ObservableField<String>()

    val task: Task
        get() = Task(DateUtil.timestump, DateUtil.currentDate, title.get()!!, content.get()!!)

    fun save(onSuccess: OnSuccess, onError: OnError) = when (validate()) {
        ERROR_TITLE -> onError("No title!")
        ERROR_CONTENT -> onError("No content!")
        else -> {
            // Save task on repository here
            onSuccess(task)
        }
    }

    fun validate() = if (!validateTitle()) ERROR_TITLE else if (!validateContent()) ERROR_CONTENT else 0

    fun validateTitle() = !title.get().isNullOrBlank()

    fun validateContent() = !content.get().isNullOrBlank()

    override fun onCleared() {
        super.onCleared()
    }

}