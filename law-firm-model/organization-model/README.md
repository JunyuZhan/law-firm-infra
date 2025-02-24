# 组织架构模型模块 (Organization Model)

## 模块说明
组织架构模型模块是律师事务所管理系统的组织架构数据结构定义模块，负责定义系统中所有与组织架构相关的实体类、数据传输对象、视图对象和枚举类型。该模块是组织管理功能的数据基础。

## 核心功能

### 1. 律师事务所
- 律所实体（LawFirm）
  - 律所名称
  - 统一社会信用代码
  - 执业许可证号
  - 法定代表人
  - 联系方式
  - 地址信息

### 2. 部门管理
- 部门实体（Department）
  - 部门名称
  - 部门编码
  - 部门负责人
  - 联系电话
  - 上级部门
  - 部门类型

### 3. 组织属性
- 部门类型
  - 业务部门
  - 职能部门
  - 管理部门
  - 特殊部门

- 组织状态
  - 正常
  - 禁用
  - 注销
  - 筹建

### 4. 组织关联
- 人员关联
  - 律师关联
  - 员工关联
  - 管理人员

- 业务关联
  - 案件关联
  - 合同关联
  - 文档关联

## 核心类说明

### 1. 实体类
- LawFirm：律所实体
  - 基本信息
  - 营业信息
  - 联系方式
  - 资质信息
  - 状态管理

- Department：部门实体
  - 部门信息
  - 层级关系
  - 负责人信息
  - 联系方式
  - 状态管理

### 2. 数据传输对象
- LawFirmDTO：律所数据传输对象
  - 创建请求
  - 更新请求
  - 查询条件
  - 详情展示

- DepartmentDTO：部门数据传输对象
  - 创建请求
  - 更新请求
  - 查询条件
  - 详情展示

### 3. 视图对象
- LawFirmVO：律所视图对象
  - 列表展示
  - 详情展示
  - 统计信息
  - 关联信息

- DepartmentVO：部门视图对象
  - 列表展示
  - 详情展示
  - 层级信息
  - 人员信息

## 使用示例

### 1. 创建律所
```java
LawFirm lawFirm = new LawFirm()
    .setName("某某律师事务所")
    .setSocialCreditCode("91110000XXX")
    .setLicenseNumber("11010000XXX")
    .setLegalRepresentative("张三")
    .setPhone("010-88888888")
    .setEmail("contact@lawfirm.com")
    .setAddress("北京市朝阳区");
```

### 2. 创建部门
```java
Department department = new Department()
    .setName("诉讼部")
    .setCode("DEP_SUS")
    .setManager("李四")
    .setPhone("010-88888001")
    .setType("业务部门")
    .setEnabled(true)
    .setLawFirmId(1001L);
```

## 注意事项
1. 组织编码必须唯一
2. 注意层级关系维护
3. 及时更新组织状态
4. 重要变更需要记录
5. 关注数据完整性 