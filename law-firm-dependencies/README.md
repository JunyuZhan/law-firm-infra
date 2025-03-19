# 依赖管理模块 (Dependencies)

## 模块说明
依赖管理模块是整个律师事务所管理系统的依赖版本管理中心，负责统一管理和控制所有第三方依赖的版本，确保各个模块使用的依赖版本一致性和兼容性。

## 主要功能
- 统一的依赖版本管理
- 依赖冲突解决
- 可重用的依赖组合
- 构建配置管理

## 依赖版本管理

### 基础环境
- Java: 21
- Maven: 3.8+
- Spring Boot: 3.2.2
- Spring Cloud: 2023.x
- Spring Security: 6.x

### 核心框架
- Spring Boot Starter Web
- Spring Boot Starter Validation
- Spring Boot Starter Cache
- Spring Boot Starter Mail
- Spring Boot Configuration Processor
- Spring Boot Starter Test

### 数据库相关
- MySQL Connector: 8.0.x
- MyBatis Plus: 3.5.5
- Druid: 1.2.20
- Dynamic DataSource: 4.2.0
- Flyway: 9.22.3

### 工具库
- Hutool: 5.8.25
- Guava: 32.1.3-jre
- Apache Commons
  - commons-lang3: 3.14.0
  - commons-collections4: 4.4
  - commons-io: 2.15.1
  - commons-csv: 1.9.0
- MapStruct: 1.5.5.Final
- Lombok: 1.18.30
- FastJSON2: 2.0.45
- Jackson: 2.16.1
- EasyExcel: 3.3.3

### Web相关
- SpringDoc OpenAPI: 2.3.0
- Knife4j: 4.3.0
- Swagger Annotations: 1.6.11
- Hibernate Validator: 8.0.1.Final

### 安全框架
- Spring Security
- JWT: 0.12.3
- Easy Captcha: 1.6.2

### 缓存
- Redis: 6.x
- Redisson: 3.25.2
- Caffeine: 3.1.8

### 消息队列
- RocketMQ: 2.2.3

### 存储
- MinIO: 8.5.7
- 阿里云OSS: 3.17.2
- 腾讯云COS: 5.6.97

### 文档处理
- iText: 7.2.5
- PDFBox: 2.0.29
- POI: 5.2.3
- PDF2DOM: 2.0.1
- XDocReport: 2.0.3

### 工作流
- Flowable: 7.0.0
  - flowable-spring-boot-starter-process
  - flowable-spring-boot-starter-actuator
  - flowable-engine-common-api
  - flowable-engine
  - flowable-spring
  - flowable-variable-service-api
  - flowable-event-registry-api
  - flowable-process-validation
  - flowable-bpmn-model
  - flowable-bpmn-converter

### 测试框架
- JUnit Jupiter: 5.10.1
- Mockito: 5.8.0
- AssertJ: 3.24.2
- TestContainers: 1.19.3
- H2: 2.2.224

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
1. 稳定性优先：选择经过充分验证的稳定版本
2. 安全性要求：及时修复安全漏洞
3. 性能考虑：评估依赖对系统性能的影响
4. 社区活跃度：优先选择活跃维护的依赖
5. 向后兼容性：确保版本升级不会破坏现有功能

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

### 依赖冲突解决
1. 使用Maven依赖树分析冲突
2. 使用exclusions排除冲突依赖
3. 使用dependencyManagement管理传递依赖
4. 必要时使用force强制版本

### 版本兼容性问题
1. Spring Boot 3.x要求Java 17+
2. 部分依赖可能需要特定版本
3. 注意依赖间的版本兼容性

### 传递性依赖管理
1. 使用dependencyManagement控制传递依赖
2. 必要时使用exclusions排除不需要的传递依赖
3. 定期检查传递依赖更新

### 可选依赖处理
1. 明确标记可选依赖
2. 提供可选依赖的替代方案
3. 在文档中说明可选依赖的用途

### 依赖排除规则
1. 排除重复的传递依赖
2. 排除不需要的传递依赖
3. 排除有安全漏洞的依赖
4. 排除过时的依赖 