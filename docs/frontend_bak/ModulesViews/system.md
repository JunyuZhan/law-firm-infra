# 系统管理模块

## 1. 功能概述

系统管理模块是律师事务所管理系统的基础支撑模块，负责系统的基础配置、用户权限、数据字典等核心功能，确保系统的安全性、可维护性和可扩展性。本模块为其他业务模块提供基础支撑，是整个系统的管理中心。

### 1.1 主要功能

- 用户管理
  - 用户信息维护
  - 角色分配
  - 权限设置
  - 部门管理
  - 团队管理

- 权限管理
  - 角色管理
  - 权限配置
  - 数据权限
  - 菜单权限
  - 功能权限

- 系统配置
  - 基础参数设置
  - 字典管理
  - 日志管理
  - 系统监控
  - 数据备份

## 2. 页面结构

```bash
views/system/
├── user/                # 用户管理
│   ├── index.vue       # 用户列表
│   └── components/     # 用户组件
│       ├── UserModal.vue       # 用户表单
│       ├── RoleSelect.vue      # 角色选择
│       ├── DeptTree.vue        # 部门树
│       ├── ResetPassword.vue   # 密码重置
│       ├── ChangePassword.vue  # 修改密码
│       ├── ImportModal.vue     # 导入弹窗
│       └── ExportModal.vue     # 导出弹窗
│
├── role/               # 角色管理
│   ├── index.vue      # 角色列表
│   └── components/    # 角色组件
│       ├── RoleModal.vue       # 角色表单
│       ├── MenuTree.vue        # 菜单树
│       ├── PermissionList.vue  # 权限列表
│       └── DataScope.vue       # 数据权限
│
├── menu/              # 菜单管理
│   ├── index.vue     # 菜单列表
│   └── components/   # 菜单组件
│       ├── MenuModal.vue       # 菜单表单
│       ├── IconSelect.vue      # 图标选择
│       └── MenuSort.vue        # 菜单排序
│
├── dept/             # 部门管理
│   ├── index.vue    # 部门列表
│   └── components/  # 部门组件
│       ├── DeptModal.vue       # 部门表单
│       ├── DeptTree.vue        # 部门树
│       └── Transfer.vue        # 人员调动
│
├── dict/            # 字典管理
│   ├── index.vue   # 字典列表
│   └── components/ # 字典组件
│       ├── DictModal.vue       # 字典表单
│       ├── DictData.vue        # 字典数据
│       └── DictExport.vue      # 字典导出
│
└── log/            # 日志管理
    ├── index.vue   # 日志列表
    └── components/ # 日志组件
        ├── LoginLog.vue        # 登录日志
        ├── OperateLog.vue      # 操作日志
        └── SystemLog.vue       # 系统日志
```

## 3. 功能清单

### 3.1 用户管理
- [x] 用户信息
  - 基本信息维护
    - 用户账号
    - 用户姓名
    - 所属部门
    - 所属团队
    - 联系方式
  - 状态管理
  - 角色分配
    - 角色设置
    - 权限继承
    - 临时授权
  - 安全设置
    - 密码管理
    - 登录控制
    - 操作限制

- [x] 组织架构
  - 部门管理
    - 部门设置
    - 人员分配
    - 权限范围
  - 团队管理
    - 团队创建
    - 成员管理
    - 团队权限

### 3.2 权限管理
- [x] 角色管理
  - 角色配置
    - 角色创建
  - 角色编辑
  - 角色删除
  - 角色复制
  - 权限分配
    - 菜单权限
  - 功能权限
  - 数据权限
    - 审批权限

- [x] 权限控制
  - 访问控制
    - 路由控制
    - 按钮控制
    - 数据控制
  - 操作控制
    - 功能限制
    - 数据范围
    - 字段权限

### 3.3 系统配置
- [x] 参数配置
  - 系统参数
  - 业务参数
  - 安全配置
  - 界面配置

- [x] 字典管理
  - 字典分类
  - 字典项维护
  - 字典值配置
  - 字典缓存

- [x] 日志管理
  - 登录日志
  - 操作日志
  - 系统日志
  - 安全日志

## 4. API 接口

### 4.1 数据结构
```typescript
// 用户信息
interface User {
  id: string;                 // 用户ID
  username: string;          // 用户名
  realName: string;         // 真实姓名
  password: string;         // 密码
  avatar: string;           // 头像
  email: string;            // 邮箱
  mobile: string;           // 手机号
  deptId: string;          // 部门ID
  roleIds: string[];       // 角色ID列表
  status: UserStatus;      // 用户状态
  createTime: string;      // 创建时间
  updateTime: string;      // 更新时间
  lastLoginTime: string;   // 最后登录时间
}

// 角色信息
interface Role {
  id: string;              // 角色ID
  name: string;           // 角色名称
  code: string;           // 角色编码
  sort: number;          // 排序号
  status: RoleStatus;    // 角色状态
  dataScope: DataScope;  // 数据权限范围
  menuIds: string[];     // 菜单权限
  deptIds: string[];     // 数据权限部门
  remark: string;        // 备注
  createTime: string;    // 创建时间
  updateTime: string;    // 更新时间
}

// 菜单信息
interface Menu {
  id: string;             // 菜单ID
  parentId: string;      // 父级ID
  name: string;          // 菜单名称
  path: string;          // 路由路径
  component: string;     // 组件路径
  icon: string;          // 图标
  sort: number;         // 排序号
  type: MenuType;       // 菜单类型
  permission: string;   // 权限标识
  status: MenuStatus;   // 菜单状态
  visible: boolean;     // 是否显示
  keepAlive: boolean;   // 是否缓存
  createTime: string;   // 创建时间
  updateTime: string;   // 更新时间
}

// 部门信息
interface Department {
  id: string;            // 部门ID
  parentId: string;     // 父级ID
  name: string;         // 部门名称
  code: string;         // 部门编码
  sort: number;        // 排序号
  leader: string;      // 负责人
  phone: string;       // 联系电话
  email: string;       // 邮箱
  status: DeptStatus; // 部门状态
  createTime: string;  // 创建时间
  updateTime: string;  // 更新时间
}

// 字典信息
interface Dict {
  id: string;           // 字典ID
  name: string;        // 字典名称
  type: string;        // 字典类型
  status: DictStatus; // 字典状态
  remark: string;     // 备注
  createTime: string; // 创建时间
  updateTime: string; // 更新时间
  items: DictItem[]; // 字典项列表
}

// 日志信息
interface Log {
  id: string;          // 日志ID
  type: LogType;      // 日志类型
  title: string;      // 日志标题
  method: string;     // 请求方法
  params: string;     // 请求参数
  result: string;     // 返回结果
  ip: string;        // 操作IP
  location: string;  // 操作地点
  browser: string;   // 浏览器
  os: string;        // 操作系统
  status: number;    // 状态码
  userId: string;    // 操作人ID
  createTime: string;// 创建时间
}
```

### 4.2 接口列表
```typescript
// 用户管理接口
export const getUserList = (params: UserListParams) => {
  return http.get('/system/user/list', { params });
};

export const createUser = (data: Partial<User>) => {
  return http.post('/system/user/create', data);
};

export const updateUser = (id: string, data: Partial<User>) => {
  return http.put(`/system/user/${id}`, data);
};

export const deleteUser = (id: string) => {
  return http.delete(`/system/user/${id}`);
};

export const resetPassword = (id: string, password: string) => {
  return http.post(`/system/user/password/reset/${id}`, { password });
};

// 角色管理接口
export const getRoleList = (params: RoleListParams) => {
  return http.get('/system/role/list', { params });
};

export const createRole = (data: Partial<Role>) => {
  return http.post('/system/role/create', data);
};

export const updateRole = (id: string, data: Partial<Role>) => {
  return http.put(`/system/role/${id}`, data);
};

export const deleteRole = (id: string) => {
  return http.delete(`/system/role/${id}`);
};

// 菜单管理接口
export const getMenuList = (params: MenuListParams) => {
  return http.get('/system/menu/list', { params });
};

export const createMenu = (data: Partial<Menu>) => {
  return http.post('/system/menu/create', data);
};

export const updateMenu = (id: string, data: Partial<Menu>) => {
  return http.put(`/system/menu/${id}`, data);
};

export const deleteMenu = (id: string) => {
  return http.delete(`/system/menu/${id}`);
};

// 部门管理接口
export const getDeptList = (params: DeptListParams) => {
  return http.get('/system/dept/list', { params });
};

export const createDept = (data: Partial<Department>) => {
  return http.post('/system/dept/create', data);
};

export const updateDept = (id: string, data: Partial<Department>) => {
  return http.put(`/system/dept/${id}`, data);
};

export const deleteDept = (id: string) => {
  return http.delete(`/system/dept/${id}`);
};

// 字典管理接口
export const getDictList = (params: DictListParams) => {
  return http.get('/system/dict/list', { params });
};

export const createDict = (data: Partial<Dict>) => {
  return http.post('/system/dict/create', data);
};

export const updateDict = (id: string, data: Partial<Dict>) => {
  return http.put(`/system/dict/${id}`, data);
};

export const deleteDict = (id: string) => {
  return http.delete(`/system/dict/${id}`);
};

// 日志管理接口
export const getLogList = (params: LogListParams) => {
  return http.get('/system/log/list', { params });
};

export const exportLog = (params: LogExportParams) => {
  return http.get('/system/log/export', { params });
};

export const clearLog = (type: LogType) => {
  return http.post('/system/log/clear', { type });
};
```

## 5. 权限控制

### 5.1 角色权限

| 功能         | 管理员 | 律所主任 | 合伙人 | 执业律师 | 实习律师 | 行政人员 |
|------------|--------|----------|--------|----------|----------|----------|
| 用户管理     | ✓全所  | ✓全所    | -      | -        | -        | -        |
| 角色管理     | ✓      | ✓        | -      | -        | -        | -        |
| 菜单管理     | ✓      | -        | -      | -        | -        | -        |
| 部门管理     | ✓      | ✓        | -      | -        | -        | ✓部门    |
| 字典管理     | ✓      | ✓        | -      | -        | -        | -        |
| 参数配置     | ✓      | ✓        | -      | -        | -        | -        |
| 日志查看     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | ✓部门    |
| 数据备份     | ✓      | -        | -      | -        | -        | -        |
| 系统监控     | ✓      | -        | -      | -        | -        | -        |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员：可管理所有系统功能和数据
   - 律所主任：可管理除系统核心配置外的功能
   - 合伙人：可管理团队用户和查看团队日志
   - 执业律师：只能查看个人日志
   - 实习律师：只能查看个人日志
   - 行政人员：可管理部门用户和查看部门日志

2. 特殊权限说明
   - 用户管理：需要考虑数据隔离
   - 角色管理：只有高级管理员可以操作
   - 菜单管理：仅系统管理员可以操作
   - 权限分配：需要严格控制授权范围

3. 系统配置权限
   - 核心配置：仅系统管理员
   - 业务配置：管理员和律所主任
   - 部门配置：部门管理员
   - 个人配置：所有用户

### 5.3 路由配置
```typescript
// router/modules/system.ts
export default {
  path: '/system',
  name: 'System',
  component: LAYOUT,
  meta: {
    title: '系统管理',
    icon: 'ant-design:setting-outlined',
    roles: ['admin', 'director', 'partner', 'admin_finance']
  },
  children: [
    {
      path: 'user',
      name: 'UserManage',
      component: () => import('@/views/system/user/index.vue'),
      meta: {
        title: '用户管理'
      }
    },
    {
      path: 'role',
      name: 'RoleManage',
      component: () => import('@/views/system/role/index.vue'),
      meta: {
        title: '角色管理',
        roles: ['admin', 'director']
      }
    },
    {
      path: 'menu',
      name: 'MenuManage',
      component: () => import('@/views/system/menu/index.vue'),
      meta: {
        title: '菜单管理',
        roles: ['admin']
      }
    },
    {
      path: 'dict',
      name: 'DictManage',
      component: () => import('@/views/system/dict/index.vue'),
      meta: {
        title: '字典管理',
        roles: ['admin', 'director']
      }
    },
    {
      path: 'log',
      name: 'LogManage',
      component: () => import('@/views/system/log/index.vue'),
      meta: {
        title: '日志管理'
      }
    }
  ]
};
```

## 6. 状态管理

### 6.1 Store 结构
```typescript
// store/modules/system.ts
interface SystemState {
  userList: User[];
  roleList: Role[];
  menuList: Menu[];
  deptList: Department[];
  dictList: Dict[];
  logList: Log[];
  loading: boolean;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
}

const useSystemStore = defineStore({
  id: 'app-system',
  state: (): SystemState => ({
    userList: [],
    roleList: [],
    menuList: [],
    deptList: [],
    dictList: [],
    logList: [],
    loading: false,
    pagination: {
      current: 1,
      pageSize: 10,
      total: 0
    },
    searchParams: {}
  }),
  getters: {
    getUserList(): User[] {
      return this.userList;
    },
    getRoleList(): Role[] {
      return this.roleList;
    },
    getMenuList(): Menu[] {
      return this.menuList;
    }
  },
  actions: {
    async fetchUserList() {
      this.loading = true;
      try {
        const { items, total } = await getUserList({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.userList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchRoleList() {
      this.loading = true;
      try {
        const { items } = await getRoleList();
        this.roleList = items;
  } finally {
        this.loading = false;
      }
    },
    async fetchMenuList() {
      this.loading = true;
      try {
        const { items } = await getMenuList();
        this.menuList = items;
  } finally {
        this.loading = false;
      }
    }
  }
});
```

## 7. 使用说明

### 7.1 用户管理
1. 用户创建
   - 填写基本信息
   - 选择所属部门
   - 分配角色权限
   - 设置初始密码

2. 用户维护
   - 信息修改
   - 状态管理
   - 密码重置
   - 角色调整

3. 批量操作
   - 批量导入
   - 批量导出
   - 批量启用/禁用
   - 批量角色分配

### 7.2 权限配置
1. 角色配置
   - 创建角色
   - 设置权限
   - 分配菜单
   - 设置数据范围

2. 菜单配置
   - 菜单创建
   - 权限设置
   - 图标配置
   - 排序调整

3. 权限分配
   - 功能权限
   - 数据权限
   - 字段权限
   - 按钮权限

### 7.3 系统维护
1. 字典管理
   - 字典分类
   - 字典项维护
   - 字典值配置
   - 缓存管理

2. 日志管理
   - 日志查询
   - 日志导出
   - 日志清理
   - 日志分析

## 8. 最佳实践

### 8.1 权限管理
1. 权限设计
   - 遵循最小权限原则
   - 实施分级授权机制
   - 建立权限审计制度
   - 定期权限复查

2. 角色设计
   - 合理划分角色
   - 避免权限过度
   - 职责分离
   - 动态调整

3. 安全控制
   - 密码策略
   - 登录控制
   - 操作审计
   - 异常监控

### 8.2 系统配置
1. 参数配置
   - 统一配置管理
   - 动态加载配置
   - 配置版本控制
   - 配置备份恢复

2. 字典管理
   - 规范字典编码
   - 及时更新维护
   - 缓存机制优化
   - 版本管理

3. 日志管理
   - 分类管理
   - 定期归档
   - 统计分析
   - 安全审计

## 9. 注意事项

1. 安全管理
   - 定期修改密码
   - 及时清理临时权限
   - 定期审计日志
   - 监控异常操作

2. 数据维护
   - 定期数据备份
   - 及时更新配置
   - 清理无效数据
   - 优化系统性能

3. 操作规范
   - 严格权限管理
   - 规范操作流程
   - 保持日志完整
   - 及时处理异常
