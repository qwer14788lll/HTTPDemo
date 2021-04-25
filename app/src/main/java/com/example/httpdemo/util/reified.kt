package com.example.httpdemo.util

import android.content.Context
import android.content.Intent

inline fun <reified T> startActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}