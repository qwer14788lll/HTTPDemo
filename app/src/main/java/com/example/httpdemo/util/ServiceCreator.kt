package com.example.httpdemo.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "http://10.32.151.137:8080/"

    //构建Retrofit对象
    private val retrofit = Retrofit.Builder()
        //设定根地址
        .baseUrl(BASE_URL)
        //设定json转换器
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}