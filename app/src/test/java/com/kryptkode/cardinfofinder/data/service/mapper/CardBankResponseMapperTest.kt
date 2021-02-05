package com.kryptkode.cardinfofinder.data.service.mapper

import com.google.common.truth.Truth.assertThat
import com.kryptkode.cardinfofinder.utils.FakeDataFactory.makeFakeCardBankResponse
import org.junit.Before
import org.junit.Test


class CardBankResponseMapperTest {

    private lateinit var sut: CardBankResponseMapper

    @Before
    fun setUp() {
        sut = CardBankResponseMapper()
    }

    @Test
    fun `map to entity maps data`() {
        val response = makeFakeCardBankResponse()

        val result = sut.mapToEntity(response)

        assertThat(result.name).isEqualTo(response.name)
        assertThat(result.phone).isEqualTo(response.phone)
        assertThat(result.url).isEqualTo(response.url)
    }
}
