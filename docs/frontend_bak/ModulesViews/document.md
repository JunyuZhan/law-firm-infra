# 文档管理模块

## 1. 功能概述

文档管理模块是律师事务所管理系统的核心支撑模块，用于管理律所所有的电子文档，包括案件文书、合同文本、证据材料等，提供文档的全生命周期管理，支持多种格式文档的在线预览和编辑。

### 1.1 主要功能

- 文档上传与存储
  - 支持多种格式文档上传
  - 支持批量上传
  - 支持断点续传
  - 自动文件类型识别
  - 文件大小限制控制

- 文档分类管理
  - 多级目录结构
  - 自定义分类标签
  - 灵活的分类规则
  - 批量分类调整

- 文档权限控制
  - 基于角色的权限管理
  - 文档访问权限设置
  - 文档操作权限控制
  - 权限继承与覆盖

- 文档版本管理
  - 版本历史记录
  - 版本比对
  - 版本回滚
  - 版本说明

- 文档检索
  - 全文检索
  - 高级搜索
  - 标签搜索
  - 相关文档推荐

- 在线预览
  - 支持常见文档格式预览
  - 图片在线查看
  - PDF在线阅读
  - Office文档预览

- 协同编辑
  - 在线编辑
  - 多人协作
  - 修改记录
  - 实时同步

- 文档下载管理
  - 单文件下载
  - 批量打包下载
  - 断点续传支持
  - 下载记录跟踪
  - 下载权限控制

- OCR文字识别
  - 图片文字识别
  - PDF文字识别
  - 批量OCR处理
  - OCR结果编辑
  - 识别历史记录

### 1.2 应用场景

- 案件文书管理
- 合同文档管理
- 证据材料管理
- 法律文献管理
- 内部文件管理

## 2. 页面结构

```bash
src/views/document/
├── index/                    # 文档管理主页
│   ├── index.vue            # 主页面组件
│   ├── components/          # 主页组件
│   │   ├── StatisticsCard.vue   # 统计卡片
│   │   ├── RecentDocs.vue       # 最近文档
│   │   └── QuickActions.vue     # 快捷操作
├── list/                    # 文档列表
│   ├── index.vue           # 列表页面
│   ├── components/         # 列表组件
│   │   ├── SearchForm.vue      # 搜索表单
│   │   ├── CategoryTree.vue    # 分类树
│   │   ├── DocTable.vue        # 文档表格
│   │   ├── DocModal.vue        # 文档详情弹窗
│   │   └── BatchActions.vue    # 批量操作
├── detail/                  # 文档详情
│   ├── index.vue           # 详情页面
│   ├── components/         # 详情组件
│   │   ├── BasicInfo.vue       # 基本信息
│   │   ├── VersionHistory.vue  # 版本历史
│   │   ├── AccessLog.vue       # 访问日志
│   │   ├── RelatedDocs.vue     # 关联文档
│   │   └── SharePanel.vue      # 分享面板
├── preview/                 # 文档预览
│   ├── index.vue           # 预览页面
│   ├── components/         # 预览组件
│   │   ├── DocViewer.vue       # 文档查看器
│   │   ├── PdfViewer.vue       # PDF查看器
│   │   ├── ImageViewer.vue     # 图片查看器
│   │   ├── VideoPlayer.vue     # 视频播放器
│   │   ├── AudioPlayer.vue     # 音频播放器
│   │   └── TextViewer.vue      # 文本查看器
├── category/               # 分类管理
│   ├── index.vue          # 分类页面
│   ├── components/        # 分类组件
│   │   ├── CategoryForm.vue    # 分类表单
│   │   ├── CategoryTree.vue    # 分类树
│   │   └── PermissionConfig.vue # 权限配置
└── components/            # 公共组件
    ├── DocIcon.vue        # 文档图标
    ├── DocPreview.vue     # 文档预览
    ├── DocUpload.vue      # 文档上传
    ├── DocSelect.vue      # 文档选择
    ├── BatchUpload.vue    # 批量上传
    ├── DocDownload.vue    # 文档下载
    └── DocOCR.vue         # 文档OCR

```

### 组件说明

1. **主页组件**
   - `StatisticsCard.vue`: 显示文档统计信息
   - `RecentDocs.vue`: 展示最近访问/修改的文档
   - `QuickActions.vue`: 提供快捷操作按钮

2. **列表组件**
   - `SearchForm.vue`: 文档搜索和筛选
   - `CategoryTree.vue`: 文档分类树形导航
   - `DocTable.vue`: 文档列表表格
   - `DocModal.vue`: 文档详情/编辑弹窗
   - `BatchActions.vue`: 批量操作工具栏

3. **详情组件**
   - `BasicInfo.vue`: 文档基本信息展示
   - `VersionHistory.vue`: 版本历史记录
   - `AccessLog.vue`: 访问记录日志
   - `RelatedDocs.vue`: 关联文档管理
   - `SharePanel.vue`: 文档分享设置

4. **预览组件**
   - `DocViewer.vue`: 通用文档查看器，根据文件类型自动选择合适的查看器
   - `PdfViewer.vue`: PDF文件查看器，支持缩放、页面跳转等功能
   - `ImageViewer.vue`: 图片查看器，支持缩放、旋转等功能
   - `VideoPlayer.vue`: 视频播放器，支持常见视频格式播放
   - `AudioPlayer.vue`: 音频播放器，支持MP3、WAV等音频格式播放
   - `TextViewer.vue`: 文本查看器，支持代码高亮、格式化等功能

5. **分类组件**
   - `CategoryForm.vue`: 分类信息表单
   - `CategoryTree.vue`: 分类管理树形结构
   - `PermissionConfig.vue`: 分类权限配置

6. **公共组件**
   - `DocIcon.vue`: 文档类型图标
   - `DocPreview.vue`: 文档预览组件
   - `DocUpload.vue`: 文档上传组件
   - `DocSelect.vue`: 文档选择组件
   - `BatchUpload.vue`: 批量上传组件
   - `DocDownload.vue`: 文档下载组件，支持批量下载、断点续传
   - `DocOCR.vue`: 文档OCR组件，支持图片和PDF文字识别

### 页面功能

1. **文档管理主页**
   - 文档统计概览
   - 最近访问文档
   - 快捷操作入口
   - 常用文档推荐

2. **文档列表页**
   - 文档分类浏览
   - 高级搜索过滤
   - 批量操作功能
   - 列表/网格视图切换
   - 排序和筛选

3. **文档详情页**
   - 基本信息展示
   - 版本历史管理
   - 访问记录查看
   - 关联文档管理
   - 分享权限设置

4. **文档预览页**
   - 多格式文档预览
   - 在线阅读功能
   - 文档批注标记
   - 协作评论功能

5. **分类管理页**
   - 分类树形管理
   - 分类信息编辑
   - 权限规则配置
   - 分类统计信息

6. **OCR功能页**
   - 文档OCR识别
   - 批量OCR处理
   - OCR结果编辑
   - OCR历史记录
   - 导出OCR结果

## 3. 数据结构

### 3.1 文档基本信息

```typescript
interface Document {
  id: string;                 // 文档ID
  name: string;              // 文档名称
  type: DocumentType;        // 文档类型
  category: string;          // 所属分类
  tags: string[];           // 标签列表
  size: number;             // 文件大小
  format: string;           // 文件格式
  path: string;             // 存储路径
  url: string;              // 访问地址
  thumbnail: string;        // 缩略图
  description: string;      // 文档描述
  version: string;          // 当前版本
  creator: string;          // 创建人
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
  status: DocumentStatus;   // 文档状态
  permissions: Permission[];// 权限设置
  // 新增关联字段
  source: DocumentSource;   // 文档来源
  sourceId: string;        // 来源ID（案件ID/合同ID等）
  sourceName: string;      // 来源名称
  sourceType: string;      // 来源类型
}

// 文档类型
enum DocumentType {
  CASE = 'case',           // 案件文书
  CONTRACT = 'contract',    // 合同文档
  EVIDENCE = 'evidence',    // 证据材料
  REFERENCE = 'reference',  // 参考文献
  INTERNAL = 'internal'     // 内部文件
}

// 文档状态
enum DocumentStatus {
  DRAFT = 'draft',           // 草稿
  PUBLISHED = 'published',   // 已发布
  ARCHIVED = 'archived',     // 已归档
  DELETED = 'deleted'        // 已删除
}

// 文档来源
enum DocumentSource {
  CASE = 'case',           // 案件相关
  CONTRACT = 'contract',    // 合同相关
  KNOWLEDGE = 'knowledge',  // 知识库
  SYSTEM = 'system',       // 系统文档
  OTHER = 'other'          // 其他来源
}

// 文档权限
interface Permission {
  roleId: string;          // 角色ID
  actions: string[];       // 操作权限
  scope: string;          // 权限范围
}

// 文档版本
interface DocumentVersion {
  id: string;             // 版本ID
  documentId: string;     // 文档ID
  version: string;        // 版本号
  content: string;        // 版本内容
  operator: string;       // 操作人
  comment: string;        // 版本说明
  createTime: string;     // 创建时间
}

// 文档分类
interface Category {
  id: string;             // 分类ID
  name: string;           // 分类名称
  parentId: string;       // 父级ID
  path: string;          // 分类路径
  level: number;         // 层级
  sort: number;          // 排序
  permissions: Permission[]; // 权限设置
}
```

### 3.2 访问记录

```typescript
interface AccessLog {
  id: string;              // 记录ID
  documentId: string;      // 文档ID
  userId: string;          // 用户ID
  userName: string;        // 用户名称
  action: string;          // 操作类型
  ip: string;              // 访问IP
  deviceInfo: string;      // 设备信息
  createTime: string;      // 访问时间
}
```

## 4. 接口定义

### 4.1 文档管理接口

```typescript
interface DocumentApi {
  // 文档列表
  getDocumentList(params: DocumentQuery): Promise<PageResult<Document>>;
  
  // 文档详情
  getDocumentDetail(id: string): Promise<Document>;
  
  // 创建文档
  createDocument(data: Partial<Document>): Promise<Document>;
  
  // 更新文档
  updateDocument(id: string, data: Partial<Document>): Promise<Document>;
  
  // 删除文档
  deleteDocument(id: string): Promise<void>;
  
  // 批量删除
  batchDeleteDocuments(ids: string[]): Promise<void>;
  
  // 移动文档
  moveDocument(id: string, targetCategory: string): Promise<void>;
  
  // 复制文档
  copyDocument(id: string, targetCategory: string): Promise<Document>;
  
  // 新增关联方法
  // 获取关联文档列表
  getRelatedDocuments(params: {
    sourceType: string;
    sourceId: string;
  }): Promise<Document[]>;
  
  // 关联文档
  linkDocument(params: {
    documentId: string;
    sourceType: string;
    sourceId: string;
  }): Promise<void>;
  
  // 取消关联
  unlinkDocument(params: {
    documentId: string;
    sourceType: string;
    sourceId: string;
  }): Promise<void>;
}
```

### 4.2 文件操作接口

```typescript
interface FileApi {
  // 上传文件
  uploadFile(file: File, options?: UploadOptions): Promise<FileInfo>;
  
  // 下载文件
  downloadFile(id: string): Promise<Blob>;
  
  // 预览文件
  previewFile(id: string): Promise<PreviewInfo>;
  
  // 获取上传地址
  getUploadUrl(filename: string): Promise<string>;
  
  // 分片上传
  uploadChunk(chunk: Blob, params: ChunkParams): Promise<void>;
  
  // 合并分片
  mergeChunks(fileHash: string, filename: string): Promise<FileInfo>;

  // 下载相关
  batchDownload(ids: string[]): Promise<Blob>;
  getDownloadProgress(taskId: string): Promise<DownloadProgress>;
  pauseDownload(taskId: string): Promise<void>;
  resumeDownload(taskId: string): Promise<void>;
  
  // OCR相关
  ocrDocument(file: File): Promise<OcrResult>;
  batchOcrDocuments(files: File[]): Promise<OcrResult[]>;
  getOcrProgress(taskId: string): Promise<OcrProgress>;
  saveOcrResult(documentId: string, result: OcrResult): Promise<void>;
}

// 下载进度
interface DownloadProgress {
  taskId: string;
  fileName: string;
  totalSize: number;
  downloadedSize: number;
  progress: number;
  status: 'pending' | 'downloading' | 'paused' | 'completed' | 'error';
  error?: string;
}

// OCR结果
interface OcrResult {
  documentId: string;
  pages: Array<{
    pageNo: number;
    content: string;
    blocks: Array<{
      text: string;
      confidence: number;
      bounds: {
        left: number;
        top: number;
        width: number;
        height: number;
      };
    }>;
  }>;
  summary: {
    totalPages: number;
    totalWords: number;
    averageConfidence: number;
  };
}
```

### 4.3 版本管理接口

```typescript
interface VersionApi {
  // 获取版本历史
  getVersionHistory(documentId: string): Promise<DocumentVersion[]>;
  
  // 创建新版本
  createVersion(documentId: string, data: Partial<DocumentVersion>): Promise<DocumentVersion>;
  
  // 回滚版本
  rollbackVersion(documentId: string, versionId: string): Promise<void>;
  
  // 比较版本
  compareVersions(versionId1: string, versionId2: string): Promise<VersionDiff>;
}
```

## 5. 状态管理

### 5.1 State 定义

```typescript
interface DocumentState {
  documentList: Document[];        // 文档列表
  currentDocument: Document | null;// 当前文档
  total: number;                  // 总数
  loading: boolean;               // 加载状态
  uploadProgress: number;         // 上传进度
  previewUrl: string | null;      // 预览地址
}
```

### 5.2 Actions 定义

```typescript
interface DocumentActions {
  // 获取文档列表
  getDocumentList(params: DocumentQuery): Promise<void>;
  
  // 获取文档详情
  getDocumentDetail(id: string): Promise<void>;
  
  // 上传文档
  uploadDocument(file: File): Promise<void>;
  
  // 更新文档
  updateDocument(data: Partial<Document>): Promise<void>;
  
  // 删除文档
  deleteDocument(id: string): Promise<void>;
  
  // 预览文档
  previewDocument(id: string): Promise<void>;
}
```

## 6. 使用示例

### 6.1 文档列表

```vue
<template>
  <div class="document-list">
    <SearchForm @search="handleSearch" />
    <a-table
      :columns="columns"
      :data-source="documentList"
      :loading="loading"
    >
      <!-- 表格列配置 -->
    </a-table>
  </div>
</template>

<script lang="ts" setup>
import { useDocumentStore } from '@/store/modules/document';
import type { DocumentQuery } from '@/types/document';

const documentStore = useDocumentStore();

// 获取文档列表
const handleSearch = async (params: DocumentQuery) => {
  await documentStore.getDocumentList(params);
};
</script>
```

### 6.2 文档上传

```vue
<template>
  <FileUpload
    :accept="acceptTypes"
    :max-size="maxSize"
    :multiple="true"
    @success="handleUploadSuccess"
    @error="handleUploadError"
  />
</template>

<script lang="ts" setup>
import { FileUpload } from '@/components/Upload';
import { useDocumentStore } from '@/store/modules/document';

const documentStore = useDocumentStore();

// 处理上传成功
const handleUploadSuccess = async (fileInfo: FileInfo) => {
  await documentStore.createDocument({
    name: fileInfo.name,
    url: fileInfo.url,
    // ...其他字段
  });
};
</script>
```

### 6.3 文档关联示例

```vue
<template>
  <div class="document-relation">
    <!-- 文档来源信息 -->
    <a-descriptions v-if="document.source !== 'document'" bordered>
      <a-descriptions-item label="来源类型">
        {{ getSourceTypeText(document.sourceType) }}
      </a-descriptions-item>
      <a-descriptions-item label="来源名称">
        <a @click="handleSourceClick(document)">{{ document.sourceName }}</a>
      </a-descriptions-item>
    </a-descriptions>

    <!-- 关联操作按钮 -->
    <a-space>
      <a-button @click="handleLink" v-if="document.source === 'document'">
        关联到案件/合同
      </a-button>
      <a-button @click="handleUnlink" v-else>
        取消关联
      </a-button>
    </a-space>
  </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
import { useDocumentStore } from '@/store/modules/document';

const router = useRouter();
const documentStore = useDocumentStore();

// 跳转到来源页面
const handleSourceClick = (document: Document) => {
  if (document.source === 'case') {
    router.push(`/case/detail/${document.sourceId}`);
  } else if (document.source === 'contract') {
    router.push(`/contract/detail/${document.sourceId}`);
  }
};

// 关联文档
const handleLink = async () => {
  // 打开关联选择弹窗
  // ...
};

// 取消关联
const handleUnlink = async () => {
  // 确认取消关联
  // ...
};
</script>
```

### 6.4 文档下载示例

```vue
<template>
  <div class="document-download">
    <a-space>
      <a-button @click="handleDownload" :loading="downloading">
        <download-outlined />
        下载文档
      </a-button>
      <a-progress
        v-if="showProgress"
        :percent="downloadProgress"
        size="small"
        status="active"
      />
    </a-space>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { useDocumentStore } from '@/store/modules/document';

const downloading = ref(false);
const showProgress = ref(false);
const downloadProgress = ref(0);

const handleDownload = async () => {
  downloading.value = true;
  showProgress.value = true;
  try {
    await documentStore.downloadDocument(props.documentId, (progress) => {
      downloadProgress.value = progress;
    });
  } finally {
    downloading.value = false;
    showProgress.value = false;
  }
};
</script>
```

### 6.5 OCR处理示例

```vue
<template>
  <div class="document-ocr">
    <DocOCR
      :file="currentFile"
      @success="handleOcrSuccess"
      @error="handleOcrError"
    >
      <template #result="{ result }">
        <OcrEditor
          v-model="result"
          @save="handleSaveOcr"
        />
      </template>
    </DocOCR>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { DocOCR, OcrEditor } from '@/components/Document';
import { useDocumentStore } from '@/store/modules/document';

const documentStore = useDocumentStore();

const handleOcrSuccess = async (result: OcrResult) => {
  // 处理OCR结果
  await documentStore.saveOcrResult(props.documentId, result);
};
</script>
```

## 7. 注意事项

1. 文件上传
   - 限制文件大小和类型
   - 处理大文件分片上传
   - 保证上传安全性

2. 文件预览
   - 支持主流文件格式
   - 处理跨域问题
   - 优化预览性能

3. 权限控制
   - 严格控制文档访问权限
   - 记录文档操作日志
   - 防止未授权访问

4. 数据安全
   - 文档加密存储
   - 定期数据备份
   - 防止敏感信息泄露

5. 性能优化
   - 文档缓存策略
   - 分页加载优化
   - 大文件处理优化

## 5. 权限控制

### 5.1 角色权限
| 功能模块     | 管理员   | 律所主任   | 合伙人      | 执业律师      | 实习律师   | 行政人员    |
|------------|----------|------------|------------|--------------|------------|------------|
| 查看文档     | ✓全所    | ✓全所      | ✓团队      | ✓个人+团队    | ✓个人      | ✓部门相关   |
| 上传文档     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门相关   |
| 编辑文档     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | ✓部门相关   |
| 删除文档     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | ✓部门相关   |
| 分类管理     | ✓全所    | ✓全所      | ✓团队      | -            | -          | ✓部门相关   |
| 权限设置     | ✓全所    | ✓全所      | ✓团队      | -            | -          | -          |
| 文档导出     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门相关   |
| OCR处理     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | ✓部门相关   |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任：可查看和管理所有文档
   - 合伙人：可查看和管理本团队的文档
   - 执业律师：可查看个人创建的文档和团队共享的文档
   - 实习律师：仅可查看和上传个人文档
   - 行政人员：可查看与本部门相关的文档

2. 特殊权限说明
   - 文档上传：所有用户都可以上传个人文档
   - 文档编辑：只能编辑有权限的文档
   - 文档删除：只有管理员、律所主任和创建者可以删除
   - 文档分享：需要考虑数据保密性和分级管理

3. 文档分级权限
   - 公开文档：所有人可见
   - 部门文档：部门内可见
   - 团队文档：团队内可见
   - 个人文档：仅创建者可见
   - 保密文档：需要特殊授权

### 5.3 路由配置
```typescript
// router/modules/document.ts
export default {
  path: '/document',
  name: 'Document',
  component: LAYOUT,
  meta: {
    title: '文档管理',
    icon: 'ant-design:file-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_finance']
  },
  children: [
    {
      path: 'list',
      name: 'DocumentList',
      component: () => import('@/views/document/list/index.vue'),
      meta: {
        title: '文档列表'
      }
    },
    {
      path: 'category',
      name: 'DocumentCategory',
      component: () => import('@/views/document/category/index.vue'),
      meta: {
        title: '分类管理',
        roles: ['admin', 'director']
      }
    },
    {
      path: 'detail/:id',
      name: 'DocumentDetail',
      component: () => import('@/views/document/detail/index.vue'),
      meta: {
        title: '文档详情',
        hideMenu: true
      }
    }
  ]
};
```
