package com.wangzhen.network.task;

import com.wangzhen.network.base.BaseTask;
import com.wangzhen.network.callback.RequestCallback;
import com.wangzhen.network.type.RequestType;

/**
 * upload task
 * Created by wangzhen on 2020/4/16.
 */
public abstract class UploadTask extends BaseTask {
    public <EntityType> UploadTask(RequestCallback<EntityType> callback) {
        super(callback, RequestType.UPLOAD);
    }
}
