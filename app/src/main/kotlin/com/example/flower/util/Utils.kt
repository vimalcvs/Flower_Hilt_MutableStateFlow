package com.example.flower.util

import android.content.Intent
import android.os.Build

object Utils {
    inline fun <reified T> Intent.getParcelableExtraCompat(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(
            key,
            T::class.java
        )
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T?
    }

}
