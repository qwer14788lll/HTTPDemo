package com.example.httpdemo.util

import com.example.httpdemo.model.App
import retrofit2.Call
import retrofit2.http.GET

interface AppService {
    @GET("json/get_data.json")
    fun getAppData(): Call<List<App>>
}