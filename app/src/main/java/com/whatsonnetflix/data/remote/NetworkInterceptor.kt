package com.whatsonnetflix.data.remote

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import timber.log.Timber
import java.io.IOException

class NetworkInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            val requestWithHeaders = request.newBuilder()
                .addHeader("X-RapidAPI-Key", "70c53275f4msh676abc3ec64f40dp1e5bccjsn507899abc973")
                .addHeader("X-RapidAPI-Host", "unogs-unogs-v1.p.rapidapi.com")
                .build()
            chain.proceed(requestWithHeaders)
        } catch (e: Exception) {
            Timber.i("Interceptor error: $e")
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(999)
                .message(e.message!!)
                .body("{${e}}".toResponseBody(null)).build()
        }
    }
}