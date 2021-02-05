package com.kryptkode.cardinfofinder.data.service

import com.kryptkode.cardinfofinder.data.service.response.CardInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BinListService {

    @GET("{cardNumber}")
    suspend fun getCardInfo(
        @Path("cardNumber") cardNumber: String,
    ): CardInfoResponse
}

