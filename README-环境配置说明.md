# 律师事务所管理系统环境配置说明

本文档详细介绍系统的环境配置方案，适用于开发人员、测试人员和运维人员。

## 一、多环境配置概述

系统支持多种运行环境，通过不同的配置文件和启动参数实现差异化配置。目前支持以下环境：

1. **开发环境（develop）**：用于开发人员日常开发，配置宽松，日志详尽
2. **测试环境（test）**：用于功能测试和集成测试，接近生产环境但保留部分调试功能
3. **生产环境（prod）**：用于正式部署，安全性和性能优先，关闭调试功能

## 二、配置文件及其关系

系统采用Spring Boot的多环境配置方案，配置文件加载顺序如下：

1. `application.yml` - 主配置文件，包含共享的基础配置和默认值
2. `application-{profile}.yml` - 特定环境的配置文件，会覆盖主配置中的同名配置

### 配置加载原理

- 当使用 `--spring.profiles.active=develop` 启动应用时，系统会：
  1. 先加载 `application.yml` 中的所有配置
  2. 然后加载 `application-develop.yml` 中的配置，并覆盖同名配置项

- 这种机制允许在不同环境使用不同的配置值，同时共享大部分基础配置

### 环境配置文件清单

| 配置文件名 | 用途 | 主要特点 |
|------------|------|----------|
| application.yml | 主配置文件 | 包含所有默认配置和共享属性 |
| application-develop.yml | 开发环境配置 | 开启详细日志、禁用安全特性 |
| application-test.yml | 测试环境配置 | 适中日志级别、基本安全特性、测试数据库 |
| application-prod.yml | 生产环境配置 | 最小日志、完整安全特性、性能优化 |

## 三、关键配置项说明

### 1. 数据库配置

主配置文件中定义基础数据库配置：

```yaml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: ${SPRING_DATASOURCE_DRIVER_CLASS_NAME:com.mysql.cj.jdbc.Driver}
    url: ${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/law_firm?...}
    username: ${SPRING_DATASOURCE_USERNAME:root}
    password: ${SPRING_DATASOURCE_PASSWORD:${DATABASE_PASSWORD:}}
    hikari:
      # 连接池配置...
```

各环境的差异：

- **开发环境**：可使用环境变量 `DEV_DB_PASSWORD` 提供密码
- **测试环境**：使用单独的测试库 `law_firm_test`，通过 `TEST_DB_PASSWORD` 提供密码
- **生产环境**：强制使用 `SPRING_DATASOURCE_PASSWORD` 环境变量提供密码，不硬编码

> **重要说明**：我们已移除了环境配置文件中的 `lawfirm.database` 部分，统一使用 `spring.datasource`
> 配置数据源，避免重复和混淆。

### 2. 日志配置

日志配置反映了不同环境的调试需求：

```yaml
logging:
  level:
    root: 日志级别
    com.lawfirm: 应用日志级别
```

环境差异：
- **开发环境**：详细日志 (DEBUG级别)
- **测试环境**：适中日志 (INFO级别)
- **生产环境**：最少日志 (WARN/INFO级别)，且配置日志文件和滚动策略

### 3. 安全配置

安全组件的启用状态：

```yaml
security:
  enabled: true/false  # 是否启用安全组件
  ignore-urls:
    # 安全豁免的URL列表
```

环境差异：
- **开发环境**：禁用安全组件 (`enabled: false`)
- **测试环境**：启用基本安全，保留更多豁免URL
- **生产环境**：启用完整安全特性，最小化豁免URL，严格的CORS配置

### 4. 业务功能配置

各业务模块的启用状态：

```yaml
lawfirm:
  search:
    enabled: true/false
  storage:
    enabled: true/false
  cache:
    enabled: true/false
```

环境差异：
- **开发环境**：大部分高级功能关闭，使用本地简化实现
- **测试环境**：启用部分功能，使用本地实现
- **生产环境**：启用全部功能，使用生产级实现

## 四、环境变量支持

系统广泛支持通过环境变量覆盖配置。所有配置文件中的 `${VAR_NAME:default}` 格式表示：
- 如果环境变量 `VAR_NAME` 存在，则使用其值
- 否则使用冒号后的默认值 `default`

### 主要环境变量

| 环境变量 | 说明 | 默认值 | 使用环境 |
|---------|------|-------|---------|
| SPRING_PROFILES_ACTIVE | 激活的环境 | develop | 所有 |
| DEV_DB_PASSWORD | 开发数据库密码 | (空) | 开发环境 |
| TEST_DB_PASSWORD | 测试数据库密码 | (空) | 测试环境 |
| SPRING_DATASOURCE_PASSWORD | 生产数据库密码 | (空) | 生产环境 |
| LOG_PATH | 日志文件路径 | /var/log/lawfirm | 生产环境 |
| LAWFIRM_STORAGE_TYPE | 存储类型 | LOCAL/MINIO | 所有环境 |

## 五、配置最佳实践

### 1. 避免重复配置

- 将共享配置放在 `application.yml` 中
- 环境配置文件中只包含需要覆盖的配置项
- 不要在多个位置定义相同的配置项（如我们移除了 `lawfirm.database` 重复配置）

### 2. 敏感信息处理

- 开发环境：可使用环境变量或本地配置文件（不提交到版本控制）
- 测试环境：使用环境变量或安全的配置管理工具
- 生产环境：严格使用环境变量，不在配置文件中硬编码敏感信息

### 3. 环境特定配置

- 数据库连接：每个环境使用不同的数据库
- 性能参数：生产环境优化连接池、缓存等参数
- 安全配置：生产环境启用全部安全特性
- 日志级别：根据环境调整日志详细程度

## 六、启动应用

### 1. 指定环境启动

```bash
# 开发环境
java -jar law-firm-api-1.0.0.jar --spring.profiles.active=develop

# 测试环境
java -jar law-firm-api-1.0.0.jar --spring.profiles.active=test

# 生产环境
java -jar law-firm-api-1.0.0.jar --spring.profiles.active=prod
```

### 2. 使用环境变量

```bash
# 设置环境变量并启动
export SPRING_PROFILES_ACTIVE=prod
export SPRING_DATASOURCE_PASSWORD=secure_password
java -jar law-firm-api-1.0.0.jar
```

### 3. 使用启动脚本

```bash
# 使用项目提供的启动脚本
./start-law-firm.sh  # 或 start-law-firm.bat (Windows)
```

## 七、新增环境配置

如需添加新的环境配置（如预生产环境），按照以下步骤操作：

1. 创建新的配置文件 `application-{环境名}.yml`
2. 基于现有配置文件，只包含需要覆盖的配置项
3. 启动时使用 `--spring.profiles.active={环境名}`

例如，创建预生产环境配置 `application-staging.yml`：

```yaml
# 预生产环境配置
spring:
  datasource:
    url: jdbc:mysql://staging-db:3306/law_firm?...
    username: ${STAGING_DB_USERNAME:root}
    password: ${STAGING_DB_PASSWORD:}

# 调整日志级别
logging:
  level:
    root: INFO
    com.lawfirm: INFO

# 其他特定配置...
``` 