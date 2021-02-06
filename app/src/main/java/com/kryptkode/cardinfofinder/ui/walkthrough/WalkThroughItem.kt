package com.kryptkode.cardinfofinder.ui.walkthrough

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkThroughItem(
    val title: String,
    val subtitle: String,
    @DrawableRes val image: Int,
) : Parcelable
