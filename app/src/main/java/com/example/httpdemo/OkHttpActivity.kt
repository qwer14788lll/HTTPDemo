package com.example.httpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.httpdemo.databinding.ActivityOkHttpBinding
import com.example.httpdemo.util.HttpUtil
import com.example.httpdemo.util.RequestMethod
import okhttp3.*
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
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

        mBinding.buttonSendGet.setOnClickListener {
            //sendHttp()
            val url = "http://www.baidu.com"
            HttpUtil.sendHttp(url, RequestMethod.GET, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        mBinding.htmlText.text = e.toString()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        mBinding.htmlText.text = response.body()?.string()
                    }
                }
            })
        }

        mBinding.buttonSendPost.setOnClickListener {
            //sendHttp("http://10.32.151.137:8080/login", "POST")
            val url = "http://10.32.151.137:8080/login"
            HttpUtil.sendHttp(url,RequestMethod.Post,object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        mBinding.htmlText.text = e.toString()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        mBinding.htmlText.text = response.body()?.string()
                    }
                }
            })
        }
    }
}