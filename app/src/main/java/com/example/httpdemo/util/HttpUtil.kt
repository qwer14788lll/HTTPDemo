package com.example.httpdemo.util

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object HttpUtil {
    /**
     * 根据指定的网络地址字符串和请求方式，获取服务器返回的数据
     * @param stringUrl 网络地址字符串
     * @param request 请求方式（枚举类型）
     * @param listener 网络请求监听器
     */
    fun sendHttp(stringUrl: String, request: RequestMethod, listener: HttpCallbackListener) {
        thread {
            //提前创建网络连接对象，以便在异常结束时关闭连接
            var connection: HttpURLConnection? = null
            try {
                //将字符串地址转化为URL网络地址，打开该网络地址的连接，并转换为HttpURLConnection实例
                connection = URL(stringUrl).openConnection() as HttpURLConnection
                //设置连接超时数和读取超时数，以及更多设置
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                //如果为POST，则打开输出流，将要提交给服务器的数据写入连接
                if (request == RequestMethod.Post) {
                    connection.requestMethod = "POST"
                    val output = DataOutputStream(connection.outputStream)
                    output.writeBytes("username=admin&password=123456")
                }
                //获取服务器返回的输入流
                val input = connection.inputStream
                //将输入流读入缓存中
                val reader = BufferedReader(InputStreamReader(input))
                //创建字符串拼接对象，准备拼接字符串
                val stringBuilder = StringBuilder()
                //循环遍历逐行读数据
                reader.use { reader.forEachLine { stringBuilder.append(it) } }
                //回调接口方法
                listener.onFinish(stringBuilder.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                //回调接口方法
                listener.onError(e)
            } finally { //任务结束，关闭连接
                connection?.disconnect()
            }
        }
    }


}