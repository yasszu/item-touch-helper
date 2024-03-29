package com.example.todoapp.ui.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityEditTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.base.BaseActivity
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Yasuhiro Suzuki on 2017/07/30.
 */

class EditTaskActivity: BaseActivity() {

    lateinit var binding: ActivityEditTaskBinding

    private val viewModel: EditTaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_task)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.findFragmentByTag(EditTaskFragment.TAG)?: EditTaskFragment.newInstance().apply {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, this, EditTaskFragment.TAG)
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit_task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        R.id.action_done -> save()
        else -> super.onOptionsItemSelected(item)
    }

    private fun save(): Boolean {
        viewModel.save(
                { task -> finishEdit(task) },
                { error -> showErrorSnackbar(error) }
        )
        return true
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.parentLayout, message, Snackbar.LENGTH_LONG).apply {
            setAction("OK") { dismiss() }
            show()
        }
    }

    private fun finishEdit(task: Task) {
        val intent = intent.apply {
            putExtra("task", task)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {
        fun getIntent(activity: Activity) = Intent(activity, EditTaskActivity::class.java)
    }

}
