package com.kryptkode.cardinfofinder.ui.cardinfo

import com.kryptkode.cardinfofinder.data.model.CardInfo

data class CardInfoViewState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val errorMessage: String = "",
    val books: CardInfo = CardInfo()
)
