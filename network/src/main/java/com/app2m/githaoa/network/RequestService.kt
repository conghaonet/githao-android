package com.app2m.githaoa.network

import com.app2m.githaoa.network.data.AuthorizationData
import com.app2m.githaoa.network.data.AuthorizationPost
import com.app2m.githaoa.network.data.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RequestService {
    @GET("weather/city/101310201")
    suspend fun getDataAsync() : Response<DataBean>

    @GET("err/weather/city/101310201")
    suspend fun getErrDataAsync() : Response<DataBean>

    @GET("user")
    suspend fun getUserAsync() : Response<UserData>

    @POST("authorizations")
    suspend fun postAuthorizations(@Body auth: AuthorizationPost = AuthorizationPost()): Response<AuthorizationData>

    @GET("user")
    suspend fun getLoginUser() : Response<UserData>
}