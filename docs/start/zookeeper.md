# zookeeper使用方法
## 下载
从 [官网](https://zookeeper.apache.org/) 下载，比如下载apache-zookeeper-3.8.1-bin.tar.gz

## 修改配置
解压后，将 apache-zookeeper-3.8.1-bin/conf/zoo_sample.cfg 重命名为 zoo.cfg，修改日志和数据存放位置：
```shell
# dataDir表示保存数据的目录，修改成你希望的位置。
dataDir=/tmp/zookeeper
```

## 运行
```shell
cd apache-zookeeper-3.8.1-bin/bin/
./zkServer.sh start
```

看到以下提示表示启动成功
```text
Using config: .../conf/zoo.cfg
Starting zookeeper ... STARTED
```

windows平台下，双击运行 apache-zookeeper-3.8.1-bin/zkServer.cmd

## 验证

方法1：
使用jvm命令jps
```shell
jps
```
看到"QuorumPeerMain"表示启动成功

