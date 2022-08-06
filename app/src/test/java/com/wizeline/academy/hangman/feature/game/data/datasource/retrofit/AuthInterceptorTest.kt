package com.wizeline.academy.hangman.feature.game.data.datasource.retrofit

import com.google.common.truth.Truth.assertThat
import okhttp3.Request
import org.junit.Before
import org.junit.Test

class AuthInterceptorTest {

    private val expectedApiKey = "test-api-key"

    private lateinit var subjectUnderTest: AuthInterceptor

    @Before
    fun setUp() {
        subjectUnderTest = AuthInterceptor(expectedApiKey)
    }

    @Test
    fun `assert that intercept adds the auth query param`() {
        val request = request()
        val fakeChain = FakeChain(request)

        subjectUnderTest.intercept(fakeChain)

        val result = fakeChain.result
        assertThat(result).isNotNull()
        result?.let {
            assertThat(it.url).isNotNull()
            assertThat(it.url.queryParameter(AuthInterceptor.QUERY_PARAM_API_KEY)).isNotNull()
            assertThat(it.url.queryParameter(AuthInterceptor.QUERY_PARAM_API_KEY)).isEqualTo(
                expectedApiKey
            )
        }
    }

    private fun request() = Request.Builder()
        .url("https://wizeline.com")
        .get()
        .build()
}