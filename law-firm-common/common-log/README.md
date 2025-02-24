# Common Log Module

## 模块说明
`common-log` 是法律事务管理系统的通用日志模块，提供了统一的日志记录、链路追踪和性能监控功能。该模块采用 AOP 方式实现，可以无侵入地为应用添加日志功能。

## 功能特性

### 1. 请求日志记录
- 自动记录控制器方法的调用信息
- 支持记录请求参数和响应结果
- 支持参数脱敏处理
- 记录请求执行时间
- 支持异步日志记录

### 2. 链路追踪
- 自动为每个请求生成唯一的 traceId
- 支持跨线程的 traceId 传递
- 支持从请求头获取上游 traceId
- 响应头自动返回 traceId

### 3. 异常处理
- 统一的异常日志记录
- 可配置是否记录完整堆栈信息
- 异常信息脱敏处理

### 4. 配置灵活
```yaml
lawfirm:
  log:
    # 是否启用方法调用日志
    enable-method-log: true
    # 是否启用请求响应日志
    enable-request-log: true
    # 是否启用异步日志
    enable-async-log: true
    # 是否启用链路追踪
    enable-tracing: true
    # 是否记录请求参数
    log-request-params: true
    # 是否记录响应结果
    log-response-body: true
    # 是否记录异常堆栈
    log-stack-trace: true
    # 需要排除的路径
    exclude-paths:
      - "/actuator/**"
      - "/swagger-ui/**"
    # 需要排除的请求参数字段（敏感信息）
    exclude-param-fields:
      - "password"
      - "token"
      - "authorization"
```

## 核心组件

### 1. LogAspect
- 核心日志切面，处理请求的日志记录
- 支持异步日志记录
- 支持路径排除
- 支持参数脱敏

### 2. MdcTraceIdFilter
- 实现请求链路追踪
- 自动生成和管理 traceId
- 支持跨服务传递 traceId

### 3. LogUtils
- 日志工具类
- 提供参数格式化
- 提供异常堆栈格式化
- 提供敏感信息脱敏功能

### 4. AsyncLogConfig
- 异步日志线程池配置
- 可配置线程池参数
- 支持优雅关闭

## 使用方法

### 1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-log</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 配置属性
在 application.yml 中添加相关配置：
```yaml
lawfirm:
  log:
    enable-method-log: true
    enable-async-log: true
    # 其他配置...
```

### 3. 使用示例
```java
@RestController
@RequestMapping("/api")
public class UserController {
    
    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        // 方法调用会自动记录日志
        // 包括请求参数、响应结果、执行时间等
        return userService.getUser(id);
    }
}
```

## 注意事项

1. 异步日志
   - 异步日志默认启用
   - 使用专用线程池处理日志
   - 应用关闭时会等待日志处理完成

2. 敏感信息
   - 默认对 password、token、authorization 等字段进行脱敏
   - 可通过配置添加其他敏感字段

3. 性能影响
   - 异步日志对性能影响较小
   - 可通过 exclude-paths 排除高频调用路径

4. 日志级别
   - 正常请求使用 INFO 级别
   - 异常信息使用 ERROR 级别
   - 可通过日志配置文件调整

## 测试覆盖

模块包含完整的单元测试，覆盖了以下场景：
- 正常请求日志记录
- 异常处理和日志记录
- 参数脱敏处理
- 异步日志处理
- 路径排除功能
- MDC上下文传递
- 性能监控

## 相关文档
- [操作日志设计文档](../docs/operation-log-design.md)
- [行为跟踪使用指南](../docs/behavior-track-guide.md)
- [日志规范文档](../docs/log-specification.md) 