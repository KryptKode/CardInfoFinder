package com.kryptkode.cardinfofinder.utils

import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(0, RANDOM_INT_RANGE)
    }

    fun randomLong(): Long {
        return randomInt().toLong()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < RANDOM_BOOL_NUM
    }

    private const val RANDOM_INT_RANGE = 1000
    private const val RANDOM_BOOL_NUM = 0.5
}
