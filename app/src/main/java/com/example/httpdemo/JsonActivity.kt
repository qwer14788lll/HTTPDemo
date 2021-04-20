package com.example.httpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.httpdemo.databinding.ActivityJsonBinding
import com.example.httpdemo.model.App
import com.example.httpdemo.model.nCoV2019
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

        mBinding.buttonJsonGson.setOnClickListener {
            sendHttp(
                "https://lab.isaaclin.cn/nCoV/api/overall",
                "GSON"
            )
        }
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
                val bodyString = response.body()?.string() ?: "目标地址暂无响应，请检查网络连接后重试"
                runOnUiThread {
                    mBinding.htmlText.text = if (jsonParser == "JSONObject") parseJSONObject(bodyString) else parseGSON(bodyString)
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
//        //初始化gson对象
//        val gson = Gson()
//        //核准数据类型
//        val typeOf = object : TypeToken<List<App>>() {}.type
//        //开始解析
//        val appList = gson.fromJson<List<App>>(data, typeOf)
//        val stringBuilder = StringBuilder()
//        for (app in appList) {
//            stringBuilder.append("id：").append(app.id).append('\n')
//            stringBuilder.append("名称：").append(app.name).append('\n')
//            stringBuilder.append("版本：").append(app.version).append('\n').append('\n')
//        }
//        return stringBuilder.toString()
        val gson = Gson()
        val list = gson.fromJson(data, nCoV2019::class.java)
        val overallList = list.results
        val stringBuilder = StringBuilder()
        for (overall in overallList){
            stringBuilder.append("现存确诊人数：").append(overall.currentConfirmedCount).append('\n')
            stringBuilder.append("累计确诊人数：").append(overall.confirmedCount).append('\n')
        }
        return stringBuilder.toString()
    }
}