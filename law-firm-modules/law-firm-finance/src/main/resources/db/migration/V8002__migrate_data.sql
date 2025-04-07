-- 将旧收费记录表数据迁移到新表（如果旧表存在）
INSERT INTO `finance_fee_record` (
  `id`, `amount`, `paid_amount`, `fee_status`, `fee_type`, `case_id`, `client_id`, 
  `law_firm_id`, `payment_time`, `payment_method`, `description`, `remark`, 
  `tenant_id`, `create_time`, `update_time`, `create_by`, `update_by`, `deleted`
)
SELECT 
  `id`, `amount`, `paid_amount`, `fee_status`, `fee_type`, `case_id`, `client_id`, 
  `law_firm_id`, `payment_time`, `payment_method`, `description`, `remark`, 
  `law_firm_id` AS `tenant_id`, `create_time`, `update_time`, `create_by`, `update_by`, `deleted`
FROM `fee_record`
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'fee_record');

-- 将旧支出记录表数据迁移到新表（如果旧表存在）
INSERT INTO `finance_expense_record` (
  `id`, `amount`, `expense_status`, `expense_type`, `law_firm_id`, `department_id`, 
  `applicant_id`, `approver_id`, `expense_time`, `payment_method`, `description`, `remark`, 
  `tenant_id`, `create_time`, `update_time`, `create_by`, `update_by`, `deleted`
)
SELECT 
  `id`, `amount`, `expense_status`, `expense_type`, `law_firm_id`, `department_id`, 
  `applicant_id`, `approver_id`, `expense_time`, `payment_method`, `description`, `remark`, 
  `law_firm_id` AS `tenant_id`, `create_time`, `update_time`, `create_by`, `update_by`, `deleted`
FROM `expense_record`
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'expense_record');

-- 将旧发票表数据迁移到新表（如果旧表存在）
INSERT INTO `finance_invoice` (
  `id`, `title`, `type`, `amount`, `tax_rate`, `tax_amount`, `issue_date`, 
  `invoice_no`, `matter_id`, `remark`, `status`, `tenant_id`, 
  `create_time`, `update_time`, `create_by`, `update_by`, `deleted`
)
SELECT 
  `id`, `title`, `type`, `amount`, `tax_rate`, `tax_amount`, `issue_date`, 
  `invoice_no`, `matter_id`, `remark`, `status`, 1 AS `tenant_id`, 
  `create_time`, `update_time`, `creator` AS `create_by`, `updater` AS `update_by`, 0 AS `deleted`
FROM `fin_invoice`
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'fin_invoice');

-- 设置自增ID序列
SELECT @max_fee_id := IFNULL(MAX(`id`), 0) FROM `finance_fee_record`;
SET @sql = CONCAT('ALTER TABLE `finance_fee_record` AUTO_INCREMENT = ', @max_fee_id + 1);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT @max_expense_id := IFNULL(MAX(`id`), 0) FROM `finance_expense_record`;
SET @sql = CONCAT('ALTER TABLE `finance_expense_record` AUTO_INCREMENT = ', @max_expense_id + 1);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT @max_invoice_id := IFNULL(MAX(`id`), 0) FROM `finance_invoice`;
SET @sql = CONCAT('ALTER TABLE `finance_invoice` AUTO_INCREMENT = ', @max_invoice_id + 1);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt; 