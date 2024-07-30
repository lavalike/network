package com.wangzhen.network.sample.task

import com.wangzhen.network.callback.RequestCallback
import com.wangzhen.network.task.PostFormTask

/**
 * PostFormTask
 * Created by wangzhen on 2020/4/16.
 */
class TestPostFormTask(callback: RequestCallback<*>?) : PostFormTask(callback) {
    override fun getApi(): String {
        return "http://10.120.226.40:8080/wangzhen/plugin/plugin.json"
    }
}
