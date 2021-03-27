package com.example.todoapp.ui.tasks

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTasksBinding
import com.example.todoapp.helper.ItemTouchHelperCallback
import com.example.todoapp.model.Task
import com.example.todoapp.ui.base.BaseFragment
import com.example.todoapp.ui.edit.EditTaskActivity
import com.google.android.material.snackbar.Snackbar


/**
 * A placeholder fragment containing a simple view.
 */
class TasksFragment : BaseFragment() {

    companion object {
        val TAG: String = TasksFragment::class.java.simpleName
        fun newInstance() = TasksFragment()
    }

    lateinit var binding: FragmentTasksBinding

    private val viewModel: TasksViewModel by activityViewModels()

    private val adapter: TasksViewAdapter by lazy { TasksViewAdapter(viewModel) }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                (intent.getParcelableExtra("task") as Task?)?.let {
                    viewModel.addTask(it)
                    binding.recyclerView.scrollToPosition(0)
                    Snackbar.make(binding.parentLayout, "${it.title} is saved", Snackbar.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false).apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =  LinearLayoutManager(context)

            viewModel.listener = object: TasksViewModel.Listener {
                override fun onRemoveItem() {
                    Snackbar.make(binding.parentLayout, R.string.item_remove, Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo) { viewModel.restoreLastItems() }
                            .show()
                }

                override fun onClickFab() {
                    activity?.let { startForResult.launch(EditTaskActivity.getIntent(it)) }
                }
            }

            // Set ItemTouchHelper
            ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(recyclerView)
        }
        return binding.root
    }

}

