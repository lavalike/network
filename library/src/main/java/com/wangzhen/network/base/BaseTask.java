package com.wangzhen.network.base;

import android.text.TextUtils;
import android.view.View;

import com.wangzhen.network.ClientLoader;
import com.wangzhen.network.Network;
import com.wangzhen.network.callback.ProgressCallback;
import com.wangzhen.network.callback.RequestCallback;
import com.wangzhen.network.loading.LoadingPage;
import com.wangzhen.network.manager.CallManager;
import com.wangzhen.network.manager.LifecycleManager;
import com.wangzhen.network.parser.ResponseParser;
import com.wangzhen.network.type.RequestType;
import com.wangzhen.network.util.ParamsBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * base task
 * Created by wangzhen on 2020/4/15.
 */
public abstract class BaseTask<EntityType> implements Task, Callback {
    private final RequestCallback<EntityType> mCallback;
    private final RequestType mRequestType;

    private Map<String, Object> mParamsMap;
    private Map<String, String> mFilesMap;
    private Map<String, Set<String>> mHeaders;
    private Object mTag;
    private LoadingPage mLoadingPage;

    public BaseTask(RequestCallback<EntityType> callback, RequestType type) {
        this.mCallback = callback;
        this.mRequestType = type;
    }

    @Override
    public Call exe() {
        return doTask();
    }

    private Call doTask() {
        String url = transformUrl();
        Request.Builder builder = new Request.Builder();
        switch (mRequestType) {
            case GET:
                builder.url(ParamsBuilder.buildGet(mParamsMap, url));
                break;
            case POST_JSON:
                builder.url(url).post(ParamsBuilder.buildPostJson(mParamsMap));
                break;
            case POST_FORM:
                builder.url(url).post(ParamsBuilder.buildPostForm(mParamsMap));
                break;
            case UPLOAD:
                builder.url(url).post(ParamsBuilder.buildUpload(mParamsMap, mFilesMap));
                break;
        }
        ParamsBuilder.buildHeaders(builder, mHeaders);

        if (mCallback instanceof ProgressCallback) {
            builder.tag(mCallback);
        }

        if (mTag instanceof View) {
            LifecycleManager.registerView((View) mTag);
        }

        if (mCallback != null) {
            mCallback.onStart();
        }

        Call call = ClientLoader.getClient().newCall(builder.build());
        CallManager.get().add(mTag, call);
        call.enqueue(this);
        return call;
    }

    private String transformUrl() {
        String api = getApi();
        if (!TextUtils.isEmpty(api) && api.startsWith("/")) {
            if (Network.sConfig != null && !TextUtils.isEmpty(Network.sConfig.baseUrl)) {
                return Network.sConfig.baseUrl + api;
            }
        }
        return api;
    }

    @Override
    public Call retry() {
        return doTask();
    }

    @Override
    public Task put(String key, Object value) {
        if (mParamsMap == null) {
            mParamsMap = new HashMap<>();
        }
        mParamsMap.put(key, value);
        return this;
    }

    @Override
    public Task putFile(String key, String filePath) {
        if (mFilesMap == null) {
            mFilesMap = new HashMap<>();
        }
        mFilesMap.put(key, filePath);
        return this;
    }

    @Override
    public Task addHeader(String name, String value) {
        if (mHeaders == null) {
            mHeaders = new HashMap<>();
        }
        Set<String> values = mHeaders.get(name);
        if (values == null) {
            values = new HashSet<>();
            mHeaders.put(name, values);
        }
        values.add(value);
        return this;
    }

    @Override
    public Task setTag(Object tag) {
        this.mTag = tag;
        return this;
    }

    @Override
    public Task setLoadingPage(LoadingPage page) {
        if (page != null) {
            page.setTask(this);
        }
        mLoadingPage = page;
        return this;
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) {
        CallManager.get().removeCall(mTag, call);
        ResponseParser.newInstance().loadingPage(mLoadingPage).parseResponse(response, mCallback);
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        CallManager.get().removeCall(mTag, call);
        ResponseParser.newInstance().loadingPage(mLoadingPage).parseError(e, mCallback);
    }
}
