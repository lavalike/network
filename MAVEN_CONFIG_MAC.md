# maven instructions for mac

### 1、 创建GPG密钥

打开终端，执行：

``` shell
brew install gpg
```

等待安装完成。

### 2、创建密钥

执行：

``` shell
gpg --full-gen-key
```

在执行过程中，加密方式选择RSA and RSA，长度输入4096，过期时间直接回车代表不过期，然后提示让输入User ID和一个邮箱，这里可以使用注册SonaType时候用的用户名和邮箱，当然也可以用其他的。接着最后一步输入O，注意这里是大写的O不是零，然后回车接着就会提示让输入密码，输入一次后还会有一次确认输入，这个密码一定要记住后期会用到。

操作完成后，个人目录下会生成如下目录：

> ~/.gnupg/openpgp-revocs.d

目录下存在一个.rev文件，后续配置会用到该.rec文件名的末尾8位字符。

### 3、将密钥上传到服务器

> 公钥ID：.rev文件名末尾8位字符

``` shell
gpg --keyserver hkp://pool.sks-keyservers.net --send-keys 公钥ID
gpg --keyserver hkp://keyserver.ubuntu.com:11371 --send-keys 公钥ID

查询
gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys 公钥ID
```

### 4、创建gpg文件

切换到~/.gnupg目录，执行：

``` shell
gpg --export-secret-keys -o secring.gpg
```

secring.gpg文件创建过程中，输入创建密钥时设置的密码，密码验证成功后会在~/.gnupg目录生成secring.gpg文件。

### 5、创建gradle.properties配置文件

在用户目录.gradle目录下创建gradle.properties，完整路径：

> ~/.gradle/gradle.properties

文件内容：

```
signing.keyId=.rev文件名称末尾8位字符
signing.password=创建密钥时所设置密码
signing.secretKeyRingFile=secring.gpg文件路径
ossUsername=sonatype用户名
ossPassword=sonatype密码
```

### 6、上传

sync之后，在gradle面板中执行upload/uploadArchives，上传成功后会出现以下提示

```
...
> Task :library:uploadArchives

BUILD SUCCESSFUL in 1m 7s
21 actionable tasks: 21 executed
3:25:06 PM: Task execution finished 'uploadArchives'.
```

### 7、发布

登录[https://s01.oss.sonatype.org/](https://s01.oss.sonatype.org/)，选择左侧的 **Staging Repositories**
![](https://tva1.sinaimg.cn/large/008i3skNgy1gpuvbw5r9tj319l0u010a.jpg)

选中刚上传的包，切换到 **Content** 选项卡，可以查看包内文件
![](https://tva1.sinaimg.cn/large/008i3skNgy1gpuvdc03zdj319l0u0n5i.jpg)

确认无误后，点击 **close**，填写描述信息，点击 **Confirm**
![](https://tva1.sinaimg.cn/large/008i3skNgy1gpuvgm1e7lj319l0u0qba.jpg)

正常情况下，等待几分钟后，**Activity** 选项卡中出现 **Repository closed** 即为操作成功，然后点击 **Release** 按钮即可发布到 MavenCentral, 等待几个小时后可以在 [https://search.maven.org/](https://search.maven.org/) 查询发布结果

