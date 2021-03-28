package com.example.todoapp.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Yasuhiro Suzuki on 2017/06/18.
 */
object DateUtil {

    private const val PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ"

    val currentDate: String
        get() = SimpleDateFormat(PATTERN, Locale.JAPAN)
                .format(Calendar.getInstance().time)
                .toString()

    val timestamp: Long
        get() = System.currentTimeMillis()

}