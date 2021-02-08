package com.kryptkode.cardinfofinder.utils

import com.kryptkode.cardinfofinder.data.dispatcher.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestDispatchers(
    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
) : AppDispatchers {
    override val main: CoroutineDispatcher
        get() = dispatcher
    override val io: CoroutineDispatcher
        get() = dispatcher
    override val default: CoroutineDispatcher
        get() = dispatcher
}
