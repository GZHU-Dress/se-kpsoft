# SE-kpsoft

此项目由课内SE实验课而起，接手kpsoft的中间件开发，希望我们能够顺利完成
（令：期末前要完成文档提交。代码可以没写完，文档要写完）

- 架构简述：
  - 从前端通过kp协议+ws协议通过本地环回访问后端后台安卓服务
  - [usecase图](./UML/usecase.jpg)
  - [kp协议](./Protocol/ProtocolDescription_Revision.md)

---
- 工程结构

|文件夹|说明|负责人|
|-|-|-|
|[SEdoc](./SEdoc/) |为课内要求的文档格式|all|
|[Protocol](./Protocol/)|javaLib 包含ws通信模块与protocol模块|visnz/wwq|
|[test](./test/)|测试方法类|光/东|
|[android](./android/)|androidLib|xiaosi/visnz|
|CLM|核心业务逻辑模块，安卓后台|visnz|
|[log](./log/)|打包好的一个log测试lib|visnz维护|

- 逻辑结构
  - 底层逻辑：javaLib、androidLib
  - 业务逻辑：CLM（安卓后台服务）、jsLib（前台开发lib）

- 当前进度
  - 底层逻辑构建
  - [待解决问题](./待解决问题.md)

