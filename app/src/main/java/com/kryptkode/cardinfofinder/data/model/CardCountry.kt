package com.kryptkode.cardinfofinder.data.model

data class CardCountry(
    val numeric: String = "",
    val alpha2: String = "",
    val name: String = "",
    val emoji: String = "",
    val currency: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)
