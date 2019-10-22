package com.app2m.githaoa.network

import com.app2m.githaoa.network.https.Tls12SocketFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object  RetrofitClient {
    private const val BASE_URL =  "http://t.weather.sojson.com/api/"
    val reqApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(Json.nonstrict.asConverterFactory("application/json".toMediaType()))
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
        return@lazy retrofit.create(RequestService::class.java)
    }
    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BASIC
            })
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
//            .addInterceptor(SlpRequestInterceptor())
            .enableTls12OnKitkat()
            .build()
    }
}

private fun OkHttpClient.Builder.enableTls12OnKitkat() : OkHttpClient.Builder {
    return Tls12SocketFactory.enableTls12OnKitkat(this)
}