package com.wangzhen.network.task;

import com.wangzhen.network.base.BaseTask;
import com.wangzhen.network.callback.RequestCallback;
import com.wangzhen.network.type.RequestType;

/**
 * get task
 * Created by wangzhen on 2020/4/15.
 */
public abstract class GetTask extends BaseTask {
    public <EntityType> GetTask(RequestCallback<EntityType> callback) {
        super(callback, RequestType.GET);
    }
}
