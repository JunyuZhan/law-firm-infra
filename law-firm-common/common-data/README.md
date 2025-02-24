# common-data 数据访问通用模块

## 模块说明
common-data 模块提供了统一的数据访问层功能，包括数据源管理、ORM 框架集成、缓存管理、对象存储等功能。该模块基于 Spring Data、MyBatis-Plus、Redis 等技术实现，为上层应用提供完整的数据访问解决方案。

## 功能特性

### 1. 数据源管理
- 多数据源支持
- 动态数据源切换
- 数据源监控(Druid)
- 读写分离
- 数据源路由

### 2. ORM 框架支持
- MyBatis-Plus 集成
- JPA 支持
- 通用 CRUD 操作
- 分页查询
- 条件构造器

### 3. 缓存管理
- Redis 缓存集成
- 多级缓存架构
- 缓存注解支持
- 缓存同步机制
- 缓存监控

### 4. 对象存储
- MinIO 存储支持
- 阿里云 OSS 支持
- 文件上传下载
- 存储策略配置
- 访问权限控制

### 5. 搜索服务
- Elasticsearch 集成
- 全文检索
- 聚合分析
- 搜索模板
- 高亮显示

### 6. 基础功能
- 实体对象映射
- 数据类型转换
- 异常处理
- 审计日志
- 数据验证

## 使用指南

### 1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-data</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 数据源配置
```yaml
spring:
  datasource:
    dynamic:
      primary: master
      strict: true
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/law_firm_master
          username: root
          password: root
          driver-class-name: com.mysql.cj.jdbc.Driver
        slave:
          url: jdbc:mysql://localhost:3306/law_firm_slave
          username: root
          password: root
          driver-class-name: com.mysql.cj.jdbc.Driver
```

### 3. 缓存配置
```yaml
spring:
  cache:
    type: redis
  redis:
    host: localhost
    port: 6379
    database: 0
    timeout: 10000
```

### 4. 存储配置
```yaml
storage:
  type: minio  # 或 aliyun
  minio:
    endpoint: http://localhost:9000
    accessKey: minioadmin
    secretKey: minioadmin
    bucket: law-firm
  aliyun:
    endpoint: oss-cn-hangzhou.aliyuncs.com
    accessKeyId: your-access-key-id
    accessKeySecret: your-access-key-secret
    bucket: law-firm
```

### 5. 基础服务使用
```java
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserEntity, UserVO> {
    
    @Override
    protected UserEntity createEntity() {
        return new UserEntity();
    }
    
    @Override
    protected UserVO createVO() {
        return new UserVO();
    }
}
```

### 6. 动态数据源使用
```java
@Service
public class UserService {
    
    @DataSource("slave")
    public List<User> queryUsers() {
        // 从从库查询
        return userMapper.selectList(null);
    }
    
    @DataSource("master")
    public void saveUser(User user) {
        // 在主库保存
        userMapper.insert(user);
    }
}
```

## 注意事项
1. 使用动态数据源时需要在启动类上添加 `@EnableDynamicDataSource` 注解
2. 缓存使用前需要配置相应的 Redis 连接信息
3. 文件存储服务需要提前创建对应的 bucket
4. 主从数据源配置时注意数据同步机制
5. 合理使用缓存，避免缓存穿透和雪崩

## 常见问题
1. 数据源切换失败
   - 检查数据源配置是否正确
   - 确认 @DataSource 注解使用是否正确
   - 查看数据源切换日志

2. 缓存不生效
   - 检查缓存注解使用是否正确
   - 确认缓存配置是否正确
   - 验证序列化配置

3. 文件上传失败
   - 检查存储配置是否正确
   - 确认文件大小是否超限
   - 验证存储空间权限

## 相关文档
- [MyBatis-Plus 官方文档](https://baomidou.com/)
- [Spring Data JPA 文档](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Redis 官方文档](https://redis.io/documentation)
- [MinIO 官方文档](https://docs.min.io/)
- [阿里云 OSS 文档](https://help.aliyun.com/document_detail/31817.html) 