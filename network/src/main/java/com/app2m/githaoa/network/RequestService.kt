package com.app2m.githaoa.network

import com.app2m.githaoa.network.data.AuthorizationData
import com.app2m.githaoa.network.data.AuthorizationPost
import com.app2m.githaoa.network.data.RepoData
import com.app2m.githaoa.network.data.UserData
import retrofit2.Response
import retrofit2.http.*

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

    @GET("user/repos")
    suspend fun getMyRepos(
        @Query("page") page: Int = 1,
        @Query("type") type: String ="all",
        @Query("sort") sort: String ="full_name",
        @Query("direction") direction: String ="asc"
    ) : Response<List<RepoData>>

    @GET("users/{login}/starred")
    suspend fun getStarredRepos(
        @Path("login") login: String,
        @Query("page") page: Int = 1,
        @Query("sort") sort: String ="full_name",
        @Query("direction") direction: String ="asc"
    ) : Response<List<RepoData>>
}