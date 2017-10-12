## 浏览器和push-server交互协议

两者使用json数据格式,json的协议示例如下:

`
{
    "type":0,
    "msg":"hello push"
}
`

## type取值:
    0:发送标示
    1:响应连接成功
    2:推送数据

## 单机版结构设计图

![](https://github.com/huangll99/h-push/blob/master/doc/push-design.png)

## 集群方案(地址:https://github.com/huangll99/push-cluster)

![](https://github.com/huangll99/h-push/blob/master/doc/push-cluster-flow.png)


## 程序流程图

![](https://github.com/huangll99/h-push/blob/master/doc/code-flow.png)

## 后台管理页面

![](https://github.com/huangll99/h-push/blob/master/doc/admin.png)
