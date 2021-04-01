package com.example.httpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.httpdemo.databinding.ActivityJsonBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

class JsonActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityJsonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityJsonBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.buttonJsonObject.setOnClickListener {
            sendHttp(
                "http://10.32.151.137:8080/json/db.json",
                "JSONObject"
            )
        }

        mBinding.buttonJsonGson.setOnClickListener { }
    }

    /**
     * (采用OkHttp框架)根据指定的网络地址字符串和解析方式，获取服务器返回的Json数据
     * @param stringUrl 网络地址字符串
     * @param jsonParser 指定解析方式，可选JSONObject或GSON
     */
    private fun sendHttp(stringUrl: String, jsonParser: String) {
        thread {
            try {
                //创建网络连接客户端对象
                val client = OkHttpClient()
                //构建Request对象
                val request: Request = Request.Builder()
                    .url(stringUrl)
                    .build()
                val response = client.newCall(request).execute()
                val bodyString = response.body?.string() ?: "目标地址暂无响应，请检查网络连接后重试"
                runOnUiThread {
                    mBinding.htmlText.text =
                        if (jsonParser == "JSONObject") parseJSONObject(bodyString) else parseGSON(
                            bodyString
                        )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseJSONObject(data: String): String {
        val stringBuilder = StringBuilder()
        try {
//            val jsonArray = JSONArray(data)
//            for (i in 0 until jsonArray.length()) {
//                val jsonObject = jsonArray.getJSONObject(i)
//                val id = jsonObject.getString("id")
//                val name = jsonObject.getString("name")
//                val version = jsonObject.getString("version")
//                stringBuilder.append("应用id：").append(id).append('\n')
//                stringBuilder.append("应用名：").append(name).append('\n')
//                stringBuilder.append("版本号：").append(version).append('\n').append('\n')
//            }
            val json = JSONObject(data)
            val jsonArray = json.getJSONArray("slides")
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("id")
                val image = jsonObject.getString("image")
                val link = jsonObject.getString("link")
                stringBuilder.append("id：").append(id).append('\n')
                stringBuilder.append("图片地址：").append(image).append('\n')
                stringBuilder.append("链接：").append(link).append('\n').append('\n')
            }
        } catch (e: Exception) {
            stringBuilder.append(e.printStackTrace())
        } finally {
            return stringBuilder.toString()
        }
    }

    private fun parseGSON(data: String): String {
        TODO("Not yet implemented")
    }


}