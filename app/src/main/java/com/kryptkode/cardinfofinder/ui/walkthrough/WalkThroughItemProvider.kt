package com.kryptkode.cardinfofinder.ui.walkthrough

import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.util.StringResource
import javax.inject.Inject

class WalkThroughItemProvider @Inject constructor(private val stringResource: StringResource) {

    fun getItems(): List<WalkThroughItem> {
        return mutableListOf(
            WalkThroughItem(
                title = getString(R.string.walkthrough_title_1),
                subtitle = getString(R.string.walkthrough_subtitle_1),
                image = R.drawable.splash_img
            ),
            WalkThroughItem(
                title = getString(R.string.walkthrough_title_2),
                subtitle = getString(R.string.walkthrough_subtitle_2),
                image = R.drawable.splash_img
            ),
            WalkThroughItem(
                title = getString(R.string.walkthrough_title_3),
                subtitle = getString(R.string.walkthrough_subtitle_3),
                image = R.drawable.splash_img
            ),
            WalkThroughItem(
                title = getString(R.string.walkthrough_title_4),
                subtitle = getString(R.string.walkthrough_subtitle_4),
                image = R.drawable.splash_img
            )
        )
    }

    private fun getString(resId: Int): String {
        return stringResource.getString(resId)
    }
}
