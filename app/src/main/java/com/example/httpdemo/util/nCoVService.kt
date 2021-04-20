package com.example.httpdemo.util

import com.example.httpdemo.model.nCoV2019
import retrofit2.Call
import retrofit2.http.GET

interface nCoVService {
    @GET("nCoV/api/overall")
    fun getData(): Call<nCoV2019>
}