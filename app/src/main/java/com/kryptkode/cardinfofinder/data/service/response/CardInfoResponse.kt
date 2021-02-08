package com.kryptkode.cardinfofinder.data.service.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardInfoResponse(
    @get:Json(name = "scheme") val scheme: String = "",
    @get:Json(name = "type") val type: String = "",
    @get:Json(name = "brand") val brand: String = "",
    @get:Json(name = "country") val country: CardCountryResponse = CardCountryResponse(),
    @get:Json(name = "bank") val bank: CardBankResponse = CardBankResponse(),
)

@JsonClass(generateAdapter = true)
data class CardCountryResponse(
    @get:Json(name = "numeric") val numeric: String = "",
    @get:Json(name = "alpha2") val alpha2: String = "",
    @get:Json(name = "name") val name: String = "",
    @get:Json(name = "emoji") val emoji: String = "",
    @get:Json(name = "currency") val currency: String = "",
    @get:Json(name = "latitude") val latitude: Double = 0.0,
    @get:Json(name = "longitude") val longitude: Double = 0.0,
)

@JsonClass(generateAdapter = true)
data class CardBankResponse(
    @get:Json(name = "name") val name: String = "",
    @get:Json(name = "url") val url: String = "",
    @get:Json(name = "phone") val phone: String = "",
)
