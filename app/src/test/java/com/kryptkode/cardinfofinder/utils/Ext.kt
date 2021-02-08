package com.kryptkode.cardinfofinder.utils


import com.google.common.io.Resources.getResource
import com.kryptkode.cardinfofinder.data.service.BinListService
import com.squareup.moshi.Moshi
import java.io.File
import java.net.URL
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun getResourceAsString(path: String): String {
    val uri: URL = getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}

private val okHttpClient: OkHttpClient
    get() = OkHttpClient.Builder().build()

private val moshi: Moshi
    get() = Moshi.Builder().build()

fun makeTestApiService(mockWebServer: MockWebServer): BinListService = Retrofit.Builder()
    .baseUrl(mockWebServer.url("/"))
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()
    .create(BinListService::class.java)
