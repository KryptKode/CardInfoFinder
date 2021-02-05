package com.kryptkode.cardinfofinder.data.service.mapper

import com.google.common.truth.Truth.assertThat
import com.kryptkode.cardinfofinder.utils.FakeDataFactory.makeFakeCardCountryResponse
import org.junit.Before
import org.junit.Test

class CardCountryResponseMapperTest {

    private lateinit var sut: CardCountryResponseMapper

    @Before
    fun setUp() {
        sut = CardCountryResponseMapper()
    }

    @Test
    fun `map to entity maps data`() {
        val response = makeFakeCardCountryResponse()

        val result = sut.mapToEntity(response)

        assertThat(result.numeric).isEqualTo(response.numeric)
        assertThat(result.alpha2).isEqualTo(response.alpha2)
        assertThat(result.name).isEqualTo(response.name)
        assertThat(result.emoji).isEqualTo(response.emoji)
        assertThat(result.currency).isEqualTo(response.currency)
        assertThat(result.latitude).isEqualTo(response.latitude)
        assertThat(result.longitude).isEqualTo(response.longitude)
    }
}
