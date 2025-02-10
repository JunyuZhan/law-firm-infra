# 数据模型模块 (Model)

## 模块说明
数据模型模块是律师事务所管理系统的数据结构定义层，负责定义系统中所有业务实体、数据传输对象(DTO)和枚举类型。该模块是整个系统的基础数据模型，为其他模块提供数据结构支持。

## 子模块说明

### base-model
基础模型模块，提供基础的实体类和通用功能：
- 基础实体类
- 通用枚举
- 基础接口
- 数据校验
- 公共注解

### contract-model
合同模型模块，定义合同相关的数据结构：
- 合同实体
- 合同DTO
- 合同枚举
- 合同常量
- 合同校验

### case-model
案件模型模块，定义案件相关的数据结构：
- 案件实体
- 案件DTO
- 案件枚举
- 案件常量
- 案件校验

### client-model
客户模型模块，定义客户相关的数据结构：
- 客户实体
- 客户DTO
- 客户枚举
- 客户常量
- 客户校验

### finance-model
财务模型模块，定义财务相关的数据结构：
- 财务实体
- 财务DTO
- 财务枚举
- 财务常量
- 财务校验

### personnel-model
人事模型模块，定义人事相关的数据结构：
- 人员实体
- 人员DTO
- 人员枚举
- 人员常量
- 人员校验

### system-model
系统模型模块，定义系统相关的数据结构：
- 系统实体
- 系统DTO
- 系统枚举
- 系统常量
- 系统校验

## 技术架构

### 核心依赖
- base-model：基础模型依赖
- common-core：核心功能依赖
- common-data：数据访问依赖
- MyBatis Plus：ORM框架
- Jakarta Validation：数据校验
- Jackson：JSON处理
- Lombok：代码简化

### 设计规范
1. 实体类规范
   - 继承自ModelBaseEntity
   - 使用JPA注解
   - 添加字段校验
   - 完整的注释
   - 合理的命名

2. DTO规范
   - 请求/响应分离
   - 字段校验注解
   - 序列化配置
   - 转换方法
   - 文档注释

3. 枚举规范
   - 实现基础接口
   - 提供转换方法
   - 统一的命名
   - 完整的注释
   - JSON序列化

## 使用说明

### 引入依赖
```xml
<!-- 基础模型 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>base-model</artifactId>
    <version>${project.version}</version>
</dependency>

<!-- 业务模型 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>contract-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 代码示例

1. 实体类定义
```java
@Data
@Entity
@Table(name = "table_name")
public class BusinessEntity extends ModelBaseEntity<BusinessEntity> {
    @NotNull
    private String field;
}
```

2. DTO定义
```java
@Data
public class BusinessDTO {
    @NotBlank
    private String field;
}
```

3. 枚举定义
```java
@Getter
public enum BusinessEnum {
    ITEM("code", "描述");
    
    private final String code;
    private final String description;
}
```

## 开发流程

### 新增模型
1. 创建Maven模块
2. 配置依赖关系
3. 定义数据结构
4. 添加单元测试
5. 更新文档

### 修改模型
1. 评估影响范围
2. 保持兼容性
3. 更新相关代码
4. 补充单元测试
5. 更新文档

## 测试规范

### 单元测试
1. 实体测试
   - 属性测试
   - 校验测试
   - 继承测试
   
2. DTO测试
   - 校验测试
   - 转换测试
   - 序列化测试

3. 枚举测试
   - 转换测试
   - 序列化测试
   - 方法测试

## 版本说明

### 当前版本
- 版本号：1.0.0
- 发布日期：2024-02-09
- 主要特性：
  - 基础模型完善
  - 业务模型实现
  - 数据校验加强

### 版本规划
- 1.1.0：新增业务模型
- 1.2.0：优化数据结构
- 2.0.0：重构升级

## 常见问题

### 问题类型
1. 继承关系问题
2. 校验规则问题
3. 序列化问题
4. 性能问题
5. 兼容性问题

### 解决方案
1. 检查继承链
2. 调整校验规则
3. 配置序列化
4. 优化数据结构
5. 保持兼容性

## 注意事项
1. 遵循命名规范
2. 保持向后兼容
3. 添加完整注释
4. 注意代码质量
5. 及时更新文档 