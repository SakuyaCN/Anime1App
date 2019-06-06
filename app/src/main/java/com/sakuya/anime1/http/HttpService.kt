package com.sakuya.anime1.http

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface HttpService {

    @GET("/")
    fun getMain(): Observable<ResponseBody>

}