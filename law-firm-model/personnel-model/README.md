# personnel-model 人事模型模块

## 模块职责

本模块专注于律师事务所人事管理相关的基础功能，包括：

1. **人员管理**：律师、行政人员、员工基本信息管理
2. **职位管理**：律师职级、行政职位管理
3. **角色管理**：律所业务角色定义
4. **合同管理**：员工合同管理
5. **简历管理**：员工教育经历、工作经历管理

## 职责边界

本模块仅包含与人事管理直接相关的实体和服务：

- **包含**：人员信息、职位信息、业务角色、合同信息、简历信息等
- **不包含**：认证信息(应放在auth-model)、组织架构信息(应放在organization-model)

## 核心实体

- **Person**：人员基本信息（姓名、性别、证件号码等）
- **Employee**：员工信息，扩展Person，包含不同员工类型的所有属性
- **Contact**：联系信息
- **Contract**：合同信息
- **WorkExperience**：工作经历
- **EducationExperience**：教育经历
- **EmployeePositionHistory**：员工职位变更历史

## 实体设计说明

为了简化实体结构，我们采用了扁平化的设计：

1. **Employee实体**包含了所有类型员工的属性（律师、行政人员等）
2. 通过`employeeType`枚举字段区分不同类型的员工
3. 特定类型员工的属性仅在对应的类型下有效

这种设计的优势：
- 简化了实体继承层次
- 降低了代码复杂度
- 保持了与auth-model的兼容性
- 易于扩展支持新的员工类型

## 模块关联设计

- **EmployeeOrganizationRelation**：员工与组织的多对多关系
- **EmployeeAuthBridge**：员工与认证模块的桥接接口

## 业务角色设计

本模块为支持前端权限设计方案，提供以下枚举和接口：

1. **PersonRoleEnum**：律师事务所人员角色枚举，包含系统管理员、律所主任、合伙人、执业律师、实习律师、行政人员、财务人员等角色。

2. **PersonPermissionChecker**：人员权限检查接口，用于检查用户的角色和权限。

注意：
- 本模块定义的角色与auth-model无直接依赖关系
- 不使用auth-model的枚举类型，而是使用字符串表示关联关系
- 模块权限相关的枚举和定义不在personnel-model中实现，应该在专门的permission-model模块或各功能模块自身中定义

## 与其他模块的关系

### 1. 与auth-model的关系
- auth-model通过`User.employeeId`关联到本模块的Employee实体
- 本模块通过`Employee.userId`关联到auth-model的User实体
- 两者之间采用ID引用方式保持松耦合

### 2. 与organization-model的关系
- 本模块通过`Employee.departmentId`等字段关联organization-model的组织架构
- 组织架构相关信息不在本模块中定义

## 数据模型设计

### 1. Person 人员基本信息
- 包含基础的个人信息，如姓名、性别、证件信息等
- 作为Employee的父类

### 2. Employee 员工信息
- 扩展Person实体，包含所有员工通用信息
- 包含employeeType字段区分不同类型员工
- 包含律师特有属性（执业证号、执业年限等）
- 包含行政人员特有属性（职能类型、工作职责等）
- 与User实体通过userId字段关联

### 3. Contact 联系信息
- 包含通讯录相关信息
- 可以与Person关联

### 4. Contract 合同信息
- 员工合同信息
- 与Employee关联

## 目录结构

```
personnel-model/
├── src/main/java/com/lawfirm/model/personnel/
│   ├── entity/                # 实体类
│   │   ├── Person.java        # 人员基本信息
│   │   ├── Employee.java      # 员工信息（包含律师、行政人员属性）
│   │   ├── Contact.java       # 联系信息
│   │   ├── Contract.java      # 合同信息
│   │   ├── relation/          # 关联实体
│   │   ├── history/           # 历史记录实体
│   │   └── resume/            # 简历相关实体
│   ├── dto/                   # 数据传输对象
│   ├── vo/                    # 视图对象
│   ├── enums/                 # 枚举定义
│   │   ├── PersonTypeEnum.java       # 人员类型枚举
│   │   ├── EmployeeTypeEnum.java     # 员工类型枚举
│   │   ├── EmployeeStatusEnum.java   # 员工状态枚举
│   │   ├── LawyerLevelEnum.java      # 律师级别枚举
│   │   └── StaffFunctionEnum.java    # 行政职能枚举
│   ├── constant/              # 常量定义
│   ├── mapper/                # 映射接口
│   └── service/               # 服务接口
└── pom.xml                    # 项目依赖
```

## 使用示例

### 获取员工信息
```java
// 获取律师信息
Employee lawyer = employeeService.getById(lawyerId);
if (lawyer.getEmployeeType() == EmployeeTypeEnum.LAWYER) {
    String licenseNumber = lawyer.getLicenseNumber();
    LawyerLevelEnum level = lawyer.getLawyerLevel();
    // 处理律师特有属性
}

// 获取行政人员信息
Employee staff = employeeService.getById(staffId);
if (staff.getEmployeeType() == EmployeeTypeEnum.STAFF) {
    StaffFunctionEnum function = staff.getFunctionType();
    String jobDuties = staff.getJobDuties();
    // 处理行政人员特有属性
}
```

### 创建新员工
```java
Employee employee = new Employee();
// 设置基本信息
employee.setName("张三");
employee.setGender(1);
employee.setMobile("13800138000");

// 设置员工通用信息
employee.setWorkNumber("EMP20230001");
employee.setDepartmentId(1L);
employee.setEntryDate(LocalDate.now());

// 设置为律师
employee.setEmployeeType(EmployeeTypeEnum.LAWYER);
employee.setLicenseNumber("L20230001");
employee.setLawyerLevel(LawyerLevelEnum.JUNIOR);
employee.setPracticeYears(3);

// 保存
employeeService.save(employee);
```

## 注意事项

1. 业务角色(PersonRoleEnum)与系统角色(auth-model中的RoleEnum)在业务层建立关联
2. 系统角色负责基础的认证和授权
3. 业务角色负责特定业务场景的权限控制
4. PersonPermissionChecker的实现类需要在具体的应用服务中提供

## 依赖关系
- 依赖 organization-model：复用组织架构中的部门、职位等信息
- 依赖 base-model：复用基础实体类和工具类

## 迁移记录

### 常量统一重构 (2025-03-09)
- 废弃 PersonnelConstant、LawyerConstant、EmployeeConstant 三个常量类
- 创建统一的 PersonnelConstants 接口作为唯一常量定义中心
- 按功能域和实体分组组织常量
- 更新所有实体类和枚举类，统一使用新的常量接口
- 添加权限相关常量，包括操作权限类型、数据权限范围和模块编码
- 优化枚举类设计，增强与常量定义的一致性
- 完善权限和跨模块交互设计

### JPA到MyBatis Plus迁移 (2024-04-28)
- 移除JPA相关注解和导入
- 保留并使用MyBatis Plus注解(@TableName, @TableField等)
- 移除pom.xml中的Spring Data JPA依赖
- 保留Jakarta Validation API进行数据验证
- 迁移完成的实体类:
  - Person.java
  - Employee.java
  - Lawyer.java
  - Staff.java
  - Contact.java
  - Contract.java