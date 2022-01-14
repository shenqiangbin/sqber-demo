zfile 是存储的相关文件，以 z 开头只是为了让目录排在后面。

- cnki-dianzi-demo: 测试接口的小示例
- common-tool: 工具类模块
- common-web: web项目常用类
- spring-log4j-demo: SpringBoot 测试项目

spring-db-demo、spring-log4j-demo、spring-other-demo 3个均为 Spring Boot 项目，
可以分别启动。

注意：
如果要部署的话，也要使用 root 项目下的 maven package 进行。
而不能是我只想部署 spring-db-demo，就点击 spring-db-demo 下的 maven package，这样是不行的。

## 打包

由于 pom 的父类不是 spring-boot-starter-parent。
因此在使用 spring-boot-maven-plugin 插件打包的时候，并没有把相关依赖打在一起。
也就是没有对包再重新打包，我们看到的 spring-log4j-demo.jar.original 就是原始的包， 而 spring-log4j-demo.jar 是 spring boot
再次打包生成的。
而在 spring-boot-start-parent.pom 文件中可以看到下面的配置
```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <executions>
    <execution>
      <id>repackage</id>
      <goals>
        <goal>repackage</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <mainClass>${start-class}</mainClass>
  </configuration>
</plugin>
```
因此，在我们的 spring boot 项目的 pom 文件中，也需要添加是 repackage。
如下。

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <version>${spring-boot.version}</version>
    <executions>
        <execution>
            <goals>
                <!--把依赖的包都打包到生成的 jar 包中-->
                <goal>repackage</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```


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

定义接口的返回结果，统一使用 Resp 类
包含了 状态码，消息、接口数据

### 统一异常捕捉（拦截器）

ExceptionOpr 实现对异常的统一处理。

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

数据库访问不使用 MyBatis，统一使用 MyJdbc。参照 spring-db-demo 中的 MyJdbcController 的写法。
