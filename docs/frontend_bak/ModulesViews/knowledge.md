# 知识库管理模块

## 1. 功能概述

知识库管理模块是律师事务所管理系统的重要支撑模块，用于管理和共享律所的法律知识资源，包括法律法规、案例分析、文书模板、专业文献等内容，提供知识的收集、整理、分享和应用功能。

### 1.1 主要功能

- 知识分类管理
  - 多级分类体系
  - 标签管理
  - 分类权限控制
  - 分类统计分析

- 知识内容管理
  - 法律法规库
  - 案例分析库
  - 文书模板库
  - 专业文献库
  - 培训资料库

- 知识共享与协作
  - 知识贡献
  - 知识评论
  - 知识推荐
  - 协作编辑
  - 版本控制

- 知识检索
  - 全文检索
  - 高级搜索
  - 智能推荐
  - 相关知识关联
  - 使用频率统计

### 1.2 应用场景

1. 法律研究
   - 法律法规查询
   - 案例分析研究
   - 法律观点整理
   - 专业文献阅读

2. 业务支持
   - 文书模板使用
   - 案例经验参考
   - 法律依据查找
   - 专业知识学习

3. 团队协作
   - 知识共享
   - 经验交流
   - 团队学习
   - 培训资料管理

## 2. 页面结构

```bash
views/knowledge/
├── index/                # 知识库主页
│   ├── index.vue        # 主页面
│   └── components/      # 主页组件
│       ├── Statistics.vue      # 统计信息
│       ├── HotKnowledge.vue    # 热门知识
│       ├── RecentUpdate.vue    # 最近更新
│       └── QuickAccess.vue     # 快速访问
│
├── category/            # 分类管理
│   ├── index.vue       # 分类页面
│   └── components/     # 分类组件
│       ├── CategoryTree.vue    # 分类树
│       ├── CategoryForm.vue    # 分类表单
│       └── Permission.vue      # 权限设置
│
├── content/            # 内容管理
│   ├── index.vue      # 内容页面
│   └── components/    # 内容组件
│       ├── Editor.vue         # 富文本编辑器
│       ├── Preview.vue        # 内容预览
│       ├── Version.vue        # 版本管理
│       └── Comment.vue        # 评论管理
│
├── template/           # 模板管理
│   ├── index.vue      # 模板页面
│   └── components/    # 模板组件
│       ├── TemplateList.vue   # 模板列表
│       ├── TemplateForm.vue   # 模板表单
│       └── TemplateUse.vue    # 模板使用
│
└── components/        # 公共组件
    ├── Search.vue     # 搜索组件
    ├── Filter.vue     # 筛选组件
    ├── Share.vue      # 分享组件
    └── Tags.vue       # 标签组件
```

## 3. 功能清单

### 3.1 知识分类
- [x] 分类管理
  - 分类创建
  - 分类编辑
  - 分类删除
  - 分类排序
  - 权限设置

- [x] 标签管理
  - 标签创建
  - 标签关联
  - 标签统计
  - 标签推荐

### 3.2 知识内容
- [x] 法律法规
  - 法规录入
  - 法规更新
  - 法规分类
  - 关联解读
  
- [x] 案例分析
  - 案例收集
  - 分析撰写
  - 要点提取
  - 经验总结
  
- [x] 文书模板
  - 模板创建
  - 模板分类
  - 模板使用
  - 模板更新

- [x] 专业文献
  - 文献收录
  - 文献分类
  - 文献检索
  - 文献下载

### 3.3 知识应用
- [x] 检索功能
  - 全文检索
  - 高级检索
  - 结果筛选
  - 检索历史

- [x] 共享协作
  - 知识分享
  - 在线评论
  - 协作编辑
  - 版本管理

## 4. API 接口

### 4.1 数据结构
```typescript
// 知识基本信息
interface Knowledge {
  id: string;                 // 知识ID
  title: string;             // 标题
  type: KnowledgeType;       // 类型
  category: string;          // 分类
  tags: string[];           // 标签
  content: string;          // 内容
  author: string;           // 作者
  source: string;           // 来源
  viewCount: number;        // 查看次数
  likeCount: number;        // 点赞数
  commentCount: number;     // 评论数
  status: KnowledgeStatus;  // 状态
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}

// 知识类型
enum KnowledgeType {
  LAW = 'law',             // 法律法规
  CASE = 'case',           // 案例分析
  TEMPLATE = 'template',    // 文书模板
  DOCUMENT = 'document',    // 专业文献
  TRAINING = 'training'     // 培训资料
}

// 知识状态
enum KnowledgeStatus {
  DRAFT = 'draft',         // 草稿
  PUBLISHED = 'published', // 已发布
  ARCHIVED = 'archived',   // 已归档
  DELETED = 'deleted'      // 已删除
}

// 知识分类
interface Category {
  id: string;             // 分类ID
  name: string;           // 分类名称
  parentId: string;       // 父级ID
  path: string;          // 分类路径
  level: number;         // 层级
  sort: number;          // 排序
  count: number;         // 知识数量
  createTime: string;    // 创建时间
  updateTime: string;    // 更新时间
}

// 知识评论
interface Comment {
  id: string;            // 评论ID
  knowledgeId: string;   // 知识ID
  content: string;       // 评论内容
  author: string;        // 评论者
  replyTo: string;       // 回复ID
  createTime: string;    // 创建时间
}

// 知识版本
interface Version {
  id: string;           // 版本ID
  knowledgeId: string;  // 知识ID
  version: string;      // 版本号
  content: string;      // 版本内容
  operator: string;     // 操作者
  comment: string;      // 版本说明
  createTime: string;   // 创建时间
}
```

### 4.2 接口列表
```typescript
// 获取知识列表
export const getKnowledgeList = (params: KnowledgeQuery) => {
  return http.get('/knowledge/list', { params });
};

// 获取知识详情
export const getKnowledgeDetail = (id: string) => {
  return http.get(`/knowledge/detail/${id}`);
};

// 创建知识
export const createKnowledge = (data: Partial<Knowledge>) => {
  return http.post('/knowledge/create', data);
};

// 更新知识
export const updateKnowledge = (id: string, data: Partial<Knowledge>) => {
  return http.put(`/knowledge/update/${id}`, data);
};

// 删除知识
export const deleteKnowledge = (id: string) => {
  return http.delete(`/knowledge/delete/${id}`);
};

// 获取分类列表
export const getCategoryList = () => {
  return http.get('/knowledge/category/list');
};

// 创建分类
export const createCategory = (data: Partial<Category>) => {
  return http.post('/knowledge/category/create', data);
};

// 获取评论列表
export const getCommentList = (knowledgeId: string) => {
  return http.get(`/knowledge/comment/list/${knowledgeId}`);
};

// 添加评论
export const addComment = (data: Partial<Comment>) => {
  return http.post('/knowledge/comment/add', data);
};

// 获取版本历史
export const getVersionHistory = (knowledgeId: string) => {
  return http.get(`/knowledge/version/${knowledgeId}`);
};

// 创建新版本
export const createVersion = (data: Partial<Version>) => {
  return http.post('/knowledge/version/create', data);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能 | 管理员 | 律所主任 | 合伙人 | 执业律师 | 实习律师 | 行政人员 |
|-----|--------|----------|--------|----------|----------|----------|
| 查看知识 | ✓全所 | ✓全所 | ✓团队 | ✓个人+团队 | ✓个人 | ✓部门相关 |
| 新增知识 | ✓全所 | ✓全所 | ✓团队 | ✓个人 | - | ✓部门相关 |
| 编辑知识 | ✓全所 | ✓全所 | ✓团队 | ✓个人 | - | ✓部门相关 |
| 删除知识 | ✓全所 | ✓全所 | ✓团队 | - | - | - |
| 分享知识 | ✓全所 | ✓全所 | ✓团队 | ✓团队 | - | ✓部门相关 |
| 导出知识 | ✓全所 | ✓全所 | ✓团队 | ✓个人 | ✓个人 | ✓部门相关 |
| 分类管理 | ✓ | ✓ | - | - | - | - |
| 审核知识 | ✓ | ✓ | ✓团队 | - | - | - |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任：可查看和管理所有知识库内容
   - 合伙人：可查看和管理本团队的知识内容
   - 执业律师：可查看公开内容和团队共享的知识
   - 实习律师：仅可查看公开的知识内容
   - 行政人员：可查看与本部门相关的知识内容

2. 特殊权限说明
   - 知识创建：需要经过审核才能发布
   - 知识编辑：只能编辑自己创建的内容
   - 知识删除：只有管理员、律所主任和团队负责人可以删除
   - 知识分享：需要考虑知识产权和保密要求

3. 知识分级权限
   - 公开知识：所有人可见
   - 部门知识：部门内可见
   - 团队知识：团队内可见
   - 个人知识：仅创建者可见
   - 机密知识：需要特殊授权

### 5.3 路由配置
```typescript
// router/modules/knowledge.ts
export default {
  path: '/knowledge',
  name: 'Knowledge',
  component: LAYOUT,
  meta: {
    title: '知识库管理',
    icon: 'ant-design:book-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_finance']
  },
  children: [
    {
      path: 'list',
      name: 'KnowledgeList',
      component: () => import('@/views/knowledge/list/index.vue'),
      meta: {
        title: '知识列表'
      }
    },
    {
      path: 'category',
      name: 'KnowledgeCategory',
      component: () => import('@/views/knowledge/category/index.vue'),
      meta: {
        title: '分类管理',
        roles: ['admin', 'director']
      }
    },
    {
      path: 'detail/:id',
      name: 'KnowledgeDetail',
      component: () => import('@/views/knowledge/detail/index.vue'),
      meta: {
        title: '知识详情',
        hideMenu: true
      }
    }
  ]
};
```

## 6. 状态管理

### 6.1 Store 结构
```typescript
// store/modules/knowledge.ts
interface KnowledgeState {
  knowledgeList: Knowledge[];
  currentKnowledge: Knowledge | null;
  categoryList: Category[];
  loading: boolean;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
  filterOptions: {
    types: KnowledgeType[];
    categories: string[];
    tags: string[];
  };
}

const useKnowledgeStore = defineStore({
  id: 'app-knowledge',
  state: (): KnowledgeState => ({
    knowledgeList: [],
    currentKnowledge: null,
    categoryList: [],
    loading: false,
    pagination: {
      current: 1,
      pageSize: 10,
    total: 0
    },
    searchParams: {},
    filterOptions: {
      types: [],
      categories: [],
      tags: []
    }
  }),
  getters: {
    getKnowledgeList(): Knowledge[] {
      return this.knowledgeList;
    },
    getCurrentKnowledge(): Knowledge | null {
      return this.currentKnowledge;
    },
    getCategoryList(): Category[] {
      return this.categoryList;
    }
  },
  actions: {
    async fetchKnowledgeList() {
      this.loading = true;
      try {
        const { items, total } = await getKnowledgeList({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.knowledgeList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchKnowledgeDetail(id: string) {
      this.loading = true;
      try {
        this.currentKnowledge = await getKnowledgeDetail(id);
      } finally {
        this.loading = false;
      }
    },
    async fetchCategoryList() {
      this.loading = true;
      try {
        this.categoryList = await getCategoryList();
      } finally {
        this.loading = false;
      }
    }
  }
});
```

## 7. 使用说明

### 7.1 知识创建流程
1. 进入知识库页面
2. 点击"新建知识"按钮
3. 选择知识类型
4. 填写基本信息
   - 标题
   - 分类
   - 标签
   - 来源
5. 编辑知识内容
6. 设置访问权限
7. 提交发布

### 7.2 知识管理
1. 内容维护
   - 定期更新内容
   - 及时修正错误
   - 补充相关资料
   - 处理用户反馈

2. 分类管理
   - 合理设置分类
   - 定期整理归类
   - 优化分类结构
   - 更新分类统计

3. 知识共享
   - 设置共享范围
   - 处理共享申请
   - 跟踪使用情况
   - 收集用户反馈

### 7.3 注意事项
1. 内容安全
   - 严格权限控制
   - 保护敏感信息
   - 备份重要内容
   - 追踪使用记录

2. 质量控制
   - 内容审核把关
   - 及时更新维护
   - 规范格式要求
   - 完善知识体系

3. 使用规范
   - 遵守知识产权
   - 注明参考来源
   - 规范引用格式
   - 维护知识秩序

## 8. 最佳实践

### 8.1 内容创建
1. 选择合适分类
2. 使用规范格式
3. 添加适当标签
4. 注明参考来源

### 8.2 知识管理
1. 定期内容审查
2. 及时更新维护
3. 优化检索体验
4. 完善知识结构

### 8.3 知识共享
1. 促进知识交流
2. 鼓励经验分享
3. 建立反馈机制
4. 优化共享流程

### 8.4 系统维护
1. 定期数据备份
2. 清理无效内容
3. 优化系统性能
4. 完善功能模块 
