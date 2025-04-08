-- 为工作任务表添加模块关联字段
ALTER TABLE `task` 
  ADD COLUMN `case_id` bigint(20) DEFAULT NULL COMMENT '案例ID' AFTER `parent_id`,
  ADD COLUMN `client_id` bigint(20) DEFAULT NULL COMMENT '客户ID' AFTER `case_id`,
  ADD COLUMN `schedule_id` bigint(20) DEFAULT NULL COMMENT '日程ID' AFTER `client_id`,
  ADD COLUMN `is_legal_task` tinyint(1) DEFAULT '0' COMMENT '是否法律专业任务' AFTER `schedule_id`,
  ADD COLUMN `document_ids` varchar(1000) DEFAULT NULL COMMENT '关联文档ID列表（JSON数组）' AFTER `is_legal_task`,
  ADD COLUMN `department_id` bigint(20) DEFAULT NULL COMMENT '所属部门ID' AFTER `document_ids`,
  ADD INDEX `idx_task_case_id` (`case_id`),
  ADD INDEX `idx_task_client_id` (`client_id`),
  ADD INDEX `idx_task_department_id` (`department_id`);

-- 为任务附件表添加字段
ALTER TABLE `task_attachment`
  ADD COLUMN `uploader_avatar` varchar(255) DEFAULT NULL COMMENT '上传者头像' AFTER `creator_name`,
  ADD COLUMN `file_suffix` varchar(20) DEFAULT NULL COMMENT '文件后缀' AFTER `file_type`,
  ADD COLUMN `preview_url` varchar(255) DEFAULT NULL COMMENT '预览地址' AFTER `storage_path`,
  ADD COLUMN `download_url` varchar(255) DEFAULT NULL COMMENT '下载地址' AFTER `preview_url`,
  ADD COLUMN `download_count` int(11) DEFAULT '0' COMMENT '下载次数' AFTER `download_url`,
  ADD COLUMN `previewable` tinyint(1) DEFAULT '0' COMMENT '是否可预览' AFTER `download_count`,
  ADD COLUMN `file_icon` varchar(50) DEFAULT NULL COMMENT '文件图标' AFTER `previewable`; 