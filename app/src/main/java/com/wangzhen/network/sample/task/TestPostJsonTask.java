package com.wangzhen.network.sample.task;

import com.wangzhen.network.callback.RequestCallback;
import com.wangzhen.network.task.PostJsonTask;

/**
 * TestPostJsonTask
 * Created by wangzhen on 2020/4/16.
 */
public class TestPostJsonTask extends PostJsonTask {
    public <EntityType> TestPostJsonTask(RequestCallback<EntityType> callback) {
        super(callback);
    }

    @Override
    public String getApi() {
        return "http://10.120.226.40:8080/wangzhen/plugin/plugin.json";
    }
}
