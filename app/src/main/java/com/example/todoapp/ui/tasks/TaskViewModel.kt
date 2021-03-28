package com.example.todoapp.ui.tasks

import androidx.databinding.ObservableField
import android.view.View
import com.example.todoapp.model.Task
import java.lang.ref.WeakReference

/**
 * Created by Yasuhiro Suzuki on 2017/07/08.
 */
class TaskViewModel(_task: Task){

    val task = ObservableField<Task>()

    var navigator: WeakReference<TaskNavigator>? = null

    init {
        task.set(_task)
    }

    fun setNavigator(nav: TaskNavigator) {
        navigator = WeakReference(nav)
    }

    /** Listener Binding */
    fun onClickItem(view: View) {
        navigator?.get()?.onClickItem(task.get()!!)
    }

}