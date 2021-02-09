package com.kryptkode.cardinfofinder.ui.cardinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.kryptkode.cardinfofinder.data.model.CardInfo
import com.kryptkode.cardinfofinder.data.model.DataState
import com.kryptkode.cardinfofinder.data.usecase.GetCardInfoUseCase
import com.kryptkode.cardinfofinder.utils.FakeDataFactory.makeFakeCardInfo
import com.kryptkode.cardinfofinder.utils.MainCoroutineRule
import com.kryptkode.cardinfofinder.utils.runBlockingTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CardInfoViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var getCardInfoUseCase: GetCardInfoUseCase

    private lateinit var sut: CardInfoViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = CardInfoViewModel(getCardInfoUseCase)

        stubGetCardInfoUseCase(DataState.Success(makeFakeCardInfo()))
    }

    @Test
    fun `getCardInfo emits cardNumber`() = mainCoroutineRule.runBlockingTest {
        val cardNumber = "42424"
        sut.cardNumber.test {
            sut.getCardInfo(cardNumber)
            assertThat(expectItem()).isEqualTo(cardNumber)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCardInfo sets refresh state`() = mainCoroutineRule.runBlockingTest {
        val cardNumber = "42424"
        sut.getCardInfo(cardNumber, true)

        assertThat(sut.refresh).isTrue()
    }

    @Test
    fun `areEquivalent with refresh returns false`() = mainCoroutineRule.runBlockingTest {

        sut.refresh = true

        val result = sut.areEquivalent.invoke("42424", "42424")

        assertThat(result).isFalse()
    }

    @Test
    fun `areEquivalent with same strings and without refresh returns true`() = mainCoroutineRule.runBlockingTest {

        sut.refresh = false

        val result = sut.areEquivalent.invoke("42424", "42424")

        assertThat(result).isTrue()
    }

    @Test
    fun `areEquivalent with different strings and without refresh returns false`() = mainCoroutineRule.runBlockingTest {

        sut.refresh = false

        val result = sut.areEquivalent.invoke("424", "42424")

        assertThat(result).isFalse()
    }

    @Test
    fun `stateReducer when on DataState Error returns error state`() = mainCoroutineRule.runBlockingTest {
        val initialState = CardInfoViewState()

        val errorMessage = "Error occurred"

        val result = sut.stateReducer.invoke(initialState, DataState.Error(errorMessage))

        assertThat(result.loading).isFalse()
        assertThat(result.error).isTrue()
        assertThat(result.errorMessage).isEqualTo(errorMessage)
        assertThat(result.cardInfo).isEqualTo(initialState.cardInfo)
    }

    @Test
    fun `stateReducer when on DataState Loading returns loading state`() = mainCoroutineRule.runBlockingTest {
        val initialState = CardInfoViewState()

        val result = sut.stateReducer.invoke(initialState, DataState.Loading)

        assertThat(result.loading).isTrue()
        assertThat(result.error).isFalse()
        assertThat(result.errorMessage).isEqualTo(initialState.errorMessage)
        assertThat(result.cardInfo).isEqualTo(initialState.cardInfo)
    }

    @Test
    fun `stateReducer when on DataState Success returns success state`() = mainCoroutineRule.runBlockingTest {
        val initialState = CardInfoViewState()
        val cardInfo = makeFakeCardInfo()

        val result = sut.stateReducer.invoke(initialState, DataState.Success(cardInfo))

        assertThat(result.loading).isFalse()
        assertThat(result.error).isFalse()
        assertThat(result.errorMessage).isEqualTo(initialState.errorMessage)
        assertThat(result.cardInfo).isEqualTo(cardInfo)
    }

    @Test
    fun `getCardInfo on DataState Loading posts view state`() = mainCoroutineRule.runBlockingTest {
        stubGetCardInfoUseCase(DataState.Loading)

        sut.getCardInfo("42424")

        sut.viewState.test {
            val item = expectItem()
            assertThat(item.loading).isTrue()
            assertThat(item.error).isFalse()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCardInfo on DataState Success posts view state`() = mainCoroutineRule.runBlockingTest {
        val cardInfo = makeFakeCardInfo()

        stubGetCardInfoUseCase(DataState.Success(cardInfo))

        sut.getCardInfo("42424")

        sut.viewState.test {
            val item = expectItem()
            assertThat(item.loading).isFalse()
            assertThat(item.error).isFalse()
            assertThat(item.cardInfo).isEqualTo(cardInfo)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCardInfo on DataState Error posts view state`() = mainCoroutineRule.runBlockingTest {
        val errorMessage = "Error occurred"

        stubGetCardInfoUseCase(DataState.Error(errorMessage))

        sut.getCardInfo("42424")

        sut.viewState.test {
            val item = expectItem()
            assertThat(item.loading).isFalse()
            assertThat(item.error).isTrue()
            assertThat(item.errorMessage).isEqualTo(errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun stubGetCardInfoUseCase(result: DataState<CardInfo>) {
        every {
            getCardInfoUseCase.execute(any())
        } returns flowOf(result)
    }
}
