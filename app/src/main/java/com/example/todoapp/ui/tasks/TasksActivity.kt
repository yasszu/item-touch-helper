package com.example.todoapp.ui.tasks

import android.app.Activity
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityTasksBinding
import com.example.todoapp.ui.base.BaseActivity
import com.example.todoapp.ui.edit.EditTaskActivity

class TasksActivity: BaseActivity(), TasksViewModel.Listener {

    companion object {
        const val REQUEST_EDIT_TASK = 1
    }

    lateinit var binding: ActivityTasksBinding

    private val viewModel: TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tasks)
        setSupportActionBar(binding.toolbar)

        viewModel.listener = this
        binding.viewModel = viewModel

        supportFragmentManager.findFragmentByTag(TasksFragment.TAG)?: TasksFragment.newInstance().apply {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, this, tag)
                    .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_EDIT_TASK -> { Log.d("EditTask", "RESULT_OK") }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_settings -> true
        else -> super.onOptionsItemSelected(item)
    }

    override fun onRemoveItem() {
        showDeleteSnackbar()
    }

    override fun onClickFAB() {
        EditTaskActivity.start(this, REQUEST_EDIT_TASK)
    }

    private fun showDeleteSnackbar() {
        Snackbar.make(binding.parentLayout, R.string.item_remove, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo) { viewModel.restoreLastItems() }
                .show()
    }

}
