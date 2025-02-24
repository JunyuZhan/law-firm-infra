# 人事模型模块 (Personnel Model)

## 模块说明
人事模型模块是律师事务所管理系统的人事数据结构定义模块，负责定义系统中所有与人员相关的实体类、数据传输对象、视图对象和枚举类型。该模块是人事管理功能的数据基础。

## 核心功能

### 1. 律师管理
- 律师实体（Lawyer）
  - 基本信息
  - 执业信息
    - 执业证号
    - 执业证取得日期
    - 执业机构
    - 执业状态
  - 专业信息
    - 专业领域
    - 执业经历
    - 资质认证
  - 职务信息
    - 律师职称
    - 合伙人信息
    - 部门信息

### 2. 员工管理
- 员工实体（Employee）
  - 基本信息
  - 职位信息
  - 部门信息
  - 工作状态
  - 入职信息
  - 离职信息

### 3. 人员属性
- 律师职称（LawyerTitleEnum）
  - 初级律师
  - 中级律师
  - 高级律师
  - 资深律师

- 人员状态
  - 在职
  - 离职
  - 休假
  - 停职

### 4. 人员关联
- 组织关联
  - 律所关联
  - 部门关联
  - 团队关联

- 业务关联
  - 案件关联
  - 合同关联
  - 客户关联

## 核心类说明

### 1. 实体类
- Lawyer：律师实体
  - 继承自Employee
  - 律师特有属性
  - 执业信息
  - 专业信息
  - 合伙人信息

- Employee：员工实体
  - 基础人员信息
  - 工作信息
  - 状态管理
  - 审计信息

### 2. 数据传输对象
- LawyerDTO：律师数据传输对象
  - 创建请求
  - 更新请求
  - 查询条件
  - 详情展示

- EmployeeDTO：员工数据传输对象
  - 创建请求
  - 更新请求
  - 查询条件
  - 详情展示

### 3. 视图对象
- LawyerVO：律师视图对象
  - 列表展示
  - 详情展示
  - 统计信息
  - 关联信息

- EmployeeVO：员工视图对象
  - 列表展示
  - 详情展示
  - 部门信息
  - 状态信息

## 使用示例

### 1. 创建律师
```java
Lawyer lawyer = new Lawyer()
    .setTitle(LawyerTitleEnum.SENIOR)
    .setLicenseNumber("13101200001")
    .setLicenseDate(LocalDate.of(2020, 1, 1))
    .setPracticingInstitution("某某律师事务所")
    .setSpecialties("公司法,合同法")
    .setIsPartner(true)
    .setPracticeStatus("正常");
```

### 2. 创建员工
```java
Employee employee = new Employee()
    .setName("张三")
    .setDepartmentId(1001L)
    .setPosition("行政助理")
    .setStatus("在职")
    .setEntryDate(LocalDate.now());
```

## 注意事项
1. 执业证号必须唯一
2. 注意人员状态管理
3. 及时更新执业信息
4. 重要变更需要记录
5. 关注合规要求 