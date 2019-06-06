package com.sakuya.anime1.http

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import okhttp3.ResponseBody

object HttpFactory {
    val provider = HttpProvider.getInstance().create(HttpService::class.java)

    fun getMain(consumer: Consumer<ResponseBody>, error: Consumer<Throwable>): Disposable {
        return provider.getMain()
            .subscribeOnNextConsumerAndErrorConsumer(cacheExecutor, consumer,error)
    }
}