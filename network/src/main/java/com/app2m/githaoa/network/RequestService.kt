package com.app2m.githaoa.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RequestService {
    @GET("weather/city/101310201")
    suspend fun getDataAsync() : Response<DataBean>

    @GET("err/weather/city/101310201")
    suspend fun getErrDataAsync() : Response<DataBean>
}