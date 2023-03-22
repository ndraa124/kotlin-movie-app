package com.id22.core.data.source.remote.network.config

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class QueryInterceptor : Interceptor {

    companion object {
        private const val API_KEY = "2174d146bb9c0eab47529b2e77d6b526"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val originalHttpUrl: HttpUrl = chain.request().url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        proceed(request().newBuilder().url(url).build())
    }
}
