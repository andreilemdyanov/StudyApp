package ru.fundamentals.studyapp.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.fundamentals.studyapp.util.API_KEY

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder().addQueryParameter("api_key", API_KEY).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}