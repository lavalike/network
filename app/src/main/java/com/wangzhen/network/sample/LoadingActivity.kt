package com.wangzhen.network.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wangzhen.network.callback.LoadingCallback
import com.wangzhen.network.loading.DefaultLoadingPage
import com.wangzhen.network.sample.databinding.ActivityLoadingBinding
import com.wangzhen.network.sample.entity.PluginVersion
import com.wangzhen.network.sample.task.TestPostFormTask

/**
 * loading page activity
 * Created by wangzhen on 2020/10/16.
 */
class LoadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TestPostFormTask(object : LoadingCallback<PluginVersion>() {
            override fun onSuccess(data: PluginVersion) {
                Toast.makeText(this@LoadingActivity, data.version_description, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onError(code: Int, message: String) {
                Toast.makeText(this@LoadingActivity, message, Toast.LENGTH_SHORT).show()
            }
        }).setLoadingPage(DefaultLoadingPage(binding.recycler).setDelay(3000)).exe()
    }
}