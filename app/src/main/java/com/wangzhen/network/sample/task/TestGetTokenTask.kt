package com.wangzhen.network.sample.task

import com.wangzhen.network.callback.RequestCallback
import com.wangzhen.network.task.GetTask

/**
 * TestGetTokenTask
 * Created by wangzhen on 2020/4/23.
 */
class TestGetTokenTask(callback: RequestCallback<*>?) : GetTask(callback) {
    override fun onSetupParams(vararg params: Any) {
        put("userName", params[0])
        put("secret", params[1])
    }

    override fun getApi(): String {
        return "https://jyai.birdbot.cn/jyapi/getToken.do"
    }
}
