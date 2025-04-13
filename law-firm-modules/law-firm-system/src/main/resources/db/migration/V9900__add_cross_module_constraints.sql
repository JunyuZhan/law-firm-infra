-- 全局跨模块外键约束脚本 (版本号V9900，避免与知识库模块的V9000系列冲突)
-- 此脚本集中管理所有跨模块的外键约束关系
-- 注意：执行此脚本前，确保所有相关表已创建

-- -----------------------------------------------------
-- 1. auth模块与personnel模块之间的约束
-- -----------------------------------------------------

-- 用户与人员关联约束 (auth→personnel)
ALTER TABLE personnel_person ADD CONSTRAINT fk_person_user_id FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- -----------------------------------------------------
-- 2. finance模块与其他模块之间的约束
-- -----------------------------------------------------

-- 财务与部门的外键约束 (finance→personnel)
ALTER TABLE finance_expense_record ADD CONSTRAINT fk_expense_department FOREIGN KEY (department_id) REFERENCES organization_department(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 财务与申请人的外键约束 (finance→personnel)
ALTER TABLE finance_expense_record ADD CONSTRAINT fk_expense_applicant FOREIGN KEY (applicant_id) REFERENCES personnel_employee(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 财务与审批人的外键约束 (finance→personnel)
ALTER TABLE finance_expense_record ADD CONSTRAINT fk_expense_approver FOREIGN KEY (approver_id) REFERENCES personnel_employee(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 财务与案件的外键约束 (finance→case)
ALTER TABLE finance_fee_record ADD CONSTRAINT fk_fee_case FOREIGN KEY (case_id) REFERENCES case_info(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 财务与客户的外键约束 (finance→client)
ALTER TABLE finance_fee_record ADD CONSTRAINT fk_fee_client FOREIGN KEY (client_id) REFERENCES client_info(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- -----------------------------------------------------
-- 3. knowledge模块与其他模块之间的约束
-- -----------------------------------------------------

-- 知识文档与作者(员工)的外键约束 (knowledge→personnel)
ALTER TABLE knowledge_document ADD CONSTRAINT fk_knowledge_author FOREIGN KEY (author_id) REFERENCES personnel_employee(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- -----------------------------------------------------
-- 4. case模块与其他模块之间的约束
-- -----------------------------------------------------

-- 案件与客户的外键约束 (case→client)
ALTER TABLE case_info ADD CONSTRAINT fk_case_client FOREIGN KEY (client_id) REFERENCES client_info(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 案件与律师的外键约束 (case→personnel)
ALTER TABLE case_info ADD CONSTRAINT fk_case_lawyer FOREIGN KEY (lawyer_id) REFERENCES personnel_lawyer(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 案件团队成员与用户的外键约束 (case→auth)
ALTER TABLE case_team_member ADD CONSTRAINT fk_case_team_member_user FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- 5. task模块与其他模块之间的约束
-- -----------------------------------------------------

-- 任务与案件的外键约束 (task→case)
ALTER TABLE task ADD CONSTRAINT fk_task_case FOREIGN KEY (case_id) REFERENCES case_info(id) ON DELETE CASCADE ON UPDATE CASCADE;

-- 任务与客户的外键约束 (task→client)
ALTER TABLE task ADD CONSTRAINT fk_task_client FOREIGN KEY (client_id) REFERENCES client_info(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- -----------------------------------------------------
-- 6. schedule模块与其他模块之间的约束
-- -----------------------------------------------------

-- 日程与案件关联表的外键约束 (schedule→case)
ALTER TABLE schedule_case_relation ADD CONSTRAINT fk_schedule_case FOREIGN KEY (case_id) REFERENCES case_info(id) ON DELETE CASCADE ON UPDATE CASCADE;

-- 日程与任务关联表的外键约束 (schedule→task)
ALTER TABLE schedule_task_relation ADD CONSTRAINT fk_schedule_task FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- 7. client模块与其他模块之间的约束
-- -----------------------------------------------------

-- 客户与负责律师的外键约束 (client→personnel)
ALTER TABLE client_info ADD CONSTRAINT fk_client_lawyer FOREIGN KEY (responsible_lawyer_id) REFERENCES personnel_lawyer(id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 客户跟进记录与跟进人的外键约束 (client→personnel)
ALTER TABLE client_follow_up ADD CONSTRAINT fk_follow_up_employee FOREIGN KEY (follow_up_by) REFERENCES personnel_employee(id) ON DELETE SET NULL ON UPDATE CASCADE; 