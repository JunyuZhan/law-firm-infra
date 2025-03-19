# 基础设施层 (Infrastructure Layer)

## 模块说明
基础设施层提供了一系列通用、抽象的基础设施支持，包括核心功能、工具类、Web支持、数据访问、缓存、安全、日志、消息和测试等基础功能。

## 模块列表

### 1. common-core（核心模块）
- 统一响应处理
- 统一异常处理
- 基础配置支持
- 基础实体支持
- 上下文管理
- 常量定义

### 2. common-util（工具模块）
- ID生成工具
- Bean工具类
- 文件处理工具
- 验证工具类
- 字符串工具类
- JSON处理工具
- 图片处理工具
- HTTP工具类
- 地理信息工具
- Excel处理工具
- 日期处理工具
- 加密解密工具
- 压缩解压工具
- 编码解码工具
- 集合处理工具

### 3. common-web（Web模块）
- Web工具类
- Web支持类
- 响应处理
- 过滤器
- 请求处理
- 异常处理
- 常量定义
- Web配置
- Web上下文

### 4. common-data（数据模块）
- 序列化处理
- 属性处理
- 查询处理
- 实体处理
- 数据配置
- 数据注解
- 数据切面

### 5. common-cache（缓存模块）
- 缓存服务
- 缓存常量
- 缓存配置
- 缓存切面
- 缓存注解

### 6. common-security（安全模块）
- 安全配置
- 安全工具
- 令牌处理
- 加密处理
- 安全核心
- 安全上下文
- 安全常量
- 授权处理
- 认证处理
- 安全审计
- 安全注解

### 7. common-log（日志模块）
- 日志事件
- 日志工具
- 日志属性
- 日志过滤
- 日志配置
- 日志切面
- 日志注解

### 8. common-message（消息模块）
- 消息处理
- 消息支持

### 9. common-test（测试模块）
- 测试配置
- 测试应用
- 基础测试
- 集成测试
- 性能测试

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

## 详细文档
- [核心模块文档](common-core/README.md)
- [工具模块文档](common-util/README.md)
- [Web模块文档](common-web/README.md)
- [数据模块文档](common-data/README.md)
- [缓存模块文档](common-cache/README.md)
- [安全模块文档](common-security/README.md)
- [日志模块文档](common-log/README.md)
- [消息模块文档](common-message/README.md)
- [测试模块文档](common-test/README.md)

## 最佳实践
1. 优先使用基础设施层提供的功能
2. 遵循模块的设计规范
3. 合理使用注解和配置
4. 注意性能优化
5. 做好异常处理
6. 完善单元测试

## 常见问题
1. 模块依赖问题
2. 配置问题
3. 性能问题
4. 安全问题
5. 日志问题

## 更新日志
- 2024-03-18: 初始版本发布