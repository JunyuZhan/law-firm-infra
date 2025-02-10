# 依赖管理模块 (Dependencies)

## 模块说明
依赖管理模块是整个律师事务所管理系统的依赖版本管理中心，负责统一管理和控制所有第三方依赖的版本，确保各个模块使用的依赖版本一致性和兼容性。

## 主要功能
- 统一的依赖版本管理
- 依赖冲突解决
- 可重用的依赖组合
- 构建配置管理

## 依赖版本管理

### 核心框架
- Spring Boot: 2.7.x
- Spring Cloud: 2021.0.x
- Spring Cloud Alibaba: 2021.0.x

### 数据库相关
- MySQL Connector: 8.0.x
- MyBatis Plus: 3.5.x
- Druid: 1.2.x

### 工具库
- Hutool: 5.8.x
- Guava: 31.1-jre
- Apache Commons
  - commons-lang3: 3.12.0
  - commons-collections4: 4.4
  - commons-io: 2.11.0

### Web相关
- Servlet API: 4.0.x
- Jackson: 2.13.x
- Swagger/OpenAPI: 3.0.x

### 安全框架
- Spring Security: 5.7.x
- JWT: 0.11.x
- Apache Shiro: 1.9.x

### 缓存
- Redis: 6.2.x
- Caffeine: 3.1.x

### 消息队列
- RocketMQ: 4.9.x
- Kafka: 3.3.x

### 存储
- MinIO: 8.4.x
- FastDFS: 1.27.x

### 监控和日志
- Spring Boot Admin: 2.7.x
- Micrometer: 1.9.x
- Logback: 1.2.x
- Log4j2: 2.17.x

### 测试框架
- JUnit Jupiter: 5.8.x
- Mockito: 4.8.x
- TestContainers: 1.17.x

## 使用说明

### 父项目依赖
```xml
<parent>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-dependencies</artifactId>
    <version>${revision}</version>
    <relativePath/>
</parent>
```

### 依赖引入示例
```xml
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- 数据库 -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
    </dependency>

    <!-- 工具库 -->
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
    </dependency>
</dependencies>
```

## 版本管理规范

### 版本号规则
- 主版本号：重大架构变更
- 次版本号：功能性新增
- 修订号：Bug修复、小改动

### 版本发布流程
1. SNAPSHOT版本：开发测试阶段
2. RC版本：预发布阶段
3. RELEASE版本：正式发布

## 依赖管理原则

### 版本选择原则
1. 稳定性优先
2. 安全性要求
3. 性能考虑
4. 社区活跃度
5. 向后兼容性

### 依赖升级原则
1. 定期检查安全漏洞
2. 评估升级影响
3. 充分的测试验证
4. 增量升级为主
5. 保持文档更新

## 注意事项
1. 不要在子模块中指定依赖版本号
2. 需要排除的依赖要在文档中说明
3. 添加新依赖需要经过评审
4. 定期检查依赖更新
5. 关注安全漏洞通告

## 常见问题
1. 依赖冲突解决
2. 版本兼容性问题
3. 传递性依赖管理
4. 可选依赖处理
5. 依赖排除规则 