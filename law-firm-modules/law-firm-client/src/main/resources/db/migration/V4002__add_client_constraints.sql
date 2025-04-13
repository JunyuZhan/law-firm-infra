-- 客户模块内部表之间的外键约束
-- 此脚本添加客户模块内表之间的外键约束，确保数据完整性

-- 1. 添加客户联系人表与客户信息表的外键约束
ALTER TABLE client_contact 
ADD CONSTRAINT fk_contact_client FOREIGN KEY (client_id) 
REFERENCES client_info (id) ON DELETE CASCADE ON UPDATE CASCADE;

-- 2. 添加客户跟进记录表与客户信息表的外键约束
ALTER TABLE client_follow_up 
ADD CONSTRAINT fk_follow_up_client FOREIGN KEY (client_id) 
REFERENCES client_info (id) ON DELETE CASCADE ON UPDATE CASCADE;

-- 3. 添加客户跟进记录表与客户联系人表的外键约束
ALTER TABLE client_follow_up 
ADD CONSTRAINT fk_follow_up_contact FOREIGN KEY (contact_id) 
REFERENCES client_contact (id) ON DELETE SET NULL ON UPDATE CASCADE;

-- 注意：跨模块的外键约束已在全局约束脚本V9900__add_cross_module_constraints.sql中定义
-- 如：client_info表的responsible_lawyer_id关联personnel_lawyer表
-- 如：client_follow_up表的follow_up_by关联personnel_employee表 