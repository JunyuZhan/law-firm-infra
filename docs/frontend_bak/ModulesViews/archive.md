# 档案管理模块

## 1. 功能概述

档案管理模块是律师事务所管理系统的重要支撑模块，主要负责事务所各类档案的电子化管理、借阅管理和归档管理，确保档案的安全性、完整性和可追溯性。

### 1.1 主要功能

- 档案管理
  - 档案录入与存储
  - 档案分类编目
  - 档案检索查询
  - 档案更新维护
  - 档案安全控制

- 借阅管理
  - 借阅申请处理
  - 审批流程管理
  - 借阅记录跟踪
  - 到期提醒处理
  - 归还确认管理

- 统计分析
  - 档案数量统计
  - 借阅情况分析
  - 存储空间分析
  - 操作日志记录

### 1.2 应用场景

1. 案件档案管理
   - 案件文书管理
   - 证据材料管理
   - 法律文件管理
   - 案件记录管理

2. 合同档案管理
   - 合同文本管理
   - 签署文件管理
   - 履约记录管理
   - 变更文件管理

3. 行政档案管理
   - 人事档案管理
   - 财务档案管理
   - 资产档案管理
   - 制度文件管理

## 2. 页面结构

```bash
views/archive/
├── list/                # 档案列表页
│   ├── index.vue       # 列表主页面
│   └── components/     # 列表页组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableColumns.vue    # 表格列配置
│       ├── ArchiveModal.vue    # 档案表单
│       └── BatchActions.vue    # 批量操作组件
│
├── detail/             # 档案详情页
│   ├── index.vue      # 详情主页面
│   └── components/    # 详情页组件
│       ├── BasicInfo.vue       # 基本信息
│       ├── FileContent.vue     # 文件内容
│       ├── Attachments.vue     # 附件管理
│       ├── BorrowRecord.vue    # 借阅记录
│       ├── Timeline.vue        # 操作时间线
│       └── Comments.vue        # 批注意见
│
├── borrow/            # 借阅管理
│   ├── index.vue     # 借阅列表
│   └── components/   # 借阅组件
│       ├── BorrowForm.vue      # 借阅表单
│       ├── ApprovalFlow.vue    # 审批流程
│       ├── ReturnForm.vue      # 归还表单
│       └── Statistics.vue      # 统计分析
│
└── components/        # 公共组件
    ├── ArchiveType.vue        # 档案类型
    ├── ArchiveStatus.vue      # 档案状态
    └── ArchiveVersion.vue     # 版本管理
```

## 3. 功能清单

### 3.1 档案管理
- [x] 档案录入
  - 单个录入
  - 批量导入
  - 模板导入
  - 扫描导入
  - 自动编号

- [x] 档案编目
  - 分类管理
  - 标签管理
  - 元数据管理
  - 关联关系
  - 版本控制

- [x] 档案存储
  - 文件上传
  - 加密存储
  - 备份管理
  - 存储优化
  - 空间管理

### 3.2 借阅管理
- [x] 借阅申请
  - 在线申请
  - 借阅范围
  - 借阅期限
  - 用途说明
  - 特殊申请

- [x] 审批流程
  - 审批配置
  - 流程管理
  - 权限控制
  - 审批记录
  - 催办提醒

### 3.3 统计分析
- [x] 档案统计
  - 数量统计
  - 分类统计
  - 增长趋势
  - 使用情况
  - 存储占用

- [x] 借阅分析
  - 借阅频率
  - 借阅时长
  - 逾期情况
  - 使用热度
  - 部门分布

## 4. API 接口

### 4.1 数据结构
```typescript
// 档案信息
interface Archive {
  id: string;                 // 档案ID
  archiveNo: string;          // 档案编号
  title: string;              // 档案标题
  category: string;           // 档案分类
  type: ArchiveType;         // 档案类型
  status: ArchiveStatus;     // 档案状态
  location: string;          // 存放位置
  keeper: string;            // 保管人
  createTime: string;        // 创建时间
  updateTime: string;        // 更新时间
  description?: string;      // 描述信息
  attachments?: string[];    // 附件列表
  tags?: string[];           // 标签
  metadata?: object;         // 元数据
}

// 借阅记录
interface BorrowRecord {
  id: string;                 // 记录ID
  archiveId: string;          // 档案ID
  applicant: string;          // 申请人
  purpose: string;            // 借阅目的
  borrowTime: string;         // 借阅时间
  planReturnTime: string;     // 计划归还时间
  actualReturnTime?: string;  // 实际归还时间
  status: BorrowStatus;       // 状态
  approver: string;           // 审批人
  remark?: string;            // 备注
}

// 档案类型
enum ArchiveType {
  CASE = 'case',           // 案件档案
  CONTRACT = 'contract',   // 合同档案
  PERSONNEL = 'personnel', // 人事档案
  FINANCE = 'finance',     // 财务档案
  ADMIN = 'admin'         // 行政档案
}

// 档案状态
enum ArchiveStatus {
  DRAFT = 'draft',         // 草稿
  ACTIVE = 'active',       // 生效
  BORROWED = 'borrowed',   // 借出
  ARCHIVED = 'archived',   // 归档
  DESTROYED = 'destroyed'  // 销毁
}

// 借阅状态
enum BorrowStatus {
  PENDING = 'pending',     // 待审批
  APPROVED = 'approved',   // 已批准
  REJECTED = 'rejected',   // 已拒绝
  BORROWED = 'borrowed',   // 已借出
  RETURNED = 'returned',   // 已归还
  OVERDUE = 'overdue'     // 已逾期
}
```

### 4.2 接口列表
```typescript
// 获取档案列表
export const getArchiveList = (params: ArchiveListParams) => {
  return http.get('/archive/list', { params });
};

// 获取档案详情
export const getArchiveDetail = (id: string) => {
  return http.get(`/archive/detail/${id}`);
};

// 创建档案
export const createArchive = (data: Partial<Archive>) => {
  return http.post('/archive/create', data);
};

// 更新档案
export const updateArchive = (id: string, data: Partial<Archive>) => {
  return http.put(`/archive/update/${id}`, data);
};

// 删除档案
export const deleteArchive = (id: string) => {
  return http.delete(`/archive/delete/${id}`);
};

// 提交借阅申请
export const submitBorrow = (data: Partial<BorrowRecord>) => {
  return http.post('/archive/borrow/submit', data);
};

// 审批借阅
export const approveBorrow = (id: string, data: ApprovalResult) => {
  return http.post(`/archive/borrow/approve/${id}`, data);
};

// 归还档案
export const returnArchive = (id: string, data: ReturnParams) => {
  return http.post(`/archive/return/${id}`, data);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能模块    | 管理员 | 律所主任 | 合伙人 | 执业律师 | 实习律师 | 行政人员 |
|------------|--------|----------|--------|----------|----------|----------|
| 档案录入    | ✓全所  | ✓全所    | ✓团队  | ✓个人    | -        | ✓部门全权 |
| 档案查询    | ✓全所  | ✓全所    | ✓团队  | ✓个人    | ✓个人    | ✓部门相关 |
| 档案编辑    | ✓全所  | ✓全所    | ✓团队  | -        | -        | ✓部门全权 |
| 档案删除    | ✓全所  | ✓全所    | -      | -        | -        | -        |
| 借阅申请    | ✓全所  | ✓全所    | ✓团队  | ✓个人    | ✓个人    | ✓部门相关 |
| 借阅审批    | ✓全所  | ✓全所    | ✓团队  | -        | -        | ✓部门全权 |
| 统计分析    | ✓全所  | ✓全所    | ✓团队  | -        | -        | ✓部门全权 |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任可访问所有档案
   - 合伙人可访问本团队相关档案
   - 执业律师可访问个人相关档案
   - 实习律师仅可查看个人相关档案
   - 行政人员可管理本部门档案

2. 特殊权限控制
   - 涉密档案需要特殊授权才能访问
   - 人事档案仅限人事部门访问
   - 财务档案仅限财务部门访问
   - 电子档案下载需要审批

3. 审批流程
   - 普通档案借阅：部门主管审批
   - 重要档案借阅：需经部门主管和律所主任双重审批
   - 涉密档案借阅：需经律所主任审批

### 5.3 路由配置
```typescript
// router/modules/archive.ts
export default {
  path: '/archive',
  name: 'Archive',
  component: LAYOUT,
  meta: {
    title: '档案管理',
    icon: 'ant-design:folder-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_archive']
  },
  children: [
    {
      path: 'list',
      name: 'ArchiveList',
      component: () => import('@/views/archive/list/index.vue'),
      meta: {
        title: '档案列表'
      }
    },
    {
      path: 'detail/:id',
      name: 'ArchiveDetail',
      component: () => import('@/views/archive/detail/index.vue'),
      meta: {
        title: '档案详情',
        hideMenu: true
      }
    },
    {
      path: 'borrow',
      name: 'ArchiveBorrow',
      component: () => import('@/views/archive/borrow/index.vue'),
      meta: {
        title: '借阅管理'
      }
    }
  ]
};
```

## 6. 状态管理

```typescript
// store/modules/archive.ts
interface ArchiveState {
  archiveList: Archive[];
  currentArchive: Archive | null;
  borrowRecords: BorrowRecord[];
  loading: boolean;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
}

const useArchiveStore = defineStore({
  id: 'app-archive',
  state: (): ArchiveState => ({
    archiveList: [],
    currentArchive: null,
    borrowRecords: [],
    loading: false,
    pagination: {
      current: 1,
      pageSize: 10,
      total: 0
    },
    searchParams: {}
  }),
  getters: {
    getArchiveList(): Archive[] {
      return this.archiveList;
    },
    getCurrentArchive(): Archive | null {
      return this.currentArchive;
    }
  },
  actions: {
    async fetchArchiveList() {
      this.loading = true;
      try {
        const { items, total } = await getArchiveList({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.archiveList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchArchiveDetail(id: string) {
      this.loading = true;
      try {
        this.currentArchive = await getArchiveDetail(id);
      } finally {
        this.loading = false;
      }
    }
  }
});
```

## 7. 使用说明

### 7.1 档案管理流程
1. 档案录入
   - 选择档案类型
   - 填写基本信息
   - 上传档案文件
   - 设置访问权限
   - 提交保存

2. 档案借阅
   - 提交借阅申请
   - 等待审批通过
   - 借阅档案使用
   - 按时归还档案
   - 确认归还完成

3. 档案维护
   - 定期检查档案
   - 更新档案信息
   - 处理过期档案
   - 备份重要档案
   - 清理无用档案

### 7.2 注意事项

1. 档案编号规则
   - 案件档案：AJ + 年份 + 序号，如：AJ2024001
   - 合同档案：HT + 年份 + 序号，如：HT2024001
   - 人事档案：RS + 年份 + 序号，如：RS2024001
   - 财务档案：CW + 年份 + 序号，如：CW2024001
   - 行政档案：XZ + 年份 + 序号，如：XZ2024001

2. 安全管理
   - 定期备份档案数据
   - 加密存储敏感信息
   - 严格控制访问权限
   - 记录所有操作日志

3. 规范要求
   - 统一命名规范
   - 规范分类体系
   - 标准化元数据
   - 完整操作流程

4. 性能优化
   - 分页加载数据
   - 压缩存储文件
   - 缓存常用数据
   - 优化检索速度 
