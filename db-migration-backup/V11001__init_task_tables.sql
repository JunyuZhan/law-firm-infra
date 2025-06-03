-- 任务管理模块表结构初始化
-- 版本: V11001
-- 模块: task
-- 创建时间: 2023-10-01
-- 说明: 创建任务管理模块相关表结构，基于实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- work_task表（工作任务表）
CREATE TABLE IF NOT EXISTS work_task (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  title VARCHAR(200) NOT NULL COMMENT '任务标题',
  description TEXT COMMENT '任务描述',
  status INTEGER DEFAULT 0 COMMENT '任务状态（0-待处理、1-进行中、2-已完成、3-已逾期、4-已取消）',
  priority INTEGER DEFAULT 1 COMMENT '任务优先级（1-低、2-中、3-高、4-紧急）',
  start_time DATETIME COMMENT '开始时间',
  end_time DATETIME COMMENT '结束时间',
  assignee_id BIGINT COMMENT '负责人ID',
  parent_id BIGINT COMMENT '父任务ID',
  case_id BIGINT COMMENT '案件ID',
  client_id BIGINT COMMENT '客户ID',
  schedule_id BIGINT COMMENT '日程ID',
  is_legal_task BOOLEAN DEFAULT FALSE COMMENT '是否法律专业任务',
  document_ids TEXT COMMENT '关联文档ID列表（JSON数组）',
  cancel_reason VARCHAR(500) COMMENT '取消原因',
  department_id BIGINT COMMENT '所属部门ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status_column INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_assignee_id (assignee_id),
  KEY idx_parent_id (parent_id),
  KEY idx_case_id (case_id),
  KEY idx_client_id (client_id),
  KEY idx_status (status),
  KEY idx_priority (priority),
  KEY idx_end_time (end_time),
  KEY idx_department_id (department_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作任务表';

-- work_task_category表（任务分类表）
CREATE TABLE IF NOT EXISTS work_task_category (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
  category_code VARCHAR(50) NOT NULL COMMENT '分类编码',
  parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
  path VARCHAR(255) COMMENT '分类路径',
  level INTEGER DEFAULT 1 COMMENT '层级',
  icon VARCHAR(100) COMMENT '图标',
  color VARCHAR(20) COMMENT '颜色',
  description VARCHAR(500) COMMENT '描述',
  is_system BOOLEAN DEFAULT FALSE COMMENT '是否系统内置',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_category_code (category_code),
  KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务分类表';

-- work_task_tag表（任务标签表）
CREATE TABLE IF NOT EXISTS work_task_tag (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
  tag_color VARCHAR(20) COMMENT '标签颜色',
  tag_type INTEGER DEFAULT 0 COMMENT '标签类型（0-通用、1-法律事务、2-行政事务）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_tag_name (tag_name),
  KEY idx_tag_type (tag_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务标签表';

-- work_task_tag_relation表（任务标签关联表）
CREATE TABLE IF NOT EXISTS work_task_tag_relation (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  task_id BIGINT NOT NULL COMMENT '任务ID',
  tag_id BIGINT NOT NULL COMMENT '标签ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_task_tag (task_id, tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务标签关联表';

-- work_task_comment表（任务评论表）
CREATE TABLE IF NOT EXISTS work_task_comment (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  task_id BIGINT NOT NULL COMMENT '任务ID',
  content TEXT NOT NULL COMMENT '评论内容',
  user_id BIGINT NOT NULL COMMENT '评论用户ID',
  user_name VARCHAR(100) COMMENT '评论用户名称',
  parent_id BIGINT COMMENT '父评论ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_task_id (task_id),
  KEY idx_user_id (user_id),
  KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务评论表';

-- work_task_attachment表（任务附件表）
CREATE TABLE IF NOT EXISTS work_task_attachment (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  task_id BIGINT NOT NULL COMMENT '任务ID',
  file_name VARCHAR(200) NOT NULL COMMENT '文件名称',
  file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
  file_size BIGINT COMMENT '文件大小',
  file_type VARCHAR(50) COMMENT '文件类型',
  storage_id BIGINT COMMENT '存储ID',
  uploader_id BIGINT COMMENT '上传者ID',
  uploader_name VARCHAR(100) COMMENT '上传者名称',
  download_count INTEGER DEFAULT 0 COMMENT '下载次数',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_task_id (task_id),
  KEY idx_uploader_id (uploader_id),
  KEY idx_file_type (file_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务附件表';

-- work_task_assignee表（任务分配记录表）
CREATE TABLE IF NOT EXISTS work_task_assignee (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  task_id BIGINT NOT NULL COMMENT '任务ID',
  assignee_id BIGINT NOT NULL COMMENT '负责人ID',
  assignee_name VARCHAR(100) COMMENT '负责人名称',
  assignor_id BIGINT COMMENT '分配者ID',
  assignor_name VARCHAR(100) COMMENT '分配者名称',
  assign_time DATETIME NOT NULL COMMENT '分配时间',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  PRIMARY KEY (id),
  KEY idx_task_id (task_id),
  KEY idx_assignee_id (assignee_id),
  KEY idx_assignor_id (assignor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务分配记录表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;
