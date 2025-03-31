-- 注意：此脚本应在确认数据迁移成功后才执行
-- 如果您不确定，请不要执行此脚本

-- 删除旧收费记录表
DROP TABLE IF EXISTS `fee_record`;

-- 删除旧支出记录表
DROP TABLE IF EXISTS `expense_record`;

-- 删除旧发票表
DROP TABLE IF EXISTS `fin_invoice`; 