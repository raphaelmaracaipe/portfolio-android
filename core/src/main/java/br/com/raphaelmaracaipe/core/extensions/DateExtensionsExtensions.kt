package br.com.raphaelmaracaipe.core.extensions

import android.os.Build
import java.time.Instant
import java.util.Date

fun Date.getTimeStamp() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    Instant.now().toEpochMilli()
} else {
    System.currentTimeMillis()
}