package com.example.httpdemo

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.httpdemo.databinding.ActivityHttpConnectionBinding
import com.example.httpdemo.util.HttpCallbackListener
import com.example.httpdemo.util.HttpUtil
import com.example.httpdemo.util.RequestMethod
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class HttpConnectionActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHttpConnectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHttpConnectionBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.buttonSendGet.setOnClickListener {
            //sendHttp()
            val url = "http://www.baidu.com"
            HttpUtil.sendHttp(url, RequestMethod.GET, object : HttpCallbackListener {
                override fun onFinish(response: String) {
                    runOnUiThread {
                        mBinding.htmlText.text = response
                    }
                }

                override fun onError(e: Exception) {
                    runOnUiThread {
                        mBinding.htmlText.text = e.toString()
                    }
                }
            })
        }

        mBinding.buttonSendPost.setOnClickListener {
            //sendHttp("http://10.32.151.137:8080/login","POST")
        }
    }
}