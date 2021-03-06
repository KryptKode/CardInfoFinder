package com.kryptkode.cardinfofinder.ui.cardinfo

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kryptkode.cardinfofinder.data.model.CardInfo
import com.kryptkode.cardinfofinder.data.model.DataState
import com.kryptkode.cardinfofinder.data.usecase.GetCardInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

@Suppress("EXPERIMENTAL_API_USAGE")
@HiltViewModel
class CardInfoViewModel @Inject constructor(
    private val getCardInfoUseCase: GetCardInfoUseCase
) : ViewModel() {

    private val mutableViewState = MutableStateFlow(CardInfoViewState())
    val viewState = mutableViewState.asStateFlow()

    @VisibleForTesting
    val cardNumber = MutableSharedFlow<String>()

    @VisibleForTesting
    val stateReducer = { oldState: CardInfoViewState, dataState: DataState<CardInfo> ->
        when (dataState) {
            is DataState.Error -> oldState.copy(
                loading = false,
                error = true,
                errorMessage = dataState.message,
            )
            is DataState.Loading -> oldState.copy(loading = true, error = false)
            is DataState.Success -> oldState.copy(
                loading = false,
                error = false,
                cardInfo = dataState.data
            )
        }
    }

    @VisibleForTesting
    var refresh = false

    @VisibleForTesting
    val areEquivalent: (String, String) -> Boolean = { old, new ->
        refresh.not() && old == new
    }

    init {
        cardNumber
            .distinctUntilChanged(areEquivalent)
            .flatMapLatest(getCardInfoUseCase::execute)
            .scan(CardInfoViewState()) { previous, result ->
                stateReducer(previous, result)
            }.onEach {
                mutableViewState.value = it
            }
            .launchIn(viewModelScope)
    }

    suspend fun getCardInfo(number: String, refresh: Boolean = false) {
        this.refresh = refresh
        cardNumber.emit(number)
    }
}
