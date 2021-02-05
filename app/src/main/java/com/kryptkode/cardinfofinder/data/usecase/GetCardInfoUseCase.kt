package com.kryptkode.cardinfofinder.data.usecase

import com.kryptkode.cardinfofinder.data.dispatcher.AppDispatchers
import com.kryptkode.cardinfofinder.data.model.CardInfo
import com.kryptkode.cardinfofinder.data.model.DataState
import com.kryptkode.cardinfofinder.data.service.BinListService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCardInfoUseCase(
    private val binListService: BinListService,
    private val dispatchers: AppDispatchers
) {

    fun execute(query: String): Flow<DataState<CardInfo>> = flow {
        emit(DataState.Loading)
        try {
            val result = binListService.searchBooks(query)
            emit(DataState.Success(CardInfo()))
        } catch (e: Exception) {
            emit(DataState.Error(e.localizedMessage ?: "An error occurred"))
        }
    }.flowOn(dispatchers.io)
}