package com.example.todoapp.ui.tasks

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityTasksBinding
import com.example.todoapp.ui.base.BaseActivity

class TasksActivity: BaseActivity() {

    lateinit var binding: ActivityTasksBinding

    private val viewModel: TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tasks)
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbar)

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

}
