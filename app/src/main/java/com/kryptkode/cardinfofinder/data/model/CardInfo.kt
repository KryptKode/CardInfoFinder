package com.kryptkode.cardinfofinder.data.model

data class CardInfo(
    val brand: String = "",
    val scheme: String = "",
    val type: String = "",
    val bank: CardBank = CardBank(),
    val country: CardCountry = CardCountry(),
)
