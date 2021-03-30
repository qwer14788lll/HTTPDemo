package com.example.httpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.httpdemo.databinding.ActivityXmlBinding
import com.example.httpdemo.util.ContentHandler
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread

class XmlActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityXmlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityXmlBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.buttonXmlPull.setOnClickListener { sendHttp("http://10.32.151.137:8080/xml/get_data.xml","Pull") }

        mBinding.buttonXmlSax.setOnClickListener { sendHttp("http://10.32.151.137:8080/xml/get_data.xml","SAX") }
    }

    /**
     * (采用OkHttp框架)根据指定的网络地址字符串和解析方式，获取服务器返回的XML数据
     * @param stringUrl 网络地址字符串
     * @param xmlParser 指定解析方式，可选Pull或SAX
     */
    private fun sendHttp(stringUrl: String,xmlParser:String) {
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
                    mBinding.htmlText.text = if (xmlParser == "Pull") parseXMLPull(bodyString) else parseXMLSax(bodyString)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 采用SAX解析XML数据
     * @param data 需要解析的XML数据
     * @return 返回解析结果
     */
    private fun parseXMLSax(data: String): String {
        return try {
            //通过SAX工厂方法，获得解析器的实例
            val xmlParser = SAXParserFactory.newInstance().newSAXParser().xmlReader
            //初始化SAX解析方案实例
            val contentHandler = ContentHandler()
            //将解析方案设置到解析器上
            xmlParser.contentHandler = contentHandler
            //开始解析
            xmlParser.parse(InputSource(StringReader(data)))
            //将解析结果回传
            contentHandler.getStringBuilder()
        }catch (e: Exception) {
            e.printStackTrace().toString()
        }
    }

    /**
     * 采用Pull解析XML数据
     * @param data 需要解析的XML数据
     * @return 返回解析结果
     */
    private fun parseXMLPull(data: String): String {
        val stringBuilder = StringBuilder()
        try {
            //通过Pull工厂方法，获得解析器的实例
            val xmlParse = XmlPullParserFactory.newInstance().newPullParser()
            //给解析器设置输入数据
            xmlParse.setInput(StringReader(data))
            //获取当前的解析事件类型（大节点）
            var eventType = xmlParse.eventType
            var id = ""
            var name = ""
            var version = ""
            //判断当时解析的事件是否为文档结束事件
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //记录当前节点的名字
                val nodeName = xmlParse.name
                //开始解析，根据事件类型进行分支
                when (eventType) {
                    //节点开始的位置
                    XmlPullParser.START_TAG -> {
                        when (nodeName) {
                            "id" -> id = xmlParse.nextText()
                            "name" -> name = xmlParse.nextText()
                            "version" -> version = xmlParse.nextText()
                        }
                    }
                    //节点结束的位置
                    XmlPullParser.END_TAG -> {
                        if ("app" == nodeName) {
                            stringBuilder.append("应用id：").append(id).append('\n')
                            stringBuilder.append("应用名：").append(name).append('\n')
                            stringBuilder.append("版本号：").append(version).append('\n').append('\n')
                        }
                    }
                }
                //进入下一个节点
                eventType = xmlParse.next()
            }
        } catch (e: Exception) {
            stringBuilder.append(e.printStackTrace())
        } finally {
            return stringBuilder.toString()
        }
    }
}