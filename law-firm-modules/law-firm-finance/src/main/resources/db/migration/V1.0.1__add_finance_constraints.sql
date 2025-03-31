-- 添加财务模块与组织部门的外键约束
ALTER TABLE `finance_expense_record` 
ADD CONSTRAINT `fk_expense_department` FOREIGN KEY (`department_id`) 
REFERENCES `organization_department` (`id`);

-- 添加财务模块与申请人(员工)的外键约束
ALTER TABLE `finance_expense_record` 
ADD CONSTRAINT `fk_expense_applicant` FOREIGN KEY (`applicant_id`) 
REFERENCES `personnel_employee` (`id`);

-- 添加财务模块与审批人(员工)的外键约束
ALTER TABLE `finance_expense_record` 
ADD CONSTRAINT `fk_expense_approver` FOREIGN KEY (`approver_id`) 
REFERENCES `personnel_employee` (`id`);

-- 添加财务模块与案件的外键约束（如果存在案件表）
-- ALTER TABLE `finance_fee_record` 
-- ADD CONSTRAINT `fk_fee_case` FOREIGN KEY (`case_id`) 
-- REFERENCES `case_info` (`id`);

-- 添加财务模块与客户的外键约束（如果存在客户表）
-- ALTER TABLE `finance_fee_record` 
-- ADD CONSTRAINT `fk_fee_client` FOREIGN KEY (`client_id`) 
-- REFERENCES `client_info` (`id`); 