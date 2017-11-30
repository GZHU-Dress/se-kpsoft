# SE-kpsoft

此项目由课内SE实验课而起，接手kpsoft的中间件开发
希望我们能够顺利完成
（令：mod强哥赛高）
（二令：期末前要完成文档提交。代码可以没写完，文档要写完）

---
- 工程结构：

|文件夹|说明|负责人|
|-|-|-|
|[SEdoc](./SEdoc/) |为课内要求的文档格式|all|
|[Protocol](./Protocol/)| 为协议解释器封装器|visnz|
|[test](./test/)|测试方法类|光/东|
|[server](./server/)|ws模块|wwq|
|AndroidServer|模块总和工程|xiaosi/visnz|


- AndroidServer结构简述
  - 自身功能
    - Controllor 控制器
    - RuntimeData 运行时数据
    - ThreadPool 线程池
  - 接口接入
    - WebSocket+Buffered 网络传送接口
    - DevManager 安卓对本地Dev的调用与获取
    - ProtocolEncoder 协议的编解码器
