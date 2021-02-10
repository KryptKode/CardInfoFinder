package com.kryptkode.cardinfofinder.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider

fun getString(@StringRes resId: Int): String {
    return ApplicationProvider.getApplicationContext<Context>()
        .getString(resId)
}
