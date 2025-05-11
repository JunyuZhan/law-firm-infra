-- 档案模块表结构初始化
-- 版本: V12001
-- 模块: archive
-- 创建时间: 2025-04-26
-- 说明: 创建档案管理相关表，基于实体类定义
-- 依赖: V0001基础表结构

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 检查依赖表是否存在
SELECT 1 FROM information_schema.tables 
WHERE table_schema = DATABASE() 
  AND table_name IN ('sys_dict_type', 'sys_dict_data');

-- archive_main表（档案主表）
CREATE TABLE IF NOT EXISTS archive_main (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  archive_no VARCHAR(100) NOT NULL COMMENT '档案编号',
  source_type INTEGER COMMENT '来源类型（1-案件，2-合同，3-文档，4-行政）',
  source_id BIGINT COMMENT '来源ID',
  title VARCHAR(200) NOT NULL COMMENT '档案标题',
  archive_time VARCHAR(50) COMMENT '归档时间',
  department_id BIGINT COMMENT '部门ID',
  handler_id BIGINT COMMENT '经办人ID',
  handler_name VARCHAR(100) COMMENT '经办人姓名',
  status INTEGER DEFAULT 1 COMMENT '档案状态（1-已归档，2-已同步，3-已销毁）',
  sync_time VARCHAR(50) COMMENT '同步时间',
  is_synced INTEGER DEFAULT 0 COMMENT '是否已同步（0-未同步，1-已同步）',
  archive_data LONGTEXT COMMENT '归档数据（JSON格式）',
  keywords VARCHAR(500) COMMENT '关键词',
  remark VARCHAR(500) COMMENT '备注',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_archive_no (archive_no),
  KEY idx_source (source_type, source_id),
  KEY idx_title (title(191)),
  KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='档案主表';

-- archive_file表（档案文件表）
CREATE TABLE IF NOT EXISTS archive_file (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  title VARCHAR(200) NOT NULL COMMENT '档案标题',
  archive_type INTEGER COMMENT '档案类型',
  archive_status INTEGER DEFAULT 1 COMMENT '档案状态',
  archive_no VARCHAR(100) COMMENT '档案编号',
  business_id VARCHAR(100) COMMENT '相关业务ID（案件ID/合同ID等）',
  business_type VARCHAR(50) COMMENT '业务类型（案件/合同等）',
  file_path VARCHAR(500) COMMENT '文件路径',
  file_size BIGINT COMMENT '文件大小（KB）',
  file_type VARCHAR(50) COMMENT '文件类型',
  storage_location VARCHAR(100) COMMENT '存储位置编码',
  archive_time DATETIME COMMENT '归档时间',
  archive_user_id VARCHAR(50) COMMENT '归档人ID',
  archive_user_name VARCHAR(100) COMMENT '归档人姓名',
  borrow_status INTEGER DEFAULT 0 COMMENT '借阅状态（0-未借出，1-已借出）',
  borrower_id VARCHAR(50) COMMENT '借阅人ID',
  borrower_name VARCHAR(100) COMMENT '借阅人姓名',
  borrow_time DATETIME COMMENT '借阅时间',
  expected_return_time DATETIME COMMENT '预计归还时间',
  actual_return_time DATETIME COMMENT '实际归还时间',
  sync_status INTEGER DEFAULT 0 COMMENT '同步状态（0-未同步，1-已同步）',
  sync_time DATETIME COMMENT '同步时间',
  remark VARCHAR(500) COMMENT '备注',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_archive_no (archive_no),
  KEY idx_business (business_type, business_id(100)),
  KEY idx_borrow_status (borrow_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='档案文件表';

-- case_archive表（案件档案表）
CREATE TABLE IF NOT EXISTS case_archive (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  case_id VARCHAR(100) NOT NULL COMMENT '案件ID',
  case_no VARCHAR(100) COMMENT '案件编号',
  case_title VARCHAR(200) NOT NULL COMMENT '案件标题',
  lawyer_id VARCHAR(50) COMMENT '负责律师ID',
  lawyer_name VARCHAR(100) COMMENT '负责律师姓名',
  client_id VARCHAR(50) COMMENT '客户ID',
  client_name VARCHAR(100) COMMENT '客户名称',
  case_type VARCHAR(50) COMMENT '案件类型',
  case_status VARCHAR(50) COMMENT '案件状态',
  case_amount DECIMAL(20,2) COMMENT '案件金额',
  case_start_time DATETIME COMMENT '案件开始时间',
  case_end_time DATETIME COMMENT '案件结束时间',
  archive_time DATETIME COMMENT '归档时间',
  archive_user_id VARCHAR(50) COMMENT '归档人ID',
  archive_user_name VARCHAR(100) COMMENT '归档人姓名',
  archive_remark VARCHAR(500) COMMENT '归档备注',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_case_id (case_id),
  KEY idx_case_no (case_no),
  KEY idx_case_title (case_title(191)),
  KEY idx_archive_time (archive_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='案件档案表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 