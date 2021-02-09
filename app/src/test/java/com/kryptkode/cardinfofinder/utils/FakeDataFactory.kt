package com.kryptkode.cardinfofinder.utils

import com.kryptkode.cardinfofinder.data.model.CardBank
import com.kryptkode.cardinfofinder.data.model.CardCountry
import com.kryptkode.cardinfofinder.data.model.CardInfo
import com.kryptkode.cardinfofinder.data.service.response.CardBankResponse
import com.kryptkode.cardinfofinder.data.service.response.CardCountryResponse
import com.kryptkode.cardinfofinder.data.service.response.CardInfoResponse
import com.kryptkode.cardinfofinder.utils.DataFactory.randomString

object FakeDataFactory {

    fun makeFakeCardBankResponse(): CardBankResponse {
        return CardBankResponse(
            randomString(),
            randomString(),
            randomString(),
        )
    }

    fun makeFakeCardCountryResponse(): CardCountryResponse {
        return CardCountryResponse(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
        )
    }

    fun makeFakeCardResponse(): CardInfoResponse {
        return CardInfoResponse(
            randomString(),
            randomString(),
            randomString(),
            makeFakeCardCountryResponse(),
            makeFakeCardBankResponse()
        )
    }

    fun makeFakeCardBank(): CardBank {
        return CardBank(
            randomString(),
            randomString(),
            randomString(),
        )
    }

    fun makeFakeCardCountry(): CardCountry {
        return CardCountry(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
        )
    }

    fun makeFakeCardInfo(): CardInfo {
        return CardInfo(
            randomString(),
            randomString(),
            randomString(),
            makeFakeCardBank(),
            makeFakeCardCountry()
        )
    }
}
