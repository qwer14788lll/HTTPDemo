package com.example.httpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.httpdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.webview.settings.javaScriptEnabled = true
        mBinding.webview.webViewClient = WebViewClient()
        mBinding.webview.loadUrl("https://www.baidu.com/")
        mBinding.button.setOnClickListener {
            val str = mBinding.editTextTextPersonName.text
            when (mBinding.spinner.selectedItem.toString()) {
                "百度" -> mBinding.webview.loadUrl("https://www.baidu.com/s?wd=${str}")
                "必应" -> mBinding.webview.loadUrl("https://www.sogou.com/web?query=${str}")
                "搜狗" -> mBinding.webview.loadUrl("https://www.so.com/s?q=${str}")
                "360" -> mBinding.webview.loadUrl("https://cn.bing.com/search?q=${str}")
            }
        }
    }
}