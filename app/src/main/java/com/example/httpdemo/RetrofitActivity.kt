package com.example.httpdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.httpdemo.databinding.ActivityRetrofitBinding
import com.example.httpdemo.model.App
import com.example.httpdemo.model.nCoV2019
import com.example.httpdemo.util.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRetrofitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.buttonGet.setOnClickListener {
            //startActivity<MainActivity>(applicationContext)
            //val appService = ServiceCreator.create(AppService::class.java)
            val appService = ServiceCreator.create<AppService>()
            val stringBuilder = StringBuilder()
            //调用接口方法，发送相应的网络请求
            appService.getAppData().enqueue(object : Callback<List<App>> {
                override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                    val list = response.body()
                    if (list != null) {
                        for (app in list) {
                            Log.i("response", app.id)
                            Log.i("response", app.name)
                            Log.i("response", app.version)
                            stringBuilder.append("应用id：").append(app.id).append('\n')
                            stringBuilder.append("应用名：").append(app.name).append('\n')
                            stringBuilder.append("版本号：").append(app.version).append('\n')
                                .append('\n')
                        }
                    }
                    mBinding.htmlText.text = stringBuilder.toString()
                }

                override fun onFailure(call: Call<List<App>>, t: Throwable) {
                    t.printStackTrace()
                    stringBuilder.append(t)
                    mBinding.htmlText.text = stringBuilder.toString()
                }
            })
        }

        mBinding.buttonNcov.setOnClickListener {
//            val retrofit = Retrofit.Builder()
//                .baseUrl("https://lab.isaaclin.cn/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//            val nCoVService = retrofit.create(nCoVService::class.java)
            val nCoVService = ServiceCreator.create(nCoVService::class.java)
            val stringBuilder = StringBuilder()
            nCoVService.getData().enqueue(object : Callback<nCoV2019> {
                override fun onResponse(call: Call<nCoV2019>, response: Response<nCoV2019>) {
                    val ncov = response.body()
                    val list = ncov?.results
                    if (list != null) {
                        for (overall in list) {
                            stringBuilder.append("现存确诊人数：").append(overall.currentConfirmedCount)
                                .append('\n')
                            stringBuilder.append("累计确诊人数：").append(overall.confirmedCount)
                                .append('\n')
                            mBinding.htmlText.text = stringBuilder.toString()
                        }
                    }
                }

                override fun onFailure(call: Call<nCoV2019>, t: Throwable) {
                    t.printStackTrace()
                    stringBuilder.append(t)
                    mBinding.htmlText.text = stringBuilder.toString()
                }
            })
        }

        mBinding.buttonPost.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.32.151.137:8080/")
                .build()
            val userService = retrofit.create(UserService::class.java)
            val stringBuilder = StringBuilder()
            userService.login("admin", "123456").enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.i("response", response.toString())
                    val body = response.body()?.string()
                    Log.i("response", body.toString())
                    stringBuilder.append(body)
                    mBinding.htmlText.text = stringBuilder.toString()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                    mBinding.htmlText.text = t.toString()
                }
            })
        }
    }
}