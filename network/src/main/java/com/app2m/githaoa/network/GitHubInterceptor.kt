package com.app2m.githaoa.network

import okhttp3.Headers
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class GitHubInterceptor: Interceptor {
    companion object {
        const val API_VERSION = "application/vnd.github.v3+json"
        const val REQUEST_MEDIA_TYPE = "application"
        const val REQUEST_MEDIA_SUB_TYPE = "json"
        const val CONTENT_TYPE = "$REQUEST_MEDIA_TYPE/$REQUEST_MEDIA_SUB_TYPE"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        var builder = originalRequest.newBuilder()
        //这一步是为了urlEncode，否则url包含特殊字符时会出错。
//        val originalRequestUrl = HttpUrl.parse(originalRequest.url.toString()).uri().toString()
        val originalRequestUrl = originalRequest.url.toUrl().toHttpUrlOrNull()?.toUri().toString()
        builder = builder.url(originalRequestUrl)
        builder.header("accept", API_VERSION)
            .header("content-type", CONTENT_TYPE)
            .header("authorization", SharedPreferencesUtil.getAuthorizationBasic() ?: "")
//            .header("authorization", "Basic Y29uZ2hhb25ldDp2NTA1TU5DUA==")
        val request = builder.build()
        val response = chain.proceed(request)
        //异常处理
        if (response.code >= 400) {

        }
        return response

    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return !contentEncoding.isNullOrBlank() && !contentEncoding.equals("identity", ignoreCase = true)
    }
}