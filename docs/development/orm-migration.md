# JPA 到 MyBatis Plus 迁移指南

## 1. 迁移概述

本文档详细描述了将律师事务所管理系统从 Spring Data JPA 迁移到 MyBatis Plus 的过程、原因及最佳实践。此次迁移旨在提高系统性能，简化开发流程，并解决之前存在的一些技术问题。

## 2. 迁移原因

### 2.1 技术需求变更
- 系统核心框架采用了 Spring Boot 和 MyBatis Plus 作为主要技术栈
- 需要统一项目的 ORM 技术选型，避免同时使用多种数据访问技术导致的复杂性

### 2.2 解决问题
- IDE 对 JPA 方法的索引和识别问题，导致开发过程中出现大量编译错误提示
- JPA 和 MyBatis Plus 混合使用造成的开发效率降低和代码维护难度增加
- 提高查询性能和数据访问效率

## 3. 迁移范围

此次迁移涉及以下组件：

- 实体类：修改 JPA 注解为 MyBatis Plus 注解
- Repository 层：从 JpaRepository 改为 BaseMapper
- Service 层：适配 MyBatis Plus 的操作方法

## 4. 迁移步骤详解

### 4.1 实体类迁移

从 JPA 注解转换为 MyBatis Plus 注解：

```java
// 原 JPA 注解
@Entity
@Table(name = "storage_bucket")
public class StorageBucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "bucket_name")
    private String bucketName;
    // ...
}

// 迁移后 MyBatis Plus 注解
@Data
@TableName("storage_bucket")
@EqualsAndHashCode(callSuper = true)
public class StorageBucket extends BaseStorage {
    @TableField("bucket_name")
    private String bucketName;
    // ...
}
```

### 4.2 Repository 层迁移

从 JpaRepository 迁移到 BaseMapper：

```java
// 原 JPA 接口
public interface BucketRepository extends JpaRepository<StorageBucket, Long> {
    long countByBucketName(String bucketName);
    StorageBucket findByBucketName(String bucketName);
    // ...
}

// 迁移后 MyBatis Plus 接口
public interface BucketRepository extends BaseMapper<StorageBucket> {
    @Select("SELECT COUNT(*) FROM storage_bucket WHERE bucket_name = #{bucketName}")
    long countByBucketName(@Param("bucketName") String bucketName);
    
    @Select("SELECT * FROM storage_bucket WHERE bucket_name = #{bucketName} LIMIT 1")
    StorageBucket findByBucketName(@Param("bucketName") String bucketName);
    // ...
}
```

### 4.3 Service 层迁移

调整 Service 实现类以适配 MyBatis Plus：

```java
// 原基于 JPA 的服务实现
@Service
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    
    // 使用 JPA 特有方法
    public boolean exists(String name) {
        return bucketRepository.existsByBucketName(name);
    }
    // ...
}

// 迁移后基于 MyBatis Plus 的服务实现
@Service
public class BucketServiceImpl extends ServiceImpl<BucketRepository, StorageBucket> implements BucketService {
    private final BucketRepository bucketRepository;
    
    // 使用 MyBatis Plus 方法
    public boolean exists(String name) {
        return bucketRepository.selectCount(
            new QueryWrapper<StorageBucket>().eq("bucket_name", name)
        ) > 0;
    }
    // ...
}
```

## 5. 数据访问最佳实践

### 5.1 查询方法

使用 MyBatis Plus 的条件构造器进行查询：

```java
// 简单查询
QueryWrapper<StorageBucket> query = new QueryWrapper<>();
query.eq("bucket_name", bucketName);
StorageBucket bucket = bucketRepository.selectOne(query);

// 复杂条件查询
QueryWrapper<StorageBucket> query = new QueryWrapper<>();
query.eq("storage_type", StorageTypeEnum.MINIO.getCode())
     .ge("create_time", startTime)
     .orderByDesc("create_time");
List<StorageBucket> buckets = bucketRepository.selectList(query);
```

### 5.2 分页查询

使用 MyBatis Plus 的分页插件：

```java
Page<StorageBucket> page = new Page<>(current, size);
QueryWrapper<StorageBucket> query = new QueryWrapper<>();
query.eq("status", 1);
return bucketRepository.selectPage(page, query);
```

### 5.3 自定义 SQL

对于复杂查询，使用注解或 XML 配置：

```java
// 使用注解方式
@Select("SELECT * FROM storage_bucket WHERE bucket_name LIKE CONCAT('%', #{keyword}, '%')")
List<StorageBucket> searchByKeyword(@Param("keyword") String keyword);

// 复杂查询推荐使用 XML 配置
// 在 Mapper XML 中定义
<select id="findActiveBuckets" resultType="com.lawfirm.model.storage.entity.bucket.StorageBucket">
    SELECT * FROM storage_bucket 
    WHERE status = 1 
    AND storage_type = #{storageType}
    ORDER BY create_time DESC
</select>

// 在接口中声明
List<StorageBucket> findActiveBuckets(@Param("storageType") Integer storageType);
```

## 6. 注意事项

1. **避免递归调用**：在 ServiceImpl 中使用批量方法时，需使用 `super.xxx` 而非 `this.xxx` 避免递归调用
2. **事务管理**：保持与 JPA 相同的事务处理策略，使用 `@Transactional` 注解
3. **实体映射**：确保实体类中的字段都正确映射到表字段，使用 `@TableField`
4. **主键策略**：根据需要设置适当的主键生成策略，使用 `@TableId`
5. **逻辑删除**：如需要，使用 MyBatis Plus 的逻辑删除功能

## 7. 迁移验证

1. **单元测试**：对所有迁移的 Repository 和 Service 编写全面的单元测试
2. **功能测试**：确保所有功能在迁移后能正常工作
3. **性能测试**：对比迁移前后的性能差异，特别是复杂查询的性能
4. **SQL 监控**：使用 SQL 监控工具检查生成的 SQL 是否高效

## 8. 未来展望

1. **优化基础架构**：重构基础服务类，减少 BaseServiceImpl 和 MyBatis Plus 的 ServiceImpl 之间的功能重复
2. **代码生成**：使用 MyBatis Plus 的代码生成功能简化开发
3. **性能调优**：根据实际运行情况，进一步优化查询性能
4. **文档更新**：持续更新技术文档，确保团队了解最新的数据访问最佳实践

通过本次迁移，我们成功地将系统的 ORM 框架从 JPA 统一为 MyBatis Plus，提高了开发效率，解决了之前存在的技术问题，并为未来的性能优化打下了基础。 