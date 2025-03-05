## 认证授权模型模块 (Auth Model)

## 目录结构
```
auth-model/
├── config/                # 配置类
│   └── AuthProperties.java # 认证配置属性类
├── constant/              # 常量定义
│   ├── AuthApiConstants.java  # API路径常量
│   ├── AuthCacheKeyConstants.java # 缓存键常量
│   ├── AuthErrorCode.java # 错误码常量
│   └── SecurityConstants.java # 安全相关常量
├── dto/                   # 传输对象
│   ├── auth/              # 认证相关
│   │   ├── CaptchaDTO.java  # 验证码对象
│   │   ├── LoginDTO.java    # 登录请求
│   │   ├── PasswordResetDTO.java # 密码重置
│   │   └── TokenDTO.java    # 令牌信息
│   ├── role/              # 角色相关
│   │   ├── RoleCreateDTO.java # 角色创建
│   │   └── RoleUpdateDTO.java # 角色更新
│   └── user/              # 用户相关
│       ├── UserCreateDTO.java # 用户创建
│       ├── UserQueryDTO.java  # 用户查询
│       └── UserUpdateDTO.java # 用户更新
├── entity/                # 实体定义
│   ├── Department.java    # 部门实体
│   ├── LoginHistory.java  # 登录历史
│   ├── Permission.java    # 权限实体
│   ├── Position.java      # 职位实体
│   ├── Role.java          # 角色实体
│   ├── RolePermission.java # 角色权限关联
│   ├── User.java          # 用户实体
│   ├── UserGroup.java     # 用户组实体
│   └── UserRole.java      # 用户角色关联
├── enums/                 # 枚举定义
│   ├── AuthSourceEnum.java # 认证来源枚举
│   ├── DepartmentTypeEnum.java # 部门类型枚举
│   ├── LoginTypeEnum.java # 登录类型枚举
│   ├── PermissionTypeEnum.java # 权限类型枚举
│   ├── PositionLevelEnum.java # 职位级别枚举
│   ├── UserStatusEnum.java # 用户状态枚举
│   └── UserTypeEnum.java  # 用户类型枚举
├── mapper/                # 数据访问接口
│   ├── UserMapper.java    # 用户数据访问
│   ├── RoleMapper.java    # 角色数据访问
│   ├── PermissionMapper.java # 权限数据访问
│   ├── UserRoleMapper.java # 用户角色关联
│   ├── RolePermissionMapper.java # 角色权限关联
│   ├── DepartmentMapper.java # 部门数据访问
│   ├── PositionMapper.java # 职位数据访问
│   ├── UserGroupMapper.java # 用户组数据访问
│   └── LoginHistoryMapper.java # 登录历史记录
├── service/               # 服务接口
│   ├── PermissionService.java # 权限服务
│   ├── RoleService.java   # 角色服务
│   └── UserService.java   # 用户服务
└── vo/                    # 视图对象
    ├── PermissionVO.java  # 权限视图
    ├── RoleVO.java        # 角色视图
    └── UserVO.java        # 用户视图
```

## 模块说明
认证授权模型模块是一个纯定义模块，负责定义系统中所有与认证授权相关的实体、接口、服务定义、枚举、常量等。本模块不包含具体实现，所有实现都将在对应的服务模块中完成。

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
- User：用户实体，定义用户基本信息
- Role：角色实体，定义角色基本信息
- Permission：权限实体，定义功能权限和数据权限
- UserRole：用户角色关联实体，定义用户与角色的多对多关系
- RolePermission：角色权限关联实体，定义角色与权限的多对多关系
- Department：部门实体，定义组织架构中的部门信息
- Position：职位实体，定义职位信息
- UserGroup：用户组实体，定义用户组信息
- LoginHistory：登录历史实体，记录用户登录信息

### 2. 数据传输对象（dto）
- auth：认证相关DTO
  - LoginDTO：登录请求对象，支持多种登录方式
  - TokenDTO：令牌信息对象，包含访问令牌和刷新令牌
  - CaptchaDTO：验证码对象，用于图形验证码
  - PasswordResetDTO：密码重置对象，用于重置密码
- user：用户相关DTO
  - UserCreateDTO：用户创建对象
  - UserUpdateDTO：用户更新对象
  - UserQueryDTO：用户查询对象
- role：角色相关DTO
  - RoleCreateDTO：角色创建对象
  - RoleUpdateDTO：角色更新对象

### 3. 视图对象（vo）
- UserVO：用户视图对象，用于前端展示
- RoleVO：角色视图对象，用于前端展示
- PermissionVO：权限视图对象，用于前端展示

### 4. 枚举定义（enums）
- UserTypeEnum：用户类型枚举，如管理员、普通用户
- UserStatusEnum：用户状态枚举，如正常、禁用
- PermissionTypeEnum：权限类型枚举，如菜单、按钮、接口
- DepartmentTypeEnum：部门类型枚举，如管理部门、业务部门
- PositionLevelEnum：职位级别枚举，如高层、中层、基层
- LoginTypeEnum：登录类型枚举，如用户名密码、短信验证码、邮箱验证码
- AuthSourceEnum：认证来源枚举，如网页端、移动端、小程序

### 5. 常量定义（constant）
- SecurityConstants：安全相关常量，如Token前缀、过期时间
- AuthErrorCode：认证授权错误码
- AuthCacheKeyConstants：认证缓存键常量
- AuthApiConstants：认证API路径常量

### 6. 配置类（config）
- AuthProperties：认证配置属性类，支持以下配置：
  - Jwt：JWT相关配置（密钥、过期时间等）
  - Security：安全相关配置（密码策略、验证码等）
  - Ldap：LDAP认证配置
  - Sms：短信验证码配置
  - Email：邮箱验证码配置
  - ThirdParty：第三方登录配置（微信、钉钉）

### 7. 服务接口（service）
- UserService：用户服务接口，定义用户管理相关操作
- RoleService：角色服务接口，定义角色管理相关操作
- PermissionService：权限服务接口，定义权限管理相关操作

### 8. 数据访问接口（mapper）
- UserMapper：用户数据访问接口，定义用户表的CRUD操作和复杂查询
- RoleMapper：角色数据访问接口，定义角色表的CRUD操作和复杂查询
- PermissionMapper：权限数据访问接口，定义权限表的CRUD操作和复杂查询
- UserRoleMapper：用户角色关联数据访问接口，处理用户与角色的多对多关系
- RolePermissionMapper：角色权限关联数据访问接口，处理角色与权限的多对多关系
- DepartmentMapper：部门数据访问接口，支持树形结构查询
- PositionMapper：职位数据访问接口，定义职位表的操作
- UserGroupMapper：用户组数据访问接口，处理用户组相关操作
- LoginHistoryMapper：登录历史数据访问接口，记录和查询用户登录历史

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
- 部门实体使用TreeEntity特性

### 3. 认证方式
本模块支持以下认证方式：
- 用户名密码认证
- 短信验证码认证
- 邮箱验证码认证
- LDAP认证
- 扫码登录认证
- 微信登录认证
- 钉钉登录认证

### 4. 多端支持
本模块支持多种认证来源：
- 网页端
- 移动端
- 小程序
- 开放API
- 系统内部
- 后台管理

### 5. 配置说明
通过AuthProperties配置类，可以灵活配置：
- JWT令牌相关参数（密钥、过期时间）
- 安全策略（密码规则、验证码策略）
- LDAP连接参数
- 短信验证码参数
- 邮箱验证码参数
- 第三方登录参数

### 6. 注意事项
- 本模块仅包含定义，不包含实现
- 具体实现在对应的服务模块中完成
- 遵循接口定义规范
- 注意依赖管理
- 配置参数需要在应用配置文件中设置
- Mapper接口定义了数据访问层的抽象，但具体SQL实现在服务模块中 