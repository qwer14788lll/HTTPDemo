package com.example.httpdemo.util

import kotlin.Exception

interface HttpCallbackListener {
    /**
     * 当服务器响应我们的网络请求时调用（成功）
     * @param response 服务器返回的数据
     */
    fun onFinish(response: String)

    /**
     * 当服务器未响应我们的网络请求时调用（失败）
     * @param e 网络错误信息
     */
    fun onError(e: Exception)
}