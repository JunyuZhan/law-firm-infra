# 法律事务管理系统配置指南

## 配置文件结构

系统采用分层模块化的配置文件结构，确保配置的高内聚、低耦合和易维护性。

### 配置文件命名规范

1. **主配置文件**：`application.yml`
   - 位置：API层（`law-firm-api/src/main/resources/application.yml`）
   - 作用：定义基础配置和导入其他配置文件

2. **环境特定配置**：`application-{env}.yml`
   - 位置：API层（`law-firm-api/src/main/resources/config/`）
   - 环境：`dev`、`test`、`prod`
   - 作用：定义特定环境的配置（如数据库连接、服务地址等）

3. **模块特定配置**：`application-{module}.yml`
   - 位置：各模块的资源目录（`src/main/resources/`）
   - 作用：定义特定模块的配置（如工作流、搜索、存储等）

4. **通用配置**：`application-common.yml`
   - 位置：API层（`law-firm-api/src/main/resources/config/`）
   - 作用：定义通用的配置（如线程池、缓存、安全策略等）

## 配置文件内容规范

1. **顶部注释**：每个配置文件需要在顶部添加注释，说明配置文件的用途

2. **分组注释**：使用注释将相关配置分组，提高可读性

3. **配置项注释**：对于重要配置项，添加注释说明用途和可选值

4. **环境变量**：所有可能随环境变化的配置均使用环境变量
   - 格式：`${ENV_VAR:default_value}`
   - 命名：使用大写字母和下划线，形如`MODULE_FUNCTION_PROPERTY`

5. **配置命名空间**：
   - 使用模块名称作为顶级配置命名空间
   - 例如：`workflow.config.datasource.schema`

## 敏感信息处理

1. **加密敏感信息**：
   - 使用Jasypt对敏感信息加密
   - 格式：`ENC(${ENV_VAR:encrypted_value})`

2. **避免硬编码**：
   - 不在配置文件中硬编码密码、密钥等敏感信息
   - 使用环境变量或密钥管理系统

## 配置验证

1. **验证类**：
   - 为每个配置模块创建对应的验证类
   - 使用`@Validated`和`@ConfigurationProperties`注解

2. **验证规则**：
   - 使用JSR-380注解（`@NotNull`、`@Min`等）验证配置值
   - 提供有意义的错误信息

## 环境配置示例

### 开发环境 (application-dev.yml)

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/law_firm?useUnicode=true&characterEncoding=utf8
    username: ${MYSQL_USERNAME:root}
    password: ${MYSQL_PASSWORD:password}
```

### 生产环境 (application-prod.yml)

```yml
spring:
  datasource:
    url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE}?useUnicode=true&characterEncoding=utf8
    username: ${MYSQL_USERNAME}
    password: ENC(${MYSQL_PASSWORD})
```

## 模块配置示例

### 工作流模块 (application-workflow.yml)

```yml
workflow:
  config:
    datasource:
      schema: law_firm_workflow

flowable:
  database-schema-update: false
  async-executor-activate: true
  history-level: full
```

## 通用配置示例

### 通用配置 (application-common.yml)

```yml
spring:
  application:
    name: law-firm
  main:
    allow-bean-definition-overriding: false
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
```

## 最佳实践

1. **最小化配置**：只在配置文件中定义必要的配置，使用合理的默认值

2. **配置分层**：将配置分层，避免重复定义

3. **定期审查**：定期审查配置文件，移除未使用的配置

4. **文档化**：为配置项添加明确的注释和文档

5. **版本控制**：将配置文件纳入版本控制，跟踪配置变更

6. **集中管理**：集中管理环境变量和敏感信息 