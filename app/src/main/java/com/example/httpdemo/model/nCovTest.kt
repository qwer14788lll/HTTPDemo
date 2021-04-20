package com.example.httpdemo.model

data class nCovTest(
    val results: List<Result>,
    val success: Boolean
)

data class Result(
    val confirmedCount: Int,
    val confirmedIncr: Int,
    val curedCount: Int,
    val curedIncr: Int,
    val currentConfirmedCount: Int,
    val currentConfirmedIncr: Int,
    val deadCount: Int,
    val deadIncr: Int,
    val generalRemark: String,
    val globalStatistics: GlobalStatistics,
    val note1: String,
    val note2: String,
    val note3: String,
    val remark1: String,
    val remark2: String,
    val remark3: String,
    val remark4: String,
    val remark5: String,
    val seriousCount: Int,
    val seriousIncr: Int,
    val suspectedCount: Int,
    val suspectedIncr: Int,
    val updateTime: Long
)

data class GlobalStatistics(
    val confirmedCount: Int,
    val confirmedIncr: Int,
    val curedCount: Int,
    val curedIncr: Int,
    val currentConfirmedCount: Int,
    val currentConfirmedIncr: Int,
    val deadCount: Int,
    val deadIncr: Int
)