zfile 是存储的相关文件，以 z 开头只是为了让目录排在后面。

- cnki-dianzi-demo: 测试接口的小示例
- common-tool: 工具类模块
- common-web: web项目常用类
- spring-log4j-demo: SpringBoot 测试项目

## common-tool

常用工具类模块：
- JSON处理类（JSONUtil）
- 接口调用（HttpHelper）

## common-web

web 接口项目常用的类，可以被其它 Web 项目所使用。
spring-log4j-demo 是一个 SpringBoot 项目。可以再创建其它 SpringBoot 项目。
然后再复用 common-web 模块。

common-web 有的内容如下：

- 统一返回结果类
- 统一异常捕捉类

### 统一返回结果类

定义接口的返回结果，统一使用 BaseResponse 类
包含了 状态码，消息、接口数据

### 统一异常捕捉（拦截器）

LogAspect 是对控制器异常的统一处理。控制器则无需再处理异常。

## 日志

日志使用 logback，相关配置在 logback-spring.xml 中。
日志如果保存在数据库中，则初始化数据库的语句在 zfile 目录中，文件名为 logback-mysql-scripts.sql


## 枚举

关于枚举规范的使用。

定义枚举参照 CheckInfoSyncState

1、枚举要实现接口 IEnum
2、根据数值转换枚举使用下面的方法。

``` java
CheckInfoSyncState state = IEnum.getEnumByVal(CheckInfoSyncState.class, 30);
```


## 数据库访问