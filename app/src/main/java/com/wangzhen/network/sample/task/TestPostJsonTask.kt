package com.wangzhen.network.sample.task

import com.wangzhen.network.callback.RequestCallback
import com.wangzhen.network.task.PostJsonTask

/**
 * TestPostJsonTask
 * Created by wangzhen on 2020/4/16.
 */
class TestPostJsonTask(callback: RequestCallback<*>?) : PostJsonTask(callback) {
    override fun getApi(): String {
        return "http://10.120.226.40:8080/wangzhen/plugin/plugin.json"
    }
}
