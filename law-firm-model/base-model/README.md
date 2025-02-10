# 基础模型模块 (Base Model)

## 模块说明
基础模型模块是律师事务所管理系统的基础数据模型层，为所有业务模型提供基础实体类、通用接口和基础功能支持。该模块是所有业务模型模块的基础，确保了整个系统数据模型的一致性和规范性。

## 核心功能

### 基础实体
- ModelBaseEntity：模型基础实体，提供通用字段和功能
- TreeEntity：树形结构实体，支持层级数据
- BaseEntity：最基础实体类，提供ID等基本字段

### 通用接口
- StatusAware：状态感知接口
- TenantAware：租户感知接口
- TreeAware：树形结构接口
- AuditAware：审计感知接口
- VersionAware：版本感知接口

### 基础功能
- 实体继承体系
- 通用字段定义
- 数据校验规则
- 序列化配置
- 数据转换

### 数据结构
- DTO基类
- VO基类
- Query基类
- Result基类
- Page封装

## 技术架构

### 核心依赖
- common-core：核心功能支持
- common-data：数据访问支持
- MyBatis Plus：ORM支持
- Jakarta Validation：数据校验
- Jackson：JSON处理
- Lombok：代码简化

### 模块结构
```
base-model/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/lawfirm/model/base/
│   │   │       ├── entity/     # 基础实体
│   │   │       ├── dto/        # 基础DTO
│   │   │       ├── vo/         # 基础VO
│   │   │       ├── query/      # 查询对象
│   │   │       ├── result/     # 结果封装
│   │   │       ├── enums/      # 基础枚举
│   │   │       ├── convert/    # 转换器
│   │   │       └── constants/  # 常量定义
│   │   └── resources/
│   └── test/                   # 单元测试
└── pom.xml
```

## 核心组件

### ModelBaseEntity
基础实体类，提供通用字段和功能：
```java
@Data
@Accessors(chain = true)
public abstract class ModelBaseEntity<T extends ModelBaseEntity<T>> 
    extends DataBaseEntity<T> 
    implements TenantAware, StatusAware {
    
    @Version
    private Integer version;     // 版本号
    private Long tenantId;       // 租户ID
    private Integer status;      // 状态
    private Integer sort;        // 排序号
}
```

### BaseDTO
基础传输对象：
```java
@Data
public abstract class BaseDTO implements Serializable {
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
```

### BaseQuery
基础查询对象：
```java
@Data
public abstract class BaseQuery implements Serializable {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String orderBy;
    private Boolean asc = true;
}
```

## 使用说明

### 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>base-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 实体定义
```java
@Data
@Entity
@Table(name = "table_name")
public class BusinessEntity extends ModelBaseEntity<BusinessEntity> {
    // 业务字段
}
```

### DTO定义
```java
@Data
public class BusinessDTO extends BaseDTO {
    // 业务字段
}
```

### 查询定义
```java
@Data
public class BusinessQuery extends BaseQuery {
    // 查询条件
}
```

## 开发规范

### 实体规范
1. 必须继承ModelBaseEntity
2. 必须使用JPA注解
3. 必须添加字段校验
4. 必须添加完整注释
5. 必须实现相关接口

### 命名规范
1. 实体类：Entity结尾
2. DTO类：DTO结尾
3. 查询类：Query结尾
4. 枚举类：Enum结尾
5. 常量类：Constants结尾

### 注释规范
1. 类注释完整
2. 字段注释清晰
3. 方法注释详细
4. 枚举值注释
5. 接口说明完整

## 扩展开发

### 新增基类
1. 继承合适的父类
2. 添加通用字段
3. 实现必要接口
4. 添加单元测试
5. 更新文档说明

### 新增接口
1. 定义接口规范
2. 添加默认实现
3. 编写使用示例
4. 补充单元测试
5. 更新接口文档

## 版本说明

### 当前版本
- 版本号：1.0.0
- 发布日期：2024-02-09
- 主要特性：
  - 基础实体完善
  - 通用接口定义
  - 数据校验加强

### 版本规划
- 1.1.0：新增基础特性
- 1.2.0：优化现有功能
- 2.0.0：架构重构

## 常见问题

### 继承问题
1. 继承层次过深
2. 字段重复定义
3. 接口冲突
4. 注解继承
5. 泛型使用

### 解决方案
1. 控制继承层次
2. 使用组合模式
3. 接口默认实现
4. 注解继承配置
5. 泛型边界限定

## 注意事项
1. 避免继承滥用
2. 接口职责单一
3. 保持向后兼容
4. 注意性能影响
5. 维护文档及时 