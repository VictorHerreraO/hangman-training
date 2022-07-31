package com.wizeline.academy.hangman.feature.game.data.datasource.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val apiKey: String
) : Interceptor {
    private companion object {
        const val QUERY_PARAM_API_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = chain.request().url
        val url = originalUrl.newBuilder()
            .addQueryParameter(QUERY_PARAM_API_KEY, apiKey)
            .build()
        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
