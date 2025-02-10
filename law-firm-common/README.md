# 公共模块 (Common)

## 模块说明
公共模块是律师事务所管理系统的基础支撑模块，提供了各个业务模块共用的工具类、通用功能和基础设施代码。该模块被所有其他模块依赖，是整个系统的基石。

## 子模块说明

### common-core
核心功能模块，提供最基础的工具和通用功能：
- 基础实体类
- 通用枚举
- 异常处理
- 工具类库
- 常量定义

### common-data
数据访问模块，提供统一的数据访问支持：
- 数据源配置
- MyBatis增强
- 分页支持
- 数据审计
- 多租户支持

### common-redis
缓存处理模块，提供统一的缓存操作接口：
- Redis配置
- 缓存操作封装
- 分布式锁
- 缓存注解
- 会话管理

### common-log
日志处理模块，提供统一的日志记录功能：
- 操作日志
- 审计日志
- 异常日志
- 性能日志
- 日志查询

### common-security
安全框架模块，提供统一的安全管理功能：
- 认证授权
- 权限控制
- 密码管理
- 安全过滤
- 防护配置

### common-swagger
API文档模块，提供统一的接口文档管理：
- Swagger配置
- 接口文档
- 在线调试
- 文档导出
- 版本管理

### common-web
Web功能模块，提供统一的Web层支持：
- 统一响应
- 统一异常处理
- 参数校验
- 跨域配置
- 文件处理

## 技术架构

### 核心依赖
- Spring Boot
- Spring Cloud
- MyBatis Plus
- Redis
- Spring Security
- Swagger

### 设计理念
1. 高内聚，低耦合
2. 可扩展性优先
3. 约定优于配置
4. DRY原则
5. SOLID原则

## 使用说明

### 引入依赖
```xml
<!-- 核心功能 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-core</artifactId>
    <version>${project.version}</version>
</dependency>

<!-- 数据访问 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-data</artifactId>
    <version>${project.version}</version>
</dependency>

<!-- 根据需要引入其他模块 -->
```

### 配置示例
```yaml
lawfirm:
  common:
    # 数据源配置
    datasource:
      enabled: true
      url: jdbc:mysql://localhost:3306/lawfirm
      username: root
      password: root
    
    # Redis配置
    redis:
      enabled: true
      host: localhost
      port: 6379
    
    # 安全配置
    security:
      enabled: true
      ignore-urls:
        - /api/auth/**
        - /api/public/**
```

## 开发规范

### 命名规范
1. 包名：com.lawfirm.common.{module}
2. 类名：驼峰命名，见名知意
3. 方法名：动词开头，驼峰命名
4. 常量：全大写，下划线分隔

### 代码规范
1. 遵循阿里巴巴Java开发手册
2. 必须添加完整注释
3. 统一异常处理
4. 统一日志记录
5. 统一返回格式

## 扩展开发

### 新增工具类
1. 在appropriate模块下创建工具类
2. 添加完整注释和单元测试
3. 在README中更新文档
4. 提交代码评审

### 新增功能模块
1. 创建新的Maven模块
2. 配置正确的依赖关系
3. 实现基础功能
4. 编写单元测试
5. 更新文档

## 版本说明

### 当前版本
- 版本号：1.0.0
- 发布日期：2024-02-09
- 主要特性：
  - 基础功能完善
  - 性能优化
  - 安全加固

### 版本规划
- 1.1.0：性能优化
- 1.2.0：新增特性
- 2.0.0：架构升级

## 常见问题

### 问题排查
1. 依赖冲突
2. 配置无效
3. 性能问题
4. 安全漏洞
5. 兼容性问题

### 解决方案
1. 检查依赖版本
2. 查看启动日志
3. 使用监控工具
4. 升级补丁版本
5. 参考文档说明

## 注意事项
1. 保持向后兼容
2. 避免重复造轮子
3. 关注性能影响
4. 注意代码质量
5. 及时更新文档 