package com.kryptkode.cardinfofinder.data.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.kryptkode.cardinfofinder.data.dispatcher.AppDispatchers
import com.kryptkode.cardinfofinder.data.error.ErrorHandler
import com.kryptkode.cardinfofinder.data.model.CardInfo
import com.kryptkode.cardinfofinder.data.model.DataState
import com.kryptkode.cardinfofinder.data.service.BinListService
import com.kryptkode.cardinfofinder.data.service.mapper.CardResponseMapper
import com.kryptkode.cardinfofinder.data.service.response.CardInfoResponse
import com.kryptkode.cardinfofinder.utils.FakeDataFactory.makeFakeCardInfo
import com.kryptkode.cardinfofinder.utils.FakeDataFactory.makeFakeCardResponse
import com.kryptkode.cardinfofinder.utils.MainCoroutineRule
import com.kryptkode.cardinfofinder.utils.TestDispatchers
import com.kryptkode.cardinfofinder.utils.runBlockingTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.lang.Exception
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCardInfoUseCaseTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var errorHandler: ErrorHandler
    private lateinit var binListService: BinListService
    private lateinit var mapper: CardResponseMapper
    private lateinit var dispatchers: AppDispatchers

    private lateinit var sut: GetCardInfoUseCase

    @Before
    fun setup() {
        errorHandler = mockk()
        binListService = mockk()
        mapper = mockk()
        dispatchers = TestDispatchers()

        sut = GetCardInfoUseCase(errorHandler, binListService, mapper, dispatchers)

        stubErrorHandler("")
        stubBinListService(makeFakeCardResponse())
        stubMapper(makeFakeCardInfo())
    }


    @Test
    fun `execute emits loading state initially`() = coroutineRule.runBlockingTest {
        sut.execute("").test {
            assertThat(expectItem()).isEqualTo(DataState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `execute emits success state`() = coroutineRule.runBlockingTest {
        val testCardResponse = makeFakeCardResponse()
        val cardInfo = makeFakeCardInfo()
        stubBinListService(testCardResponse)
        stubMapper(cardInfo)
        val cardNumber = "1233"
        sut.execute(cardNumber).test {
            assertThat(expectItem()).isEqualTo(DataState.Loading)
            assertThat(expectItem()).isEqualTo(DataState.Success(cardInfo))
            expectComplete()
        }
    }

    @Test
    fun `execute on success calls binListService with passed param`() = coroutineRule.runBlockingTest {
        val testCardResponse = makeFakeCardResponse()
        val cardInfo = makeFakeCardInfo()
        stubBinListService(testCardResponse)
        stubMapper(cardInfo)

        val cardNumber = "1233"
        sut.execute(cardNumber).test {

            coVerify {
                binListService.getCardInfo(cardNumber)
            }

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `execute on success calls mapper`() = coroutineRule.runBlockingTest {
        val testCardResponse = makeFakeCardResponse()
        val cardInfo = makeFakeCardInfo()
        stubBinListService(testCardResponse)
        stubMapper(cardInfo)
        val cardNumber = "1233"
        sut.execute(cardNumber).test {
            verify {
                mapper.mapToEntity(testCardResponse)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `execute emits error state`() = coroutineRule.runBlockingTest {
        val message = "Error occurred"
        val exception = Exception(message)
        stubErrorHandler(message)
        stubBinListServiceException(exception)

        sut.execute("1233").test {
            assertThat(expectItem()).isEqualTo(DataState.Loading)
            assertThat(expectItem()).isEqualTo(DataState.Error(message))
            expectComplete()
        }
    }

    @Test
    fun `execute on error calls error handler`() = coroutineRule.runBlockingTest {
        val message = "Error occurred"
        val exception = Exception(message)
        stubErrorHandler(message)
        stubBinListServiceException(exception)

        sut.execute("1233").test {

            verify {
                errorHandler.getErrorMessage(exception)
            }

            cancelAndConsumeRemainingEvents()
        }
    }

    private fun stubErrorHandler(message: String) {
        every {
            errorHandler.getErrorMessage(any())
        } returns message
    }

    private fun stubBinListService(cardInfoResponse: CardInfoResponse) {
        coEvery {
            binListService.getCardInfo(any())
        } returns cardInfoResponse
    }

    private fun stubBinListServiceException(throwable: Throwable) {
        coEvery {
            binListService.getCardInfo(any())
        } throws throwable
    }

    private fun stubMapper(cardInfo: CardInfo){
        every {
            mapper.mapToEntity(any())
        }returns cardInfo
    }
}
