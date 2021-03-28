package com.example.todoapp.ui.tasks

import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.helper.ItemTouchHelperAdapter

/**
 * Created by Yasuhiro Suzuki on 2017/06/18.
 */
class TasksViewAdapter(private val viewModel: TasksViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter{

    init {
        viewModel.addObservableListCallBack(object: ObservableList.OnListChangedCallback<ObservableList<TaskViewModel>>(){
            override fun onChanged(sender: ObservableList<TaskViewModel>?) {
            }

            override fun onItemRangeChanged(sender: ObservableList<TaskViewModel>?, start: Int, count: Int) {
                notifyItemRangeChanged(start, count)
            }

            override fun onItemRangeInserted(sender: ObservableList<TaskViewModel>?, start: Int, count: Int) {
                notifyItemInserted(start)
            }

            override fun onItemRangeMoved(sender: ObservableList<TaskViewModel>?, from: Int, to: Int, count: Int) {
                // Don't implement notify
            }

            override fun onItemRangeRemoved(sender: ObservableList<TaskViewModel>?, start: Int, count: Int) {
                notifyItemRangeRemoved(start, count)
            }
        })
    }

    override fun getItemCount(): Int {
        return viewModel.taskItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent ,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as TaskViewHolder
        holder.bind(viewModel.taskItems[position])
    }

    override fun onMoveItem(from: Int, to: Int) {
        viewModel.moveItem(from, to) { notifyItemMoved(from, to) }
    }

    override fun onRemoveItem(from: Int) {
        viewModel.removeItem(from)
    }

    class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: TaskViewModel) {
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

}