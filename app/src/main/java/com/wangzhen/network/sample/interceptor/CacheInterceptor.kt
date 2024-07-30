package com.wangzhen.network.sample.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * CacheInterceptor
 * Created by wangzhen on 2020/9/5.
 */
class CacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // 设置响应的缓存时间为60秒，即设置Cache-Control头，并移除pragma消息头，因为pragma也是控制缓存的一个消息头属性
        return chain.proceed(chain.request()).newBuilder()
            .removeHeader("pragma")
            .header("Cache-Control", "max-age=60")
            .build()
    }
}
