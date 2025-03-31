-- 任务表
CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_name` varchar(100) NOT NULL COMMENT '任务名称',
  `task_description` text DEFAULT NULL COMMENT '任务描述',
  `priority` tinyint(4) DEFAULT 3 COMMENT '优先级(1-5,1最高)',
  `status` varchar(20) NOT NULL DEFAULT 'pending' COMMENT '状态(pending-待处理,processing-处理中,completed-已完成,canceled-已取消)',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `deadline` datetime DEFAULT NULL COMMENT '截止时间',
  `estimated_hours` decimal(10,2) DEFAULT NULL COMMENT '预计工时',
  `actual_hours` decimal(10,2) DEFAULT NULL COMMENT '实际工时',
  `completion_rate` tinyint(4) DEFAULT 0 COMMENT '完成度(0-100)',
  `creator_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `creator_name` varchar(50) NOT NULL COMMENT '创建人姓名',
  `assignee_id` bigint(20) DEFAULT NULL COMMENT '负责人ID',
  `assignee_name` varchar(50) DEFAULT NULL COMMENT '负责人姓名',
  `case_id` bigint(20) DEFAULT NULL COMMENT '关联案件ID',
  `client_id` bigint(20) DEFAULT NULL COMMENT '关联客户ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父任务ID',
  `is_milestone` tinyint(1) DEFAULT 0 COMMENT '是否里程碑(0-否,1-是)',
  `is_recurring` tinyint(1) DEFAULT 0 COMMENT '是否重复任务(0-否,1-是)',
  `recurring_rule` varchar(255) DEFAULT NULL COMMENT '重复规则',
  `task_type` varchar(20) DEFAULT 'normal' COMMENT '任务类型(normal-普通任务,meeting-会议任务,review-审核任务)',
  `reminder_time` datetime DEFAULT NULL COMMENT '提醒时间',
  `remind_before_mins` int(11) DEFAULT NULL COMMENT '提前提醒分钟数',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  INDEX `idx_task_assignee_id` (`assignee_id`),
  INDEX `idx_task_creator_id` (`creator_id`),
  INDEX `idx_task_case_id` (`case_id`),
  INDEX `idx_task_client_id` (`client_id`),
  INDEX `idx_task_status` (`status`),
  INDEX `idx_task_deadline` (`deadline`),
  INDEX `idx_task_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- 任务标签表
CREATE TABLE IF NOT EXISTS `task_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tag_name` varchar(50) NOT NULL COMMENT '标签名称',
  `tag_color` varchar(20) DEFAULT NULL COMMENT '标签颜色',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tag_name` (`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务标签表';

-- 任务标签关联表
CREATE TABLE IF NOT EXISTS `task_tag_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_task_tag` (`task_id`,`tag_id`),
  CONSTRAINT `fk_tag_relation_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_tag_relation_task_tag` FOREIGN KEY (`tag_id`) REFERENCES `task_tag` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务标签关联表';

-- 任务参与者表
CREATE TABLE IF NOT EXISTS `task_participant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_name` varchar(50) NOT NULL COMMENT '用户姓名',
  `role` varchar(20) DEFAULT 'member' COMMENT '角色(owner-负责人,member-参与者,follower-关注者)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_task_participant` (`task_id`,`user_id`),
  INDEX `idx_task_participant_user_id` (`user_id`),
  CONSTRAINT `fk_participant_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务参与者表';

-- 任务评论表
CREATE TABLE IF NOT EXISTS `task_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务ID',
  `content` text NOT NULL COMMENT '评论内容',
  `creator_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `creator_name` varchar(50) NOT NULL COMMENT '创建人姓名',
  `reply_to_id` bigint(20) DEFAULT NULL COMMENT '回复的评论ID',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_task_comment_task_id` (`task_id`),
  INDEX `idx_task_comment_creator_id` (`creator_id`),
  CONSTRAINT `fk_comment_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务评论表';

-- 任务进度记录表
CREATE TABLE IF NOT EXISTS `task_progress` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务ID',
  `progress` tinyint(4) NOT NULL COMMENT '进度百分比(0-100)',
  `description` varchar(500) DEFAULT NULL COMMENT '进度描述',
  `hours_spent` decimal(10,2) DEFAULT NULL COMMENT '耗时(小时)',
  `creator_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `creator_name` varchar(50) NOT NULL COMMENT '创建人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `idx_task_progress_task_id` (`task_id`),
  CONSTRAINT `fk_progress_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务进度记录表';

-- 任务附件表
CREATE TABLE IF NOT EXISTS `task_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务ID',
  `file_name` varchar(100) NOT NULL COMMENT '文件名',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小(字节)',
  `file_type` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `storage_path` varchar(255) NOT NULL COMMENT '存储路径',
  `creator_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `creator_name` varchar(50) NOT NULL COMMENT '创建人姓名',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `idx_task_attachment_task_id` (`task_id`),
  CONSTRAINT `fk_attachment_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务附件表';

-- 任务历史表
CREATE TABLE IF NOT EXISTS `task_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务ID',
  `action_type` varchar(20) NOT NULL COMMENT '操作类型(create-创建,update-更新,assign-分配,comment-评论,status_change-状态变更)',
  `action_description` varchar(500) NOT NULL COMMENT '操作描述',
  `field_name` varchar(50) DEFAULT NULL COMMENT '字段名称',
  `old_value` varchar(500) DEFAULT NULL COMMENT '旧值',
  `new_value` varchar(500) DEFAULT NULL COMMENT '新值',
  `operator_id` bigint(20) NOT NULL COMMENT '操作人ID',
  `operator_name` varchar(50) NOT NULL COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `idx_task_history_task_id` (`task_id`),
  INDEX `idx_task_history_create_time` (`create_time`),
  CONSTRAINT `fk_history_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务历史表'; 