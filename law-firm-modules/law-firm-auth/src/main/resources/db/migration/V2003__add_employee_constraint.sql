-- 注意：此约束已移至全局约束脚本 V9900__add_cross_module_constraints.sql
-- 保留此文件是为了避免影响已有的数据库迁移历史记录
-- 未来新环境部署时，请确保使用最新的迁移方案

-- 用户与人员关联约束
-- 添加外键约束到personnel_person表，关联sys_user表
-- 注意：此约束依赖于personnel_person表已创建（V3000中创建）

-- 添加personnel_person表与sys_user表的外键约束 (已迁移至全局约束脚本)
-- 请参考 V9900__add_cross_module_constraints.sql 