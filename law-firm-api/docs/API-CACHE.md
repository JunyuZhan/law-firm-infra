# API缓存机制使用指南

## 1. 缓存概述

API层缓存机制用于提升系统性能，主要针对只读操作进行缓存，减少数据库访问，降低系统负载。缓存基于Spring Cache和Redis实现，提供了简单易用的注解式缓存功能。

## 2. 缓存配置

### 2.1 全局配置

在`application.yml`中配置：

```yaml
law-firm:
  api:
    cache:
      enabled: true                # 是否启用缓存，默认true
      default-ttl: 3600           # 默认缓存过期时间（秒），默认3600秒
```

可以通过环境变量进行配置：

- `API_CACHE_ENABLED`: 是否启用缓存
- `API_CACHE_DEFAULT_TTL`: 默认缓存过期时间

### 2.2 不同数据类型的缓存时间

API缓存管理器为不同类型的数据配置了不同的过期时间：

| 数据类型 | 缓存名称 | 过期时间 |
|---------|---------|---------|
| 字典数据 | dict | 24小时 |
| 菜单数据 | menu | 12小时 |
| 用户数据 | user | 2小时 |
| 角色数据 | role | 6小时 |
| 权限数据 | permission | 6小时 |
| 客户数据 | client | 1小时 |
| 案件数据 | case | 30分钟 |
| 合同数据 | contract | 30分钟 |
| 文档数据 | document | 30分钟 |
| 统计数据 | statistics | 1小时 |

## 3. 缓存使用方法

### 3.1 基本用法

使用`@Cacheable`注解标记需要缓存的方法：

```java
@RestController
@RequestMapping("/api/example")
@CacheConfig(cacheNames = "example", cacheManager = "apiCacheManager")
public class ExampleController {
    
    @GetMapping("/{id}")
    @Cacheable(key = "#id")
    public CommonResult<ExampleDTO> getById(@PathVariable Long id) {
        // 方法实现
    }
}
```

### 3.2 缓存配置类

在控制器类上使用`@CacheConfig`注解配置缓存：

```java
@CacheConfig(cacheNames = "example", cacheManager = "apiCacheManager")
```

- `cacheNames`: 缓存名称，应与数据类型对应
- `cacheManager`: 缓存管理器，固定为"apiCacheManager"

### 3.3 缓存方法

使用`@Cacheable`注解标记需要缓存的方法：

```java
@Cacheable(key = "#id")
```

- `key`: 缓存键表达式，使用SpEL表达式
- `condition`: 缓存条件，满足条件时才缓存
- `unless`: 除非条件，满足条件时不缓存

### 3.4 清除缓存

使用`@CacheEvict`注解清除缓存：

```java
@PutMapping("/{id}")
@CacheEvict(key = "#id")
public CommonResult<ExampleDTO> update(@PathVariable Long id, @RequestBody ExampleDTO dto) {
    // 方法实现
}
```

清除所有缓存：

```java
@DeleteMapping("/clear")
@CacheEvict(allEntries = true)
public CommonResult<Void> clearCache() {
    // 方法实现
}
```

### 3.5 组合缓存操作

使用`@Caching`注解组合多个缓存操作：

```java
@Caching(
    cacheable = {
        @Cacheable(key = "'detail:' + #id")
    },
    evict = {
        @CacheEvict(key = "'list'")
    }
)
```

## 4. 最佳实践

### 4.1 适合缓存的场景

- 查询操作（只读）
- 数据变化不频繁
- 访问频率较高
- 计算成本较高

### 4.2 不适合缓存的场景

- 写操作（增删改）
- 数据实时性要求高
- 数据变化频繁
- 访问频率低

### 4.3 缓存键设计

- 使用有意义的前缀
- 包含足够的标识信息
- 避免过长的键名
- 考虑使用哈希函数处理复杂参数

### 4.4 缓存一致性

- 在更新操作后清除相关缓存
- 使用合适的过期时间
- 考虑使用缓存预热机制
- 监控缓存命中率

## 5. 示例代码

参考`CacheDemoController`类获取完整的示例代码：

```java
@GetMapping("/basic")
@Cacheable(key = "'basic'")
public CommonResult<Map<String, Object>> basicCache() {
    // 方法实现
}

@GetMapping("/param")
@Cacheable(key = "#id")
public CommonResult<Map<String, Object>> paramCache(@RequestParam Long id) {
    // 方法实现
}

@GetMapping("/condition")
@Cacheable(key = "#id", condition = "#id > 10")
public CommonResult<Map<String, Object>> conditionCache(@RequestParam Long id) {
    // 方法实现
}
```

## 6. 注意事项

1. 缓存的方法应该是幂等的，多次调用结果相同
2. 缓存的返回值必须是可序列化的
3. 避免缓存大对象，可能导致Redis内存压力
4. 合理设置缓存过期时间，避免数据不一致
5. 监控缓存使用情况，及时调整配置 