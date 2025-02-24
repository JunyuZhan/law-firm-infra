# 通用层 (Common Layer)

## 模块说明
通用层是律师事务所管理系统的基础设施层，提供了各个业务模块所需的通用功能支持。该层采用模块化设计，每个子模块都专注于特定的功能领域，以确保代码的高内聚低耦合。

## 技术栈
- JDK 21
- Spring Boot 3.2.2
- SpringDoc OpenAPI 2.3.0
- Knife4j 4.3.0
- MapStruct 1.5.5.Final
- Lombok 1.18.30
- Hutool 5.8.25
- Apache Commons
- Guava 32.1.3
- FastJSON 2.0.45

## 模块结构

### 1. 核心模块 (common-core)
核心模块是整个通用层的基础，提供最基本的功能支持。

```
common-core/
├── api/          # 基础API接口定义
├── config/       # 核心配置类
├── constant/     # 系统常量定义
├── context/      # 上下文管理
├── entity/       # 基础实体类
└── exception/    # 异常处理机制
```

主要功能：
- 基础接口定义
- 核心配置管理
- 全局常量维护
- 上下文管理机制
- 基础实体类定义
- 统一异常处理

### 2. 工具模块 (common-util)
提供丰富的工具类支持，简化开发过程。

```
common-util/
├── base/         # 基础工具类
│   ├── BaseUtils.java
│   ├── ServletUtils.java
│   └── SpringUtils.java
├── collection/   # 集合工具
├── compress/     # 压缩解压工具
├── crypto/       # 加密解密工具
├── date/         # 日期时间工具
├── excel/        # Excel处理工具
├── file/         # 文件操作工具
├── geo/          # 地理位置工具
├── http/         # HTTP请求工具
├── id/           # ID生成工具
├── image/        # 图片处理工具
├── json/         # JSON处理工具
├── string/       # 字符串处理工具
└── validate/     # 数据验证工具
```

主要功能：
- 通用工具类集合
- 数据格式转换
- 文件操作处理
- 加密解密功能
- 验证工具集合

### 3. Web模块 (common-web)
提供Web应用开发所需的各种支持。

```
common-web/
├── config/       # Web配置
├── constant/     # Web常量
├── context/      # Web上下文
├── exception/    # Web异常处理
├── filter/       # 过滤器
├── request/      # 请求处理
├── response/     # 响应处理
├── support/      # 支持类
└── utils/        # Web工具类
```

主要功能：
- Web环境配置
- 请求响应处理
- 统一异常处理
- 过滤器链管理
- 上下文管理

### 4. 安全模块 (common-security)
提供系统安全相关的基础设施。

```
common-security/
├── annotation/   # 安全注解
├── authentication/ # 认证相关
├── authorization/  # 授权相关
├── audit/         # 安全审计
├── context/       # 安全上下文
├── crypto/        # 加密服务
├── token/         # 令牌服务
└── constants/     # 安全常量
```

主要功能：
- 认证授权框架
- 安全注解支持
- 审计日志记录
- 加密解密服务
- 令牌管理服务

### 5. 数据模块 (common-data)
提供数据访问和处理的统一支持。

```
common-data/
├── config/       # 数据源配置
├── handler/      # 数据处理器
├── interceptor/  # 数据拦截器
└── utils/        # 数据工具类
```

主要功能：
- 数据源管理
- 数据访问支持
- 数据处理工具
- 拦截器支持

### 6. 缓存模块 (common-cache)
提供统一的缓存处理机制。

```
common-cache/
├── config/       # 缓存配置
├── handler/      # 缓存处理器
└── utils/        # 缓存工具类
```

主要功能：
- 缓存配置管理
- 缓存操作封装
- 缓存工具支持

### 7. 日志模块 (common-log)
提供统一的日志处理机制。

```
common-log/
├── annotation/   # 日志注解
├── aspect/       # 日志切面
├── event/        # 日志事件
└── service/      # 日志服务
```

主要功能：
- 操作日志记录
- 审计日志管理
- 日志切面处理
- 日志事件发布

### 8. 消息模块 (common-message)
提供消息处理的统一支持。

```
common-message/
├── config/       # 消息配置
├── handler/      # 消息处理器
├── producer/     # 消息生产者
└── consumer/     # 消息消费者
```

主要功能：
- 消息配置管理
- 消息发送接收
- 消息处理机制

### 9. 测试模块 (common-test)
提供测试相关的支持。

```
common-test/
├── annotation/   # 测试注解
├── mock/         # Mock工具
└── utils/        # 测试工具类
```

主要功能：
- 测试工具支持
- Mock数据生成
- 测试用例支持

## 依赖关系
```
common-core           # 基础依赖
    ├── common-util   # 依赖core
    ├── common-web    # 依赖core
    ├── common-data   # 依赖core和util
    ├── common-cache  # 依赖core和util
    ├── common-security # 依赖core和web
    ├── common-log    # 依赖core和security
    ├── common-message # 依赖core、util和cache
    └── common-test   # 依赖所有模块
```

## 使用说明

### 1. 添加依赖
在需要使用通用层功能的模块的pom.xml中添加相应依赖：

```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-core</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 配置说明
各模块的配置项都在application.yml中进行配置，例如：

```yaml
lawfirm:
  common:
    web:
      cors:
      enabled: true
    security:
      token:
        expire: 7200
    cache:
      type: redis
    log:
      enabled: true
```

### 3. 开发建议
1. 遵循依赖关系，避免循环依赖
2. 优先使用工具模块提供的工具类
3. 遵循接口定义，实现自定义扩展
4. 合理使用注解简化开发
5. 注意异常处理和日志记录

## 版本历史

### 1.0.0 (2024-02-21)
- 初始版本发布
- 完整的模块化架构
- 基础功能支持
- 完整的文档支持 