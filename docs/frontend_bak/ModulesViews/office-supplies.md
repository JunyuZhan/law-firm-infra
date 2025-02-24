# 办公用品管理模块

## 1. 功能概述

办公用品管理模块是律师事务所管理系统的后勤保障模块，用于管理律所日常办公用品的采购、入库、领用、库存等全流程管理，确保办公用品供应的及时性和规范性。

## 2. 页面结构

```bash
views/office-supplies/
├── list/                # 办公用品列表
│   ├── index.vue       # 列表主页面
│   └── components/     # 列表组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableColumns.vue    # 表格列配置
│       ├── SupplyForm.vue      # 用品表单
│       └── SupplyDetail.vue    # 用品详情
│
├── stock/              # 库存管理
│   ├── index.vue      # 库存页面
│   └── components/    # 库存组件
│       ├── StockList.vue       # 库存列表
│       ├── StockIn.vue         # 入库管理
│       ├── StockOut.vue        # 出库管理
│       └── StockCheck.vue      # 库存盘点
│
├── apply/             # 领用申请
│   ├── index.vue     # 申请页面
│   └── components/   # 申请组件
│       ├── ApplyForm.vue       # 申请表单
│       ├── ApplyList.vue       # 申请列表
│       └── Approval.vue        # 审批处理
│
└── components/        # 公共组件
    ├── CategorySelect.vue     # 分类选择
    ├── QuantityInput.vue      # 数量输入
    └── StatusTag.vue          # 状态标签
```

## 3. 功能清单

### 3.1 办公用品管理
- [x] 用品信息
  - 基本信息
    - 用品名称
    - 用品编号
    - 用品分类
    - 规格型号
    - 单位
    - 单价
  - 库存信息
    - 当前库存
    - 最低库存
    - 最高库存
    - 库存位置
  
- [x] 分类管理
  - 分类维护
  - 分类设置
  - 分类统计
  - 分类报表

### 3.2 库存管理
- [x] 入库管理
  - 采购入库
  - 退还入库
  - 调拨入库
  - 盘盈入库

- [x] 出库管理
  - 领用出库
  - 报废出库
  - 调拨出库
  - 盘亏出库

- [x] 库存盘点
  - 盘点计划
  - 盘点执行
  - 盘点差异
  - 盘点报告

### 3.3 领用管理
- [x] 领用申请
  - 在线申请
  - 批量申请
  - 紧急申请
  - 定期申请

- [x] 审批流程
  - 审批配置
  - 审批处理
  - 审批记录
  - 审批统计

### 3.4 采购管理
- [x] 采购计划
  - 需求收集
  - 计划制定
  - 计划审批
  - 执行跟踪

- [x] 供应商管理
  - 供应商信息
  - 供应商评价
  - 价格管理
  - 合作记录

## 4. API 接口

### 4.1 数据结构
```typescript
// 办公用品信息
interface OfficeSupply {
  id: string;                 // 用品ID
  code: string;              // 用品编号
  name: string;              // 用品名称
  category: string;          // 分类
  specification: string;     // 规格型号
  unit: string;             // 单位
  price: number;            // 单价
  minStock: number;         // 最低库存
  maxStock: number;         // 最高库存
  currentStock: number;     // 当前库存
  location: string;         // 存放位置
  status: SupplyStatus;     // 状态
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}

// 库存记录
interface StockRecord {
  id: string;              // 记录ID
  supplyId: string;        // 用品ID
  type: StockType;         // 记录类型
  quantity: number;        // 数量
  operator: string;        // 操作人
  remark: string;          // 备注
  createTime: string;      // 创建时间
}

// 领用申请
interface SupplyApply {
  id: string;              // 申请ID
  userId: string;          // 申请人ID
  department: string;      // 申请部门
  items: ApplyItem[];      // 申请物品
  status: ApplyStatus;     // 申请状态
  urgency: UrgencyLevel;   // 紧急程度
  reason: string;          // 申请原因
  createTime: string;      // 申请时间
}

// 采购计划
interface PurchasePlan {
  id: string;              // 计划ID
  title: string;           // 计划标题
  period: string;          // 采购周期
  items: PurchaseItem[];   // 采购物品
  status: PlanStatus;      // 计划状态
  totalAmount: number;     // 总金额
  createTime: string;      // 创建时间
}
```

### 4.2 接口列表
```typescript
// 获取办公用品列表
export const getSupplyList = (params: SupplyListParams) => {
  return http.get('/office-supplies/list', { params });
};

// 获取办公用品详情
export const getSupplyDetail = (id: string) => {
  return http.get(`/office-supplies/detail/${id}`);
};

// 创建办公用品
export const createSupply = (data: Partial<OfficeSupply>) => {
  return http.post('/office-supplies/create', data);
};

// 更新办公用品
export const updateSupply = (id: string, data: Partial<OfficeSupply>) => {
  return http.put(`/office-supplies/update/${id}`, data);
};

// 删除办公用品
export const deleteSupply = (id: string) => {
  return http.delete(`/office-supplies/delete/${id}`);
};

// 入库登记
export const stockIn = (data: StockInParams) => {
  return http.post('/office-supplies/stock/in', data);
};

// 出库登记
export const stockOut = (data: StockOutParams) => {
  return http.post('/office-supplies/stock/out', data);
};

// 提交领用申请
export const submitApply = (data: Partial<SupplyApply>) => {
  return http.post('/office-supplies/apply/submit', data);
};

// 审批申请
export const approveApply = (id: string, data: ApprovalParams) => {
  return http.post(`/office-supplies/apply/approve/${id}`, data);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能         | 管理员 | 律所主任 | 合伙人 | 执业律师 | 实习律师 | 行政人员 |
|------------|--------|----------|--------|----------|----------|----------|
| 查看列表     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | ✓个人    | ✓部门    |
| 库存管理     | ✓      | ✓审批    | -      | -        | -        | ✓部门    |
| 领用申请     | ✓      | ✓        | ✓      | ✓申请    | ✓申请    | ✓审批    |
| 采购管理     | ✓      | ✓审批    | -      | -        | -        | ✓申请    |
| 基础设置     | ✓      | ✓        | -      | -        | -        | ✓部门    |

### 5.2 数据权限说明

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员：可查看和管理所有办公用品数据
   - 律所主任：可查看所有数据，审批采购和大额领用
   - 合伙人：可查看团队数据，申请领用
   - 执业律师：可查看个人数据，申请领用
   - 实习律师：可查看个人数据，申请领用
   - 行政人员：负责日常办公用品管理，处理领用申请

2. 特殊权限说明
   - 采购管理：需要通过审批流程
   - 领用申请：一般由行政人员审批，大额需要律所主任审批
   - 库存管理：行政人员负责日常管理
   - 基础设置：仅管理员和律所主任可以配置

3. 审批流程权限
   - 一般领用：行政人员审批
   - 大额领用：需要律所主任审批
   - 采购申请：需要律所主任或管理员审批

### 5.3 路由配置
```typescript
// router/modules/office-supplies.ts
export default {
  path: '/office-supplies',
  name: 'OfficeSupplies',
  component: LAYOUT,
  meta: {
    title: '办公用品',
    icon: 'ant-design:shopping-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_finance']
  },
  children: [
    {
      path: 'list',
      name: 'SupplyList',
      component: () => import('@/views/office-supplies/list/index.vue'),
      meta: {
        title: '用品列表'
      }
    },
    {
      path: 'stock',
      name: 'StockManage',
      component: () => import('@/views/office-supplies/stock/index.vue'),
      meta: {
        title: '库存管理',
        roles: ['admin', 'director', 'admin_finance']
      }
    },
    {
      path: 'apply',
      name: 'SupplyApply',
      component: () => import('@/views/office-supplies/apply/index.vue'),
      meta: {
        title: '领用申请'
      }
    },
    {
      path: 'purchase',
      name: 'SupplyPurchase',
      component: () => import('@/views/office-supplies/purchase/index.vue'),
      meta: {
        title: '采购管理',
        roles: ['admin', 'director', 'admin_finance']
      }
    }
  ]
};
```

## 6. 状态管理

### 6.1 Store 结构
```typescript
// store/modules/office-supplies.ts
interface OfficeSuppliesState {
  supplyList: OfficeSupply[];
  currentSupply: OfficeSupply | null;
  stockRecords: StockRecord[];
  applyList: SupplyApply[];
  loading: boolean;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
  filterOptions: {
    categories: string[];
    statuses: SupplyStatus[];
  };
}

const useOfficeSuppliesStore = defineStore({
  id: 'app-office-supplies',
  state: (): OfficeSuppliesState => ({
    supplyList: [],
    currentSupply: null,
    stockRecords: [],
    applyList: [],
    loading: false,
    pagination: {
      current: 1,
      pageSize: 10,
      total: 0
    },
    searchParams: {},
    filterOptions: {
      categories: [],
      statuses: []
    }
  }),
  getters: {
    getSupplyList(): OfficeSupply[] {
      return this.supplyList;
    },
    getCurrentSupply(): OfficeSupply | null {
      return this.currentSupply;
    },
    getStockRecords(): StockRecord[] {
      return this.stockRecords;
    },
    getApplyList(): SupplyApply[] {
      return this.applyList;
    }
  },
  actions: {
    async fetchSupplyList() {
      this.loading = true;
      try {
        const { items, total } = await getSupplyList({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.supplyList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchSupplyDetail(id: string) {
      this.loading = true;
      try {
        this.currentSupply = await getSupplyDetail(id);
      } finally {
        this.loading = false;
      }
    },
    async fetchStockRecords(supplyId: string) {
      this.loading = true;
      try {
        this.stockRecords = await getStockRecords(supplyId);
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
    }
  }
});
```

## 7. 使用说明

### 7.1 办公用品领用流程
1. 进入领用申请页面
2. 填写领用申请
   - 选择用品类型
   - 填写数量
   - 说明用途
   - 选择紧急程度
3. 提交申请
4. 等待审批
5. 领取用品
6. 确认签收

### 7.2 采购申请流程
1. 进入采购管理页面
2. 填写采购计划
   - 采购物品清单
   - 采购数量
   - 预算金额
   - 供应商信息
3. 提交审批
4. 执行采购
5. 验收入库
6. 更新库存

### 7.3 库存管理
1. 日常盘点
   - 定期盘点
   - 记录库存
   - 处理差异
   - 更新系统

2. 预警管理
   - 设置预警阈值
   - 监控库存变化
   - 及时补货
   - 优化库存

### 7.4 注意事项
1. 物品管理
   - 规范编码管理
   - 定期盘点核查
   - 及时补充库存
   - 控制库存上限

2. 使用管理
   - 按需申请领用
   - 避免囤积浪费
   - 爱惜使用
   - 及时报修

3. 数据维护
   - 及时更新信息
   - 准确记录使用
   - 定期数据核查
   - 分析使用趋势

## 8. 最佳实践

### 8.1 库存管理
1. 建立分类编码体系
2. 实施动态库存管理
3. 设置科学的库存预警
4. 优化库存结构

### 8.2 领用管理
1. 规范申请流程
2. 建立定额标准
3. 实施分类管理
4. 追踪使用情况

### 8.3 采购管理
1. 制定采购计划
2. 建立供应商档案
3. 实施比价采购
4. 控制采购成本

### 8.4 数据分析
1. 使用量分析
2. 消耗趋势分析
3. 成本分析
4. 供应商评估
