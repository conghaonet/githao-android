package com.app2m.githaoa.network

import com.app2m.githaoa.network.data.UserData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RequestService {
    @GET("weather/city/101310201")
    suspend fun getDataAsync() : Response<DataBean>

    @GET("err/weather/city/101310201")
    suspend fun getErrDataAsync() : Response<DataBean>

    @GET("user")
    suspend fun getUserAsync() : Response<UserData>
}