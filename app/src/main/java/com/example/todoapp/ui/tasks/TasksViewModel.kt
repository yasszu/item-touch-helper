package com.example.todoapp.ui.tasks

import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.TasksRepository
import com.example.todoapp.model.Task
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yasuhiro Suzuki on 2017/06/18.
 */
class TasksViewModel: ViewModel(), TaskNavigator {

    interface Listener {
        fun onRemoveItem()
        fun onClickFab()
    }

    private val compositeDisposable = CompositeDisposable()

    val taskItems: ObservableList<TaskViewModel> = ObservableArrayList()

    var lastItem: Pair<Int, TaskViewModel>? = null

    var observableListCallback: ObservableList.OnListChangedCallback<ObservableList<TaskViewModel>>? = null

    var listener: Listener? = null

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        TasksRepository.getTasks().map { task ->
            addTask(task)
        }
    }

    fun addTask(task: Task) {
        TaskViewModel(task).apply {
            setNavigator(this@TasksViewModel)
        }.also {
            taskItems.add(0, it)
        }
    }

    fun moveItem(from: Int, to: Int, onItemMoved: () -> Unit) {
        removeObservableListCallback() // Stop calling OnListChangedCallback >>>>>
        moveItem(from, to)
        onItemMoved()
        restoreObservableListCallback() // <<<<< Restore OnListChangedCallback
    }

    private fun moveItem(from: Int, to: Int) {
        val target = taskItems[from]
        taskItems.removeAt(from)
        taskItems.add(to, target)

        // Save items to repository here
    }

    fun removeItem(from: Int) {
        storeLastItem(from)
        taskItems.removeAt(from)
        listener?.onRemoveItem()

        // Save items to repository here
    }

    private fun storeLastItem(index: Int) {
        lastItem = Pair(index, taskItems[index])
    }

    fun restoreLastItems() {
        lastItem?.let {
            taskItems.add(it.first, it.second)
        }
    }

    fun addObservableListCallBack(callback: ObservableList.OnListChangedCallback<ObservableList<TaskViewModel>>) {
        taskItems.addOnListChangedCallback(callback)
        observableListCallback = callback
    }

    private fun removeObservableListCallback() {
        observableListCallback?.let {
            taskItems.removeOnListChangedCallback(it)
        }
    }

    private fun restoreObservableListCallback() {
        observableListCallback?.let {
            taskItems.addOnListChangedCallback(it)
        }
    }

    /**
     * ListenerBinding
     */
    fun onClickFAB(view: View) {
        listener?.onClickFab()
    }

    override fun onClickItem(task: Task) {
        Log.d("TaskNavigator", task.id.toString())
    }

    override fun onCleared() {
        super.onCleared()
        listener = null
        compositeDisposable.clear()
        removeObservableListCallback()
    }

}
