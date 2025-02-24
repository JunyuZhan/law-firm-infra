# 合同管理模块

## 1. 功能概述

合同管理模块是律师事务所管理系统的重要组成部分，用于管理律所与客户之间的各类合同，包括委托合同、服务合同等，提供合同全生命周期管理功能。

## 2. 页面结构

```bash
views/contract/
├── list/                # 合同列表页
│   ├── index.vue       # 列表主页面
│   └── components/     # 列表页组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableColumns.vue    # 表格列配置
│       ├── ContractModal.vue   # 合同表单
│       └── BatchActions.vue    # 批量操作组件
│
├── detail/             # 合同详情页
│   ├── index.vue      # 详情主页面
│   └── components/    # 详情页组件
│       ├── BasicInfo.vue       # 基本信息
│       ├── ContractContent.vue # 合同内容
│       ├── Attachments.vue     # 附件管理
│       ├── SignInfo.vue        # 签署信息
│       ├── RelatedCase.vue     # 关联案件
│       ├── Timeline.vue        # 操作时间线
│       └── Comments.vue        # 批注意见
│
├── template/          # 合同模板
│   ├── index.vue     # 模板列表
│   └── components/   # 模板组件
│       ├── TemplateForm.vue    # 模板表单
│       ├── TemplateEditor.vue  # 模板编辑器
│       └── TemplatePreview.vue # 模板预览
│
└── components/        # 公共组件
    ├── ContractType.vue       # 合同类型
    ├── ContractStatus.vue     # 合同状态
    └── ContractVersion.vue    # 版本管理
```

## 3. 功能清单

### 3.1 合同管理
- [x] 合同列表
  - 合同查询
    - 合同编号
    - 合同名称
    - 合同类型
    - 签署状态
    - 生效状态
    - 签署时间
    - 到期时间
  - 合同新增
  - 合同编辑
  - 合同删除
  - 批量操作
  - 合同导入导出

- [x] 合同详情
  - 基本信息
    - 合同编号
    - 合同名称
    - 合同类型
    - 签署主体
    - 签署日期
    - 生效日期
    - 到期日期
    - 合同金额
    - 合同状态
  - 合同内容
    - 在线预览
    - 在线编辑
    - 版本管理
    - 变更记录
  - 附件管理
    - 附件上传
    - 附件预览
    - 附件下载
    - 附件删除
  - 签署信息
    - 签署流程
    - 签署记录
    - 签署文件
    - 签署日志
  - 关联信息
    - 关联案件
    - 关联客户
    - 关联文档
    - 关联记录

### 3.2 合同模板
- [x] 模板管理
  - 模板分类
  - 模板新增
  - 模板编辑
  - 模板删除
  - 模板导入导出
  
- [x] 模板编辑
  - 内容编辑
  - 变量设置
  - 格式设置
  - 版本管理
  - 权限控制

- [x] 模板应用
  - 模板选择
  - 变量填充
  - 内容预览
  - 批量生成

### 3.3 合同审批
- [x] 审批流程
  - 审批配置
  - 审批节点
  - 审批角色
  - 审批规则
  
- [x] 审批管理
  - 待审批
  - 已审批
  - 审批记录
  - 审批统计

### 3.4 合同监控
- [x] 到期提醒
  - 即将到期
  - 已经到期
  - 提醒设置
  - 提醒记录

- [x] 履约监控
  - 履约进度
  - 付款节点
  - 履约评估
  - 风险预警

## 4. API 接口

### 4.1 数据结构
```typescript
// 合同基本信息
interface Contract {
  id: string;                 // 合同ID
  contractNo: string;         // 合同编号
  name: string;              // 合同名称
  type: ContractType;        // 合同类型
  status: ContractStatus;    // 合同状态
  signStatus: SignStatus;    // 签署状态
  amount: number;            // 合同金额
  partyA: string;           // 甲方
  partyB: string;           // 乙方
  signDate: string;         // 签署日期
  effectiveDate: string;    // 生效日期
  expiryDate: string;       // 到期日期
  content: string;          // 合同内容
  attachments: string[];    // 附件列表
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}

// 合同类型
enum ContractType {
  DELEGATION = 'delegation',    // 委托合同
  SERVICE = 'service',         // 服务合同
  CONSULTATION = 'consultation',// 咨询合同
  COOPERATION = 'cooperation'  // 合作合同
}

// 合同状态
enum ContractStatus {
  DRAFT = 'draft',           // 草稿
  PENDING = 'pending',       // 待审批
  ACTIVE = 'active',         // 生效
  EXPIRED = 'expired',       // 过期
  TERMINATED = 'terminated'  // 终止
}

// 签署状态
enum SignStatus {
  UNSIGNED = 'unsigned',     // 未签署
  SIGNING = 'signing',       // 签署中
  SIGNED = 'signed',        // 已签署
  REJECTED = 'rejected'     // 已拒签
}

// 合同模板
interface ContractTemplate {
  id: string;              // 模板ID
  name: string;           // 模板名称
  type: ContractType;     // 合同类型
  content: string;        // 模板内容
  variables: Variable[];  // 模板变量
  version: string;        // 版本号
  status: number;         // 状态
  createTime: string;     // 创建时间
  updateTime: string;     // 更新时间
}

// 审批记录
interface ApprovalRecord {
  id: string;             // 记录ID
  contractId: string;     // 合同ID
  node: string;          // 审批节点
  approver: string;      // 审批人
  status: ApprovalStatus;// 审批状态
  opinion: string;       // 审批意见
  createTime: string;    // 审批时间
}
```

### 4.2 接口列表
```typescript
// 获取合同列表
export const getContractList = (params: ContractListParams) => {
  return http.get('/contract/list', { params });
};

// 获取合同详情
export const getContractDetail = (id: string) => {
  return http.get(`/contract/detail/${id}`);
};

// 创建合同
export const createContract = (data: Partial<Contract>) => {
  return http.post('/contract/create', data);
};

// 更新合同
export const updateContract = (id: string, data: Partial<Contract>) => {
  return http.put(`/contract/update/${id}`, data);
};

// 删除合同
export const deleteContract = (id: string) => {
  return http.delete(`/contract/delete/${id}`);
};

// 获取合同模板列表
export const getTemplateList = (params: TemplateListParams) => {
  return http.get('/contract/template/list', { params });
};

// 创建合同模板
export const createTemplate = (data: Partial<ContractTemplate>) => {
  return http.post('/contract/template/create', data);
};

// 提交审批
export const submitApproval = (id: string) => {
  return http.post(`/contract/approval/submit/${id}`);
};

// 审批操作
export const approve = (id: string, data: ApprovalParams) => {
  return http.post(`/contract/approval/${id}`, data);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能模块     | 管理员   | 律所主任   | 合伙人      | 执业律师      | 实习律师   | 行政人员    |
|------------|----------|------------|------------|--------------|------------|------------|
| 查看合同     | ✓全所    | ✓全所      | ✓团队      | ✓个人+团队    | ✓个人/只读  | ✓部门相关   |
| 创建合同     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | -          |
| 编辑合同     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | -          |
| 删除合同     | ✓全所    | ✓全所      | ✓团队      | -            | -          | -          |
| 审批合同     | ✓全所    | ✓全所      | ✓团队      | -            | -          | ✓部门相关   |
| 归档合同     | ✓全所    | ✓全所      | ✓团队      | -            | -          | ✓部门相关   |
| 导出合同     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | ✓部门相关   |
| 模板管理     | ✓全所    | ✓全所      | ✓团队      | -            | -          | -          |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任：可查看和管理所有合同
   - 合伙人：可查看和管理本团队的合同
   - 执业律师：可查看个人创建的合同和团队共享的合同
   - 实习律师：仅可查看被分配的合同
   - 行政人员：可查看与本部门相关的合同

2. 特殊权限说明
   - 合同创建：执业律师可以创建合同，但需要上级审批
   - 合同编辑：执业律师只能编辑草稿状态的合同
   - 合同删除：仅管理员和律所主任有权限
   - 合同审批：需要按照审批流程进行
   - 模板管理：仅高级角色可以管理模板

3. 审批流程权限
   - 一般合同：团队负责人审批
   - 重要合同：需要律所主任审批
   - 特殊合同：需要管理员审批
   - 财务相关：需要财务部门会签

### 5.3 路由配置
```typescript
// router/modules/contract.ts
export default {
  path: '/contract',
  name: 'Contract',
  component: LAYOUT,
  meta: {
    title: '合同管理',
    icon: 'ant-design:file-text-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'finance']
  },
  children: [
    {
      path: 'list',
      name: 'ContractList',
      component: () => import('@/views/contract/list/index.vue'),
      meta: {
        title: '合同列表'
      }
    },
    {
      path: 'detail/:id',
      name: 'ContractDetail',
      component: () => import('@/views/contract/detail/index.vue'),
      meta: {
        title: '合同详情',
        hideMenu: true
      }
    },
    {
      path: 'template',
      name: 'ContractTemplate',
      component: () => import('@/views/contract/template/index.vue'),
      meta: {
        title: '合同模板',
        roles: ['admin', 'director', 'partner']
      }
    }
  ]
};
```

## 6. 状态管理

### 6.1 Store 结构
```typescript
// store/modules/contract.ts
interface ContractState {
  contractList: Contract[];
  currentContract: Contract | null;
  templateList: ContractTemplate[];
  loading: boolean;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
  filterOptions: {
    types: ContractType[];
    statuses: ContractStatus[];
    signStatuses: SignStatus[];
  };
}

const useContractStore = defineStore({
  id: 'app-contract',
  state: (): ContractState => ({
    contractList: [],
    currentContract: null,
    templateList: [],
    loading: false,
    pagination: {
      current: 1,
      pageSize: 10,
      total: 0
    },
    searchParams: {},
    filterOptions: {
      types: [],
      statuses: [],
      signStatuses: []
    }
  }),
  getters: {
    getContractList(): Contract[] {
      return this.contractList;
    },
    getCurrentContract(): Contract | null {
      return this.currentContract;
    },
    getTemplateList(): ContractTemplate[] {
      return this.templateList;
    }
  },
  actions: {
    async fetchContractList() {
      this.loading = true;
      try {
        const { items, total } = await getContractList({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.contractList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchContractDetail(id: string) {
      this.loading = true;
      try {
        this.currentContract = await getContractDetail(id);
      } finally {
        this.loading = false;
      }
    },
    async fetchTemplateList() {
      this.loading = true;
      try {
        const { items } = await getTemplateList();
        this.templateList = items;
      } finally {
        this.loading = false;
      }
    }
  }
});
```

## 7. 使用说明

### 7.1 合同创建流程
1. 进入合同列表页面
2. 点击"新建合同"按钮
3. 选择合同类型和模板
4. 填写基本信息
   - 合同名称
   - 签署主体
   - 合同金额
   - 合同期限
5. 编辑合同内容
6. 上传附件材料
7. 设置审批流程
8. 提交审批

### 7.2 合同审批流程
1. 提交审批
   - 选择审批流程
   - 填写申请说明
   - 上传相关材料
2. 审批处理
   - 审批人审查
   - 提出修改意见
   - 会签相关部门
   - 审批结果确认
3. 签署执行
   - 合同盖章
   - 签署确认
   - 文件归档
   - 状态更新

### 7.3 合同监控
1. 合同履约
   - 履约进度跟踪
   - 付款节点提醒
   - 重要事项提醒
   - 风险预警通知

2. 到期管理
   - 到期预警提醒
   - 续签评估建议
   - 终止流程处理
   - 档案归档管理

### 7.4 注意事项
1. 合同安全
   - 严格执行权限控制
   - 敏感信息加密
   - 操作日志记录
   - 定期安全审计

2. 数据备份
   - 定期数据备份
   - 版本管理
   - 历史记录保存
   - 应急恢复预案

3. 规范要求
   - 遵守合同法规
   - 使用标准模板
   - 及时更新状态
   - 保持信息完整

## 8. 最佳实践

### 8.1 合同起草
1. 使用标准模板
2. 仔细核对信息
3. 明确合同条款
4. 注意格式规范

### 8.2 合同审查
1. 合规性审查
2. 风险点识别
3. 条款完整性
4. 文字准确性

### 8.3 合同管理
1. 分类存档管理
2. 定期状态更新
3. 及时跟踪进度
4. 做好变更记录

### 8.4 风险防控
1. 预防性审查
2. 动态风险评估
3. 应急处理预案
4. 纠纷解决机制
