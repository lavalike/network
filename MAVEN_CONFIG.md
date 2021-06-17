# maven instructions

[![](https://tva1.sinaimg.cn/large/008i3skNgy1grld2985l2j319v0u0b2a.jpg)](https://bintray.com/)

### 一、注册和激活SonaType

[https://issues.sonatype.org/secure/Dashboard.jspa](https://issues.sonatype.org/secure/Dashboard.jspa)

### 二、申请组件

![](https://tva1.sinaimg.cn/large/008i3skNgy1grldigo841j31dk0u0wri.jpg)

注意

* 项目选择： **Community Support - Open Source Project Repository Hosting**
* 类型选择：**New Project**
* 概要：描述组件功能
* Group Id：[查看要求](https://central.sonatype.org/publish/requirements/coordinates/)
* Project URL：开源项目地址
* SCM URL：项目的git地址，后缀通常为.git

### 三、申请密钥

为了确保中央存储库中组件的质量水平，OSSRH对提交的文件有明确的要求，第一个文件都要有一个对应的asc文件，即 [GPG](http://c.biancheng.net/view/4832.html) 签名文件。

#### 安装GPG

windows安装：

[https://www.gpg4win.org/get-gpg4win.html](https://www.gpg4win.org/get-gpg4win.html)

macOS：

``` shell
brew install gpg
```

#### 创建密钥

``` shell
gpg --full-gen-key
```

在执行过程中，加密方式选择 **(1) RSA and RSA**，长度输入 **4096**，过期时间直接回车代表永不过期

![](https://tva1.sinaimg.cn/large/008i3skNgy1grlegr1l7uj311w0r87a1.jpg)

输入真实姓名和电子邮箱，输入大写字母O确认，两次确认密码后，密钥创建成功。

![](https://tva1.sinaimg.cn/large/008i3skNgy1grleo6gsy6j311w0r8wl5.jpg)

个人目录下会生成以下目录

> ~/.gnupg/openpgp-revocs.d

取该目录下.rev文件名末尾8位字符作为公钥ID

#### 上传密钥

``` shell
gpg --keyserver hkp://pool.sks-keyservers.net --send-keys 公钥ID
gpg --keyserver hkp://keyserver.ubuntu.com:11371 --send-keys 公钥ID

查询
gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys 公钥ID
```

#### 创建GPG文件

切换到 **~/.gnupg** 目录执行：

``` shell
gpg --export-secret-keys -o secring.gpg
```

输入创建密钥时设置的密码，密码验证成功会在 **~/.gnupg** 目录生成 **secring.gpg** 文件。

#### 创建gradle.properties

在用户目录.gradle目录下创建gradle.properties

> ~/.gradle/gradle.properties

写入内容

```
signing.keyId=.rev文件名称末尾8位字符
signing.password=创建密钥时所设置密码
signing.secretKeyRingFile=secring.gpg文件路径
ossUsername=sonatype用户名
ossPassword=sonatype密码
```

### 四、配置脚本

``` gradle
apply plugin: 'maven'
apply plugin: 'signing'

def PUBLISH_GROUP_ID = 'io.github.lavalike'
def PUBLISH_ARTIFACT_ID = "network"
def PUBLISH_VERSION = "0.0.2"

signing {
    required { gradle.taskGraph.hasTask("uploadArchives") }
    sign configurations.archives
}

uploadArchives {
    repositories {
        mavenDeployer {

            beforeDeployment { MavenDeployment deployment -> signing.signPom(deployment) }

            repository(url: "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
                authentication(userName: ossUsername, password: ossPassword)
            }

            snapshotRepository(url: "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
                authentication(userName: ossUsername, password: ossPassword)
            }

            pom.groupId = PUBLISH_GROUP_ID
            pom.artifactId = PUBLISH_ARTIFACT_ID
            pom.version = PUBLISH_VERSION

            pom.project {
                name 'network'
                packaging 'aar'
                description '基于OKHttp的网络组件'
                url 'http://github.com/lavalike'

                scm {
                    connection 'scm:https://github.com/lavalike/network.git'
                    developerConnection 'scm:git@github.com:lavalike/network.git'
                    url 'https://github.com/lavalike/network'
                }

                licenses {
                    license {
                        name 'The Apache License, Version 2.0'
                        url 'http://www.apache.org/licenses/LICENSE-2.0.txt'
                    }
                }

                developers {
                    developer {
                        id 'lavalike'
                        name 'wangzhen'
                        email 'lavalike@yeah.net'
                    }
                }
            }
        }
    }
}

task androidSourcesJar(type: Jar) {
    classifier = 'sources'
    from android.sourceSets.main.java.srcDirs
}

artifacts {
    archives androidSourcesJar
}
```

### 五、上传组件

sync之后，在gradle面板中执行upload/uploadArchives，上传成功后会出现以下提示

```
...
> Task :library:uploadArchives

BUILD SUCCESSFUL in 1m 7s
21 actionable tasks: 21 executed
3:25:06 PM: Task execution finished 'uploadArchives'.
```

### 六、发布到mavenCentral

登录[https://s01.oss.sonatype.org/](https://s01.oss.sonatype.org/)，选择左侧的 **Staging Repositories**
![](https://tva1.sinaimg.cn/large/008i3skNgy1gpuvbw5r9tj319l0u010a.jpg)

选中刚上传的包，切换到 **Content** 选项卡，可以查看包内文件
![](https://tva1.sinaimg.cn/large/008i3skNgy1gpuvdc03zdj319l0u0n5i.jpg)

确认无误后，点击 **close**，填写描述信息，点击 **Confirm**
![](https://tva1.sinaimg.cn/large/008i3skNgy1gpuvgm1e7lj319l0u0qba.jpg)

正常情况下，等待几分钟后，**Activity** 选项卡中出现 **Repository closed** 即为操作成功，然后点击 **Release** 按钮即可发布到 MavenCentral, 等待几个小时后可以在 [https://search.maven.org/](https://search.maven.org/) 查询发布结果


