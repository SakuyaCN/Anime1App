package com.sakuya.anime1.http

import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

class HttpProvider {
    private lateinit var retrofit: Retrofit
    var time: Long = 30000//默认30s超时
    private fun initConfig() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://anime1.me/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient())
            .build()
    }

    private fun okHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
                .build()
            it.proceed(request)
        }
        .connectTimeout(time, TimeUnit.MILLISECONDS)
        .readTimeout(time, TimeUnit.MILLISECONDS)
        .writeTimeout(time, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .build()


    /**指定超时时间*/
    fun setOutTime(time: Long): HttpProvider = let {
        it.time = time
        return it
    }

    companion object {
        @Volatile private var INSTANCE: HttpProvider?=null
        fun getInstance(): HttpProvider = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HttpProvider().also { INSTANCE = it }
        }
    }

    init {
        initConfig()
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}