# network

> 基于OkHttp3网络框架

[![Platform](https://p0-xtjj-private.juejin.cn/tos-cn-i-73owjymdk6/4cd5360d82f04d97bff4bcd018d907a1~tplv-73owjymdk6-jj-mark:0:0:0:0:q75.awebp?policy=eyJ2bSI6MywidWlkIjoiMjUwNjU0MjI0MDI0Nzk0MyJ9\&rk3s=e9ecf3d6\&x-orig-authkey=f32326d3454f2ac7e96d3d06cdbb035152127018\&x-orig-expires=1722408299\&x-orig-sign=fq4bVV7ED7UmKRmM%2Fm%2BsgN20CyY%3D)](https://www.android.com)
[![](https://p0-xtjj-private.juejin.cn/tos-cn-i-73owjymdk6/f6bb05c4eb1d47c1b613af0855246d9b~tplv-73owjymdk6-jj-mark:0:0:0:0:q75.awebp?policy=eyJ2bSI6MywidWlkIjoiMjUwNjU0MjI0MDI0Nzk0MyJ9\&rk3s=e9ecf3d6\&x-orig-authkey=f32326d3454f2ac7e96d3d06cdbb035152127018\&x-orig-expires=1722408299\&x-orig-sign=SrulS27FeiGfYIMCY6oOgWBAH4Y%3D)](https://jitpack.io/#lavalike/network)

[mavenCentral迁移指南](MAVEN_CONFIG.md)

### 依赖导入

项目根目录

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

模块目录

```gradle
dependencies {
	implementation 'com.github.lavalike:network:0.0.3'
}
```

### 初始化配置

请在合适的地方进行初始化：

```java
Network.init(NetConfig.Builder().baseUrl(YOUR_BASE_URL).build());
```

> 也支持**开箱即用**，即：忽略初始化配置，**但需要在发起请求时指定完整 URL**。

NetConfig支持的方法：

```java
public static class Builder {
    public Builder baseUrl(String baseUrl);
    public Builder netInterceptor(Interceptor interceptor);
    public Builder interceptor(Interceptor interceptor);
    public CookieJar cookieJar;
    public Builder retryOnConnectionFailure(boolean retry);
    public Builder connectTimeout(long connectTimeout);
    public Builder readTimeout(long readTimeout);
    public Builder writeTimeout(long writeTimeout);
    public Builder debug(boolean debug);
    public NetConfig build();
}
```

### 请求类型

|     类型     |     继承关系     |
| :--------: | :----------: |
|     GET    |    GetTask   |
| POST\_JSON | PostJsonTask |
| POST\_FORM | PostFormTask |
|   UPLOAD   |  UploadTask  |

网络请求Task支持方法

```java
public interface Task {
    void onSetupParams(Object... params);
    String getApi();
    Task put(String key, Object value);
    Task putFile(String key, String filePath);
    Task addHeader(String name, String value);
    Task setTag(Object tag);
    Task setLoadingPage(LoadingPage page);
    Call exe(Object... params);
    Call retry();
}
```

### 发起请求

链式传递参数

```java
new TestTask(new ProgressCallback<String>() {
	@Override
	public void onSuccess(String data) {

	}

	@Override
	public void onProgress(int progress) {

	}
}).setTag(this)
	.put("param", "value")
	.putFile("sourceFile", "file url")
	.addHeader("header1","value1")
        .addHeader("header2","value2")
	.exe();
```

无需重写onSetupParams方法

### 设置加载页

setLoadingPage(LoadingPage page)

> **注意**：\
> 需要在LoadingPage中传入有效的布局才能成功显示Loading效果

1、使用默认加载页 **DefaultLoadingPage**

```java
new TestTask(new LoadingCallback<String>() {
    @Override
    public void onSuccess(String data) {
    
    }
}).setLoadingPage(new DefaultLoadingPage(recycler)).exe();
```

2、LoadingPage自定义成功延迟回调与加载时长

```java
new DefaultLoadingPage(recycler).setDelay(3000).setDuration(2000)
```

3、自定义加载页

继承 **AbsLoadingPage** 实现自定义加载 UI 及逻辑

### 错误输出

初始化配置时，**debug** 默认为 **false**，输出较为友好的统一提示，配置为 **true** 可输出具体错误信息

```java
Network.init(new NetConfig.Builder()
        .debug(true)
        .build());
```
