package com.kryptkode.cardinfofinder.data.usecase

import com.kryptkode.cardinfofinder.data.dispatcher.AppDispatchers
import com.kryptkode.cardinfofinder.data.error.ErrorHandler
import com.kryptkode.cardinfofinder.data.model.CardInfo
import com.kryptkode.cardinfofinder.data.model.DataState
import com.kryptkode.cardinfofinder.data.service.BinListService
import com.kryptkode.cardinfofinder.data.service.mapper.CardResponseMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCardInfoUseCase @Inject constructor(
    private val errorHandler: ErrorHandler,
    private val binListService: BinListService,
    private val mapper: CardResponseMapper,
    private val dispatchers: AppDispatchers
) {

    fun execute(cardNumber: String): Flow<DataState<CardInfo>> = flow {
        emit(DataState.Loading)
        try {
            val result = binListService.getCardInfo(cardNumber)
            emit(DataState.Success(mapper.mapToEntity(result)))
        } catch (e: Exception) {
            emit(DataState.Error(errorHandler.getErrorMessage(e)))
        }
    }.flowOn(dispatchers.io)
}
