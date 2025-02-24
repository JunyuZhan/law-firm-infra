# 印章管理模块

## 1. 功能概述

印章管理模块是律师事务所管理系统的重要组成部分，用于管理律所各类印章的使用、保管和审批流程，确保印章使用的规范性和安全性。本模块包括印章基本信息管理、使用申请、审批流程、使用记录等功能。

### 1.1 主要功能

- 印章基础管理
  - 印章信息维护
  - 印章分类管理
  - 印章状态跟踪
  - 保管人管理
  - 使用权限管理

- 印章使用管理
  - 用印申请
  - 审批流程
  - 用印记录
  - 交接管理
  - 外带管理

- 印章监控管理
  - 使用统计
  - 异常监控
  - 到期提醒
  - 年检管理

## 2. 页面结构

```bash
views/seal/
├── list/                # 印章列表
│   ├── index.vue       # 列表主页面
│   └── components/     # 列表组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableList.vue       # 表格列表
│       ├── SealModal.vue       # 印章表单
│       └── BatchActions.vue    # 批量操作
│
├── apply/              # 用印申请
│   ├── index.vue      # 申请页面
│   └── components/    # 申请组件
│       ├── ApplyForm.vue       # 申请表单
│       ├── ApprovalFlow.vue    # 审批流程
│       ├── FileUpload.vue      # 文件上传
│       └── Preview.vue         # 预览组件
│
├── record/            # 使用记录
│   ├── index.vue     # 记录页面
│   └── components/   # 记录组件
│       ├── RecordList.vue      # 记录列表
│       ├── RecordDetail.vue    # 记录详情
│       └── Statistics.vue      # 统计分析
│
└── components/        # 公共组件
    ├── SealType.vue          # 印章类型
    ├── SealStatus.vue        # 印章状态
    └── ApprovalStatus.vue    # 审批状态
```

## 3. 功能清单

### 3.1 印章管理
- [x] 印章信息
  - 基本信息
    - 印章名称
    - 印章编号
    - 印章类型
    - 印章规格
    - 刻制时间
    - 启用时间
    - 有效期限
    - 保管人员
  - 使用权限
    - 使用范围
    - 审批流程
    - 特殊限制
    - 备注说明
  
- [x] 印章分类
  - 公章
  - 合同专用章
  - 财务专用章
  - 法定代表人名章
  - 其他印章

### 3.2 用印管理
- [x] 用印申请
  - 申请信息
    - 申请人
    - 申请时间
    - 用印文件
    - 用印数量
    - 用印事由
  - 审批流程
    - 部门主管审批
    - 分管领导审批
    - 印章保管人确认
  
- [x] 用印记录
  - 用印时间
  - 用印文件
  - 用印份数
  - 经办人
  - 见证人
  - 备注说明

### 3.3 监控管理
- [x] 使用监控
  - 使用频次统计
  - 使用部门分析
  - 使用目的分析
  - 异常使用预警
  
- [x] 维护管理
  - 年检管理
  - 更换管理
  - 销毁管理
  - 交接管理

## 4. API 接口

### 4.1 数据结构
```typescript
// 印章信息
interface Seal {
  id: string;                 // 印章ID
  name: string;              // 印章名称
  code: string;              // 印章编号
  type: SealType;           // 印章类型
  specification: string;     // 印章规格
  status: SealStatus;       // 印章状态
  makeDate: string;         // 刻制时间
  validDate: string;        // 有效期限
  custodian: string;        // 保管人
  department: string;       // 所属部门
  remark: string;          // 备注说明
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}

// 用印申请
interface SealApply {
  id: string;              // 申请ID
  sealId: string;         // 印章ID
  applicant: string;      // 申请人
  department: string;     // 申请部门
  purpose: string;        // 用印事由
  documents: string[];    // 用印文件
  copies: number;         // 用印份数
  useDate: string;        // 使用日期
  status: ApplyStatus;    // 申请状态
  createTime: string;     // 申请时间
}

// 用印记录
interface SealRecord {
  id: string;             // 记录ID
  applyId: string;       // 申请ID
  sealId: string;        // 印章ID
  operator: string;      // 经办人
  witness: string;       // 见证人
  useTime: string;       // 用印时间
  returnTime: string;    // 归还时间
  documents: string[];   // 用印文件
  copies: number;        // 用印份数
  remark: string;        // 备注说明
  createTime: string;    // 创建时间
}
```

### 4.2 接口列表
```typescript
// 获取印章列表
export const getSealList = (params: SealListParams) => {
  return http.get('/seal/list', { params });
};

// 获取印章详情
export const getSealDetail = (id: string) => {
  return http.get(`/seal/detail/${id}`);
};

// 创建印章
export const createSeal = (data: Partial<Seal>) => {
  return http.post('/seal/create', data);
};

// 更新印章
export const updateSeal = (id: string, data: Partial<Seal>) => {
  return http.put(`/seal/update/${id}`, data);
};

// 提交用印申请
export const submitSealApply = (data: Partial<SealApply>) => {
  return http.post('/seal/apply/submit', data);
};

// 审批用印申请
export const approveSealApply = (id: string, data: ApprovalParams) => {
  return http.post(`/seal/apply/approve/${id}`, data);
};

// 记录用印情况
export const recordSealUse = (data: Partial<SealRecord>) => {
  return http.post('/seal/record/create', data);
};

// 获取用印记录
export const getSealRecords = (params: RecordQueryParams) => {
  return http.get('/seal/records', { params });
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能         | 管理员 | 律所主任 | 合伙人 | 执业律师 | 实习律师 | 行政人员 |
|------------|--------|----------|--------|----------|----------|----------|
| 查看印章     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | ✓保管    |
| 用印申请     | ✓      | ✓        | ✓      | ✓        | -        | ✓        |
| 用印审批     | ✓      | ✓        | ✓团队   | -        | -        | -        |
| 印章管理     | ✓      | ✓        | -      | -        | -        | ✓保管    |
| 记录查看     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | ✓保管    |

### 5.2 数据权限说明

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员：可查看和管理所有印章
   - 律所主任：可查看所有印章，审批用印申请
   - 合伙人：可查看团队印章，审批团队用印
   - 执业律师：可申请用印
   - 实习律师：无权限
   - 印章保管人：负责日常保管和用印执行

2. 特殊权限说明
   - 用印申请：需要通过审批流程
   - 用印执行：需要印章保管人确认
   - 印章管理：仅管理员和保管人可以管理
   - 记录查看：按照角色权限范围查看

3. 审批流程权限
   - 一般用印：部门主管审批
   - 重要用印：需要律所主任审批
   - 特殊用印：需要管理员审批

### 5.3 路由配置
```typescript
// router/modules/seal.ts
export default {
  path: '/seal',
  name: 'Seal',
  component: LAYOUT,
  meta: {
    title: '印章管理',
    icon: 'ant-design:stamp-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'admin_finance']
  },
  children: [
    {
      path: 'list',
      name: 'SealList',
      component: () => import('@/views/seal/list/index.vue'),
      meta: {
        title: '印章列表'
      }
    },
    {
      path: 'apply',
      name: 'SealApply',
      component: () => import('@/views/seal/apply/index.vue'),
      meta: {
        title: '用印申请'
      }
    },
    {
      path: 'record',
      name: 'SealRecord',
      component: () => import('@/views/seal/record/index.vue'),
      meta: {
        title: '用印记录'
      }
    }
  ]
};
```

## 6. 状态管理

### 6.1 Store 结构
```typescript
// store/modules/seal.ts
interface SealState {
  sealList: Seal[];
  currentSeal: Seal | null;
  applyList: SealApply[];
  recordList: SealRecord[];
  loading: boolean;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
  filterOptions: {
    types: SealType[];
    statuses: SealStatus[];
  };
}

const useSealStore = defineStore({
  id: 'app-seal',
  state: (): SealState => ({
    sealList: [],
    currentSeal: null,
    applyList: [],
    recordList: [],
    loading: false,
    pagination: {
      current: 1,
      pageSize: 10,
      total: 0
    },
    searchParams: {},
    filterOptions: {
      types: [],
      statuses: []
    }
  }),
  getters: {
    getSealList(): Seal[] {
      return this.sealList;
    },
    getCurrentSeal(): Seal | null {
      return this.currentSeal;
    },
    getApplyList(): SealApply[] {
      return this.applyList;
    },
    getRecordList(): SealRecord[] {
      return this.recordList;
    }
  },
  actions: {
    async fetchSealList() {
      this.loading = true;
      try {
        const { items, total } = await getSealList({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.sealList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchSealDetail(id: string) {
      this.loading = true;
      try {
        this.currentSeal = await getSealDetail(id);
      } finally {
        this.loading = false;
      }
    },
    async fetchApplyList() {
      this.loading = true;
      try {
        this.applyList = await getApplyList(this.searchParams);
      } finally {
        this.loading = false;
      }
    },
    async fetchRecordList() {
      this.loading = true;
      try {
        this.recordList = await getSealRecords(this.searchParams);
      } finally {
        this.loading = false;
      }
    }
  }
});
```

## 7. 使用说明

### 7.1 用印申请流程
1. 进入用印申请页面
2. 填写申请信息
   - 选择印章类型
   - 上传用印文件
   - 填写用印事由
   - 说明用印数量
3. 提交审批
4. 等待审批通过
5. 执行用印
6. 记录用印情况

### 7.2 印章使用规范
1. 使用要求
   - 严格履行审批程序
   - 如实填写用印记录
   - 按规定使用印章
   - 及时归还印章

2. 保管要求
   - 指定专人保管
   - 建立台账记录
   - 定期检查核对
   - 妥善保管印章

### 7.3 注意事项
1. 安全管理
   - 严格使用权限
   - 规范保管制度
   - 及时更新记录
   - 定期安全检查

2. 使用管理
   - 遵守使用规范
   - 及时登记备案
   - 保证文件真实
   - 防止违规使用

3. 监督管理
   - 定期检查核对
   - 及时发现问题
   - 严格责任追究
   - 完善管理制度

## 8. 最佳实践

### 8.1 印章管理
1. 建立完整台账
2. 实施分级管理
3. 定期清查核对
4. 及时更新信息

### 8.2 用印管理
1. 规范申请流程
2. 严格审批制度
3. 完整记录信息
4. 加强使用监督

### 8.3 安全管理
1. 落实保管责任
2. 执行交接制度
3. 加强使用监控
4. 防范使用风险

### 8.4 统计分析
1. 用印情况分析
2. 使用效率评估
3. 风险点识别
4. 持续优化改进 
