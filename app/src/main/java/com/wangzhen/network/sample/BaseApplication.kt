package com.wangzhen.network.sample

import android.app.Application
import com.wangzhen.network.Network
import com.wangzhen.network.config.NetConfig
import com.wangzhen.network.sample.interceptor.CacheInterceptor
import com.wangzhen.network.sample.interceptor.UrlInterceptor

/**
 * BaseApplication
 * Created by wangzhen on 2020/4/15.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Network.init(
            NetConfig.Builder()
                .netInterceptor(CacheInterceptor())
                .interceptor(UrlInterceptor())
                .build()
        )
    }
}
