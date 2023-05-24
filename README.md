# smart-classroom-misc

## 首次启动

### 安装并启动Nacos
项目使用Nacos作为注册中心，安装和启动方法见 [这里](./docs/start/nacos.md)
```shell
sh startup.sh -m standalone
```

### 安装并启动RocketMQ
项目的消息队列使用RocketMQ，安装和启动方法见 [这里](./docs/start/rocketmq.md)
```shell
nohup sh bin/mqnamesrv &
nohup sh bin/mqbroker -n localhost:9876 --enable-proxy &
```

首次启动的时候需要创建一个Topic.
```shell
sh bin/mqadmin updatetopic -n localhost:9876 -t SmartClassroomTopic -c DefaultCluster
```

### 导入数据库
数据库文件位于 docs/db 完全导入到自己的数据库中。

修改配置文件中的jdbc配置
src/main/resources/application-repository.properties
```properties
spring.datasource.primary.jdbc-url=
spring.datasource.primary.username=
spring.datasource.primary.password=
```

如果需要做二次开发，也需要修改saber工具中的jdbc配置：
src/main/java/com/smart/classroom/misc/tool/saber/config/SaberConfig.java

### 打包facade.
因为smart-classroom-misc和smart-classroom-subscription两个项目的facade包相互依赖，而我们又没有将其上传至中央仓库。

因此在启动前，需要先到这两个项目下各自打包facade，安装到本地仓库中。

```shell
cd smart-classroom-misc
./mvnw install -pl scm-facade -DskipTests

cd smart-classroom-subscription
./mvnw install -pl scs-facade -DskipTests
```


### 启动应用.
分别运行两个项目的main文件即可：
```shell
cd smart-classroom-misc
src/main/java/com/smart/classroom/misc/MiscApplication.java

cd smart-classroom-subscription
src/main/java/com/smart/classroom/subscription/SubscriptionApplication.java
```


### 验证
```shell
# 健康检查接口有内容返回表示成功
curl http://localhost:6500/api/index/healthy
```
```text
{"code":"OK","data":"服务健康, 现在时间：2023-05-22 16:36:00 远程调用结果：Hello,你好. 我是smart-classroom-subscription,状态正常 现在时间：2023-05-22 16:36:00","msg":"成功"}
```

如果你也将前端项目 smart-classroom-front 启动了，那么就能够看到对应的页面了。

## 日常启动

### 启动Nacos
项目使用Nacos作为注册中心，安装和启动方法见 [这里](./docs/start/nacos.md)
```shell
sh startup.sh -m standalone
```

### 启动RocketMQ
项目的消息队列使用RocketMQ，安装和启动方法见 [这里](./docs/start/rocketmq.md)
```shell
nohup sh bin/mqnamesrv &
nohup sh bin/mqbroker -n localhost:9876 --enable-proxy &
```

### 启动应用.
分别运行两个项目的main文件即可：
```shell
cd smart-classroom-misc
src/main/java/com/smart/classroom/misc/MiscApplication.java

cd smart-classroom-subscription
src/main/java/com/smart/classroom/subscription/SubscriptionApplication.java
```

如果你也将前端项目 smart-classroom-front 启动了，那么就能够看到对应的页面了。
