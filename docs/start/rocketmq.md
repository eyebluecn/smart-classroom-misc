# Nacos使用方法
## 下载
从 [官网](https://rocketmq.apache.org/zh/docs/quickStart/01quickstart/) 下载，比如下载rocketmq-all-5.1.0-bin-release.zip

## 启动NameServer
```shell
$ nohup sh bin/mqnamesrv &

### 验证namesrv是否启动成功
$ tail -f ~/logs/rocketmqlogs/namesrv.log
The Name Server boot success...
```


## 启动Broker+Proxy
```shell
### 先启动broker
$ nohup sh bin/mqbroker -n localhost:9876 --enable-proxy &

### 验证broker是否启动成功, 比如, broker的ip是192.168.1.2 然后名字是broker-a
$ tail -f ~/logs/rocketmqlogs/proxy.log 
The broker[broker-a,192.169.1.2:10911] boot success...
```

首次启动的时候需要创建一个Topic.
```shell
sh bin/mqadmin updatetopic -n localhost:9876 -t SmartClassroomTopic -c DefaultCluster
```

## 关闭
```shell
$ sh bin/mqshutdown broker
$ sh bin/mqshutdown namesrv
```


## 常见问题
### 有可能rocket-mq会因为磁盘不足而报错：
```text
the broker's disk is full
```
这个时候将磁盘阈值调大：
```text
vi bin/runbroker.sh
```
增加一行java启动参数
```shell
JAVA_OPT="${JAVA_OPT} -Drocketmq.broker.diskSpaceWarningLevelRatio=0.99"
```
然后重启rocketmq即可。



