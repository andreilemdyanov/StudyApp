package ru.fundamentals.studyapp.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.fundamentals.studyapp.App

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (App.instance.hasNetwork())
            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
        else
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                .build()
        return chain.proceed(request)
    }
}