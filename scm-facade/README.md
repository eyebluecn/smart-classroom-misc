# 接口设计

## 关于facade接口返回是否用Result封装？

可以参考这篇文章，https://blog.csdn.net/g6U8W7p06dCO99fQ3/article/details/121186828

当然文中的说法并不全对，我认为封装Result或者不封装Result都有各自的优缺点。

究竟封装Result还是不封装，一个判断方法是：

- 如果微服务系统的技术栈统一，中间件能够很好处理服务调用的异常及监控，那么就不封装Result。
- 如果微服务系统的技术栈不统一，比如跨语言了，或者中间件没有完善的异常及监控能力，那么就应该封装Result。（当然web层Controller返回给前端的数据一定要封装）

本项目统一采用不封装Result的做法。

