package com.example.todoapp.ui.base

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View


/**
 * Created by Yasuhiro Suzuki on 2017/07/08.
 */
@SuppressLint("Registered")
open class BaseActivity : androidx.appcompat.app.AppCompatActivity() {

    val statusBarHeight: Int
        get() = if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0

    private val resourceId: Int
        get() = resources.getIdentifier("status_bar_height", "dimen", "android")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}