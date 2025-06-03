-- 知识库模块表结构初始化
-- 版本: V9001
-- 模块: knowledge
-- 创建时间: 2023-10-01
-- 说明: 创建知识库管理模块相关表结构，基于实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- knowledge_category表（知识分类表）
CREATE TABLE IF NOT EXISTS knowledge_category (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  name VARCHAR(100) NOT NULL COMMENT '分类名称',
  code VARCHAR(50) NOT NULL COMMENT '分类编码',
  parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
  path VARCHAR(255) COMMENT '分类路径（从根到当前节点的路径，用/分隔）',
  level INTEGER DEFAULT 1 COMMENT '分类层级（从1开始）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  remarks VARCHAR(500) COMMENT '备注',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_category_code (code),
  KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识分类表';

-- knowledge_tag表（知识标签表）
CREATE TABLE IF NOT EXISTS knowledge_tag (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  name VARCHAR(50) NOT NULL COMMENT '标签名称',
  code VARCHAR(50) NOT NULL COMMENT '标签编码',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  remarks VARCHAR(255) COMMENT '备注',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_tag_code (code),
  KEY idx_tag_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识标签表';

-- knowledge_document表（知识文档表）
CREATE TABLE IF NOT EXISTS knowledge_document (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  title VARCHAR(200) NOT NULL COMMENT '知识标题',
  knowledge_type VARCHAR(50) NOT NULL COMMENT '知识类型',
  content TEXT COMMENT '知识内容',
  summary VARCHAR(500) COMMENT '知识摘要',
  category_id BIGINT COMMENT '分类ID',
  keywords VARCHAR(255) COMMENT '关键词（逗号分隔）',
  author_id BIGINT COMMENT '作者ID',
  author_name VARCHAR(100) COMMENT '作者名称',
  status INTEGER DEFAULT 0 COMMENT '状态（0-草稿 1-已发布 2-已归档）',
  like_count INTEGER DEFAULT 0 COMMENT '点赞次数',
  view_count INTEGER DEFAULT 0 COMMENT '浏览次数',
  remarks VARCHAR(500) COMMENT '备注',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_category_id (category_id),
  KEY idx_knowledge_type (knowledge_type),
  KEY idx_author_id (author_id),
  KEY idx_status (status),
  KEY idx_title (title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识文档表';

-- knowledge_tag_relation表（知识标签关联表）
CREATE TABLE IF NOT EXISTS knowledge_tag_relation (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  knowledge_id BIGINT NOT NULL COMMENT '知识ID',
  tag_id BIGINT NOT NULL COMMENT '标签ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_knowledge_tag (knowledge_id, tag_id),
  KEY idx_tag_id (tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识标签关联表';

-- knowledge_attachment表（知识文档附件表）
CREATE TABLE IF NOT EXISTS knowledge_attachment (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  knowledge_id BIGINT NOT NULL COMMENT '知识ID',
  file_name VARCHAR(200) NOT NULL COMMENT '文件名称',
  file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
  file_size BIGINT COMMENT '文件大小（字节）',
  file_type VARCHAR(50) COMMENT '文件类型',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  storage_id BIGINT COMMENT '存储ID（关联存储系统）',
  remarks VARCHAR(255) COMMENT '备注',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_knowledge_id (knowledge_id),
  KEY idx_file_type (file_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识文档附件表';

-- 知识库评论表
CREATE TABLE IF NOT EXISTS knowledge_comment (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  knowledge_id BIGINT NOT NULL COMMENT '知识ID',
  content TEXT NOT NULL COMMENT '评论内容',
  user_id BIGINT NOT NULL COMMENT '评论用户ID',
  user_name VARCHAR(100) COMMENT '评论用户名称',
  parent_id BIGINT COMMENT '父评论ID',
  reply_user_id BIGINT COMMENT '被回复用户ID',
  reply_user_name VARCHAR(100) COMMENT '被回复用户名称',
  like_count INTEGER DEFAULT 0 COMMENT '点赞数',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常 1-已屏蔽）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_knowledge_id (knowledge_id),
  KEY idx_user_id (user_id),
  KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识库评论表';

-- 知识库收藏表
CREATE TABLE IF NOT EXISTS knowledge_favorite (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  knowledge_id BIGINT NOT NULL COMMENT '知识ID',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  group_id BIGINT COMMENT '收藏分组ID',
  note VARCHAR(255) COMMENT '收藏备注',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_user_knowledge (user_id, knowledge_id),
  KEY idx_knowledge_id (knowledge_id),
  KEY idx_group_id (group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识库收藏表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 