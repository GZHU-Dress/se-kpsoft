- 文档的开头：从index.html文件开始

- 导入包编译/运行：
```sh
javac -cp <filepath of log.jar> <main.java>
java -cp <filepath of log.jar> <main>
```

# 范例代码：
## 简单使用
- 主函数Main调用初始化方法：
```java
  Core.install();
```
会根据标配生成一个默认logcat(DefaultLogcat)
输出流指向当前System.out

- 输出Log
```java
  Log.d("HelloWorld");
```
输出一个以Debug为标志的log信息
结果如下：
``[ Debug ] : { ( Main:main ) : HelloWorld } ``
其中前面为标志，中间为调用的类和方法，后面为信息
Log的一些列接口详见Log接口中的说明
Log等级详见Log.Level，有五个等级

## 可见性
- 当输出Log过多时候，可以筛选Logcat的输出等级，比如设置``只显示warn及以上等级的log``。设置后在以后的低级信息经过Log的广播方法的时候，将不会被发送
如：
```java
Core.getDefaultLogcat().setLevelFilter(Log.Level.Warn);
```
上述代码先获取core的内置DefaultLogcat，设置它的等级筛选

## 分流器
```java
  Core.setPrintLog(PrintLogcat.defaultFilepath);
```
调用该方法设置一个PrintLog对象，并指明一个文件地址
Log会分流输出到该地址的文件过去。
默认的文件地址在当前目录下的log/Main_[CurrentTime].log

- 扩写分流器类
Log接口中有管理Logcat类的列表，向Log中添加Logcat对象，增加分流器。
Logcat是抽象类，可以通过继承扩写自定义输出方向和筛选方式
