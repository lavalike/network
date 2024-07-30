package com.wangzhen.network.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wangzhen.network.callback.LoadingCallback;
import com.wangzhen.network.loading.DefaultLoadingPage;
import com.wangzhen.network.sample.entity.PluginVersion;
import com.wangzhen.network.sample.task.TestPostFormTask;

/**
 * loading page activity
 * Created by wangzhen on 2020/10/16.
 */
public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        View recycler = findViewById(R.id.recycler);

        new TestPostFormTask(new LoadingCallback<PluginVersion>() {
            @Override
            public void onSuccess(PluginVersion data) {
                Toast.makeText(LoadingActivity.this, data.version_description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(LoadingActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }).setLoadingPage(new DefaultLoadingPage(recycler).setDelay(3000)).exe();
    }
}