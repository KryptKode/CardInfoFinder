package com.kryptkode.cardinfofinder.data.service.mapper

import com.kryptkode.cardinfofinder.data.model.CardCountry
import com.kryptkode.cardinfofinder.data.service.response.CardCountryResponse
import javax.inject.Inject

class CardCountryResponseMapper @Inject constructor() : Mapper<CardCountryResponse, CardCountry> {
    override fun mapToEntity(response: CardCountryResponse): CardCountry {
        return CardCountry(
            response.numeric,
            response.alpha2,
            response.name,
            response.emoji,
            response.currency,
            response.latitude,
            response.longitude,
        )
    }
}
