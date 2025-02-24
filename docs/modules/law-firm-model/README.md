# law-firm-model 数据模型模块

## 模块结构
```
law-firm-model/
├── entity      // 实体类
├── dto         // 数据传输对象
├── vo          // 视图对象
├── enums       // 枚举定义
└── constant    // 常量定义
```

## 功能说明

### 1. entity
实体类定义数据库表对应的Java对象。

主要功能：
- 基础实体类
- 业务实体类
- 关联实体类
- 实体映射配置
- JPA注解定义

详细文档：[entity说明](entity/README.md)

### 2. dto
数据传输对象用于不同层之间的数据传输。

主要功能：
- 请求DTO
- 响应DTO
- 分页DTO
- 查询条件DTO
- 统计DTO

详细文档：[dto说明](dto/README.md)

### 3. vo
视图对象用于前端展示的数据封装。

主要功能：
- 列表视图对象
- 详情视图对象
- 树形结构对象
- 统计视图对象
- 导出视图对象

详细文档：[vo说明](vo/README.md)

### 4. enums
枚举定义系统中使用的所有枚举类型。

主要功能：
- 状态枚举
- 类型枚举
- 错误码枚举
- 权限枚举
- 业务枚举

详细文档：[enums说明](enums/README.md)

### 5. constant
常量定义系统中使用的所有常量。

主要功能：
- 系统常量
- 业务常量
- 配置常量
- 缓存常量
- 安全常量

详细文档：[constant说明](constant/README.md)

## 技术架构
- Java 8+
- Lombok
- JPA
- Hibernate Validator
- Jackson

## 依赖关系
本模块为基础模块，不依赖其他业务模块

## 使用说明
1. 实体类命名规范：
   - 以Entity结尾
   - 使用@Entity注解
   - 必须有无参构造函数

2. DTO命名规范：
   - 以DTO结尾
   - 按功能分包
   - 使用Builder模式

3. VO命名规范：
   - 以VO结尾
   - 按功能分包
   - 实现序列化接口

4. 枚举命名规范：
   - 以Enum结尾
   - 实现接口或抽象类
   - 提供代码和描述

5. 常量命名规范：
   - 全大写
   - 下划线分隔
   - 按功能分类 