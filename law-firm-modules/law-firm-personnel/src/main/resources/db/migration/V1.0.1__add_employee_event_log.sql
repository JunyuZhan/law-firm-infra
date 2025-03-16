-- 创建员工事件日志表
CREATE TABLE IF NOT EXISTS `employee_event_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `employee_id` bigint(20) NOT NULL COMMENT '员工ID',
  `event_type` varchar(50) NOT NULL COMMENT '事件类型: CREATED-创建, STATUS_CHANGED-状态变更, POSITION_CHANGED-职位变更, etc',
  `event_time` datetime NOT NULL COMMENT '事件发生时间',
  `original_value` varchar(255) DEFAULT NULL COMMENT '原始值',
  `new_value` varchar(255) DEFAULT NULL COMMENT '新值',
  `source` varchar(50) NOT NULL DEFAULT 'SYSTEM' COMMENT '事件来源: SYSTEM-系统, UI-用户界面, API-接口调用',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `operator_name` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  KEY `idx_employee_id` (`employee_id`),
  KEY `idx_event_type` (`event_type`),
  KEY `idx_event_time` (`event_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工事件日志表';

-- 创建员工通知记录表
CREATE TABLE IF NOT EXISTS `employee_notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `employee_id` bigint(20) NOT NULL COMMENT '员工ID',
  `notification_type` varchar(50) NOT NULL COMMENT '通知类型: WELCOME-入职欢迎, BIRTHDAY-生日祝福, CONTRACT_EXPIRY-合同到期, RESIGN-离职',
  `subject` varchar(200) NOT NULL COMMENT '通知主题',
  `content` text NOT NULL COMMENT '通知内容',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态: 0-未发送, 1-已发送, 2-发送失败',
  `receiver` varchar(100) NOT NULL COMMENT '接收人(邮箱或其他联系方式)',
  `cc` varchar(255) DEFAULT NULL COMMENT '抄送人',
  `error_message` varchar(500) DEFAULT NULL COMMENT '错误信息',
  `retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '重试次数',
  `last_retry_time` datetime DEFAULT NULL COMMENT '最后重试时间',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  KEY `idx_employee_id` (`employee_id`),
  KEY `idx_notification_type` (`notification_type`),
  KEY `idx_status` (`status`),
  KEY `idx_send_time` (`send_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工通知记录表'; 