# 利益冲突检查模块

## 1. 功能概述

利益冲突检查模块是律师事务所管理系统中的重要风险控制模块，用于在承接新业务时进行利益冲突检查，防止因利益冲突而产生的职业风险。本模块提供全面的冲突检查、风险评估和预警功能。

## 2. 页面结构

```bash
views/conflict/
├── check/                # 冲突检查
│   ├── index.vue        # 检查主页面
│   └── components/      # 检查组件
│       ├── CheckForm.vue       # 检查表单
│       ├── ResultList.vue      # 结果列表
│       ├── DetailModal.vue     # 详情弹窗
│       └── ReportView.vue      # 报告查看
│
├── record/              # 检查记录
│   ├── index.vue       # 记录页面
│   └── components/     # 记录组件
│       ├── RecordList.vue      # 记录列表
│       ├── RecordDetail.vue    # 记录详情
│       └── ExportReport.vue    # 导出报告
│
├── rules/               # 规则配置
│   ├── index.vue       # 规则页面
│   └── components/     # 规则组件
│       ├── RuleList.vue        # 规则列表
│       ├── RuleForm.vue        # 规则表单
│       └── RuleTest.vue        # 规则测试
│
└── components/         # 公共组件
    ├── ConflictType.vue       # 冲突类型
    ├── RiskLevel.vue         # 风险等级
    └── ApprovalFlow.vue      # 审批流程
```

## 3. 功能清单

### 3.1 冲突检查
- [x] 基础信息检查
  - 当事人信息
  - 对方当事人
  - 案件信息
  - 关联方信息
  
- [x] 高级检查
  - 历史业务关系
  - 关联企业检查
  - 关键人员检查
  - 竞业限制检查

- [x] 检查结果
  - 风险等级评估
  - 冲突详情说明
  - 处理建议
  - 审批流程

### 3.2 检查记录
- [x] 记录管理
  - 检查历史
  - 处理结果
  - 审批记录
  - 导出报告

- [x] 统计分析
  - 冲突类型统计
  - 风险等级分布
  - 处理结果分析
  - 趋势分析

### 3.3 规则配置
- [x] 规则管理
  - 冲突规则配置
  - 风险等级定义
  - 审批流程设置
  - 预警规则设置

## 4. API 接口

### 4.1 数据结构
```typescript
// 冲突检查
interface ConflictCheck {
  id: string;                 // 检查ID
  checkNo: string;           // 检查编号
  type: ConflictType;        // 检查类型
  status: CheckStatus;       // 检查状态
  submitter: string;         // 提交人
  checkTime: string;         // 检查时间
  result: CheckResult;       // 检查结果
  riskLevel: RiskLevel;      // 风险等级
  approvalStatus: string;    // 审批状态
  createTime: string;        // 创建时间
  updateTime: string;        // 更新时间
}

// 检查对象
interface CheckTarget {
  id: string;               // 对象ID
  name: string;            // 对象名称
  type: TargetType;        // 对象类型
  idNumber: string;        // 证件号码
  contact: string;         // 联系方式
  address: string;         // 地址
  relationship: string;    // 关系说明
  relatedParties: string[]; // 关联方
}

// 检查规则
interface ConflictRule {
  id: string;              // 规则ID
  name: string;           // 规则名称
  type: RuleType;         // 规则类型
  content: string;        // 规则内容
  riskLevel: RiskLevel;   // 风险等级
  status: boolean;        // 启用状态
  creator: string;        // 创建人
  createTime: string;     // 创建时间
  updateTime: string;     // 更新时间
}

// 检查结果
interface CheckResult {
  hasConflict: boolean;   // 是否存在冲突
  conflictType: string[]; // 冲突类型
  riskLevel: RiskLevel;   // 风险等级
  details: string;        // 详细说明
  suggestion: string;     // 处理建议
  relatedCases: string[]; // 关联案件
  relatedClients: string[]; // 关联客户
}

// 风险等级
enum RiskLevel {
  HIGH = 'high',         // 高风险
  MEDIUM = 'medium',     // 中风险
  LOW = 'low',          // 低风险
  NONE = 'none'         // 无风险
}

// 检查状态
enum CheckStatus {
  PENDING = 'pending',   // 待检查
  CHECKING = 'checking', // 检查中
  COMPLETED = 'completed',// 已完成
  APPROVED = 'approved', // 已审批
  REJECTED = 'rejected'  // 已拒绝
}
```

### 4.2 接口列表
```typescript
// 提交检查
export const submitCheck = (data: Partial<ConflictCheck>) => {
  return http.post('/conflict/check/submit', data);
};

// 获取检查结果
export const getCheckResult = (id: string) => {
  return http.get(`/conflict/check/result/${id}`);
};

// 获取检查记录
export const getCheckRecords = (params: CheckRecordQuery) => {
  return http.get('/conflict/check/records', { params });
};

// 获取检查详情
export const getCheckDetail = (id: string) => {
  return http.get(`/conflict/check/detail/${id}`);
};

// 审批检查
export const approveCheck = (id: string, data: ApprovalParams) => {
  return http.post(`/conflict/check/approve/${id}`, data);
};

// 获取规则列表
export const getRuleList = (params: RuleQuery) => {
  return http.get('/conflict/rules', { params });
};

// 创建规则
export const createRule = (data: Partial<ConflictRule>) => {
  return http.post('/conflict/rules/create', data);
};

// 更新规则
export const updateRule = (id: string, data: Partial<ConflictRule>) => {
  return http.put(`/conflict/rules/${id}`, data);
};

// 删除规则
export const deleteRule = (id: string) => {
  return http.delete(`/conflict/rules/${id}`);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能         | 管理员 | 律所主任 | 合伙人 | 执业律师 | 实习律师 | 行政人员 |
|------------|--------|----------|--------|----------|----------|----------|
| 发起检查     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | -        |
| 查看结果     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | -        |
| 审批检查     | ✓      | ✓        | ✓团队   | -        | -        | -        |
| 规则配置     | ✓      | ✓        | -      | -        | -        | -        |
| 导出报告     | ✓      | ✓        | ✓团队   | ✓个人    | -        | -        |

### 5.2 数据权限说明

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任：可查看和管理所有检查记录
   - 合伙人：可查看和管理本团队的检查记录
   - 执业律师：可查看个人发起的检查记录
   - 实习律师：无权限访问
   - 行政人员：无权限访问

2. 特殊权限说明
   - 检查发起：执业律师可以发起检查，但需要上级审批
   - 结果查看：仅相关人员可以查看检查结果
   - 规则配置：仅管理员和律所主任可以配置
   - 报告导出：需要相应的数据权限

3. 审批流程权限
   - 一般检查：团队负责人审批
   - 重要检查：需要律所主任审批
   - 特殊检查：需要管理员审批

### 5.3 路由配置
```typescript
// router/modules/conflict.ts
export default {
  path: '/conflict',
  name: 'Conflict',
  component: LAYOUT,
  meta: {
    title: '利益冲突',
    icon: 'ant-design:alert-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer']
  },
  children: [
    {
      path: 'check',
      name: 'ConflictCheck',
      component: () => import('@/views/conflict/check/index.vue'),
      meta: {
        title: '冲突检查'
      }
    },
    {
      path: 'record',
      name: 'ConflictRecord',
      component: () => import('@/views/conflict/record/index.vue'),
      meta: {
        title: '检查记录'
      }
    },
    {
      path: 'rules',
      name: 'ConflictRules',
      component: () => import('@/views/conflict/rules/index.vue'),
      meta: {
        title: '规则配置',
        roles: ['admin', 'director']
      }
    }
  ]
};
```

## 6. 状态管理

### 6.1 Store 结构
```typescript
// store/modules/conflict.ts
interface ConflictState {
  checkList: ConflictCheck[];
  currentCheck: ConflictCheck | null;
  ruleList: ConflictRule[];
  loading: boolean;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
  filterOptions: {
    types: string[];
    statuses: string[];
    riskLevels: string[];
  };
}

const useConflictStore = defineStore({
  id: 'app-conflict',
  state: (): ConflictState => ({
    checkList: [],
    currentCheck: null,
    ruleList: [],
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
      riskLevels: []
    }
  }),
  getters: {
    getCheckList(): ConflictCheck[] {
      return this.checkList;
    },
    getCurrentCheck(): ConflictCheck | null {
      return this.currentCheck;
    },
    getRuleList(): ConflictRule[] {
      return this.ruleList;
    }
  },
  actions: {
    async fetchCheckList() {
      this.loading = true;
      try {
        const { items, total } = await getCheckRecords({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.checkList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchCheckDetail(id: string) {
      this.loading = true;
      try {
        this.currentCheck = await getCheckDetail(id);
      } finally {
        this.loading = false;
      }
    },
    async fetchRuleList() {
      this.loading = true;
      try {
        const { items } = await getRuleList();
        this.ruleList = items;
      } finally {
        this.loading = false;
      }
    }
  }
});
```

## 7. 使用说明

### 7.1 冲突检查流程
1. 进入冲突检查页面
2. 填写检查信息
   - 选择检查类型
   - 填写当事人信息
   - 填写对方当事人
   - 填写案件信息
3. 提交检查申请
4. 系统自动检查
5. 生成检查报告
6. 提交审批流程

### 7.2 规则配置说明
1. 规则类型
   - 客户关系规则
   - 案件关联规则
   - 人员关系规则
   - 时间限制规则

2. 风险等级
   - 高风险：存在直接利益冲突
   - 中风险：存在潜在利益冲突
   - 低风险：存在间接关联
   - 无风险：无利益冲突

3. 规则维护
   - 定期更新规则
   - 测试规则有效性
   - 记录规则变更
   - 评估规则效果

### 7.3 注意事项
1. 检查要求
   - 及时进行检查
   - 信息填写完整
   - 保存检查记录
   - 遵循审批流程

2. 数据安全
   - 信息保密处理
   - 权限严格控制
   - 操作留痕记录
   - 敏感信息加密

3. 风险防控
   - 定期复查机制
   - 动态风险评估
   - 预警信息处理
   - 应急处理预案

## 8. 最佳实践

### 8.1 检查准备
1. 收集完整信息
2. 确认信息准确性
3. 识别关键要素
4. 预判潜在风险

### 8.2 检查执行
1. 严格执行规则
2. 全面信息核查
3. 及时记录结果
4. 规范处理流程

### 8.3 结果处理
1. 准确评估风险
2. 提供处理建议
3. 跟踪处理结果
4. 总结经验教训

### 8.4 持续改进
1. 规则优化更新
2. 流程持续改进
3. 效果定期评估
4. 系统功能完善 
