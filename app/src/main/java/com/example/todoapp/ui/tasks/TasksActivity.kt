package com.example.todoapp.ui.tasks

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityTasksBinding
import com.example.todoapp.ui.base.BaseActivity
import com.example.todoapp.ui.edit.EditTaskActivity
import com.google.android.material.snackbar.Snackbar

class TasksActivity: BaseActivity() {

    companion object {
        const val REQUEST_EDIT_TASK = 1
    }

    lateinit var binding: ActivityTasksBinding

    private val viewModel: TasksViewModel by viewModels()

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            Snackbar.make(binding.parentLayout, "Saved", Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tasks)
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbar)

        viewModel.listener = object: TasksViewModel.Listener {
            override fun onRemoveItem() {
                showDeleteSnackbar()
            }

            override fun onClickFAB() {
                startEditTaskActivity()
            }
        }

        supportFragmentManager.findFragmentByTag(TasksFragment.TAG)?: TasksFragment.newInstance().apply {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, this, tag)
                    .commit()
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

    private fun showDeleteSnackbar() {
        Snackbar.make(binding.parentLayout, R.string.item_remove, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo) { viewModel.restoreLastItems() }
                .show()
    }

    private fun startEditTaskActivity() {
        startForResult.launch(EditTaskActivity.getIntent(this, REQUEST_EDIT_TASK))
    }

}
