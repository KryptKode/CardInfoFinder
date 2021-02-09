package com.kryptkode.cardinfofinder.data.service

import com.google.common.truth.Truth.assertThat
import com.kryptkode.cardinfofinder.utils.getResourceAsString
import com.kryptkode.cardinfofinder.utils.makeTestApiService
import java.net.HttpURLConnection
import kotlin.jvm.Throws
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class BinListServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: BinListService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        sut = makeTestApiService(mockWebServer)
    }

    @Test
    fun `getCardInfo is successful`() = runBlocking {

        mockHttpResponse("binlist-response.json", HttpURLConnection.HTTP_OK)

        val result = sut.getCardInfo("12344")

        assertThat(result.scheme).isEqualTo("mastercard")
        assertThat(result.type).isEqualTo("debit")
        assertThat(result.brand).isEqualTo("Debit")
    }

    @Test
    @Throws(HttpException::class)
    fun `getCardInfo fails with network errors`() = runBlocking {

        mockHttpResponse("binlist-response.json", HttpURLConnection.HTTP_GATEWAY_TIMEOUT)

        val exception = try {
            sut.getCardInfo("12344")
            null
        } catch (e: Throwable) {
            e
        }
        assertThat(exception).isInstanceOf(HttpException::class.java)
        assertThat(exception?.message).isEqualTo("HTTP 504 Server Error")
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun mockHttpResponse(fileName: String, responseCode: Int) = mockWebServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getResourceAsString(fileName))
    )
}
