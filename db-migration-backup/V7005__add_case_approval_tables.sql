-- 新增案件审批主表
CREATE TABLE IF NOT EXISTS case_approval (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  case_id BIGINT NOT NULL COMMENT '案件ID',
  approval_type VARCHAR(50) NOT NULL COMMENT '审批类型（如letter_issue等）',
  approval_title VARCHAR(200) COMMENT '审批标题',
  approval_status INTEGER DEFAULT 0 COMMENT '审批状态（0-待审批 1-审批通过 2-审批拒绝 3-已撤回）',
  initiator_id BIGINT NOT NULL COMMENT '发起人ID',
  initiator_name VARCHAR(50) COMMENT '发起人姓名',
  current_approver_id BIGINT COMMENT '当前审批人ID',
  current_approver_name VARCHAR(50) COMMENT '当前审批人姓名',
  start_time DATETIME COMMENT '开始时间',
  deadline DATETIME COMMENT '截止时间',
  completion_time DATETIME COMMENT '完成时间',
  remarks VARCHAR(500) COMMENT '备注',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY idx_case_id (case_id),
  KEY idx_approval_type (approval_type),
  KEY idx_approval_status (approval_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='案件审批主表';

-- 新增案件审批流转记录表
CREATE TABLE IF NOT EXISTS case_approval_flow (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  approval_id BIGINT NOT NULL COMMENT '审批主表ID',
  approver_id BIGINT NOT NULL COMMENT '审批人ID',
  approver_name VARCHAR(50) COMMENT '审批人姓名',
  approval_status INTEGER DEFAULT 0 COMMENT '审批状态（0-待审批 1-审批通过 2-审批拒绝 3-已撤回）',
  approval_opinion VARCHAR(500) COMMENT '审批意见',
  approval_time DATETIME COMMENT '审批时间',
  node_order INTEGER COMMENT '节点顺序',
  remarks VARCHAR(500) COMMENT '备注',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY idx_approval_id (approval_id),
  KEY idx_approver_id (approver_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='案件审批流转记录表'; 