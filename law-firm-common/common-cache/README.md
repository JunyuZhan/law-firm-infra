# Common Cache Module

## 简介
Common Cache 模块提供了统一的缓存解决方案，包括Redis缓存、分布式锁、限流等功能。

## 主要功能

### 1. Redis缓存
- 提供了`CacheUtil`工具类，封装了Redis的常用操作
- 支持String、Hash、List、Set等数据类型
- 支持设置过期时间
- 支持批量操作

### 2. 分布式锁
- 提供了`RedisLockUtil`工具类，基于Redisson实现
- 支持可重入锁
- 支持等待时间和持有锁时间设置
- 支持自动续期

### 3. 限流功能
- 提供了`@RateLimiter`注解，支持方法级别的限流
- 支持设置限流速率和时间单位
- 基于Redis实现，支持分布式环境
- 提供了`RateLimiterUtil`工具类，支持编程式限流

### 4. 防重提交
- 提供了`@RepeatSubmit`注解，防止表单重复提交
- 支持设置间隔时间和提示消息
- 基于Redis实现，支持分布式环境

### 5. 缓存防护
- 提供了`CacheGuardUtil`工具类，实现缓存防护功能
- 使用布隆过滤器防止缓存穿透
- 使用分布式锁防止缓存击穿
- 使用随机过期时间防止缓存雪崩
- 支持缓存降级，当Redis不可用时使用本地缓存

### 6. 缓存同步
- 提供了`CacheSyncUtil`工具类，实现多节点缓存同步
- 使用分布式锁保证同步更新的原子性
- 支持先更新数据库再删除缓存的更新模式
- 支持同步删除缓存

### 7. 缓存预热
- 提供了`@CacheWarmUp`注解，支持方法级别的缓存预热
- 支持自定义缓存键生成策略
- 支持使用方法名和参数作为缓存键
- 自动将方法返回值预热到缓存中

## 配置说明

### Redis配置
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
```

### 缓存配置
```yaml
law-firm:
  cache:
    enabled: true
    type: REDIS
    expiration: 720
    refresh-time: 120
```

## 使用示例

### 1. 使用缓存防护
```java
@Autowired
private CacheGuardUtil cacheGuardUtil;

// 防止缓存穿透
Object value = cacheGuardUtil.getWithPenetrationGuard("key", () -> {
    // 查询数据库
    return dbService.getData();
});

// 防止缓存击穿
Object value = cacheGuardUtil.getWithBreakdownGuard("key", () -> {
    // 查询数据库
    return dbService.getData();
});

// 使用缓存降级
Object value = cacheGuardUtil.getWithFallback("key", () -> {
    // 返回本地缓存数据
    return localCache.getData();
});
```

### 2. 使用缓存同步
```java
@Autowired
private CacheSyncUtil cacheSyncUtil;

// 同步更新缓存
cacheSyncUtil.syncUpdate("key", newValue);

// 同步删除缓存
cacheSyncUtil.syncDelete("key");

// 先更新数据库再删除缓存
cacheSyncUtil.syncUpdateWithDb("key", () -> {
    // 更新数据库
    return dbService.updateData();
});
```

### 3. 使用缓存预热
```java
@CacheWarmUp(keyPrefix = "user", useMethodName = true, useParams = true)
public User getUserById(Long id) {
    return userService.getById(id);
}
```

## 注意事项
1. 使用分布式锁时，必须在finally块中释放锁
2. 限流注解的速率要根据实际业务需求合理设置
3. 防重提交的间隔时间要考虑网络延迟
4. 缓存的过期时间要根据数据的实时性要求来设置
5. 使用布隆过滤器时要合理设置预期元素数量和误判率
6. 缓存同步时要注意并发性能和数据一致性的平衡
7. 缓存预热要在系统启动时或业务低峰期进行 