# 日志模型模块 (Log Model)

## 模块说明
日志模型模块是一个纯定义模块，负责定义系统中所有与日志相关的实体、接口、服务定义、枚举等。本模块不包含具体实现，所有实现都将在对应的服务模块中完成。

## 设计原则

### 1. 纯定义原则
- 只包含接口、实体、DTO等定义
- 不包含具体实现代码
- 不包含业务逻辑
- 不包含具体实现的测试代码

### 2. 依赖原则
- 只依赖基础模型层（base-model）
- 不依赖具体实现模块
- 避免循环依赖
- 最小化外部依赖

### 3. 扩展原则
- 定义通用的扩展接口
- 预留扩展点
- 支持自定义日志类型
- 支持自定义处理逻辑

## 核心定义

### 1. 实体定义（entity）
- BaseLog：日志基类，定义共同属性
- SystemLog：系统日志实体定义
- AuditLog：审计日志实体定义
- OperationLog：操作日志实体定义
- LoginLog：登录日志实体定义

### 2. 数据传输对象（dto）
- LogCreateDTO：日志创建传输对象
- LogQueryDTO：日志查询传输对象
- LogExportDTO：日志导出传输对象
- LogAnalysisDTO：日志分析传输对象

### 3. 视图对象（vo）
- LogVO：日志视图对象
- LogStatVO：日志统计视图对象
- LogAnalysisVO：日志分析视图对象

### 4. 枚举定义（enums）
- LogTypeEnum：日志类型枚举
- LogLevelEnum：日志级别枚举
- OperateTypeEnum：操作类型枚举
- BusinessTypeEnum：业务类型枚举

### 5. 服务接口（service）
- LogService：日志服务接口
- LogAnalysisService：日志分析服务接口
- LogExportService：日志导出服务接口

### 6. 事件定义（event）
- LogEvent：日志事件定义
- LogEventListener：日志事件监听器接口

### 7. 切面定义（aspect）
- LogAspect：日志切面定义
- LogPointcut：日志切点定义

## 目录结构
```
log-model/
├── entity/           # 日志实体定义
│   ├── base/         # 基础日志
│   │   └── BaseLog.java
│   ├── system/       # 系统日志
│   │   └── SystemLog.java
│   ├── audit/        # 审计日志
│   │   └── AuditLog.java
│   └── operation/    # 操作日志
│       └── OperationLog.java
├── dto/              # 数据传输对象
│   ├── LogCreateDTO.java
│   ├── LogQueryDTO.java
│   └── LogExportDTO.java
├── vo/               # 视图对象
│   ├── LogVO.java
│   └── LogStatVO.java
├── enums/            # 枚举定义
│   ├── LogTypeEnum.java
│   ├── LogLevelEnum.java
│   └── OperateTypeEnum.java
├── service/          # 服务接口
│   ├── LogService.java
│   └── LogAnalysisService.java
├── event/            # 事件定义
│   ├── LogEvent.java
│   └── LogEventListener.java
└── aspect/           # 切面定义
    └── LogAspect.java
```

## 使用说明

### 1. 依赖引入
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>log-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 实体使用
- 所有日志实体都继承自BaseLog
- 可以根据需要扩展自定义日志实体
- 实体定义包含必要的属性和方法

### 3. 服务接口
- LogService定义了基础的日志操作接口
- 可以实现自定义的日志服务
- 支持泛型以适应不同日志类型

### 4. 事件使用
- 通过LogEvent定义日志相关事件
- 实现LogEventListener处理日志事件
- 支持异步事件处理

### 5. 注意事项
- 本模块仅包含定义，不包含实现
- 具体实现在对应的服务模块中完成
- 遵循接口定义规范
- 注意依赖管理 

## 迁移记录
### JPA到MyBatis Plus迁移 (2024-04-28)
- 验证完成：本模块已使用MyBatis Plus注解
- 使用的注解包括@TableName和@TableField
- 无需移除JPA相关注解和导入，因为本模块从创建开始就使用MyBatis Plus
- 已确认pom.xml中使用的是MyBatis Plus依赖，没有包含Spring Data JPA依赖
- 迁移完成的实体类:
  - BaseLog
  - AuditableLog
  - SystemLog
  - LoginLog
  - AuditLog
  - AuditRecord
  - BusinessLog
  - OperationLog 