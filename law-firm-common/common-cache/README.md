# Common Cache Module

## 模块说明
`common-cache` 是法律事务管理系统的通用缓存模块，提供了基于 Redis 的缓存解决方案。该模块采用 AOP 方式实现，支持声明式缓存和分布式锁功能。

## 功能特性

### 1. 简单缓存注解 @SimpleCache
- 支持 SpEL 表达式动态生成缓存键
- 可配置缓存过期时间
- 支持方法名和参数自动作为缓存键
- 异常情况自动跳过缓存

### 2. Redis配置
- 支持单机模式
- 支持连接池配置
- 集成 Redisson 客户端
- 支持多种数据序列化方式

### 3. 测试支持
- 提供测试专用的 Redis 配置
- 基于 TestContainers 的 Redis 容器
- 自动化测试支持

## 核心组件

### 1. SimpleCacheAspect
- 核心缓存切面
- 处理 @SimpleCache 注解
- 支持缓存键生成策略
- 异常处理机制

### 2. RedisConfig
- Redis 连接配置
- 连接池参数设置
- 序列化器配置
- Redisson 客户端配置

### 3. CacheConstants
- 缓存相关常量定义
- 缓存键前缀管理
- 默认过期时间配置

## 配置说明

### 1. Redis配置
```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
      password: 
      timeout: 10000
      lettuce:
        pool:
          max-active: 8
          max-wait: -1
          max-idle: 8
          min-idle: 0
```

### 2. Redisson配置
```yaml
redisson:
  single-server-config:
    address: redis://localhost:6379
    database: 0
    password: 
    timeout: 10000
```

## 使用方法

### 1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-cache</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 使用缓存注解
```java
@Service
public class UserService {
    
    @SimpleCache(
        key = "user:detail:#userId", 
        timeout = 30, 
        timeUnit = TimeUnit.MINUTES
    )
    public UserDTO getUserDetail(Long userId) {
        // 方法调用会自动缓存
        return userRepository.findById(userId);
    }

    @SimpleCache(
        key = "user:list", 
        useMethodName = true,  // 使用方法名作为键的一部分
        useParams = true,      // 使用参数作为键的一部分
        timeout = 10
    )
    public List<UserDTO> getUserList(String type) {
        // 方法调用会自动缓存
        return userRepository.findByType(type);
    }
}
```

### 3. 测试用例编写
```java
@SpringBootTest
@Import(RedisTestConfig.class)
public class CacheTest {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @BeforeEach
    void setUp() {
        // 清除测试相关的缓存键
        Set<String> keys = redisTemplate.keys("test:*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
    
    @Test
    void testCache() {
        // 编写测试用例
    }
}
```

## 注意事项

1. 缓存键生成
   - 建议使用统一的前缀
   - 避免键名冲突
   - 合理使用 SpEL 表达式

2. 缓存过期时间
   - 根据业务需求设置合理的过期时间
   - 避免缓存穿透和雪崩

3. 异常处理
   - 缓存异常不会影响主业务流程
   - 异常情况下自动降级到直接调用

4. 测试注意事项
   - 测试前清理相关缓存
   - 使用专门的测试配置
   - 注意测试隔离

## 测试覆盖

模块包含完整的单元测试，覆盖了以下场景：
- 基本缓存功能
- 缓存过期处理
- 异常情况处理
- 动态缓存键生成
- 参数序列化
- Redis 连接配置 