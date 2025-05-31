# 权限矩阵功能说明

## 概述

权限矩阵功能提供了一套完整的权限管理解决方案，将前端的权限检查逻辑迁移到后端，实现统一的权限控制和管理。

## 核心组件

### 1. 数据模型层 (auth-model)

#### 服务接口
- `PermissionMatrixService`: 权限矩阵核心服务接口
- `PermissionChecker`: 权限检查接口（兼容现有系统）

#### 数据传输对象
- `PermissionMatrixDTO`: 权限检查请求对象
- `PermissionMatrixVO`: 权限矩阵视图对象

#### 配置类
- `PermissionMatrixConfig`: 权限矩阵配置，定义角色-模块-操作映射

### 2. 服务实现层 (law-firm-auth)

#### 服务实现
- `PermissionMatrixServiceImpl`: 权限矩阵服务实现类
  - 实现 `PermissionMatrixService` 接口
  - 实现 `PermissionChecker` 接口（向后兼容）
  - 支持缓存机制

#### REST API
- `PermissionMatrixController`: 权限矩阵REST控制器

#### 配置
- `PermissionMatrixCacheConfig`: 缓存配置
- `AuthAutoConfiguration`: 自动配置类

## 主要功能

### 1. 权限矩阵生成

```java
// 获取完整权限矩阵
PermissionMatrixVO fullMatrix = permissionMatrixService.getFullPermissionMatrix();

// 获取角色权限矩阵
PermissionMatrixVO roleMatrix = permissionMatrixService.getRolePermissionMatrix(RoleEnum.LAWYER);

// 获取用户权限矩阵
PermissionMatrixVO userMatrix = permissionMatrixService.getUserPermissionMatrix(userId);
```

### 2. 权限检查

```java
// 检查单个权限
boolean hasPermission = permissionMatrixService.checkUserPermission(
    userId, ModuleTypeEnum.DOCUMENT, OperationTypeEnum.READ_ONLY);

// 批量权限检查
List<PermissionMatrixDTO> requests = Arrays.asList(
    new PermissionMatrixDTO(ModuleTypeEnum.DOCUMENT, OperationTypeEnum.READ_ONLY),
    new PermissionMatrixDTO(ModuleTypeEnum.CASE, OperationTypeEnum.CREATE)
);
Map<String, Boolean> results = permissionMatrixService.batchCheckUserPermissions(userId, requests);
```

### 3. 数据范围管理

```java
// 获取用户数据范围
DataScopeEnum dataScope = permissionMatrixService.getUserDataScope(userId, ModuleTypeEnum.DOCUMENT);

// 获取用户可访问模块
List<ModuleTypeEnum> modules = permissionMatrixService.getUserAccessibleModules(userId);

// 获取用户模块操作权限
List<OperationTypeEnum> operations = permissionMatrixService.getUserModuleOperations(userId, ModuleTypeEnum.DOCUMENT);
```

## REST API 接口

### 基础路径
```
/api/auth/permission-matrix
```

### 主要接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/full` | 获取完整权限矩阵 |
| GET | `/current-user` | 获取当前用户权限矩阵 |
| GET | `/user/{userId}` | 获取指定用户权限矩阵 |
| GET | `/role/{roleCode}` | 获取角色权限矩阵 |
| GET | `/check` | 检查用户权限 |
| GET | `/data-scope` | 获取用户数据范围 |
| POST | `/batch-check` | 批量检查用户权限 |
| GET | `/accessible-modules` | 获取用户可访问模块 |
| GET | `/module-operations` | 获取用户模块操作权限 |
| POST | `/refresh` | 刷新权限矩阵缓存 |
| GET | `/validate` | 验证权限矩阵配置 |

## 权限配置

### 角色层级

```
ADMIN (管理员)
├── PARTNER (合伙人)
│   ├── LAWYER (律师)
│   │   ├── ASSISTANT (助理)
│   │   └── PARALEGAL (律师助理)
│   └── MANAGER (经理)
└── FINANCE (财务)
```

### 模块类型

- SYSTEM: 系统管理
- USER: 用户管理
- ROLE: 角色管理
- PERMISSION: 权限管理
- DOCUMENT: 文档管理
- CASE: 案件管理
- CLIENT: 客户管理
- CONTRACT: 合同管理
- FINANCE: 财务管理
- SCHEDULE: 日程管理
- MESSAGE: 消息管理
- KNOWLEDGE: 知识库
- ARCHIVE: 归档管理
- ORGANIZATION: 组织管理

### 操作类型

- FULL: 完全权限
- APPROVE: 审批权限
- CREATE: 创建权限
- READ_ONLY: 只读权限
- PERSONAL: 个人权限
- APPLY: 申请权限
- EDIT: 编辑权限
- DELETE: 删除权限
- ARCHIVE: 归档权限

### 数据范围

- ALL: 全部数据
- DEPARTMENT_FULL: 部门完整数据
- TEAM: 团队数据
- DEPARTMENT_RELATED: 部门相关数据
- PERSONAL: 个人数据

## 缓存机制

系统使用Spring Cache进行权限矩阵缓存：

- `permissionMatrix`: 完整权限矩阵缓存
- `userPermissionMatrix`: 用户权限矩阵缓存
- `rolePermissionMatrix`: 角色权限矩阵缓存
- `userDataScope`: 用户数据范围缓存
- `userAccessibleModules`: 用户可访问模块缓存
- `userModuleOperations`: 用户模块操作权限缓存

## 向后兼容

`PermissionMatrixServiceImpl` 同时实现了 `PermissionChecker` 接口，确保与现有系统的兼容性：

```java
// 兼容现有的权限检查方式
boolean hasPermission = permissionChecker.hasPermission(userId, "document", OperationTypeEnum.READ_ONLY);
DataScopeEnum dataScope = permissionChecker.getDataScope(userId, "document");
boolean hasRole = permissionChecker.hasRole(userId, "LAWYER");
boolean hasPermissionCode = permissionChecker.hasPermission(userId, "document:read_only");
```

## 测试

提供了完整的单元测试：
- `PermissionMatrixServiceTest`: 权限矩阵服务测试类

## 使用示例

### 前端集成

```typescript
// 获取当前用户权限矩阵
const response = await api.get('/api/auth/permission-matrix/current-user');
const permissionMatrix = response.data;

// 检查权限
const hasPermission = await api.get('/api/auth/permission-matrix/check', {
  params: {
    moduleCode: 'document',
    operationCode: 'read_only'
  }
});

// 批量检查权限
const batchResult = await api.post('/api/auth/permission-matrix/batch-check', [
  { moduleType: 'DOCUMENT', operationType: 'READ_ONLY' },
  { moduleType: 'CASE', operationType: 'CREATE' }
]);
```

### 后端使用

```java
@Autowired
private PermissionMatrixService permissionMatrixService;

// 在业务逻辑中检查权限
if (!permissionMatrixService.checkUserPermission(userId, ModuleTypeEnum.DOCUMENT, OperationTypeEnum.CREATE)) {
    throw new AccessDeniedException("无权限创建文档");
}

// 根据数据范围过滤数据
DataScopeEnum dataScope = permissionMatrixService.getUserDataScope(userId, ModuleTypeEnum.DOCUMENT);
switch (dataScope) {
    case ALL:
        // 返回所有数据
        break;
    case TEAM:
        // 返回团队数据
        break;
    case PERSONAL:
        // 返回个人数据
        break;
}
```

## 配置管理

权限配置集中在 `PermissionMatrixConfig` 类中，可以通过修改该类来调整权限规则：

```java
// 添加新的角色权限配置
rolePermissions.put(RoleEnum.NEW_ROLE, Map.of(
    ModuleTypeEnum.DOCUMENT, new ModulePermissionConfig(
        true, // hasAccess
        DataScopeEnum.TEAM, // dataScope
        Arrays.asList(OperationTypeEnum.READ_ONLY, OperationTypeEnum.CREATE), // allowedOperations
        false, // readOnly
        false, // requiresApproval
        false  // canCreateForTeam
    )
));
```

## 注意事项

1. **缓存刷新**: 修改权限配置后需要调用 `/refresh` 接口刷新缓存
2. **权限继承**: 角色权限支持层级继承，高级角色自动拥有低级角色的权限
3. **数据范围**: 数据范围限制用户能够访问的数据范围，需要在业务逻辑中实现
4. **性能优化**: 使用批量权限检查接口可以减少网络请求次数
5. **配置验证**: 定期使用 `/validate` 接口检查权限配置的完整性

## 扩展性

系统设计支持以下扩展：

1. **新增角色**: 在 `RoleEnum` 中添加新角色，并在 `PermissionMatrixConfig` 中配置权限
2. **新增模块**: 在 `ModuleTypeEnum` 中添加新模块类型
3. **新增操作**: 在 `OperationTypeEnum` 中添加新操作类型
4. **自定义数据范围**: 在 `DataScopeEnum` 中添加新的数据范围类型
5. **缓存策略**: 可以替换为Redis等分布式缓存