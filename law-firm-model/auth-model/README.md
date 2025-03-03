## 目录结构

```
auth-model/
├── entity/           # 实体定义
│   ├── user/         # 用户相关
│   │   ├── User.java         # 用户实体
│   │   └── UserGroup.java    # 用户组实体
│   ├── role/         # 角色相关
│   │   ├── Role.java         # 角色实体
│   │   └── Permission.java   # 权限实体
│   ├── relation/     # 关联关系
│   │   ├── UserRole.java     # 用户角色关联
│   │   └── RolePermission.java # 角色权限关联
│   └── org/          # 组织相关
│       ├── Department.java    # 部门实体
│       └── Position.java      # 职位实体
├── dto/              # 传输对象
│   ├── auth/         # 认证相关
│   │   ├── LoginDTO.java     # 登录请求
│   │   └── TokenDTO.java     # 令牌信息
│   ├── user/         # 用户相关
│   │   ├── UserCreateDTO.java
│   │   ├── UserUpdateDTO.java
│   │   └── UserQueryDTO.java
│   └── role/         # 角色相关
│       ├── RoleCreateDTO.java
│       └── RoleUpdateDTO.java
├── vo/               # 视图对象
│   ├── UserVO.java
│   ├── RoleVO.java
│   └── PermissionVO.java
├── enums/            # 枚举定义
│   ├── UserTypeEnum.java
│   ├── UserStatusEnum.java
│   └── PermissionTypeEnum.java
└── service/          # 服务接口
    ├── UserService.java
    ├── RoleService.java
    └── PermissionService.java
```

## 认证授权模型模块 (Auth Model)

## 模块说明
# 认证授权模型模块 (Auth Model)

## 模块说明
认证授权模型模块是一个纯定义模块，负责定义系统中所有与认证授权相关的实体、接口、服务定义、枚举等。本模块不包含具体实现，所有实现都将在对应的服务模块中完成。

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
- 支持自定义认证方式
- 支持自定义授权策略

## 核心定义

### 1. 实体定义（entity）
- User：用户实体定义
- Role：角色实体定义
- Permission：权限实体定义
- UserRole：用户角色关联定义
- RolePermission：角色权限关联定义
- Department：部门实体定义
- Position：职位实体定义
- UserGroup：用户组实体定义
- LoginHistory：登录历史定义

### 2. 数据传输对象（dto）
- auth：认证相关DTO
  - LoginDTO：登录请求对象
  - TokenDTO：令牌信息对象
- user：用户相关DTO
  - UserCreateDTO：用户创建对象
  - UserUpdateDTO：用户更新对象
  - UserQueryDTO：用户查询对象
- role：角色相关DTO
  - RoleCreateDTO：角色创建对象
  - RoleUpdateDTO：角色更新对象
  - RoleQueryDTO：角色查询对象

### 3. 视图对象（vo）
- UserVO：用户视图对象
- RoleVO：角色视图对象
- PermissionVO：权限视图对象
- DepartmentVO：部门视图对象
- PositionVO：职位视图对象

### 4. 枚举定义（enums）
- UserTypeEnum：用户类型枚举
- UserStatusEnum：用户状态枚举
- PermissionTypeEnum：权限类型枚举
- DepartmentTypeEnum：部门类型枚举
- PositionLevelEnum：职位级别枚举

### 5. 服务接口（service）
- UserService：用户服务接口
- RoleService：角色服务接口
- PermissionService：权限服务接口
- DepartmentService：部门服务接口
- PositionService：职位服务接口

## 复用的人员相关枚举和常量

本模块复用了以下人员相关的枚举和常量：

### 1. EmployeeStatusEnum
- 定义了员工的状态，包括试用期和正式状态。

### 2. PersonTypeEnum
- 定义了人员类型，包括律师和行政人员。

### 3. PersonnelConstant
- 包含与人员相关的常量，如状态、人员类型等。

### 4. CenterTypeEnum
- 定义了中心类型，包括人力资源中心、财务中心、行政中心、IT中心和其他中心。

## 目录结构
```
auth-model/
├── entity/           # 实体定义
│   ├── user/         # 用户相关
│   │   ├── User.java         # 用户实体
│   │   └── UserGroup.java    # 用户组实体
│   ├── role/         # 角色相关
│   │   ├── Role.java         # 角色实体
│   │   └── Permission.java   # 权限实体
│   ├── relation/     # 关联关系
│   │   ├── UserRole.java     # 用户角色关联
│   │   └── RolePermission.java # 角色权限关联
│   └── org/          # 组织相关
│       ├── Department.java    # 部门实体
│       └── Position.java      # 职位实体
├── dto/              # 传输对象
│   ├── auth/         # 认证相关
│   │   ├── LoginDTO.java     # 登录请求
│   │   └── TokenDTO.java     # 令牌信息
│   ├── user/         # 用户相关
│   │   ├── UserCreateDTO.java
│   │   ├── UserUpdateDTO.java
│   │   └── UserQueryDTO.java
│   └── role/         # 角色相关
│       ├── RoleCreateDTO.java
│       └── RoleUpdateDTO.java
├── vo/               # 视图对象
│   ├── UserVO.java
│   ├── RoleVO.java
│   └── PermissionVO.java
├── enums/            # 枚举定义
│   ├── UserTypeEnum.java
│   ├── UserStatusEnum.java
│   └── PermissionTypeEnum.java
└── service/          # 服务接口
    ├── UserService.java
    ├── RoleService.java
    └── PermissionService.java
```

## 使用说明

### 1. 依赖引入
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>auth-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 实体使用
- 所有实体都继承自ModelBaseEntity
- 用户实体实现TenantEntity接口
- 组织实体使用TreeEntity特性

### 3. 服务接口
- 定义了标准的CRUD操作
- 支持自定义业务接口
- 预留扩展接口

### 4. 注意事项
- 本模块仅包含定义，不包含实现
- 具体实现在对应的服务模块中完成
- 遵循接口定义规范
- 注意依赖管理 