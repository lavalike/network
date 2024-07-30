package com.wangzhen.network.sample.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * UrlInterceptor
 * Created by wangzhen on 2020/4/17.
 */
class UrlInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        Log.e("TAG", "-> UrlInterceptor " + request.url.toString())
        return chain.proceed(request)
    }
}
