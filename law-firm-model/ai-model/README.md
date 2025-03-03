# AI数据模型模块

## 模块说明
AI数据模型模块是一个纯定义模块，负责定义与AI相关的所有实体、DTO、VO、枚举和常量等。本模块不包含具体实现，所有实现都将在对应的服务模块中完成。

## 目录结构
```
ai-model/
├── entity/           # 实体定义
│   ├── AIModelConfig.java   # AI模型配置实体
│   └── UserFeedback.java     # 用户反馈实体
├── dto/              # 传输对象
│   ├── AIRequestDTO.java      # AI请求对象
│   └── AIResponseDTO.java     # AI响应对象
├── vo/               # 视图对象
│   └── AIResultVO.java         # AI结果视图对象
├── enums/            # 枚举定义
│   ├── ModelStatus.java         # AI模型状态枚举
│   └── FeedbackType.java        # 用户反馈类型枚举
├── constant/         # 常量定义
│   └── AIConstants.java          # 与AI模块相关的常量
└── service/          # 服务接口定义
    ├── AIService.java           # AI服务接口
    └── FeedbackService.java      # 用户反馈服务接口
```

## 功能
- 定义AI模型的配置和反馈数据结构
- 提供与AI相关的数据传输和展示格式

## 依赖
- **common-core**: 提供核心功能
- **common-data**: 提供数据访问功能
- **base-model**: 提供基础数据模型支持
- **spring-boot-starter**: Spring Boot基础支持 

## 使用说明

### 1. 依赖引入
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>ai-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 实体使用
- 所有实体都应遵循定义的接口和规范。

### 3. 注意事项
- 本模块仅包含定义，不包含实现。
- 具体实现在对应的服务模块中完成。
- 遵循接口定义规范。
- 注意依赖管理.

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
- 支持自定义AI模型

## 核心定义

### 1. 实体定义（entity）
- AIModelConfig：AI模型配置实体
- UserFeedback：用户反馈实体

### 2. 数据传输对象（dto）
- AIRequestDTO：AI请求对象
- AIResponseDTO：AI响应对象

### 3. 视图对象（vo）
- AIResultVO：AI结果视图对象

### 4. 枚举定义（enums）
- ModelStatus：AI模型状态枚举
- FeedbackType：用户反馈类型枚举

### 5. 常量定义（constant）
- AIConstants：与AI模块相关的常量

### 6. 服务接口（service）
- **AIService**: 提供AI模型的调用和管理功能。
- **FeedbackService**: 处理用户反馈的相关功能。

## 使用说明

### 1. 依赖引入
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>ai-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 实体使用
- 所有实体都应遵循定义的接口和规范。

### 3. 注意事项
- 本模块仅包含定义，不包含实现。
- 具体实现在对应的服务模块中完成。
- 遵循接口定义规范。
- 注意依赖管理. 