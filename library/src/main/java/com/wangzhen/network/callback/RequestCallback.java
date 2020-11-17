package com.wangzhen.network.callback;

import androidx.annotation.MainThread;

import com.wangzhen.network.type.ErrorType;

/**
 * base callback
 * Created by wangzhen on 2020/4/15.
 */
public interface RequestCallback<ResultType> {
    @MainThread
    void onStart();

    @MainThread
    void onSuccess(ResultType data);

    /**
     * @param code    code
     * @param message message
     * @see ErrorType
     */
    @MainThread
    void onError(int code, String message);

    @MainThread
    void onCancel();

    @MainThread
    void onComplete();
}
