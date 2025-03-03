# MyBatis Plus 开发指南

## 介绍

本指南详细介绍了律师事务所管理系统中 MyBatis Plus 的使用方法和最佳实践。MyBatis Plus 是一个强大的 ORM 框架，它在 MyBatis 的基础上提供了更多的功能，简化了数据库操作，提高了开发效率。

**官方文档**：[MyBatis Plus 官方文档](https://baomidou.com/)

## 项目配置

### Maven 依赖

所有依赖版本定义在 `law-firm-dependencies/pom.xml` 中，各个模块只需引用依赖而不指定版本：

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
</dependency>
```

### Spring Boot 配置

在 `application.yml` 中配置 MyBatis Plus：

```yaml
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  type-aliases-package: com.lawfirm.model.*.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
```

## 实体类

### 实体类定义

实体类需要继承相应的基类，并使用 MyBatis Plus 注解标注：

```java
@Data
@TableName("storage_bucket")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class StorageBucket extends BaseStorage {

    private static final long serialVersionUID = 1L;

    /**
     * 桶名称
     */
    @TableField("bucket_name")
    private String bucketName;

    /**
     * 桶类型
     */
    @TableField("bucket_type")
    private BucketTypeEnum bucketType;
    
    // 其他字段...
}
```

### 常用注解

- `@TableName`：指定表名
- `@TableId`：指定主键 ID 和 ID 生成策略
- `@TableField`：指定字段名和其他属性
- `@TableLogic`：标识逻辑删除字段
- `@Version`：标识乐观锁版本字段
- `@EnumValue`：标识枚举类属性映射关系

## Mapper 接口

### 基础 Mapper

每个实体类需要定义一个对应的 Mapper 接口，继承 `BaseMapper`：

```java
@Mapper
public interface BucketRepository extends BaseMapper<StorageBucket> {
    
    // 自定义方法...
    @Select("SELECT COUNT(*) FROM storage_bucket WHERE bucket_name = #{bucketName}")
    long countByBucketName(@Param("bucketName") String bucketName);
}
```

### BaseMapper 内置方法

`BaseMapper` 提供了许多开箱即用的方法：

| 方法名          | 说明                   |
|----------------|------------------------|
| insert         | 插入一条记录             |
| deleteById     | 根据 ID 删除记录        |
| deleteBatchIds | 批量删除记录             |
| updateById     | 根据 ID 更新记录        |
| selectById     | 根据 ID 查询记录        |
| selectBatchIds | 根据 ID 批量查询        |
| selectList     | 根据条件查询多条记录      |
| selectOne      | 根据条件查询一条记录      |
| selectCount    | 根据条件查询记录总数      |
| selectPage     | 分页查询                |

## Service 层

### Service 接口

定义业务服务接口：

```java
public interface BucketService extends IService<StorageBucket> {
    
    // 业务方法
    BucketVO create(BucketCreateDTO createDTO);
    
    boolean updateConfig(Long id, String config);
    
    // 其他方法...
}
```

### Service 实现

实现服务类：

```java
@Slf4j
@Service
public class BucketServiceImpl extends ServiceImpl<BucketRepository, StorageBucket> implements BucketService {

    private final BucketRepository bucketRepository;
    
    public BucketServiceImpl(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }
    
    @Override
    @Transactional
    public BucketVO create(BucketCreateDTO createDTO) {
        // 业务逻辑...
        
        StorageBucket bucket = new StorageBucket();
        // 设置属性...
        
        // 保存对象
        save(bucket);  // 调用 ServiceImpl 提供的方法
        
        return convert(bucket);
    }
    
    // 其他方法实现...
}
```

> **注意**：当继承 `ServiceImpl` 时，在调用批量方法如 `saveBatch`、`updateBatchById` 时，请使用 `super.xxx` 而非 `this.xxx` 以避免递归调用。

## 条件构造器

### 常用查询

```java
// 等值查询
QueryWrapper<StorageBucket> query = new QueryWrapper<>();
query.eq("bucket_name", bucketName);
StorageBucket bucket = bucketRepository.selectOne(query);

// 多条件查询
QueryWrapper<StorageBucket> query = new QueryWrapper<>();
query.eq("storage_type", StorageTypeEnum.MINIO.getCode())
     .like("bucket_name", keyword)
     .ge("create_time", startTime)
     .le("create_time", endTime)
     .orderByDesc("create_time");
List<StorageBucket> buckets = bucketRepository.selectList(query);
```

### Lambda 条件构造器

使用 Lambda 表达式进行类型安全的查询：

```java
LambdaQueryWrapper<StorageBucket> query = new LambdaQueryWrapper<>();
query.eq(StorageBucket::getStorageType, StorageTypeEnum.MINIO)
     .like(StringUtils.hasText(keyword), StorageBucket::getBucketName, keyword)
     .between(StorageBucket::getCreateTime, startTime, endTime)
     .orderByDesc(StorageBucket::getCreateTime);
```

## 分页查询

### 配置分页插件

在配置类中注册分页插件：

```java
@Configuration
public class MybatisPlusConfig {
    
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
```

### 使用分页

```java
// 创建分页对象
Page<StorageBucket> page = new Page<>(current, size);

// 查询条件
QueryWrapper<StorageBucket> query = new QueryWrapper<>();
query.eq("status", 1);

// 执行分页查询
Page<StorageBucket> result = bucketRepository.selectPage(page, query);

// 获取分页结果
List<StorageBucket> records = result.getRecords();  // 当前页数据
long total = result.getTotal();                     // 总记录数
long pages = result.getPages();                     // 总页数
```

## 高级特性

### 自定义 SQL

对于复杂查询，可以使用 XML 配置：

1. 在 Mapper 接口中声明方法：

```java
public interface BucketRepository extends BaseMapper<StorageBucket> {
    
    List<BucketStatVO> selectBucketStats(@Param("startDate") LocalDate startDate, 
                                         @Param("endDate") LocalDate endDate);
}
```

2. 在 XML 文件中实现：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lawfirm.model.storage.repository.BucketRepository">
    
    <select id="selectBucketStats" resultType="com.lawfirm.model.storage.vo.BucketStatVO">
        SELECT 
            b.id,
            b.bucket_name,
            COUNT(f.id) AS file_count,
            SUM(f.storage_size) AS total_size
        FROM storage_bucket b
        LEFT JOIN file_object f ON b.id = f.bucket_id
        WHERE f.create_time BETWEEN #{startDate} AND #{endDate}
        GROUP BY b.id, b.bucket_name
        ORDER BY total_size DESC
    </select>
    
</mapper>
```

### 多租户

开启多租户支持：

1. 配置多租户插件：

```java
@Bean
public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    // 分页插件
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    // 多租户插件
    interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
        @Override
        public Expression getTenantId() {
            Long tenantId = TenantContextHolder.getTenantId();
            return new LongValue(tenantId);
        }
        
        @Override
        public String getTenantIdColumn() {
            return "tenant_id";
        }
        
        @Override
        public boolean ignoreTable(String tableName) {
            // 公共表不需要租户过滤
            return "sys_config".equals(tableName) || "sys_dict".equals(tableName);
        }
    }));
    return interceptor;
}
```

### 动态表名

对于需要动态表名的场景：

```java
@Bean
public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    // 动态表名插件
    DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
    dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
        // 根据业务需求动态修改表名
        if ("file_object".equals(tableName)) {
            return tableName + "_" + DateUtil.format(new Date(), "yyyyMM");
        }
        return tableName;
    });
    interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
    return interceptor;
}
```

## 最佳实践

### 1. 合理使用 Lambda 条件构造器

Lambda 表达式可以避免字段名拼写错误和提供类型安全：

```java
// 推荐的方式
LambdaQueryWrapper<StorageBucket> query = new LambdaQueryWrapper<>();
query.eq(StorageBucket::getStatus, 1)
     .likeRight(StringUtils.hasText(keyword), StorageBucket::getBucketName, keyword);
```

### 2. 谨慎使用 select *

尽量指定需要查询的列，避免不必要的数据传输：

```java
QueryWrapper<StorageBucket> query = new QueryWrapper<>();
query.select("id", "bucket_name", "storage_type", "create_time")
     .eq("status", 1);
```

### 3. 使用 Service 层的事务管理

在 Service 层方法上添加 `@Transactional` 注解以确保事务一致性：

```java
@Override
@Transactional(rollbackFor = Exception.class)
public boolean removeWithFiles(Long bucketId) {
    // 1. 删除文件
    fileObjectRepository.delete(
        new LambdaQueryWrapper<FileObject>().eq(FileObject::getBucketId, bucketId)
    );
    
    // 2. 删除存储桶
    return removeById(bucketId);
}
```

### 4. 避免大批量操作

对于大批量操作，应该分批次进行：

```java
@Transactional(rollbackFor = Exception.class)
public boolean saveBatchInChunks(List<StorageBucket> buckets) {
    int batchSize = 500;  // 每批次数量
    int total = buckets.size();
    
    for (int i = 0; i < total; i += batchSize) {
        int end = Math.min(i + batchSize, total);
        List<StorageBucket> subList = buckets.subList(i, end);
        super.saveBatch(subList);
    }
    
    return true;
}
```

### 5. 性能监控

在开发环境中可以开启 SQL 执行日志：

```yaml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # 开发环境
```

在生产环境中可以使用 SQL 性能监控工具：

```java
@Bean
public SqlLogInterceptor sqlLogInterceptor() {
    SqlLogInterceptor interceptor = new SqlLogInterceptor();
    interceptor.setMaxTime(1000);  // 设置慢 SQL 阈值，单位毫秒
    return interceptor;
}
```

## 常见问题与解决方案

### 枚举类型处理

对于枚举类型，可以实现 `IEnum` 接口：

```java
public enum StorageTypeEnum implements IEnum<Integer> {
    
    LOCAL(0, "本地存储"),
    MINIO(1, "MinIO存储"),
    ALIYUN(2, "阿里云OSS"),
    TENCENT(3, "腾讯云COS");
    
    private final int code;
    private final String desc;
    
    StorageTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    @Override
    public Integer getValue() {
        return this.code;
    }
    
    public String getDesc() {
        return this.desc;
    }
}
```

### 逻辑删除

在实体类中标记逻辑删除字段：

```java
@TableLogic
@TableField("deleted")
private Integer deleted;
```

### 乐观锁

在实体类中标记版本字段：

```java
@Version
@TableField("version")
private Integer version;
```

并在配置中添加乐观锁插件：

```java
@Bean
public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
    return interceptor;
}
```

---

本指南提供了在律师事务所管理系统中使用 MyBatis Plus 的基本方法和最佳实践。随着项目的发展，我们会不断完善和更新本指南。如有疑问或改进建议，请联系项目技术负责人。 