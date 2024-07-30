package com.wangzhen.network.base;

import com.wangzhen.network.loading.LoadingPage;

import okhttp3.Call;

/**
 * network task callback
 * Created by wangzhen on 2020/4/15.
 */
public interface Task {
    String getApi();

    Task put(String key, Object value);

    Task putFile(String key, String filePath);

    Task addHeader(String name, String value);

    Task setTag(Object tag);

    Task setLoadingPage(LoadingPage page);

    Call exe();

    Call retry();
}
