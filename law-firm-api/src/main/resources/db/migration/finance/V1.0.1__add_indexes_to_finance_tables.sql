-- 为收费记录表添加索引
ALTER TABLE fee_record ADD INDEX idx_payment_time (payment_time);

-- 为支出记录表添加索引
ALTER TABLE expense_record ADD INDEX idx_expense_time (expense_time);

-- 为发票表添加索引
ALTER TABLE fin_invoice ADD INDEX idx_status (status); 