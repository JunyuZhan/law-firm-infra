# 案件管理模块

## 1. 功能概述

案件管理模块是律师事务所管理系统的核心业务模块，用于管理律所的所有案件信息，包括案件基本信息、案件进展、文档管理、费用管理等。本模块致力于提供完整的案件生命周期管理，帮助律师高效处理案件，提升工作效率。

## 2. 页面结构

```bash
views/case/
├── list/                # 案件列表
│   ├── index.vue       # 列表页面
│   └── components/     # 列表组件
│       ├── SearchForm.vue       # 搜索表单
│       ├── TableList.vue        # 表格列表
│       ├── FilterPanel.vue      # 筛选面板
│       └── BatchActions.vue     # 批量操作
│
├── detail/             # 案件详情
│   ├── index.vue      # 详情页面
│   └── components/    # 详情组件
│       ├── BasicInfo.vue        # 基本信息
│       ├── ClientInfo.vue       # 客户信息
│       ├── TeamInfo.vue         # 团队信息
│       ├── Timeline.vue         # 时间轴
│       └── Documents.vue        # 相关文档
│
├── create/            # 新建案件
│   ├── index.vue     # 创建页面
│   └── components/   # 创建组件
│       ├── StepForm.vue         # 分步表单
│       ├── BasicForm.vue        # 基本表单
│       ├── ClientForm.vue       # 客户表单
│       └── TeamForm.vue         # 团队表单
│
├── progress/         # 案件进展
│   ├── index.vue    # 进展页面
│   └── components/  # 进展组件
│       ├── ProgressList.vue     # 进展列表
│       ├── AddProgress.vue      # 添加进展
│       ├── Evidence.vue         # 证据材料
│       └── Court.vue            # 庭审信息
│
└── settings/        # 案件设置
    ├── index.vue   # 设置页面
    └── components/ # 设置组件
        ├── TypeConfig.vue       # 类型配置
        ├── StatusConfig.vue     # 状态配置
        ├── Template.vue         # 模板配置
        └── Workflow.vue         # 流程配置
```

## 3. 功能清单

### 3.1 案件管理
- [x] 案件列表
  - 高级搜索
  - 案件编号
    - 案件名称
  - 案件类型
  - 承办律师
  - 委托人
    - 立案日期
    - 案件状态
  - 批量操作
    - 批量分配
  - 批量导出
  - 批量归档
  - 列表展示
    - 自定义列
    - 排序筛选
    - 状态标记
    
- [x] 案件详情
  - 基本信息
    - 案件编号
    - 案件名称
    - 案件类型
    - 案件描述
    - 立案日期
    - 结案日期
    - 案件状态
  - 当事人信息
    - 委托人信息
  - 对方当事人
    - 其他相关方
  - 承办信息
    - 主办律师
    - 协办律师
    - 助理人员
    - 专家顾问
  - 案件进展
    - 进展记录
  - 重要节点
    - 下一步计划
  - 相关文档
    - 证据材料
    - 法律文书
    - 工作底稿

### 3.2 案件创建
- [x] 基本信息录入
  - 案件类型选择
  - 基础信息填写
  - 当事人信息
  - 案件描述

- [x] 团队组建
  - 主办律师指定
  - 协办律师选择
  - 助理分配
  - 角色设置

- [x] 立案流程
  - 利益冲突检查
  - 风险评估
  - 立案审批
  - 合同生成

### 3.3 案件进展
- [x] 进展管理
  - 进展记录
  - 日程安排
  - 提醒设置
  - 文件上传

- [x] 庭审管理
  - 庭审安排
  - 庭审记录
  - 证据整理
  - 材料准备

### 3.4 案件文档
- [x] 文档管理
  - 文档分类
  - 文档上传
  - 版本控制
  - 权限管理

- [x] 材料管理
  - 证据清单
  - 法律文书
  - 工作记录
  - 其他材料

## 4. API 接口

### 4.1 数据结构
```typescript
// 案件信息
interface Case {
  id: string;                 // 案件ID
  caseNo: string;            // 案件编号
  name: string;              // 案件名称
  type: CaseType;            // 案件类型
  status: CaseStatus;        // 案件状态
  description: string;       // 案件描述
  clientId: string;          // 委托人ID
  filingDate: string;        // 立案日期
  endDate: string;          // 结案日期
  mainLawyerId: string;     // 主办律师ID
  teamMembers: string[];    // 团队成员ID
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}

// 案件进展
interface Progress {
  id: string;              // 进展ID
  caseId: string;         // 案件ID
  type: ProgressType;     // 进展类型
  content: string;        // 进展内容
  date: string;          // 进展日期
  attachments: string[]; // 附件列表
  creator: string;       // 创建人
  createTime: string;    // 创建时间
}

// 庭审信息
interface Court {
  id: string;           // 庭审ID
  caseId: string;      // 案件ID
  date: string;        // 庭审日期
  location: string;    // 庭审地点
  judge: string;       // 审判人员
  participants: string[]; // 参与人员
  summary: string;     // 庭审小结
  status: CourtStatus; // 庭审状态
  createTime: string;  // 创建时间
}

// 案件文档
interface Document {
  id: string;          // 文档ID
  caseId: string;     // 案件ID
  name: string;       // 文档名称
  type: DocType;      // 文档类型
  path: string;       // 存储路径
  version: string;    // 版本号
  uploader: string;   // 上传人
  uploadTime: string; // 上传时间
}
```

### 4.2 接口列表
```typescript
// 获取案件列表
export const getCaseList = (params: CaseListParams) => {
  return http.get('/case/list', { params });
};

// 获取案件详情
export const getCaseDetail = (id: string) => {
  return http.get(`/case/${id}`);
};

// 创建案件
export const createCase = (data: Partial<Case>) => {
  return http.post('/case/create', data);
};

// 更新案件
export const updateCase = (id: string, data: Partial<Case>) => {
  return http.put(`/case/${id}`, data);
};

// 删除案件
export const deleteCase = (id: string) => {
  return http.delete(`/case/${id}`);
};

// 添加案件进展
export const addProgress = (data: Partial<Progress>) => {
  return http.post('/case/progress/add', data);
};

// 更新案件进展
export const updateProgress = (id: string, data: Partial<Progress>) => {
  return http.put(`/case/progress/${id}`, data);
};

// 添加庭审信息
export const addCourt = (data: Partial<Court>) => {
  return http.post('/case/court/add', data);
};

// 更新庭审信息
export const updateCourt = (id: string, data: Partial<Court>) => {
  return http.put(`/case/court/${id}`, data);
};

// 上传案件文档
export const uploadDocument = (data: FormData) => {
  return http.post('/case/document/upload', data);
};

// 获取案件文档列表
export const getDocumentList = (params: DocumentListParams) => {
  return http.get('/case/document/list', { params });
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能         | 管理员 | 律所主任 | 合伙人 | 执业律师 | 实习律师 | 行政人员 |
|------------|--------|----------|--------|----------|----------|----------|
| 查看案件列表 | ✓全所  | ✓全所    | ✓团队   | ✓个人    | ✓个人    | ✓部门    |
| 查看案件详情 | ✓全所  | ✓全所    | ✓团队   | ✓个人    | ✓个人    | ✓部门    |
| 创建案件     | ✓      | ✓        | ✓      | -        | -        | -        |
| 编辑案件     | ✓      | ✓        | ✓      | ✓个人    | -        | -        |
| 删除案件     | ✓      | -        | -      | -        | -        | -        |
| 分配案件     | ✓      | ✓        | ✓团队   | -        | -        | -        |
| 添加进展     | ✓      | ✓        | ✓      | ✓        | ✓        | -        |
| 编辑进展     | ✓      | ✓        | ✓      | ✓        | -        | -        |
| 上传文档     | ✓      | ✓        | ✓      | ✓        | ✓        | -        |
| 删除文档     | ✓      | ✓        | -      | -        | -        | -        |

### 5.2 数据权限说明

### 5.2 数据权限
- 管理员：可查看和管理所有案件
- 主办律师：可查看和管理自己负责的案件
- 协办律师：可查看和编辑参与的案件
- 律师助理：可查看参与的案件，添加案件进展
- 行政人员：可查看案件基本信息

### 5.3 路由配置
```typescript
// router/modules/case.ts
export default {
  path: '/case',
  name: 'Case',
  component: LAYOUT,
  meta: {
    title: '案件管理',
    icon: 'ant-design:profile-outlined',
    orderNo: 2,
  },
  children: [
    {
      path: 'list',
      name: 'CaseList',
      component: () => import('@/views/case/list/index.vue'),
      meta: {
        title: '案件列表'
      }
    },
    {
      path: 'create',
      name: 'CaseCreate',
      component: () => import('@/views/case/create/index.vue'),
      meta: {
        title: '新建案件',
        roles: ['admin', 'lawyer']
      }
    },
    {
      path: 'detail/:id',
      name: 'CaseDetail',
      component: () => import('@/views/case/detail/index.vue'),
      meta: {
        title: '案件详情',
        hideMenu: true
      }
    },
    {
      path: 'progress/:id',
      name: 'CaseProgress',
      component: () => import('@/views/case/progress/index.vue'),
      meta: {
        title: '案件进展',
        hideMenu: true
      }
    }
  ]
};
```

## 6. 状态管理

### 6.1 Store 结构
```typescript
// store/modules/case.ts
interface CaseState {
  caseList: Case[];
  currentCase: Case | null;
  loading: boolean;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
}

const useCaseStore = defineStore({
  id: 'app-case',
  state: (): CaseState => ({
    caseList: [],
    currentCase: null,
    loading: false,
    pagination: {
      current: 1,
      pageSize: 10,
      total: 0
    },
    searchParams: {}
  }),
  getters: {
    getCaseList(): Case[] {
      return this.caseList;
    },
    getCurrentCase(): Case | null {
      return this.currentCase;
    }
  },
  actions: {
    async fetchCaseList() {
      this.loading = true;
      try {
        const { items, total } = await getCaseList({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.caseList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchCaseDetail(id: string) {
      this.loading = true;
      try {
        this.currentCase = await getCaseDetail(id);
      } finally {
        this.loading = false;
      }
    }
  }
});
```

## 7. 使用说明

### 7.1 案件创建流程
1. 进入案件列表页面
2. 点击"新建案件"按钮
3. 填写案件基本信息
   - 选择案件类型
   - 填写案件名称
   - 选择委托人
   - 填写案件描述
4. 配置案件团队
   - 选择主办律师
   - 选择协办律师
   - 分配助理人员
5. 上传初始文档
6. 提交创建

### 7.2 案件进展管理
1. 进入案件详情页面
2. 切换到"进展管理"标签
3. 点击"添加进展"按钮
4. 填写进展信息
   - 选择进展类型
   - 填写进展内容
   - 上传相关附件
5. 提交保存

### 7.3 文档管理
1. 进入案件详情页面
2. 切换到"文档管理"标签
3. 选择文档分类
4. 上传或查看文档
   - 支持批量上传
   - 支持版本控制
   - 支持在线预览

### 7.4 注意事项
1. 案件编号自动生成，不可修改
2. 重要信息修改会记录操作日志
3. 文档上传需注意大小限制
4. 定期备份重要文档
5. 注意保护客户隐私信息
