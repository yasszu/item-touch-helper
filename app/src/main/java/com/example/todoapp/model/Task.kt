package com.example.todoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Yasuhiro Suzuki on 2017/06/18.
 */
@Parcelize
data class Task(
        val id: Long,
        val date: String,
        val title: String = "",
        val description: String = "",
        val completed: Boolean = false
): Parcelable