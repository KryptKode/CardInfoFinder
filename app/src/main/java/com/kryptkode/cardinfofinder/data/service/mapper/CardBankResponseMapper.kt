package com.kryptkode.cardinfofinder.data.service.mapper

import com.kryptkode.cardinfofinder.data.model.CardBank
import com.kryptkode.cardinfofinder.data.service.response.CardBankResponse
import javax.inject.Inject

class CardBankResponseMapper @Inject constructor() : Mapper<CardBankResponse, CardBank> {
    override fun mapToEntity(response: CardBankResponse): CardBank {
        return CardBank(response.name, response.url, response.phone)
    }
}
