package com.id22.core.data.source.remote.network.config

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.id22.core.BuildConfig
import com.id22.core.data.source.remote.network.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    fun chuckerInterceptor(context: Context): ChuckerInterceptor = run {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR,
        )

        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    fun provideOkHttpClient(chucker: ChuckerInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(chucker)
            .addInterceptor(QueryInterceptor())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    fun getApiClient(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
