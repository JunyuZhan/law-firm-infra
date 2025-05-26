[返回主项目说明](../README.md)

# 基础设施层 (Infrastructure Layer)

## 模块说明
基础设施层提供了一系列通用、抽象的基础设施支持，包括核心功能、工具类、Web支持、数据访问、缓存、安全、日志、消息和测试等基础功能。

## 模块列表

### 1. common-core（核心模块）
核心模块，提供基础功能支持，主要包含：
- Spring Boot 核心功能
- 数据验证支持
- AOP支持
- 配置处理
- 日志功能
- Web基础功能
- JSON处理（Jackson）

### 2. common-util（工具模块）
工具模块，依赖于common-core，提供各种工具类支持：
- Apache Commons工具（lang3, collections4, io）
- HTTP客户端（Apache HttpClient5）
- Excel处理（Apache POI, EasyExcel）
- JSON处理（Jackson）
- 通用工具（Guava）
- Web工具（可选）
- 安全工具（可选）

### 3. common-web（Web模块）
Web功能支持模块，提供：
- CORS跨域配置
- 消息转换器配置（Long转String等）
- XSS过滤
- 文件上传配置
- 全局异常处理
- IP地址工具
- Web上下文支持
- 参数验证
- 分页支持

### 4. common-data（数据模块）
数据访问模块，依赖于common-core，提供：
- MyBatis Plus支持
- 数据库操作支持
- 数据访问层抽象

### 5. common-cache（缓存模块）
缓存支持模块，依赖于common-core和common-util，提供：
- Redis缓存支持
- 本地缓存支持（Caffeine）
- Redisson分布式功能
- 连接池配置（Commons Pool2）
- AOP缓存处理

### 6. common-security（安全模块）
安全框架模块，依赖于common-core和common-web，提供：
- Spring Security集成
- JWT支持
- Redis会话管理（可选）
- 安全注解
- 认证授权接口
- 安全审计
- 令牌服务
- 加密服务
- 数据脱敏

### 7. common-log（日志模块）
日志功能模块，依赖于common-core、common-web、common-security和common-data，提供：
- Spring Boot日志集成
- AOP日志处理
- Web日志支持（可选）
- 配置处理

### 8. common-message（消息模块）
消息处理模块，依赖于common-core、common-util和common-cache，提供：
- RocketMQ集成
- 邮件服务
- 模板引擎支持（Thymeleaf，可选）
- Web支持（可选）

### 9. common-test（测试模块）
测试支持模块，依赖于其他所有模块，提供：
- Spring Boot测试支持
- Spring Security测试支持

## 子模块文档索引

- [common-cache](./common-cache/README.md)
- [common-core](./common-core/README.md)
- [common-data](./common-data/README.md)
- [common-log](./common-log/README.md)
- [common-message](./common-message/README.md)
- [common-security](./common-security/README.md)
- [common-test](./common-test/README.md)
- [common-util](./common-util/README.md)
- [common-web](./common-web/README.md)

## 使用说明

### 1. 依赖引入
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-common</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 模块依赖
根据需要引入相应模块：
```xml
<!-- 核心模块 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-core</artifactId>
</dependency>

<!-- 工具模块 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-util</artifactId>
</dependency>

<!-- Web模块 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-web</artifactId>
</dependency>

<!-- 数据模块 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-data</artifactId>
</dependency>

<!-- 缓存模块 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-cache</artifactId>
</dependency>

<!-- 安全模块 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-security</artifactId>
</dependency>

<!-- 日志模块 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-log</artifactId>
</dependency>

<!-- 消息模块 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-message</artifactId>
</dependency>

<!-- 测试模块 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-test</artifactId>
    <scope>test</scope>
</dependency>
```

### 3. 配置说明
```yaml
# application.yml
law-firm:
  common:
    core:
      enabled: true
    web:
      enabled: true
    data:
      enabled: true
    cache:
      enabled: true
    security:
      enabled: true
    log:
      enabled: true
    message:
      enabled: true
```

## 最佳实践
1. 优先使用基础设施层提供的功能
2. 遵循模块的设计规范
3. 合理使用注解和配置
4. 注意性能优化
5. 做好异常处理
6. 完善单元测试

## 更新日志
- 2024-03-18: 初始版本发布