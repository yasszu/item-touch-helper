package com.example.todoapp.ui.tasks

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.todoapp.databinding.FragmentTasksBinding
import com.example.todoapp.helper.ItemTouchHelperCallback
import com.example.todoapp.ui.base.BaseFragment


/**
 * A placeholder fragment containing a simple view.
 */
class TasksFragment : BaseFragment() {

    lateinit var binding: FragmentTasksBinding

    private val viewModel: TasksViewModel by activityViewModels()

    private val adapter: TasksViewAdapter by lazy { TasksViewAdapter(viewModel) }

    companion object {
        val TAG: String = TasksFragment::class.java.simpleName
        fun newInstance() = TasksFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false).apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =  LinearLayoutManager(context)

            // Set ItemTouchHelper
            ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(recyclerView)
        }
        return binding.root
    }


}

