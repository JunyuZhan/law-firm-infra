# 基础模型模块 (Base Model)

## 模块说明
基础模型模块是一个纯定义模块，为整个系统提供最基础的模型定义，包括基础实体、DTO、查询对象、结果对象等。本模块作为其他模型模块的基础依赖，提供统一的模型定义规范。

## 设计原则

### 1. 纯定义原则
- 只包含基础接口和抽象类定义
- 不包含具体业务实现
- 提供统一的模型规范
- 允许包含基础定义的单元测试

### 2. 依赖原则
- 最小化外部依赖
- 不依赖具体实现模块
- 作为其他模型模块的基础
- 避免循环依赖

### 3. 扩展原则
- 提供清晰的扩展点
- 使用抽象类和接口
- 支持灵活的扩展方式
- 保持向后兼容

## 核心定义

### 1. 实体定义（entity）
- ModelBaseEntity：模型基础实体，提供通用属性
- TreeEntity：树形结构实体，用于层级数据
- TenantEntity：租户实体，支持多租户

### 2. 数据传输对象（dto）
- BaseDTO：基础传输对象
- BaseExportDTO：导出基础对象

### 3. 查询对象（query）
- BaseQuery：基础查询对象，支持分页和排序

### 4. 结果对象（result）
- BaseResult：统一返回结果对象

### 5. 视图对象（vo）
- BaseVO：基础视图对象

### 6. 支持功能（support）
- 验证器（validator）：通用验证规则
- 树形结构（tree）：树形数据处理

### 7. 租户功能（tenant）
- 租户配置
- 租户元数据

### 8. 地理位置（geo）
- 地理位置相关定义

## 目录结构
```
base-model/
├── entity/           # 实体定义
│   ├── ModelBaseEntity.java  # 基础实体
│   ├── TreeEntity.java      # 树形实体
│   └── TenantEntity.java    # 租户实体
├── dto/              # 传输对象
│   ├── BaseDTO.java         # 基础DTO
│   └── BaseExportDTO.java   # 导出DTO
├── query/            # 查询对象
│   └── BaseQuery.java       # 基础查询
├── result/           # 结果对象
│   └── BaseResult.java      # 基础结果
├── vo/               # 视图对象
│   └── BaseVO.java          # 基础视图
├── support/          # 支持功能
│   ├── validator/           # 验证器
│   └── tree/               # 树形结构
├── tenant/           # 租户定义
│   ├── TenantConfig.java    # 租户配置
│   └── TenantMetadata.java  # 租户元数据
├── geo/              # 地理位置
├── service/          # 服务接口
├── constants/        # 常量定义
└── enums/            # 枚举定义
```

## 使用说明

### 1. 依赖引入
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>base-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 实体定义
- 业务实体应继承ModelBaseEntity
- 树形数据应继承TreeEntity
- 多租户实体应继承TenantEntity

### 3. 数据传输
- 使用BaseDTO作为传输对象基类
- 使用BaseExportDTO作为导出对象基类
- 使用BaseQuery作为查询对象基类

### 4. 结果处理
- 使用BaseResult包装返回结果
- 使用BaseVO构建视图对象

### 5. 注意事项
- 遵循统一的命名规范
- 合理使用继承和组合
- 保持接口的稳定性
- 注意版本兼容性 