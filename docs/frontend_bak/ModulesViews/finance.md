# 财务管理模块

## 1. 功能概述

财务管理模块是律师事务所管理系统的核心支撑模块，用于管理律所的各项财务事务，包括收支管理、费用报销、发票管理、工资管理等，为律所的财务运营提供全面的解决方案。

### 1.1 主要功能

- 收支管理
  - 收入管理
  - 支出管理
  - 账户管理
  - 预算管理
  - 财务报表

- 费用报销
  - 报销申请
  - 审批流程
  - 报销记录
  - 预算控制
  - 统计分析

- 发票管理
  - 发票开具
  - 发票接收
  - 发票归档
  - 发票查询
  - 发票统计

- 工资管理
  - 工资核算
  - 工资发放
  - 社保公积金
  - 个税申报
  - 工资单管理

### 1.2 应用场景

1. 案件收费管理
   - 收费标准设置
   - 费用计算
   - 收款管理
   - 账单生成
   - 收据开具

2. 日常费用管理
   - 办公费用
   - 差旅费用
   - 会议费用
   - 培训费用
   - 福利费用

3. 财务分析
   - 收支分析
   - 预算执行
   - 费用统计
   - 业绩分析
   - 财务报告

## 2. 页面结构

```bash
views/finance/
├── income/              # 收入管理
│   ├── index.vue       # 收入列表
│   └── components/     # 收入组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableList.vue       # 表格列表
│       ├── BillForm.vue        # 账单表单
│       └── Statistics.vue      # 统计分析
│
├── expense/            # 支出管理
│   ├── index.vue      # 支出列表
│   └── components/    # 支出组件
│       ├── ExpenseForm.vue     # 支出表单
│       ├── ApprovalFlow.vue    # 审批流程
│       ├── Budget.vue          # 预算控制
│       └── Report.vue          # 报表统计
│
├── reimbursement/     # 报销管理
│   ├── index.vue     # 报销列表
│   └── components/   # 报销组件
│       ├── ClaimForm.vue       # 报销表单
│       ├── Approval.vue        # 审批处理
│       ├── Record.vue          # 报销记录
│       └── Summary.vue         # 汇总统计
│
├── invoice/           # 发票管理
│   ├── index.vue     # 发票列表
│   └── components/   # 发票组件
│       ├── InvoiceForm.vue     # 发票表单
│       ├── Issuance.vue        # 开票管理
│       ├── Receipt.vue         # 收票管理
│       └── Archive.vue         # 归档管理
│
├── salary/            # 工资管理
│   ├── index.vue     # 工资列表
│   └── components/   # 工资组件
│       ├── PayrollForm.vue     # 工资表单
│       ├── Calculator.vue      # 工资计算
│       ├── Statement.vue       # 工资单
│       └── Tax.vue             # 税收管理
│
└── components/        # 公共组件
    ├── AccountSelect.vue      # 账户选择
    ├── CategorySelect.vue     # 类别选择
    └── AmountInput.vue        # 金额输入
```

## 3. 功能清单

### 3.1 收支管理
- [x] 收入管理
  - 收入登记
  - 收款确认
  - 收入分类
  - 收入统计
  - 账单管理

- [x] 支出管理
  - 支出登记
  - 付款审批
  - 支出分类
  - 支出统计
  - 预算控制

### 3.2 费用报销
- [x] 报销申请
  - 申请提交
  - 单据上传
  - 金额核算
  - 预算检查
  - 审批流程

- [x] 报销处理
  - 审批管理
  - 付款处理
  - 记录归档
  - 统计分析
  - 预算跟踪

### 3.3 发票管理
- [x] 发票开具
  - 开票申请
  - 发票信息
  - 开票记录
  - 发票邮寄
  - 发票查询

- [x] 发票接收
  - 发票登记
  - 发票验证
  - 发票入账
  - 发票归档
  - 发票统计

### 3.4 工资管理
- [x] 工资核算
  - 基本工资
  - 绩效工资
  - 奖金提成
  - 社保公积金
  - 个税计算

- [x] 工资发放
  - 工资单生成
  - 发放审批
  - 银行代发
  - 发放记录
  - 工资条推送

## 4. API 接口

### 4.1 数据结构
```typescript
// 收支记录
interface Transaction {
  id: string;                 // 记录ID
  type: TransactionType;      // 类型（收入/支出）
  amount: number;             // 金额
  category: string;           // 类别
  date: string;              // 日期
  description: string;       // 说明
  accountId: string;         // 账户ID
  status: TransactionStatus; // 状态
  createTime: string;        // 创建时间
  updateTime: string;        // 更新时间
}

// 报销申请
interface Reimbursement {
  id: string;                // 申请ID
  employeeId: string;        // 申请人ID
  type: ExpenseType;         // 费用类型
  amount: number;            // 金额
  invoices: string[];        // 发票列表
  description: string;       // 说明
  status: ApprovalStatus;    // 审批状态
  createTime: string;        // 创建时间
  updateTime: string;        // 更新时间
}

// 发票信息
interface Invoice {
  id: string;               // 发票ID
  number: string;           // 发票号码
  type: InvoiceType;        // 发票类型
  amount: number;           // 金额
  taxRate: number;          // 税率
  issueDate: string;        // 开票日期
  status: InvoiceStatus;    // 状态
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}

// 工资记录
interface Salary {
  id: string;               // 工资ID
  employeeId: string;       // 员工ID
  month: string;            // 工资月份
  basicSalary: number;      // 基本工资
  performance: number;      // 绩效工资
  bonus: number;            // 奖金
  deduction: number;        // 扣除项
  tax: number;              // 个税
  actualAmount: number;     // 实发金额
  status: PaymentStatus;    // 发放状态
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}
```

### 4.2 接口列表
```typescript
// 获取收支列表
export const getTransactionList = (params: TransactionListParams) => {
  return http.get('/finance/transaction/list', { params });
};

// 创建收支记录
export const createTransaction = (data: Partial<Transaction>) => {
  return http.post('/finance/transaction/create', data);
};

// 提交报销申请
export const submitReimbursement = (data: Partial<Reimbursement>) => {
  return http.post('/finance/reimbursement/submit', data);
};

// 审批报销
export const approveReimbursement = (id: string, data: ApprovalResult) => {
  return http.post(`/finance/reimbursement/approve/${id}`, data);
};

// 开具发票
export const issueInvoice = (data: Partial<Invoice>) => {
  return http.post('/finance/invoice/issue', data);
};

// 登记发票
export const registerInvoice = (data: Partial<Invoice>) => {
  return http.post('/finance/invoice/register', data);
};

// 计算工资
export const calculateSalary = (params: SalaryCalculateParams) => {
  return http.post('/finance/salary/calculate', params);
};

// 发放工资
export const paySalary = (data: SalaryPaymentParams) => {
  return http.post('/finance/salary/pay', data);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能模块     | 管理员   | 律所主任   | 合伙人      | 执业律师      | 实习律师   | 行政人员    |
|------------|----------|------------|------------|--------------|------------|------------|
| 收支管理     | ✓全所    | ✓全所/审批  | ✓团队/只读  | ✓个人/只读    | -          | ✓部门全权   |
| 费用报销     | ✓全所    | ✓全所/审批  | ✓团队/审批  | ✓个人        | ✓个人      | ✓部门全权   |
| 发票管理     | ✓全所    | ✓全所      | ✓团队/只读  | ✓个人/只读    | -          | ✓部门全权   |
| 工资管理     | ✓全所    | ✓全所/只读  | ✓团队/只读  | ✓个人/只读    | ✓个人/只读  | ✓部门全权   |
| 预算管理     | ✓全所    | ✓全所      | ✓团队/只读  | -            | -          | ✓部门全权   |
| 财务报表     | ✓全所    | ✓全所      | ✓团队/只读  | -            | -          | ✓部门全权   |
| 账户管理     | ✓全所    | ✓全所      | -          | -            | -          | ✓部门全权   |
| 税务管理     | ✓全所    | ✓全所      | -          | -            | -          | ✓部门全权   |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员：可查看和管理所有财务数据
   - 律所主任：可查看所有财务数据，负责重要财务审批
   - 合伙人：可查看团队财务数据，参与团队费用审批
   - 执业律师：可查看个人相关的财务数据
   - 实习律师：仅可查看个人工资和报销数据
   - 行政/财务人员：负责日常财务操作，对本部门数据有完整操作权限

2. 特殊权限说明
   - 收支管理：仅财务人员可以进行日常操作
   - 费用报销：所有人可以提交报销申请，需要按级别审批
   - 发票管理：财务人员负责开具和管理发票
   - 工资管理：仅财务人员可以操作，其他人只能查看个人工资
   - 预算管理：管理层和财务部门共同管理
   - 财务报表：管理层和财务部门可以查看

3. 审批流程权限
   - 一般报销：部门主管审批
   - 大额报销：需要律所主任审批
   - 预算调整：需要管理员审批
   - 工资发放：需要多级审批

### 5.3 路由配置
```typescript
// router/modules/finance.ts
export default {
  path: '/finance',
  name: 'Finance',
  component: LAYOUT,
  meta: {
    title: '财务管理',
    icon: 'ant-design:money-collect-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_finance']
  },
  children: [
    {
      path: 'transaction',
      name: 'Transaction',
      component: () => import('@/views/finance/transaction/index.vue'),
      meta: {
        title: '收支管理',
        roles: ['admin', 'director', 'admin_finance']
      }
    },
    {
      path: 'reimbursement',
      name: 'Reimbursement',
      component: () => import('@/views/finance/reimbursement/index.vue'),
      meta: {
        title: '费用报销'
      }
    },
    {
      path: 'invoice',
      name: 'Invoice',
      component: () => import('@/views/finance/invoice/index.vue'),
      meta: {
        title: '发票管理',
        roles: ['admin', 'director', 'admin_finance']
      }
    },
    {
      path: 'salary',
      name: 'Salary',
      component: () => import('@/views/finance/salary/index.vue'),
      meta: {
        title: '工资管理',
        roles: ['admin', 'director', 'admin_finance']
      }
    }
  ]
};
```

## 6. 使用说明

### 6.1 费用报销流程
1. 提交报销申请
   - 选择报销类型
   - 填写报销金额
   - 上传报销凭证
   - 填写报销说明
2. 审批流程
   - 部门主管审批
   - 财务审核
   - 金额判断是否需要更高级别审批
3. 报销处理
   - 财务复核单据
   - 登记报销记录
   - 安排付款
   - 归档凭证

### 6.2 工资发放流程
1. 工资数据准备
   - 基本工资核算
   - 绩效奖金计算
   - 考勤数据统计
   - 个税计算
2. 工资审核
   - 部门复核
   - 财务审核
   - 主管审批
3. 工资发放
   - 生成工资单
   - 银行发放
   - 推送工资条
   - 归档记录

### 6.3 发票管理流程
1. 开票申请
   - 提交开票信息
   - 财务审核
   - 开具发票
   - 发票交付
2. 发票接收
   - 验证发票
   - 登记入账
   - 扫描存档
   - 统计汇总

## 7. 注意事项

1. 数据安全
   - 严格控制财务数据访问权限
   - 定期备份财务数据
   - 记录操作日志
   - 加密敏感信息

2. 合规要求
   - 遵守财务法规
   - 执行内部制度
   - 保持凭证完整
   - 定期对账核查

3. 系统集成
   - 与银行系统对接
   - 与税务系统集成
   - 与人事系统联动
   - 与案件系统关联

4. 数据统计
   - 定期生成报表
   - 分析财务指标
   - 预算执行跟踪
   - 成本收益分析 
