# Vue Vben Admin 5.0 对接 Law-Firm-Infra 完整指南

## 🎯 关于Vue Vben Admin 5.0

**Vue Vben Admin 5.0** 是最新版本，具有以下重要特点：

### ⚠️ 重要提醒
- **5.0版本与之前版本不兼容**
- 采用全新的技术栈和架构设计
- 使用Monorepo架构，项目结构完全重构

### 🌟 5.0版本核心特性

1. **技术栈升级**
   - Vue 3 + Vite + TypeScript 
   - **Shadcn UI** 替代 Ant Design Vue
   - Monorepo架构，使用pnpm管理
   - 内置请求客户端替代axios
   - Lucide Icons图标库

2. **新架构特点**
   - `apps/` - 多应用支持
   - `packages/` - 共享包管理
   - `#/` 路径别名系统
   - 现代化的构建和开发体验

3. **安装和启动**
   ```bash
   # 克隆项目
   git clone https://github.com/vbenjs/vue-vben-admin.git
   cd vue-vben-admin
   
   # 安装依赖
   npm i -g corepack
   pnpm install
   
   # 启动开发服务器
   pnpm dev
   
   # 构建生产版本
   pnpm build
   ```

## 🔧 法律事务所系统对接配置

### 1. 环境配置文件

#### `.env.development` (开发环境)
```bash
# Vue Vben Admin 5.0 环境配置
VITE_APP_TITLE=法律事务所管理系统

# 前端开发端口 (Vue Vben Admin 5.0 默认开发端口)
VITE_PORT=5666

# 后端API地址 (您的law-firm-infra后端端口8080)
VITE_GLOB_API_URL=http://localhost:8080
VITE_GLOB_API_URL_PREFIX=/api/v1

# 开发代理配置 - 解决跨域问题
VITE_PROXY=[["api","http://localhost:8080"]]

# 法律事务所特定配置
VITE_LAW_FIRM_NAME=XX律师事务所
VITE_ENABLE_AI_ASSISTANT=true
VITE_ENABLE_DOC_ANALYSIS=true
VITE_MAX_FILE_SIZE=100
VITE_ALLOWED_FILE_TYPES=pdf,doc,docx,xls,xlsx,png,jpg,jpeg
```

#### `.env.production` (生产环境)
```bash
VITE_APP_TITLE=法律事务所管理系统
VITE_GLOB_API_URL=https://your-law-firm-domain.com
VITE_GLOB_API_URL_PREFIX=/api/v1
VITE_DROP_CONSOLE=true
VITE_BUILD_COMPRESS=gzip
```

### 2. API配置 (5.0版本适配)

#### `src/api/law-firm-config.ts`
```typescript
import { requestClient } from '#/api/request';

// 法律事务所API配置
export const lawFirmApi = requestClient.create({
  baseURL: import.meta.env.VITE_GLOB_API_URL + '/api/v1',
  timeout: 10000,
});

// 请求拦截器
lawFirmApi.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// 响应拦截器 - 适配CommonResult格式
lawFirmApi.interceptors.response.use(
  (response) => {
    const { data } = response;
    if (data.success) {
      return data.result || data.data;
    }
    throw new Error(data.message || '请求失败');
  },
  (error) => {
    if (error.response?.status === 401) {
      // 处理认证失效
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);
```

### 3. 路由配置 (5.0版本)

#### `src/router/routes/law-firm.ts`
```typescript
import type { RouteRecordRaw } from 'vue-router';

export const lawFirmRoutes: RouteRecordRaw[] = [
  {
    path: '/lawfirm',
    name: 'LawFirm',
    component: () => import('#/layouts/basic-layout.vue'),
    meta: {
      title: '法律事务所',
      icon: 'lucide:scale', // 使用Lucide图标
      order: 1,
    },
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('#/views/lawfirm/dashboard/index.vue'),
        meta: {
          title: '工作台',
          icon: 'lucide:layout-dashboard',
        },
      },
      {
        path: 'client',
        name: 'ClientManagement',
        meta: {
          title: '客户管理',
          icon: 'lucide:users',
        },
        children: [
          {
            path: 'list',
            name: 'ClientList',
            component: () => import('#/views/lawfirm/client/list.vue'),
            meta: {
              title: '客户列表',
              permissions: ['client:view'],
            },
          },
        ],
      },
    ],
  },
];
```

### 4. API接口实现

#### `src/api/modules/auth.ts`
```typescript
import { lawFirmApi } from '../law-firm-config';

interface LoginParams {
  username: string;
  password: string;
}

interface LoginResult {
  token: string;
  userInfo: any;
}

// 登录
export const loginApi = (params: LoginParams): Promise<LoginResult> => {
  return lawFirmApi.post('/auth/login', params);
};

// 获取用户信息
export const getUserInfo = (): Promise<any> => {
  return lawFirmApi.get('/auth/user/info');
};

// 获取菜单
export const getMenus = (): Promise<any[]> => {
  return lawFirmApi.get('/auth/user/menus');
};
```

#### `src/api/modules/client.ts`
```typescript
import { lawFirmApi } from '../law-firm-config';
import type { PageParams, PageResult } from '../types';

// 获取客户分页列表
export const getClientPage = (params: PageParams) => {
  return lawFirmApi.get<PageResult<any>>('/client/page', { params });
};

// 创建客户
export const createClient = (data: any) => {
  return lawFirmApi.post('/client', data);
};

// 更新客户
export const updateClient = (id: number, data: any) => {
  return lawFirmApi.put(`/client/${id}`, data);
};

// 删除客户
export const deleteClient = (id: number) => {
  return lawFirmApi.delete(`/client/${id}`);
};
```

### 5. 组件开发 (Shadcn UI)

#### `src/views/lawfirm/client/list.vue`
```vue
<template>
  <div class="p-4">
    <!-- 使用Shadcn UI组件 -->
    <Card>
      <CardHeader>
        <CardTitle>客户管理</CardTitle>
      </CardHeader>
      <CardContent>
        <!-- 搜索表单 -->
        <div class="mb-4">
          <div class="flex gap-4">
            <Input
              v-model="searchForm.name"
              placeholder="客户名称"
              class="w-48"
            />
            <Select v-model="searchForm.type">
              <SelectTrigger class="w-48">
                <SelectValue placeholder="客户类型" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="1">个人</SelectItem>
                <SelectItem value="2">企业</SelectItem>
              </SelectContent>
            </Select>
            <Button @click="handleSearch">
              <Search class="w-4 h-4 mr-2" />
              搜索
            </Button>
          </div>
        </div>
        
        <!-- 数据表格 -->
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>客户名称</TableHead>
              <TableHead>客户类型</TableHead>
              <TableHead>联系电话</TableHead>
              <TableHead>操作</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            <TableRow v-for="item in data" :key="item.id">
              <TableCell>{{ item.name }}</TableCell>
              <TableCell>
                <Badge variant="outline">
                  {{ item.type === 1 ? '个人' : '企业' }}
                </Badge>
              </TableCell>
              <TableCell>{{ item.phone }}</TableCell>
              <TableCell>
                <div class="flex gap-2">
                  <Button variant="outline" size="sm" @click="handleEdit(item)">
                    编辑
                  </Button>
                  <Button variant="destructive" size="sm" @click="handleDelete(item)">
                    删除
                  </Button>
                </div>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { 
  Card, CardContent, CardHeader, CardTitle,
  Table, TableBody, TableCell, TableHead, TableHeader, TableRow,
  Input, Select, SelectContent, SelectItem, SelectTrigger, SelectValue,
  Button, Badge 
} from '@/components/ui';
import { Search } from 'lucide-vue-next';
import { getClientPage } from '@/api/modules/client';

const data = ref([]);
const searchForm = ref({
  name: '',
  type: '',
});

const handleSearch = async () => {
  try {
    const result = await getClientPage({
      pageNum: 1,
      pageSize: 10,
      ...searchForm.value,
    });
    data.value = result.records;
  } catch (error) {
    console.error('搜索失败:', error);
  }
};

const handleEdit = (item: any) => {
  // 编辑逻辑
  console.log('编辑客户:', item);
};

const handleDelete = (item: any) => {
  // 删除逻辑
  console.log('删除客户:', item);
};

onMounted(() => {
  handleSearch();
});
</script>
```

## 🎨 UI组件差异对比

### 从Ant Design Vue到Shadcn UI

| 功能 | Ant Design Vue (旧版) | Shadcn UI (5.0版本) |
|------|----------------------|---------------------|
| 表格 | `<a-table>` | `<Table>` |
| 按钮 | `<a-button>` | `<Button>` |
| 输入框 | `<a-input>` | `<Input>` |
| 选择器 | `<a-select>` | `<Select>` |
| 卡片 | `<a-card>` | `<Card>` |
| 标签 | `<a-tag>` | `<Badge>` |
| 图标 | `ant-design:*` | `lucide:*` |

## 🔒 权限控制

### 路由权限
```typescript
// 在路由meta中定义权限
meta: {
  title: '客户管理',
  permissions: ['client:view'],
  roles: ['admin', 'lawyer'],
}
```

### 组件权限指令
```vue
<template>
  <Button v-if="hasPermission('client:create')" @click="handleCreate">
    新增客户
  </Button>
</template>

<script setup>
import { usePermission } from '@/hooks/usePermission';
const { hasPermission } = usePermission();
</script>
```

## 📦 项目结构 (5.0版本)

```
vue-vben-admin/
├── apps/                 # 应用目录
│   ├── web-antd/        # Ant Design版本
│   ├── web-ele/         # Element Plus版本
│   └── web-naive/       # Naive UI版本
├── packages/            # 共享包
│   ├── @vben/common-ui  # 通用UI组件
│   ├── @vben/locales    # 国际化
│   ├── @vben/stores     # 状态管理
│   └── @vben/utils      # 工具函数
├── docs/                # 文档
├── internal/            # 内部工具
└── scripts/             # 构建脚本
```

## 🚀 部署配置

### Dockerfile (5.0版本)
```dockerfile
FROM node:20-alpine AS builder

WORKDIR /app

# 安装pnpm
RUN corepack enable

# 复制package文件
COPY package.json pnpm-lock.yaml pnpm-workspace.yaml ./
COPY packages packages
COPY apps/web-antd apps/web-antd

# 安装依赖
RUN pnpm install

# 构建
RUN pnpm build

# 生产镜像
FROM nginx:alpine
COPY --from=builder /app/apps/web-antd/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

## 📝 迁移检查清单

- [ ] 更新到Vue Vben Admin 5.0版本
- [ ] 替换UI组件库 (Ant Design → Shadcn UI)
- [ ] 更新图标库 (ant-design → lucide)
- [ ] 修改API配置 (axios → requestClient)
- [ ] 更新路径别名 (@/ → #/)
- [ ] 适配新的项目结构
- [ ] 测试所有业务功能
- [ ] 更新部署配置

## 🔗 相关链接

- [Vue Vben Admin 5.0 官方仓库](https://github.com/vbenjs/vue-vben-admin)
- [官方文档](https://doc.vben.pro/)
- [在线预览](https://web-antd.vben.pro/)
- [Shadcn UI 文档](https://ui.shadcn.com/)
- [Lucide 图标库](https://lucide.dev/)

---

**注意**: Vue Vben Admin 5.0采用了全新的架构，请仔细阅读官方文档并进行充分测试后再投入生产使用。