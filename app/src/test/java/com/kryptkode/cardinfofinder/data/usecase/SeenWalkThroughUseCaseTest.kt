package com.kryptkode.cardinfofinder.data.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.kryptkode.cardinfofinder.data.dispatcher.AppDispatchers
import com.kryptkode.cardinfofinder.data.local.KeyValueStore
import com.kryptkode.cardinfofinder.utils.MainCoroutineRule
import com.kryptkode.cardinfofinder.utils.TestDispatchers
import com.kryptkode.cardinfofinder.utils.runBlockingTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SeenWalkThroughUseCaseTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var keyValueStore: KeyValueStore
    private lateinit var sut: SeenWalkThroughUseCase
    private lateinit var dispatchers: AppDispatchers

    @Before
    fun setup() {
        keyValueStore = mockk()
        dispatchers = TestDispatchers()
        sut = SeenWalkThroughUseCase(keyValueStore, dispatchers)
    }


    @Test
    fun `setSeenWalkthrough calls KeyValueStore with passed parameter`() = coroutineRule.runBlockingTest {
        stubKeyValueStore()

        val value = false
        sut.setSeenWalkthrough(value)

        coVerify {
            keyValueStore.setSeenWalkthrough(value)
        }
    }

    @Test
    fun `seenWalkthrough calls KeyValueStore`() = coroutineRule.runBlockingTest {
        stubKeyValueStore(false)

        sut.seenWalkthrough().test {
            verify {
                keyValueStore.seenWalkthrough
            }
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `seenWalkthrough returns data`() = coroutineRule.runBlockingTest {
        stubKeyValueStore(true)

        sut.seenWalkthrough().test {
            assertThat(expectItem()).isTrue()
            expectComplete()
        }
    }

    private fun stubKeyValueStore() {
        coEvery {
            keyValueStore.setSeenWalkthrough(any())
        } returns Unit
    }

    private fun stubKeyValueStore(value: Boolean) {
        coEvery {
            keyValueStore.seenWalkthrough
        } returns flowOf(value)
    }

}
