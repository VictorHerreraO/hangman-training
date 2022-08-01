package com.wizeline.academy.hangman.feature.game.data.datasource.retrofit

import okhttp3.*
import java.util.concurrent.TimeUnit

class FakeChain(
    private val request: Request
) : Interceptor.Chain {

    var result: Request? = null

    override fun request(): Request = request

    override fun proceed(request: Request): Response = Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_1_1)
        .code(200)
        .message("OK")
        .build()
        .also { result = request }

    override fun call(): Call {
        TODO("Not yet implemented")
    }

    override fun connectTimeoutMillis(): Int {
        TODO("Not yet implemented")
    }

    override fun connection(): Connection? {
        TODO("Not yet implemented")
    }

    override fun readTimeoutMillis(): Int {
        TODO("Not yet implemented")
    }

    override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        TODO("Not yet implemented")
    }

    override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        TODO("Not yet implemented")
    }

    override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        TODO("Not yet implemented")
    }

    override fun writeTimeoutMillis(): Int {
        TODO("Not yet implemented")
    }
}
