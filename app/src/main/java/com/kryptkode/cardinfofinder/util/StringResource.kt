package com.kryptkode.cardinfofinder.util

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class StringResource @Inject constructor(private val context: Context) {
    fun getString(@StringRes resId: Int, vararg args: Any): String {
        return context.getString(resId, *args)
    }
}
