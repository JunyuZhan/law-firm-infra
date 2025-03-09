# auth-model 认证授权模型模块

## 模块职责

本模块专注于系统认证和授权相关的基础功能，包括：

1. **用户认证**：用户名密码认证、短信验证码认证、邮箱认证、LDAP认证等
2. **权限控制**：基于角色的访问控制(RBAC)、资源权限管理
3. **安全管理**：密码策略、登录失败处理、会话管理等

## 职责边界

本模块仅包含与认证授权直接相关的实体和服务：

- **包含**：用户认证信息、角色、权限、安全策略等
- **不包含**：组织架构信息(应放在organization-model)、人员信息(应放在personnel-model)

## 核心实体

- **User**：仅包含认证所需的基本用户信息，如用户名、密码、状态等
- **Role**：系统角色定义，不包含业务角色
- **Permission**：系统权限定义
- **UserRole**：用户-角色关联
- **RolePermission**：角色-权限关联

## 与其他模块的关系

- **organization-model**：通过ID引用关联，不直接包含组织架构信息
- **personnel-model**：通过ID引用关联，不直接包含人员信息

## 设计原则

1. 保持高内聚低耦合
2. 仅关注认证授权核心功能
3. 通过ID引用而非直接包含方式与其他模块交互

## 与前端权限系统的对接

本模块为支持前端权限系统，提供以下功能：

1. **操作类型**：定义了FULL(完全权限)、READ_ONLY(只读权限)、PERSONAL(个人数据权限)、APPROVE(审批权限)和APPLY(申请权限)
2. **数据范围**：定义了ALL(全部数据)、TEAM(团队数据)、PERSONAL(个人数据)和CUSTOM(自定义数据)
3. **权限检查**：提供PermissionChecker接口，支持对用户权限和数据范围的检查

注意：具体的业务角色（如律所主任、合伙人等）和业务模块权限矩阵应在personnel-model和相应的业务模块中实现。

## 目录结构

```
auth-model/
├── annotation/            # 注解定义
│   └── RequiresPermission.java # 权限注解
├── config/                # 配置类
│   └── AuthProperties.java # 认证配置属性类
├── constant/              # 常量定义
│   └── AuthConstants.java  # 统一认证常量类
├── dto/                   # 传输对象
│   ├── auth/              # 认证相关
│   │   ├── CaptchaDTO.java  # 验证码对象
│   │   ├── LoginDTO.java    # 登录请求
│   │   ├── PasswordResetDTO.java # 密码重置
│   │   └── TokenDTO.java    # 令牌信息
│   ├── permission/        # 权限相关
│   │   ├── PermissionCreateDTO.java # 权限创建
│   │   └── PermissionUpdateDTO.java # 权限更新
│   ├── role/              # 角色相关
│   │   ├── RoleCreateDTO.java # 角色创建
│   │   └── RoleUpdateDTO.java # 角色更新
│   └── user/              # 用户相关
│       ├── BaseUserDTO.java  # 用户基础DTO
│       ├── UserCreateDTO.java # 用户创建
│       ├── UserQueryDTO.java  # 用户查询
│       └── UserUpdateDTO.java # 用户更新
├── entity/                # 实体定义
│   ├── LoginHistory.java  # 登录历史
│   ├── Permission.java    # 权限实体
│   ├── Role.java          # 角色实体
│   ├── RolePermission.java # 角色权限关联
│   ├── User.java          # 用户实体
│   └── UserRole.java      # 用户角色关联
├── enums/                 # 枚举定义
│   ├── AuthSourceEnum.java # 认证来源枚举
│   ├── AuthTypeEnum.java   # 认证类型枚举
│   ├── DataScopeEnum.java  # 数据范围枚举
│   ├── LoginTypeEnum.java  # 登录类型枚举
│   ├── ModuleTypeEnum.java # 模块类型枚举
│   ├── OperationTypeEnum.java # 操作类型枚举
│   ├── PermissionTypeEnum.java # 权限类型枚举
│   ├── RoleEnum.java       # 角色枚举
│   ├── RoleModulePermissionEnum.java # 角色模块权限关系枚举
│   └── UserStatusEnum.java # 用户状态枚举
├── mapper/                # 数据访问接口
│   ├── LoginHistoryMapper.java # 登录历史记录
│   ├── PermissionMapper.java # 权限数据访问
│   ├── RoleMapper.java    # 角色数据访问
│   ├── RolePermissionMapper.java # 角色权限关联
│   ├── UserMapper.java    # 用户数据访问
│   └── UserRoleMapper.java # 用户角色关联
├── service/               # 服务接口
│   ├── AuthService.java   # 认证服务接口
│   ├── PermissionChecker.java # 权限检查接口
│   ├── PermissionService.java # 权限服务
│   ├── RolePermissionService.java # 角色权限服务
│   ├── RoleService.java   # 角色服务
│   ├── UserPersonnelService.java # 用户与人员关联服务
│   ├── UserRoleService.java # 用户角色服务
│   └── UserService.java   # 用户服务
└── vo/                    # 视图对象
    ├── LoginVO.java       # 登录视图
    ├── MetaVO.java        # 元数据视图
    ├── PermissionVO.java  # 权限视图
    ├── RoleVO.java        # 角色视图
    ├── RouterVO.java      # 路由视图
    ├── UserInfoVO.java    # 用户信息视图
    └── UserVO.java        # 用户视图
```

## 常量统一管理

为了提高代码的可维护性和一致性，我们实现了统一的 `AuthConstants` 类，合并了原有的多个常量类的内容。

`AuthConstants` 类包含以下几个主要部分：

1. **Table**：数据表常量，定义各实体对应的表名
2. **Field**：字段常量，定义模型中重要的字段名
3. **UserStatus**：用户状态常量，定义用户的各种状态（启用、禁用、锁定等）
4. **Security**：安全相关常量，包括JWT配置、密码策略等
5. **CacheKey**：缓存键常量，用于统一缓存键的定义和管理
6. **ErrorCode**：错误码常量，用于异常和错误处理
7. **Api**：API路径常量，定义REST API的路径规范
8. **Permission**：权限常量，定义系统权限值

这种嵌套接口的设计方式，可以将相关的常量组织在一起，提高代码的可读性和维护性。

## 用户与员工关联

为了支持用户与员工的关联，我们在 User 实体中添加了 employeeId 字段，并创建了 UserPersonnelService 接口来管理这种关联关系。这种设计具有以下优势：

1. **职责清晰**：认证模块仅关注认证和授权，不直接包含人员信息
2. **松耦合**：通过ID引用而非直接包含方式实现模块间关联
3. **双向同步**：提供方法支持用户信息和员工信息的双向同步

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
- 所有实体都继承自TenantEntity
- User, Role, Permission等主要实体提供了扩展方法和兼容性方法

### 3. 认证方式
本模块支持以下认证方式：
- 用户名密码认证
- 短信验证码认证
- 邮箱验证码认证
- LDAP认证
- 其他OAuth2认证方式

### 4. 权限检查
使用PermissionChecker接口进行权限检查：
```java
// 引用常量
import static com.lawfirm.model.auth.constant.AuthConstants.Permission;

// 检查用户对模块的操作权限
boolean canAccess = permissionChecker.hasPermission(userId, "document", OperationTypeEnum.READ_ONLY);

// 获取用户对模块的数据范围
DataScopeEnum dataScope = permissionChecker.getDataScope(userId, "document");

// 检查用户是否拥有特定角色
boolean isAdmin = permissionChecker.hasRole(userId, "admin");

// 检查用户是否拥有特定权限（使用常量）
boolean canManageUsers = permissionChecker.hasPermission(userId, Permission.USER_VIEW);
```

### 5. 注解使用
可以使用RequiresPermission注解进行权限控制：
```java
// 引用常量
import static com.lawfirm.model.auth.constant.AuthConstants.Permission;

@RequiresPermission(Permission.USER_VIEW)
public void viewUser(Long userId) {
    // 查看用户信息的逻辑
}
```

### 6. 用户与员工关联
使用UserPersonnelService接口管理用户与员工的关联：
```java
// 关联用户和员工
userPersonnelService.linkUserToEmployee(userId, employeeId);

// 获取员工对应的用户
Long userId = userPersonnelService.getUserIdByEmployeeId(employeeId);

// 同步基本信息
userPersonnelService.syncUserInfoToEmployee(userId);
``` 