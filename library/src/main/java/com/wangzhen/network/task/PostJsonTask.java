package com.wangzhen.network.task;

import com.wangzhen.network.base.BaseTask;
import com.wangzhen.network.callback.RequestCallback;
import com.wangzhen.network.type.RequestType;

/**
 * post json task
 * Created by wangzhen on 2020/4/15.
 */
public abstract class PostJsonTask extends BaseTask {
    public <EntityType> PostJsonTask(RequestCallback<EntityType> callback) {
        super(callback, RequestType.POST_JSON);
    }
}
