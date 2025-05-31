<template>
  <div class="onlyoffice-editor">
    <div class="editor-toolbar" v-if="showToolbar">
      <div class="toolbar-left">
        <span class="document-title">{{ documentTitle }}</span>
        <span class="edit-status" :class="editStatusClass">{{ editStatusText }}</span>
      </div>
      <div class="toolbar-right">
        <a-button @click="saveDocument" :loading="saving" type="primary">
          保存
        </a-button>
        <a-button @click="closeEditor" :disabled="hasUnsavedChanges">
          关闭
        </a-button>
      </div>
    </div>
    
    <!-- OnlyOffice编辑器容器 -->
    <div 
      :id="editorId" 
      class="onlyoffice-container"
      :style="{ height: editorHeight }"
    ></div>

    <!-- 加载状态 -->
    <div v-if="loading" class="editor-loading">
      <a-spin size="large">
        <template #tip>正在加载文档编辑器...</template>
      </a-spin>
    </div>

    <!-- 错误提示 -->
    <div v-if="error" class="editor-error">
      <a-result
        status="error"
        :title="error"
        sub-title="请检查OnlyOffice服务器是否正常运行"
      >
        <template #extra>
          <a-button type="primary" @click="retryLoad">重试</a-button>
        </template>
      </a-result>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, computed } from 'vue';
import { message } from 'ant-design-vue';

// Props
interface Props {
  documentId: number;
  height?: string;
  showToolbar?: boolean;
  readonly?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  height: '600px',
  showToolbar: true,
  readonly: false
});

// Emits
const emit = defineEmits<{
  save: [documentId: number];
  close: [];
  ready: [];
  error: [error: string];
}>();

// 响应式数据
const loading = ref(true);
const error = ref('');
const saving = ref(false);
const hasUnsavedChanges = ref(false);
const documentTitle = ref('');
const editStatus = ref('ready'); // ready, editing, saving, saved
const editorId = ref(`onlyoffice-editor-${Date.now()}`);

// 编辑器实例
let docEditor: any = null;

// 计算属性
const editorHeight = computed(() => {
  return props.showToolbar ? `calc(${props.height} - 50px)` : props.height;
});

const editStatusClass = computed(() => ({
  'status-ready': editStatus.value === 'ready',
  'status-editing': editStatus.value === 'editing',
  'status-saving': editStatus.value === 'saving',
  'status-saved': editStatus.value === 'saved'
}));

const editStatusText = computed(() => {
  const statusMap = {
    ready: '就绪',
    editing: '编辑中',
    saving: '保存中',
    saved: '已保存'
  };
  return statusMap[editStatus.value as keyof typeof statusMap] || '未知';
});

// 方法
const loadEditor = async () => {
  try {
    loading.value = true;
    error.value = '';

    // 获取编辑器配置
    const response = await fetch(`/api/v1/documents/onlyoffice/config/${props.documentId}`, {
      headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      throw new Error(`获取编辑器配置失败: ${response.status}`);
    }

    const result = await response.json();
    if (!result.success) {
      throw new Error(result.message || '获取编辑器配置失败');
    }

    const config = result.data;
    documentTitle.value = config.document.title;

    // 初始化OnlyOffice编辑器
    initEditor(config);

  } catch (err) {
    console.error('加载OnlyOffice编辑器失败:', err);
    error.value = err instanceof Error ? err.message : '加载编辑器失败';
    emit('error', error.value);
  } finally {
    loading.value = false;
  }
};

const initEditor = (config: any) => {
  // 确保OnlyOffice脚本已加载
  if (typeof window.DocsAPI === 'undefined') {
    loadOnlyOfficeScript().then(() => {
      createEditor(config);
    });
  } else {
    createEditor(config);
  }
};

const createEditor = (config: any) => {
  // 扩展配置
  const editorConfig = {
    ...config,
    width: '100%',
    height: '100%',
    documentType: getDocumentType(config.document.fileType),
    events: {
      onAppReady: () => {
        console.log('OnlyOffice编辑器就绪');
        editStatus.value = 'ready';
        emit('ready');
      },
      onDocumentStateChange: (event: any) => {
        hasUnsavedChanges.value = event.data;
        editStatus.value = event.data ? 'editing' : 'saved';
      },
      onRequestSaveAs: (event: any) => {
        console.log('请求另存为:', event);
      },
      onRequestRename: (event: any) => {
        console.log('请求重命名:', event);
        // 可以实现重命名功能
      },
      onError: (event: any) => {
        console.error('OnlyOffice编辑器错误:', event);
        error.value = `编辑器错误: ${event.data}`;
        emit('error', error.value);
      }
    }
  };

  // 创建编辑器实例
  docEditor = new window.DocsAPI.DocEditor(editorId.value, editorConfig);
};

const loadOnlyOfficeScript = (): Promise<void> => {
  return new Promise((resolve, reject) => {
    if (document.querySelector('script[src*="documentserver"]')) {
      resolve();
      return;
    }

    const script = document.createElement('script');
    script.src = 'http://localhost:8088/web-apps/apps/api/documents/api.js';
    script.onload = () => resolve();
    script.onerror = () => reject(new Error('加载OnlyOffice脚本失败'));
    document.head.appendChild(script);
  });
};

const getDocumentType = (fileType: string) => {
  const wordTypes = ['doc', 'docx', 'odt', 'rtf', 'txt'];
  const cellTypes = ['xls', 'xlsx', 'ods', 'csv'];
  const slideTypes = ['ppt', 'pptx', 'odp'];

  if (wordTypes.includes(fileType)) return 'word';
  if (cellTypes.includes(fileType)) return 'cell';
  if (slideTypes.includes(fileType)) return 'slide';
  return 'word'; // 默认
};

const saveDocument = async () => {
  if (!docEditor) return;

  try {
    saving.value = true;
    editStatus.value = 'saving';

    // 调用OnlyOffice的保存方法
    docEditor.requestSave();
    
    // 等待保存完成（通过回调处理）
    await new Promise((resolve) => {
      const checkSaved = () => {
        if (!hasUnsavedChanges.value) {
          resolve(void 0);
        } else {
          setTimeout(checkSaved, 100);
        }
      };
      checkSaved();
    });

    message.success('文档保存成功');
    editStatus.value = 'saved';
    emit('save', props.documentId);

  } catch (err) {
    console.error('保存文档失败:', err);
    message.error('保存文档失败');
    editStatus.value = 'editing';
  } finally {
    saving.value = false;
  }
};

const closeEditor = () => {
  if (hasUnsavedChanges.value) {
    message.warning('文档有未保存的更改，请先保存');
    return;
  }
  
  if (docEditor) {
    docEditor.destroyEditor();
    docEditor = null;
  }
  
  emit('close');
};

const retryLoad = () => {
  error.value = '';
  loadEditor();
};

const getToken = () => {
  // 从localStorage或其他地方获取认证token
  return localStorage.getItem('access_token') || '';
};

// 生命周期
onMounted(() => {
  loadEditor();
});

onUnmounted(() => {
  if (docEditor) {
    docEditor.destroyEditor();
  }
});

// 监听页面刷新/关闭事件
window.addEventListener('beforeunload', (event) => {
  if (hasUnsavedChanges.value) {
    event.preventDefault();
    event.returnValue = '您有未保存的更改，确定要离开吗？';
  }
});
</script>

<style scoped>
.onlyoffice-editor {
  width: 100%;
  height: 100%;
  position: relative;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  overflow: hidden;
}

.editor-toolbar {
  height: 50px;
  background: #fafafa;
  border-bottom: 1px solid #d9d9d9;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
}

.toolbar-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.document-title {
  font-weight: 500;
  color: #262626;
}

.edit-status {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 12px;
  background: #f0f0f0;
  color: #666;
}

.status-ready { background: #e6f7ff; color: #1890ff; }
.status-editing { background: #fff7e6; color: #fa8c16; }
.status-saving { background: #f6ffed; color: #52c41a; }
.status-saved { background: #f0f5ff; color: #722ed1; }

.toolbar-right {
  display: flex;
  gap: 8px;
}

.onlyoffice-container {
  width: 100%;
  background: #fff;
}

.editor-loading {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.9);
  display: flex;
  align-items: center;
  justify-content: center;
}

.editor-error {
  padding: 50px 20px;
}
</style> 