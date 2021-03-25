package com.example.httpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.httpdemo.databinding.ActivityOkHttpBinding
import okhttp3.*
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class OkHttpActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityOkHttpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityOkHttpBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.buttonSendGet.setOnClickListener { sendHttp() }

        mBinding.buttonSendPost.setOnClickListener { sendHttp("http://10.32.151.137:8080/login", "POST") }
    }

    /**
     * (采用OkHttp框架)根据指定的网络地址字符串和请求方式，获取服务器返回的数据
     * @param stringUrl 网络地址字符串
     * @param requestMethod 请求方式
     */
    private fun sendHttp(stringUrl: String = "http://www.baidu.com", requestMethod: String = "GET") {
        thread {
            try {
                //创建网络连接客户端对象
                val client = OkHttpClient()
                val request: Request
                if (requestMethod == "GET") {
                    //构建Request对象
                    request = Request.Builder()
                        .url(stringUrl)
                        .build()
                } else {
                    //构建需要提交给服务器的参数
                    val requestBody = FormBody.Builder()
                        .add("username", "admin")
                        .add("password", "123456")
                        .build()
                    request = Request.Builder()
                        .post(requestBody)
                        .url(stringUrl)
                        .build()
                }
                val response = client.newCall(request).execute()
                val bodyString = response.body?.string() ?: "目标地址暂无响应，请检查网络连接后重试"
                runOnUiThread { mBinding.htmlText.text = bodyString }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }
}