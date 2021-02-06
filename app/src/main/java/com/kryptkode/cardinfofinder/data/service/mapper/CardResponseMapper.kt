package com.kryptkode.cardinfofinder.data.service.mapper

import com.kryptkode.cardinfofinder.data.model.CardInfo
import com.kryptkode.cardinfofinder.data.service.response.CardInfoResponse
import javax.inject.Inject

class CardResponseMapper @Inject constructor(
    private val bankMapper: CardBankResponseMapper,
    private val countryMapper: CardCountryResponseMapper
) : Mapper<CardInfoResponse, CardInfo> {

    override fun mapToEntity(response: CardInfoResponse): CardInfo {
        return CardInfo(
            response.scheme,
            response.brand,
            response.type,
            bankMapper.mapToEntity(response.bank),
            countryMapper.mapToEntity(response.country),
        )
    }
}
