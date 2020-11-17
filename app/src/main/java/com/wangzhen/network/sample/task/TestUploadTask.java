package com.wangzhen.network.sample.task;

import com.wangzhen.network.callback.RequestCallback;
import com.wangzhen.network.task.UploadTask;

/**
 * TestUploadTask
 * Created by wangzhen on 2020/4/16.
 */
public class TestUploadTask extends UploadTask {
    public <EntityType> TestUploadTask(RequestCallback<EntityType> callback) {
        super(callback);
    }

    @Override
    public String getApi() {
        return "https://jyai.birdbot.cn/jyapi/audioFile2text.do";
    }
}
