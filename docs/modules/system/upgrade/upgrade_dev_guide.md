# 升级开发指南

## 1. 升级包结构

### 1.1 目录结构
```plaintext
upgrade-package/
├── meta.json           # 升级包元信息
├── scripts/           # SQL脚本目录
│   ├── upgrade/      # 升级脚本
│   └── rollback/     # 回滚脚本
└── files/            # 文件目录
    ├── new/          # 新增文件
    ├── update/       # 更新文件
    └── delete.txt    # 待删除文件列表
```

### 1.2 meta.json格式
```json
{
  "version": "1.0.1",
  "type": "PATCH",
  "needBackup": true,
  "description": "修复xxx问题",
  "dependVersion": "1.0.0",
  "releaseTime": "2024-01-24 10:00:00"
}
```

字段说明：
- version: 升级包版本号
- type: 升级类型(FULL/PATCH)
- needBackup: 是否需要备份
- description: 升级说明
- dependVersion: 依赖版本
- releaseTime: 发布时间

## 2. SQL脚本规范

### 2.1 命名规范
- 升级脚本：V{version}_upgrade_{description}.sql
- 回滚脚本：V{version}_rollback_{description}.sql

### 2.2 内容规范
1. 每个SQL语句以分号结尾
2. 添加必要的注释说明
3. 保证SQL语句的兼容性
4. 避免使用特定数据库特性
5. 处理可能的错误情况

### 2.3 示例
```sql
-- 创建新表
CREATE TABLE IF NOT EXISTS sys_config (
  id BIGINT PRIMARY KEY,
  config_key VARCHAR(50) NOT NULL,
  config_value VARCHAR(500),
  create_time DATETIME,
  update_time DATETIME
);

-- 添加索引
CREATE INDEX idx_config_key ON sys_config(config_key);
```

## 3. 文件更新规范

### 3.1 新增文件
- 放置在files/new目录下
- 保持原有目录结构
- 包含完整路径信息

### 3.2 更新文件
- 放置在files/update目录下
- 保持原有目录结构
- 提供完整文件内容

### 3.3 删除文件
在delete.txt中列出需要删除的文件路径：
```plaintext
/WEB-INF/lib/old-lib.jar
/static/js/deprecated.js
/templates/unused.html
```

## 4. 测试规范

### 4.1 升级测试
1. 验证SQL脚本执行
2. 检查文件更新结果
3. 验证系统功能
4. 检查数据一致性

### 4.2 回滚测试
1. 验证回滚脚本执行
2. 检查文件恢复情况
3. 验证回滚后功能
4. 确认数据完整性

### 4.3 异常测试
1. 模拟升级中断
2. 测试网络异常情况
3. 验证空间不足处理
4. 检查并发访问影响

## 5. 发布规范

### 5.1 版本管理
1. 遵循语义化版本规范
2. 记录版本依赖关系
3. 维护版本更新日志

### 5.2 发布流程
1. 代码审查
2. 升级包测试
3. 文档更新
4. 打包发布

### 5.3 发布清单
1. 升级包文件
2. 更新说明文档
3. 安装部署文档
4. 回滚方案说明

## 6. 注意事项

1. 保证脚本的幂等性
2. 考虑大数据量场景
3. 注意性能影响
4. 做好版本控制
5. 完善错误处理
6. 保留操作日志 