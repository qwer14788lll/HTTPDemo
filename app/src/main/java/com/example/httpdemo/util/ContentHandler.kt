package com.example.httpdemo.util

import android.util.Log
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class ContentHandler : DefaultHandler() {
    private var nodeName = ""
    private lateinit var id: StringBuilder
    private lateinit var name: StringBuilder
    private lateinit var version: StringBuilder
    private lateinit var stringBuilder:StringBuilder

    override fun startDocument() {
        id = StringBuilder()
        name = StringBuilder()
        version = StringBuilder()
        stringBuilder = StringBuilder()
    }

    override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {
        nodeName = localName
        Log.d("TAG","uri:$uri")
        Log.d("TAG","localName当前节点:$nodeName")
        Log.d("TAG","qName:$qName")
        Log.d("TAG","attributes:$attributes")
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        when(nodeName){
            "id" -> id.append(ch,start,length)
            "name" -> name.append(ch,start,length)
            "version" -> version.append(ch,start,length)
        }
    }

    override fun endElement(uri: String, localName: String, qName: String) {
        if ("app" == localName) {
            Log.d("TAG","uri:$uri")
            Log.d("TAG","localName当前节点:$localName")
            Log.d("TAG","qName:$qName")
            stringBuilder.append("应用id：").append(id.toString().trim()).append('\n')
            stringBuilder.append("应用名：").append(name.toString().trim()).append('\n')
            stringBuilder.append("版本号：").append(version.toString().trim()).append('\n').append('\n')
            id.setLength(0)
            name.setLength(0)
            version.setLength(0)
        }
    }

    public fun getStringBuilder() = stringBuilder.toString()
}