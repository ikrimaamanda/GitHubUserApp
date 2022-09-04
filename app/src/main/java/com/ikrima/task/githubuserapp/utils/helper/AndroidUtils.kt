package com.ikrima.task.githubuserapp.utils.helper

import android.annotation.SuppressLint
import android.text.format.DateUtils
import com.ikrima.task.githubuserapp.data.responses.RepositoryResponses
import java.text.SimpleDateFormat
import java.util.*

object AndroidUtils {

    @SuppressLint("SimpleDateFormat")
    fun getUpdatedTimeFormatter(dataNow: RepositoryResponses): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

        val dateStr = dataNow.updatedAt
        val date: Date = dateStr?.let { inputFormat.parse(it) } as Date

        return DateUtils.getRelativeTimeSpanString(
            date.time,
            Calendar.getInstance().timeInMillis,
            DateUtils.MINUTE_IN_MILLIS
        ).toString()
    }

}