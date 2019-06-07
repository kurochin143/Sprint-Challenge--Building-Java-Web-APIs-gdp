package com.israel.sprint12.exception

import java.text.SimpleDateFormat
import java.util.*

class ErrorDetails {
    var title: String? = null
    var status: Int = 0
    var detail: String? = null
    private var timestamp: String? = null
    var developerMessage: String? = null

    fun getTimeStamp(): String? {
        return timestamp
    }

    fun setTimestamp(time: Long) {
        timestamp = SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(Date(time))
    }
}