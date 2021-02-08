package com.kryptkode.cardinfofinder.data.error

import com.google.common.truth.Truth.assertThat
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.util.StringResource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import org.junit.Before
import org.junit.Test

class ErrorHandlerTest {
    private lateinit var stringResource: StringResource
    private lateinit var sut: ErrorHandler

    @Before
    fun setup() {
        stringResource = mockk()
        sut = ErrorHandler(stringResource)
        stubErrorMessage("")
    }


    @Test
    fun `getErrorMessage with ConnectException returns error message`() {
        val message = "Error occurred"
        stubErrorMessage(message)
        val result = sut.getErrorMessage(ConnectException())

        assertThat(result).isEqualTo(message)
    }

    @Test
    fun `getErrorMessage with ConnectException calls string resource with correct ID`() {
        val message = "Error occurred"
        stubErrorMessage(message)

        sut.getErrorMessage(ConnectException())

        verify {
            stringResource.getString(R.string.connect_exception)
        }
    }


    @Test
    fun `getErrorMessage with UnknownHostException returns error message`() {
        val message = "Error occurred"
        stubErrorMessage(message)
        val result = sut.getErrorMessage(UnknownHostException())

        assertThat(result).isEqualTo(message)
    }

    @Test
    fun `getErrorMessage with UnknownHostException calls string resource with correct ID`() {
        val message = "Error occurred"
        stubErrorMessage(message)

        sut.getErrorMessage(UnknownHostException())

        verify {
            stringResource.getString(R.string.unknown_host_exception)
        }
    }

    @Test
    fun `getErrorMessage with SocketTimeoutException returns error message`() {
        val message = "Error occurred"
        stubErrorMessage(message)
        val result = sut.getErrorMessage(SocketTimeoutException())

        assertThat(result).isEqualTo(message)
    }

    @Test
    fun `getErrorMessage with SocketTimeoutException calls string resource with correct ID`() {
        val message = "Error occurred"
        stubErrorMessage(message)

        sut.getErrorMessage(SocketTimeoutException())

        verify {
            stringResource.getString(R.string.timed_out_exception)
        }
    }

    @Test
    fun `getErrorMessage with unexpected exception returns error message`() {
        val message = "Error occurred"
        stubErrorMessage(message)
        val result = sut.getErrorMessage(Exception())

        assertThat(result).isEqualTo(message)
    }

    @Test
    fun `getErrorMessage with unexpected exception calls string resource with correct ID`() {
        val message = "Error occurred"
        stubErrorMessage(message)

        sut.getErrorMessage(Exception())

        verify {
            stringResource.getString(R.string.unknown_exception)
        }
    }

    private fun stubErrorMessage(message: String) {
        every {
            stringResource.getString(any())
        } returns message
    }

}
