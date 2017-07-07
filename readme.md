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

## 流程

![](https://github.com/huangll99/h-push/blob/master/doc/push-flow.png)

## 集群方案(待实现)

![](https://github.com/huangll99/h-push/blob/master/doc/push-cluster-flow.png)
