package com.wangzhen.network.sample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wangzhen.network.callback.LoadingCallback;
import com.wangzhen.network.callback.ProgressCallback;
import com.wangzhen.network.loading.DefaultLoadingPage;
import com.wangzhen.network.sample.entity.PluginVersion;
import com.wangzhen.network.sample.task.TestGetTask;
import com.wangzhen.network.sample.task.TestPostFormTask;
import com.wangzhen.network.sample.task.TestPostJsonTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TestGetTask testGetTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post_json).setOnClickListener(this);
        findViewById(R.id.btn_post_form).setOnClickListener(this);
        findViewById(R.id.btn_loading_page).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                get();
                break;
            case R.id.btn_post_json:
                postJson();
                break;
            case R.id.btn_post_form:
                postForm();
                break;
            case R.id.btn_loading_page:
                startActivity(new Intent(this, LoadingActivity.class));
                break;
        }
    }

    private void postForm() {
        new TestPostFormTask(new ProgressCallback<PluginVersion>() {
            @Override
            public void onStart() {
                Log.e("TAG", "-> onStart");
            }

            @Override
            public void onProgress(int progress) {
                Log.e("TAG", "-> postForm onProgress " + progress + " " + Thread.currentThread().getName());
            }

            @Override
            public void onSuccess(PluginVersion data) {
                Toast.makeText(MainActivity.this, "Post Form -> " + data.version_name + " " + data.version_description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(MainActivity.this, "Post Form -> " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "-> onComplete");
            }
        }).setLoadingPage(new DefaultLoadingPage(findViewById(R.id.container))).exe();
    }

    private void postJson() {
        new TestPostJsonTask(new LoadingCallback<PluginVersion>() {
            @Override
            public void onSuccess(PluginVersion data) {
                Toast.makeText(MainActivity.this, "Post Json -> " + data.version_name + " " + data.version_description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(MainActivity.this, "Post Json -> " + message, Toast.LENGTH_SHORT).show();
            }
        }).exe();
    }

    private void get() {
        if (testGetTask != null) {
            Toast.makeText(this, "重试", Toast.LENGTH_SHORT).show();
            testGetTask.retry();
            return;
        }
        testGetTask = new TestGetTask(new LoadingCallback<PluginVersion>() {
            @Override
            public void onSuccess(PluginVersion data) {
                Toast.makeText(MainActivity.this, "Get -> " + data.version_name + " " + data.version_description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(MainActivity.this, "Get -> " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "请求被取消", Toast.LENGTH_SHORT).show();
            }
        });
        testGetTask.setTag(this).exe();
    }
}
