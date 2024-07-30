package com.wangzhen.network.sample.entity

/**
 * PluginVersion
 * Created by wangzhen on 2020/4/15.
 */
/**
 * version_name : 0.0.1
 * version_code : 3
 * version_description : 功能更新
 * url : http://192.168.188.199:8080/wangzhen/plugin/apk/app-release.apk
 */
data class PluginVersion(
    val version_name: String? = null,
    val version_code: Int = 0,
    val version_description: String? = null,
    val url: String? = null
)
