package com.example.todoapp.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import android.util.Log
import android.view.View
import com.example.todoapp.data.TasksRepository
import com.example.todoapp.model.Task
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yasuhiro Suzuki on 2017/06/18.
 */
class TasksViewModel: ViewModel(), TaskNavigator {

    interface Listener {
        fun onRemoveItem()
        fun onClickFAB()
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
        Observable.fromIterable(TasksRepository.getTasks())
                .map { TaskViewModel(it) }
                .doOnNext { it.setNavigator(this) }
                .doOnNext { taskItems.add(it) }
                .subscribe()
    }

    fun moveItem(from: Int, to: Int, onItemMoved: () -> Unit) {
        // Don't call OnListChangedCallback.
        removeObservableListCallback()
        moveItem(from, to)
        onItemMoved()
        restoreObservableListCallback()
    }

    private fun moveItem(from: Int, to: Int) {
        val target = taskItems[from]
        taskItems.removeAt(from)
        taskItems.add(to, target)
    }

    fun removeItem(from: Int) {
        storeLastItem(from)
        taskItems.removeAt(from)
        listener?.onRemoveItem()
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
        listener?.onClickFAB()
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
