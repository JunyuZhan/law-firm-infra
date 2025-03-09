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

- **Person**：人员基本信息
- **Employee**：员工信息，扩展Person
- **Lawyer**：律师信息，扩展Employee
- **Staff**：行政人员信息，扩展Employee
- **Contact**：联系信息
- **Contract**：合同信息
- **WorkExperience**：工作经历
- **EducationExperience**：教育经历
- **EmployeePositionHistory**：员工职位变更历史

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

- **organization-model**：通过EmployeeOrganizationService接口关联组织架构
- **auth-model**：通过EmployeeAuthBridge接口关联认证系统
- **base-model**：继承其基础实体和枚举

## 设计原则

1. 保持高内聚低耦合
2. 专注于人事管理核心功能
3. 保持模块独立性，通过桥接接口与其他模块交互
4. 使用统一的常量类管理所有常量定义

## 使用示例

### 角色权限检查
```java
// 获取用户的人员角色
PersonRoleEnum role = personPermissionChecker.getPersonRole(userId);

// 检查用户是否有案件管理模块的权限
boolean canAccessCase = personPermissionChecker.hasModulePermission(
    userId, PersonnelConstants.ModuleCode.CASE, PersonnelConstants.OperationType.FULL);

// 获取用户对文档模块的数据范围
String dataScope = personPermissionChecker.getModuleDataScope(
    userId, PersonnelConstants.ModuleCode.DOCUMENT);

// 检查用户是否是律所主任
boolean isDirector = personPermissionChecker.hasPersonRole(
    userId, PersonnelConstants.RoleType.FIRM_DIRECTOR);
```

### 员工与组织关系管理
```java
// 获取员工所属的主要组织
Long primaryOrgId = employeeOrganizationService.getPrimaryOrganizationId(employeeId);

// 将员工分配到组织
boolean success = employeeOrganizationService.assignEmployeeToOrganization(
    employeeId, organizationId, positionId, true, LocalDate.now(), null);

// 更新员工职位
boolean updated = employeeOrganizationService.updateEmployeePosition(
    employeeId, organizationId, newPositionId, "业务调整");
```

### 员工与认证系统交互
```java
// 创建用户账户
Long userId = employeeAuthBridge.createUserFromEmployee(employeeId, "初始密码");

// 检查员工权限
boolean hasPermission = employeeAuthBridge.hasPermission(
    employeeId, "document:upload");

// 获取员工的所有角色
List<String> roles = employeeAuthBridge.getEmployeeRoleCodes(employeeId);
```

## 注意事项

1. 业务角色(PersonRoleEnum)与系统角色(auth-model中的RoleEnum)在业务层建立关联
2. 系统角色负责基础的认证和授权
3. 业务角色负责特定业务场景的权限控制
4. PersonPermissionChecker的实现类需要在具体的应用服务中提供

## 目录结构
```
personnel/
├── constant/               # 常量定义
│   └── PersonnelConstants.java  # 统一常量定义接口
│
├── entity/                 # 实体类
│   ├── Person.java         # 人员基础信息
│   ├── Employee.java       # 员工信息
│   ├── Lawyer.java         # 律师信息
│   ├── Staff.java          # 行政人员信息
│   ├── Contact.java        # 联系方式
│   ├── Contract.java       # 合同信息
│   │
│   ├── history/            # 历史记录相关实体
│   │   └── EmployeePositionHistory.java # 员工职位变更历史
│   │
│   ├── relation/           # 关系实体
│   │   └── EmployeeOrganizationRelation.java # 员工与组织关系
│   │
│   └── resume/             # 简历相关实体
│       ├── EducationExperience.java  # 教育经历
│       └── WorkExperience.java       # 工作经历
│
├── service/               # 服务接口
│   ├── PersonService.java       # 人员服务接口
│   ├── EmployeeService.java     # 员工服务接口
│   ├── LawyerService.java       # 律师服务接口
│   ├── StaffService.java        # 行政人员服务接口
│   ├── EmployeeAuthBridge.java  # 员工认证桥接接口
│   ├── EmployeeOrganizationService.java # 员工组织关系服务
│   └── PersonPermissionChecker.java     # 人员权限检查接口
│
├── dto/                   # 数据传输对象
│   ├── person/            # 人员相关DTO
│   ├── employee/          # 员工相关DTO
│   ├── lawyer/            # 律师相关DTO
│   └── staff/             # 行政人员相关DTO
│
├── vo/                    # 视图对象
│   ├── PersonVO.java      # 人员视图对象
│   ├── EmployeeVO.java    # 员工视图对象
│   ├── LawyerVO.java      # 律师视图对象
│   └── StaffVO.java       # 行政人员视图对象
│
└── enums/                 # 枚举类型
    ├── PersonTypeEnum.java     # 人员类型
    ├── PersonRoleEnum.java     # 人员角色
    ├── EmployeeStatusEnum.java # 员工状态
    ├── EmployeeTypeEnum.java   # 员工类型
    ├── LawyerLevelEnum.java    # 律师职级
    ├── ContractTypeEnum.java   # 合同类型
    └── StaffFunctionEnum.java  # 行政人员职能
```

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