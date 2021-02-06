package com.kryptkode.cardinfofinder.data.service.mapper

import com.google.common.truth.Truth.assertThat
import com.kryptkode.cardinfofinder.utils.FakeDataFactory.makeFakeCardResponse
import org.junit.Before
import org.junit.Test

class CardResponseMapperTest {

    private lateinit var bankResponseMapper: CardBankResponseMapper
    private lateinit var countryResponseMapper: CardCountryResponseMapper
    private lateinit var sut: CardResponseMapper

    @Before
    fun setUp() {
        bankResponseMapper = CardBankResponseMapper()
        countryResponseMapper = CardCountryResponseMapper()
        sut = CardResponseMapper(bankResponseMapper, countryResponseMapper)
    }

    @Test
    fun `map to entity maps data`() {
        val response = makeFakeCardResponse()

        val result = sut.mapToEntity(response)

        assertThat(result.scheme).isEqualTo(response.scheme)
        assertThat(result.brand).isEqualTo(response.brand)
        assertThat(result.type).isEqualTo(response.type)
        assertThat(result.bank).isEqualTo(bankResponseMapper.mapToEntity(response.bank))
        assertThat(result.country).isEqualTo(countryResponseMapper.mapToEntity(response.country))
    }
}
