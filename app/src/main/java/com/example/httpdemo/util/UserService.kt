package com.example.httpdemo.util

import com.example.httpdemo.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") userName:String, @Field("password") password:String): Call<ResponseBody>
}