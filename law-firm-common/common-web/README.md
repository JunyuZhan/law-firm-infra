# Common Web Module

## 简介
Common Web模块是律所管理系统的Web通用功能模块，提供了Web应用开发所需的基础设施和工具类。该模块依赖于`common-core`模块，为其他业务模块提供Web层面的支持。

## 主要功能

### 1. Web配置 (`WebConfig`)
- CORS跨域配置
  - 支持所有源(`*`)
  - 允许的HTTP方法: GET, POST, PUT, DELETE, OPTIONS
  - 允许所有请求头
  - 允许携带认证信息
  - 预检请求缓存时间: 3600秒
- 消息转换器配置
  - Long类型自动转String (解决前端精度丢失问题)
  - JSON序列化和反序列化支持

### 2. 安全配置 (`SecurityConfig`)
- XSS过滤器
  - 自动过滤请求参数中的XSS攻击代码
  - 支持自定义过滤规则

### 3. 文件上传配置 (`UploadConfig`)
- 单个文件大小限制: 10MB
- 总上传大小限制: 20MB
- 支持文件上传的Multipart配置

### 4. 全局异常处理 (`GlobalExceptionHandler`)
- 框架异常处理
- 参数校验异常处理
- 参数绑定异常处理
- 未知异常统一处理

### 5. 工具类

#### IP地址工具 (`IpUtils`)
- 获取请求的真实IP地址
- 获取本机IP地址
- IP地址合法性校验
- 内网IP判断
- 公网IP判断

### 6. Web支持类

#### 基础Web支持 (`WebSupport`)
- 提供Request、Response、Session的便捷访问
- 支持Web上下文操作

#### 参数验证支持 (`ValidSupport`)
- 必填参数验证
- 整数参数验证
- 长整数参数验证
- 布尔参数验证

#### 分页支持 (`PageSupport`)
- 分页参数获取
- 排序参数处理
- 分页参数验证

### 7. 请求/响应对象
- 分页请求对象 (`PageRequest`)
  - 支持页码、每页记录数
  - 支持排序字段和排序方式

## 依赖关系
```xml
<dependencies>
    <!-- Common Core -->
    <dependency>
        <groupId>com.lawfirm</groupId>
        <artifactId>common-core</artifactId>
    </dependency>

    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- Knife4j -->
    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>knife4j-openapi3-spring-boot-starter</artifactId>
        <version>4.3.0</version>
    </dependency>
</dependencies>
```

## 使用方式

### 1. 引入依赖
在需要使用Web功能的模块的`pom.xml`中添加：
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-web</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 自动配置
模块提供了自动配置类`WebAutoConfiguration`，会自动注入以下配置：
- WebConfig (Web MVC配置)
- UploadConfig (文件上传配置)
- SecurityConfig (安全配置)

### 3. 使用示例

#### 分页请求处理
```java
@RestController
@RequestMapping("/api/example")
public class ExampleController extends PageSupport {
    
    @GetMapping("/list")
    public CommonResult<?> list() {
        Integer pageNum = getPageNum();
        Integer pageSize = getPageSize();
        String orderBy = getOrderBy();
        Boolean isAsc = getIsAsc();
        // 进行分页查询...
    }
}
```

#### IP地址工具使用
```java
@RestController
public class DemoController {
    
    @GetMapping("/ip")
    public String getIp(HttpServletRequest request) {
        // 获取真实IP地址
        String ip = IpUtils.getIpAddr(request);
        // 判断是否是内网IP
        boolean isInternal = IpUtils.isInternalIP(ip);
        return ip;
    }
}
```

## 注意事项
1. 全局异常处理器只处理框架级的基础异常，业务异常应该由具体的业务模块自行处理
2. 文件上传大小限制可以通过配置文件覆盖默认配置
3. XSS过滤器默认对所有请求参数进行过滤，如需排除特定参数，请自定义过滤规则

## 常见问题
1. 跨域请求失败
   - 检查目标域名是否在允许列表中
   - 确认请求方法是否被允许
   - 验证是否允许发送认证信息

2. 文件上传失败
   - 检查文件大小是否超过限制
   - 确认上传目录权限是否正确
   - 验证文件类型是否被允许

3. 响应格式异常
   - 检查响应对象是否正确封装
   - 确认消息转换器配置是否正确
   - 验证序列化/反序列化是否正常

## 相关文档
- [Spring Boot 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring MVC 官方文档](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Swagger 官方文档](https://swagger.io/docs/) 