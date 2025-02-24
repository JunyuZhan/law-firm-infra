# 缓存最佳实践指南

## 1. 缓存策略

### 1.1 多级缓存策略
```java
// 使用CacheGuardUtil实现多级缓存
@Autowired
private CacheGuardUtil cacheGuardUtil;

public Object getData(String key) {
    // 先查询本地缓存
    Object localValue = LocalCache.get(key);
    if (localValue != null) {
        return localValue;
    }
    
    // 查询Redis缓存，使用缓存防护
    return cacheGuardUtil.getWithBreakdownGuard(key, () -> {
        // 查询数据库
        Object dbValue = queryFromDb(key);
        // 放入本地缓存
        LocalCache.put(key, dbValue);
        return dbValue;
    });
}
```

### 1.2 缓存预热
```java
// 使用@CacheWarmUp注解进行缓存预热
@CacheWarmUp(keyPrefix = "user")
@GetMapping("/users/{id}")
public UserVO getUser(@PathVariable Long id) {
    return userService.getById(id);
}

// 或者使用CacheGuardUtil手动预热
@PostConstruct
public void warmUp() {
    List<String> hotKeys = getHotKeys();
    for (String key : hotKeys) {
        cacheGuardUtil.warmUp(key, () -> queryFromDb(key));
    }
}
```

### 1.3 缓存更新策略
```java
// 使用CacheSyncUtil保证缓存一致性
@Autowired
private CacheSyncUtil cacheSyncUtil;

public void updateData(String key, Object value) {
    // 先更新数据库，再删除缓存
    cacheSyncUtil.syncUpdateWithDb(key, () -> {
        boolean success = updateDb(value);
        return success;
    });
}
```

## 2. 缓存防护

### 2.1 防止缓存穿透
```java
// 使用布隆过滤器防止缓存穿透
public Object getWithGuard(String key) {
    return cacheGuardUtil.getWithPenetrationGuard(key, () -> {
        return queryFromDb(key);
    });
}
```

### 2.2 防止缓存击穿
```java
// 使用分布式锁防止缓存击穿
public Object getWithLock(String key) {
    return cacheGuardUtil.getWithBreakdownGuard(key, () -> {
        return queryFromDb(key);
    });
}
```

### 2.3 防止缓存雪崩
```java
// 设置随机过期时间
@Cacheable(value = "users", expiration = "#random.nextInt(3600)")
public User getUser(Long id) {
    return userMapper.selectById(id);
}
```

## 3. 缓存监控

### 3.1 监控指标
- 缓存命中率
- 缓存容量使用率
- 缓存响应时间
- 缓存并发量

### 3.2 监控实现
```java
@Autowired
private CacheMonitor cacheMonitor;

// 获取Redis监控信息
public RedisInfo getRedisInfo() {
    return cacheMonitor.getRedisInfo();
}

// 获取缓存统计信息
@Scheduled(fixedRate = 60000)
public void monitorCache() {
    CacheStats stats = cacheMonitorService.getStats();
    // 记录监控指标
    if (stats.getHitRate() < 0.8) {
        log.warn("缓存命中率过低: {}%", stats.getHitRate() * 100);
    }
    if (stats.getMemoryUsageRate() > 0.9) {
        log.warn("缓存内存使用率过高: {}%", stats.getMemoryUsageRate() * 100);
    }
}
```

## 4. 最佳实践建议

### 4.1 键名设计
- 使用统一的前缀
- 遵循业务模块划分
- 避免键名过长
- 使用合适的分隔符

```java
// 推荐的键名格式
public static final String USER_KEY = "user:%d";
public static final String USER_PROFILE_KEY = "user:%d:profile";
public static final String USER_FRIENDS_KEY = "user:%d:friends";
```

### 4.2 过期策略
- 根据数据特性设置过期时间
- 避免同时过期
- 合理使用永久缓存
- 定期清理过期数据

```java
// 设置合理的过期时间
@Cacheable(value = "users", expiration = "24h")
public User getUser(Long id) {
    return userMapper.selectById(id);
}

// 定期清理过期数据
@Scheduled(cron = "0 0 2 * * ?")
public void cleanExpiredCache() {
    cacheMonitorService.clearExpiredCache();
}
```

### 4.3 序列化策略
- 选择高效的序列化方式
- 压缩大对象
- 避免循环引用
- 注意反序列化安全

```java
// 配置高效的序列化器
@Bean
public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);
    
    // 使用 Jackson2JsonRedisSerializer
    Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
    ObjectMapper mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, 
        ObjectMapper.DefaultTyping.NON_FINAL);
    serializer.setObjectMapper(mapper);
    
    template.setValueSerializer(serializer);
    template.setHashValueSerializer(serializer);
    
    // 使用 StringRedisSerializer 作为key的序列化器
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    
    template.afterPropertiesSet();
    return template;
}
```

### 4.4 异常处理
- 优雅降级
- 超时控制
- 重试机制
- 日志记录

```java
// 使用缓存降级机制
public Object getWithFallback(String key) {
    return cacheGuardUtil.getWithFallback(key, () -> {
        // 降级为本地缓存
        return LocalCache.get(key);
    });
}

// 异常重试机制
@Retryable(value = RedisException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
public Object getWithRetry(String key) {
    return redisTemplate.opsForValue().get(key);
}
```

## 5. 性能优化

### 5.1 批量操作
```java
// 使用pipeline批量操作
public List<Object> multiGet(List<String> keys) {
    return redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
        for (String key : keys) {
            connection.get(key.getBytes());
        }
        return null;
    });
}
```

### 5.2 连接池优化
```yaml
spring:
  redis:
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        min-idle: 0
        max-wait: -1ms
```

### 5.3 内存优化
- 及时清理过期数据
- 合理设置最大内存
- 选择适当的淘汰策略
- 压缩数据

```java
// 定期清理过期数据
@Scheduled(cron = "0 0 * * * ?")
public void cleanCache() {
    Set<String> keys = redisTemplate.keys("*");
    for (String key : keys) {
        Long ttl = redisTemplate.getExpire(key);
        if (ttl != null && ttl <= 0) {
            redisTemplate.delete(key);
        }
    }
}
``` 