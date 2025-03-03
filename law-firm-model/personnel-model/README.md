# 人事模型模块 (Personnel Model)

## 模块说明
人事模型模块定义了律师事务所管理系统中的人员相关数据结构，包括：
- 人员基础信息（个人信息、证件信息等）
- 员工信息（工号、部门、职位等）
- 律师信息（执业证书、专业领域等）
- 行政人员信息（岗位职责等）

## 目录结构
```
personnel/
├── constant/               # 常量定义
│   ├── PersonConstant.java    # 人员相关常量
│   ├── EmployeeConstant.java  # 员工相关常量
│   └── LawyerConstant.java    # 律师相关常量
│
├── entity/                 # 实体类
│   ├── Person.java         # 人员基础信息
│   ├── Employee.java       # 员工信息
│   ├── Lawyer.java         # 律师信息
│   ├── Staff.java          # 行政人员信息
│   ├── Contact.java        # 联系方式
│   └── Contract.java       # 合同信息
│
├── service/               # 服务接口
│   ├── PersonService.java       # 人员服务接口
│   ├── EmployeeService.java     # 员工服务接口
│   ├── LawyerService.java       # 律师服务接口
│   └── StaffService.java        # 行政人员服务接口
│
├── dto/                   # 数据传输对象
│   ├── person/            # 人员相关DTO
│   │   ├── PersonDTO.java
│   │   ├── CreatePersonDTO.java
│   │   └── UpdatePersonDTO.java
│   │
│   ├── employee/          # 员工相关DTO
│   │   ├── EmployeeDTO.java
│   │   ├── CreateEmployeeDTO.java
│   │   └── UpdateEmployeeDTO.java
│   │
│   ├── lawyer/            # 律师相关DTO
│   │   ├── LawyerDTO.java
│   │   ├── CreateLawyerDTO.java
│   │   └── UpdateLawyerDTO.java
│   │
│   └── staff/             # 行政人员相关DTO
│       ├── StaffDTO.java
│       ├── CreateStaffDTO.java
│       └── UpdateStaffDTO.java
│
├── vo/                    # 视图对象
│   ├── PersonVO.java      # 人员视图对象
│   ├── EmployeeVO.java    # 员工视图对象
│   ├── LawyerVO.java      # 律师视图对象
│   └── StaffVO.java       # 行政人员视图对象
│
└── enums/                 # 枚举类型
    ├── PersonTypeEnum.java     # 人员类型
    ├── EmployeeStatusEnum.java # 员工状态
    ├── LawyerLevelEnum.java    # 律师职级
    └── ContractTypeEnum.java   # 合同类型
```

## 依赖关系
- 依赖 organization-model：复用组织架构中的部门、职位等信息
- 依赖 base-model：复用基础实体类和工具类

## 迁移记录
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