package com.kryptkode.cardinfofinder.ui.walkthrough

import com.google.common.truth.Truth.assertThat
import com.kryptkode.cardinfofinder.util.StringResource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test

class WalkThroughItemProviderTest {

    @MockK
    lateinit var stringResource: StringResource
    private lateinit var sut: WalkThroughItemProvider

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = WalkThroughItemProvider(stringResource)
        stubStringResource()
    }

    @Test
    fun `getItems creates 4 items`() {
        val result = sut.getItems()
        assertThat(result.size).isEqualTo(4)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun stubStringResource() {
        every {
            stringResource.getString(any())
        } returns ""
    }
}
