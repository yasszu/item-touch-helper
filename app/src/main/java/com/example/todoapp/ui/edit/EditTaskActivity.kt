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

        val tag = EditTaskFragment.TAG
        val fm = supportFragmentManager
        fm.findFragmentByTag(tag)?: EditTaskFragment.newInstance().apply {
            fm.beginTransaction().add(R.id.container, this, tag).commit()
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
                { finishEdit() },
                { showErrorSnackbar(it) }
        )
        return true
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.parentLayout, message, Snackbar.LENGTH_LONG).apply {
            setAction("OK") { dismiss() }
            show()
        }
    }

    private fun finishEdit() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    companion object {
        fun start(activity: Activity, requestCode: Int) {
            val intent = Intent(activity, EditTaskActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }

}
