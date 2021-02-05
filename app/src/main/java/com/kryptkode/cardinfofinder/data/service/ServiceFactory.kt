package com.kryptkode.cardinfofinder.data.service

import com.kryptkode.cardinfofinder.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {

    private const val MAX_TIMEOUT_SECS = 60L
    private const val BASE_URL = "https://lookup.binlist.net/"

    fun createBinListService(): BinListService {
        return makeBinListService(Moshi.Builder().build(), BuildConfig.DEBUG)
    }

    private fun makeBinListService(moshi: Moshi, isDebug: Boolean): BinListService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug))
        )
        return makeBinListService(okHttpClient, moshi)
    }

    private fun makeBinListService(client: OkHttpClient, moshi: Moshi): BinListService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(BinListService::class.java)
    }

    private fun makeOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()

            .addInterceptor(interceptor)
            .connectTimeout(MAX_TIMEOUT_SECS, TimeUnit.SECONDS)
            .readTimeout(MAX_TIMEOUT_SECS, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}
