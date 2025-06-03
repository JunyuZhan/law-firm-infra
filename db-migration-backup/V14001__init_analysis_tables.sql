-- 分析模块表结构初始化
-- 版本: V14001
-- 模块: analysis
-- 创建时间: 2024-05-10
-- 说明: 创建分析任务及历史相关表结构，基于实体类定义

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- analysis_task表（分析任务表）
CREATE TABLE IF NOT EXISTS analysis_task (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-启用，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  analysis_type VARCHAR(50) NOT NULL COMMENT '分析类型',
  task_status VARCHAR(50) NOT NULL COMMENT '任务状态',
  description VARCHAR(500) COMMENT '任务描述',
  PRIMARY KEY (id),
  KEY idx_analysis_type (analysis_type),
  KEY idx_task_status (task_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分析任务表';

-- analysis_task_history表（分析任务历史表）
CREATE TABLE IF NOT EXISTS analysis_task_history (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-启用，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  task_id BIGINT NOT NULL COMMENT '分析任务ID',
  analysis_type VARCHAR(50) NOT NULL COMMENT '分析类型',
  exec_status VARCHAR(50) NOT NULL COMMENT '执行状态',
  result_summary VARCHAR(1000) COMMENT '执行结果摘要',
  start_time DATETIME COMMENT '执行开始时间',
  end_time DATETIME COMMENT '执行结束时间',
  error_msg VARCHAR(1000) COMMENT '错误信息',
  PRIMARY KEY (id),
  KEY idx_task_id (task_id),
  KEY idx_analysis_type (analysis_type),
  KEY idx_exec_status (exec_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分析任务历史表';

SET FOREIGN_KEY_CHECKS = 1; 