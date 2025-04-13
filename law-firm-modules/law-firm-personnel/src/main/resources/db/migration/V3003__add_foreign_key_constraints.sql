-- 人事和组织模块外键约束添加脚本
-- 此脚本在所有基础表创建完成后执行，添加所有必要的外键约束

-- 阶段2：添加表之间的外键约束
-- 按照依赖关系顺序添加约束，避免循环依赖问题

-- 1. 添加personnel_employee与personnel_person的外键约束
ALTER TABLE personnel_employee 
ADD CONSTRAINT fk_employee_person FOREIGN KEY (id) 
REFERENCES personnel_person (id) ON DELETE CASCADE ON UPDATE CASCADE;

-- 2. 添加personnel_lawyer与personnel_employee的外键约束
ALTER TABLE personnel_lawyer 
ADD CONSTRAINT fk_lawyer_employee FOREIGN KEY (id) 
REFERENCES personnel_employee (id) ON DELETE CASCADE ON UPDATE CASCADE;

-- 3. 添加personnel_staff与personnel_employee的外键约束
ALTER TABLE personnel_staff 
ADD CONSTRAINT fk_staff_employee FOREIGN KEY (id) 
REFERENCES personnel_employee (id) ON DELETE CASCADE ON UPDATE CASCADE;

-- 4. 添加organization_position与organization_department的外键约束
ALTER TABLE organization_position 
ADD CONSTRAINT fk_position_department FOREIGN KEY (department_id) 
REFERENCES organization_department (id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 5. 添加organization_team与organization_department的外键约束
ALTER TABLE organization_team 
ADD CONSTRAINT fk_team_department FOREIGN KEY (department_id) 
REFERENCES organization_department (id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 6. 添加organization_team_member与organization_team的外键约束
ALTER TABLE organization_team_member 
ADD CONSTRAINT fk_team_member_team FOREIGN KEY (team_id) 
REFERENCES organization_team (id) ON DELETE CASCADE ON UPDATE CASCADE;

-- 7. 添加organization_team_member与personnel_employee的外键约束
ALTER TABLE organization_team_member 
ADD CONSTRAINT fk_team_member_employee FOREIGN KEY (employee_id) 
REFERENCES personnel_employee (id) ON DELETE CASCADE ON UPDATE CASCADE;

-- 8. 添加personnel_employee与organization_department的外键约束
ALTER TABLE personnel_employee 
ADD CONSTRAINT fk_employee_department FOREIGN KEY (department_id) 
REFERENCES organization_department (id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 9. 添加personnel_employee与organization_position的外键约束
ALTER TABLE personnel_employee 
ADD CONSTRAINT fk_employee_position FOREIGN KEY (position_id) 
REFERENCES organization_position (id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 10. 添加personnel_person与sys_user的外键约束（注意：sys_user表由auth模块创建）
-- 这个约束在auth模块的V2003__add_employee_constraint.sql中已添加
-- 此处不再重复添加，避免冲突 