package com.wangzhen.network.task;

import com.wangzhen.network.base.BaseTask;
import com.wangzhen.network.callback.RequestCallback;
import com.wangzhen.network.type.RequestType;

/**
 * post form task
 * Created by wangzhen on 2020/4/15.
 */
public abstract class PostFormTask extends BaseTask {
    public <EntityType> PostFormTask(RequestCallback<EntityType> callback) {
        super(callback, RequestType.POST_FORM);
    }
}
