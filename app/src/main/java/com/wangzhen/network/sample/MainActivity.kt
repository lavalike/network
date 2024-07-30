package com.wangzhen.network.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wangzhen.network.callback.LoadingCallback
import com.wangzhen.network.callback.ProgressCallback
import com.wangzhen.network.loading.DefaultLoadingPage
import com.wangzhen.network.manager.CallManager
import com.wangzhen.network.sample.databinding.ActivityMainBinding
import com.wangzhen.network.sample.entity.PluginVersion
import com.wangzhen.network.sample.task.TestGetTask
import com.wangzhen.network.sample.task.TestPostFormTask
import com.wangzhen.network.sample.task.TestPostJsonTask

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var testGetTask: TestGetTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvents()
    }

    private fun setEvents() {
        with(binding) {
            btnGet.setOnClickListener {
                get()
            }
            btnPostJson.setOnClickListener {
                postJson()
            }
            btnPostForm.setOnClickListener {
                postForm()
            }
            btnLoadingPage.setOnClickListener {
                startActivity(Intent(it.context, LoadingActivity::class.java))
            }
        }
    }

    private fun postForm() {
        TestPostFormTask(object : ProgressCallback<PluginVersion>() {
            override fun onStart() {
                Log.e("TAG", "-> onStart")
            }

            override fun onProgress(progress: Int) {
                Log.e(
                    "TAG", "-> postForm onProgress " + progress + " " + Thread.currentThread().name
                )
            }

            override fun onSuccess(data: PluginVersion) {
                Toast.makeText(
                    this@MainActivity,
                    "Post Form -> " + data.version_name + " " + data.version_description,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(code: Int, message: String) {
                Toast.makeText(this@MainActivity, "Post Form -> $message", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onComplete() {
                Log.e("TAG", "-> onComplete")
            }
        }).setLoadingPage(DefaultLoadingPage(findViewById(R.id.container))).exe()
    }

    private fun postJson() {
        TestPostJsonTask(object : LoadingCallback<PluginVersion>() {
            override fun onSuccess(data: PluginVersion) {
                Toast.makeText(
                    this@MainActivity,
                    "Post Json -> " + data.version_name + " " + data.version_description,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(code: Int, message: String) {
                Toast.makeText(this@MainActivity, "Post Json -> $message", Toast.LENGTH_SHORT)
                    .show()
            }
        }).exe()
    }

    private fun get() {
        if (testGetTask != null) {
            Toast.makeText(this, "重试", Toast.LENGTH_SHORT).show()
            testGetTask!!.retry()
            return
        }
        testGetTask = TestGetTask(object : LoadingCallback<PluginVersion>() {
            override fun onSuccess(data: PluginVersion) {
                Toast.makeText(
                    this@MainActivity,
                    "Get -> " + data.version_name + " " + data.version_description,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(code: Int, message: String) {
                Toast.makeText(this@MainActivity, "Get -> $message", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Toast.makeText(this@MainActivity, "请求被取消", Toast.LENGTH_SHORT).show()
            }
        })
        testGetTask!!.setTag(this).exe()
    }
}
