package com.antyl.digitalnomadstest

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class Utils {

    companion object {
        fun formatDate(s: String): String {
            val date = Date.from(Instant.parse(s))
            val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault())
            return sdf.format(date)
        }
    }
}