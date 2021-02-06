package com.kryptkode.cardinfofinder.ui.walkthrough

import com.kryptkode.cardinfofinder.R
import javax.inject.Inject

class WalkThroughItemProvider @Inject constructor() {

    fun getItems(): List<WalkThroughItem> {
        return mutableListOf(
            WalkThroughItem(
                title = "Get card info",
                subtitle = "See your card info in an instant",
                image = R.drawable.splash_img
            ),
            WalkThroughItem(
                title = "Enter manually",
                subtitle = "See your card info in an instant",
                image = R.drawable.splash_img
            ),
            WalkThroughItem(
                title = "Scan OCR",
                subtitle = "See your card info in an instant",
                image = R.drawable.splash_img
            ),
            WalkThroughItem(
                title = "View card details",
                subtitle = "See your card info in an instant",
                image = R.drawable.splash_img
            )
        )
    }
}
