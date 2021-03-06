package com.wangzhen.network;

import android.content.Context;

import com.wangzhen.network.config.NetConfig;

/**
 * Network
 * Created by wangzhen on 2020/4/15.
 */
public final class Network {
    public static Context sContext;
    public static NetConfig sConfig;

    public static void init(NetConfig config) {
        init(null, config);
    }

    public static void init(Context context, NetConfig config) {
        if (context != null) {
            sContext = context.getApplicationContext();
        }
        sConfig = config;
    }
}
