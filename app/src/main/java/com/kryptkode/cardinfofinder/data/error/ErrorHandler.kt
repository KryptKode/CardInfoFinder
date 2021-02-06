package com.kryptkode.cardinfofinder.data.error

import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.util.StringResource
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandler @Inject constructor(private val stringResource: StringResource) {

    fun getErrorMessage(e: Throwable?): String {
        if (e is ConnectException) {
            return getString(R.string.connect_exception)
        }
        if (e is UnknownHostException) {
            return getString(R.string.unknown_host_exception)
        }
        if (e is SocketTimeoutException) {
            return getString(R.string.timed_out_exception)
        }

        return getString(R.string.unknown_exception)
    }
    private fun getString(resId: Int): String {
        return stringResource.getString(resId)
    }
}
