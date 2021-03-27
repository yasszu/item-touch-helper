package com.example.todoapp.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.todoapp.databinding.FragmentEditTaskBinding
import com.example.todoapp.ui.base.BaseFragment

/**
 * Created by Yasuhiro Suzuki on 2017/07/30.
 */
class EditTaskFragment: BaseFragment() {

    lateinit var binding: FragmentEditTaskBinding

    private val viewModel: EditTaskViewModel by activityViewModels()

    companion object {
        val TAG: String = EditTaskFragment::class.java.simpleName
        fun newInstance() = EditTaskFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

}