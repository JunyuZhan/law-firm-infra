# API层功能使用指南

本文档介绍了API层的核心功能及其使用方法，包括限流、防重复提交、幂等性控制和分布式锁等。

## 1. 限流功能

### 1.1 IP级别限流

系统默认对敏感接口（如登录、注册）进行IP级别的限流，可通过配置文件调整：

```yaml
law-firm:
  api:
    rate-limit:
      enabled: true
      ip-limit-enabled: true
      ip-limit-rate: 50  # 每秒允许的请求数
      ip-limit-interval: 1  # 时间间隔（秒）
      ip-limit-paths:
        - /api/auth/login
        - /api/auth/register
```

### 1.2 接口级别限流

使用`@RateLimiter`注解对特定接口进行限流：

```java
@GetMapping("/example")
@RateLimiter(rate = 2, rateInterval = 5, rateIntervalUnit = RateIntervalUnit.SECONDS)
public CommonResult<Object> example() {
    // 方法实现
}
```

参数说明：
- `rate`: 允许的请求数
- `rateInterval`: 时间间隔
- `rateIntervalUnit`: 时间单位
- `message`: 限流提示消息

## 2. 防重复提交

使用`@RepeatSubmitPrevention`注解防止表单重复提交：

```java
@PostMapping("/save")
@RepeatSubmitPrevention(interval = 5000)
public CommonResult<Object> save(@RequestBody FormData form) {
    // 方法实现
}
```

参数说明：
- `interval`: 防重复提交间隔（毫秒）
- `timeUnit`: 时间单位
- `message`: 提示消息
- `userBased`: 是否基于用户隔离（默认true）

## 3. 幂等性控制

### 3.1 使用幂等性注解

使用`@Idempotent`注解确保接口幂等性：

```java
@PostMapping("/order")
@Idempotent(expireTime = 60, timeUnit = TimeUnit.SECONDS)
public CommonResult<Object> createOrder(@RequestHeader("X-Idempotent-Token") String token) {
    // 方法实现
}
```

参数说明：
- `type`: 幂等性标识的来源（HEADER、PARAMETER、BODY）
- `name`: 幂等性标识的名称
- `expireTime`: 过期时间
- `timeUnit`: 时间单位
- `message`: 提示消息
- `autoGenerate`: 是否自动生成幂等性标识

### 3.2 获取幂等性令牌

客户端需要先获取幂等性令牌，然后在请求头中携带：

```java
// 服务端生成令牌的示例
@GetMapping("/token")
public CommonResult<String> getToken() {
    String token = UUID.randomUUID().toString().replace("-", "");
    return CommonResult.success(token);
}

// 客户端使用示例
String token = getTokenFromServer();
HttpHeaders headers = new HttpHeaders();
headers.add("X-Idempotent-Token", token);
```

## 4. 分布式锁

使用`DistributedLockService`处理分布式环境下的并发问题：

```java
@Autowired
private DistributedLockService lockService;

public void updateData(String id, Data data) {
    lockService.executeWithLock("data:" + id, () -> {
        // 获取最新数据
        Data latestData = dataService.getById(id);
        // 更新数据
        dataService.update(data);
        return null;
    });
}
```

方法说明：
- `executeWithLock(String lockKey, Supplier<T> supplier)`: 执行有返回值的加锁操作
- `executeWithLock(String lockKey, Runnable runnable)`: 执行无返回值的加锁操作
- `executeWithLock(String lockKey, long waitTime, long leaseTime, TimeUnit timeUnit, Supplier<T> supplier)`: 自定义等待时间和持有时间

## 5. API版本控制

所有API控制器应继承`BaseApiController`以获取统一的版本控制和响应格式：

```java
@RestController
@RequestMapping("/api/example")
public class ExampleController extends BaseApiController {
    
    @GetMapping("/test")
    public CommonResult<Object> test() {
        return success(data);  // 使用统一的响应方法
    }
}
```

## 6. 配置说明

所有功能都可以通过`application.yml`进行配置：

```yaml
law-firm:
  api:
    # API版本控制
    version:
      enabled: true
      current: v1
    # 限流配置
    rate-limit:
      enabled: true
      ip-limit-enabled: true
      ip-limit-rate: 50
      ip-limit-interval: 1
    # 幂等性配置
    idempotent:
      enabled: true
      default-expire-time: 60
      auto-generate: false
    # 防重复提交配置
    repeat-submit:
      enabled: true
      default-interval: 5000
      user-based: true
    # 分布式锁配置
    distributed-lock:
      enabled: true
      default-wait-time: 5
      default-lease-time: 30
```

## 7. 示例代码

查看`ApiDemoController`类获取完整的示例代码。 