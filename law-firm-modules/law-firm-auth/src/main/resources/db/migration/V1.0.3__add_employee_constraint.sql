-- 添加auth_user表中的employee_id外键约束
-- 确保auth_user表有employee_id字段
ALTER TABLE `auth_user` ADD COLUMN IF NOT EXISTS `employee_id` bigint(20) DEFAULT NULL COMMENT '关联员工ID';

-- 添加外键约束
ALTER TABLE `auth_user` 
ADD CONSTRAINT `fk_user_employee` FOREIGN KEY (`employee_id`) 
REFERENCES `personnel_employee` (`id`) ON DELETE SET NULL; 