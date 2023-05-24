# Nacos使用方法
## 下载
从 [官网](https://nacos.io/zh-cn/index.html) 下载，比如下载nacos-server-2.1.2.zip

## 解压
unzip nacos-server-2.1.2.zip
cd nacos/bin

## 启动
```shell
sh startup.sh -m standalone
```

window下使用
```shell
startup.cmd -m standalone
```

# 控制台
访问 http://localhost:8848/nacos/ 即可看到自己注册的服务。