package com.app2m.githaoa.network

import com.app2m.githaoa.network.https.Tls12SocketFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object  RetrofitClient {
    const val BASE_API =  "https://api.github.com/"

    val reqApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return@lazy retrofit.create(RequestService::class.java)
    }
    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(GitHubInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.HEADERS
            })
            .enableTls12OnKitkat()
            .build()
    }
}

private fun OkHttpClient.Builder.enableTls12OnKitkat() : OkHttpClient.Builder {
    return Tls12SocketFactory.enableTls12OnKitkat(this)
}