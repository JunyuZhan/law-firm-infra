-- 清理测试数据
DELETE FROM fee_record;
DELETE FROM expense_record;

-- 插入收费记录测试数据
INSERT INTO fee_record (id, amount, paid_amount, fee_status, fee_type, case_id, client_id, law_firm_id, payment_time, payment_method, description, remark, created_time, updated_time, created_by, updated_by)
VALUES 
(1, 1000.00, 1000.00, 'PAID', '案件收费', 1, 1, 1, '2024-03-01 10:00:00', '银行转账', '测试案件收费1', '已支付', '2024-03-01 10:00:00', '2024-03-01 10:00:00', 'admin', 'admin'),
(2, 2000.00, 1000.00, 'PARTIAL', '案件收费', 2, 2, 1, '2024-03-02 10:00:00', '银行转账', '测试案件收费2', '部分支付', '2024-03-02 10:00:00', '2024-03-02 10:00:00', 'admin', 'admin'),
(3, 3000.00, 0.00, 'UNPAID', '咨询收费', 3, 3, 1, NULL, NULL, '测试咨询收费', '未支付', '2024-03-03 10:00:00', '2024-03-03 10:00:00', 'admin', 'admin');

-- 插入支出记录测试数据
INSERT INTO expense_record (id, amount, expense_status, expense_type, law_firm_id, department_id, applicant_id, approver_id, expense_time, payment_method, description, remark, created_time, updated_time, created_by, updated_by)
VALUES 
(1, 500.00, 'PAID', '日常运营', 1, 1, 1, 1, '2024-03-01 10:00:00', '银行转账', '办公用品采购', '已支付', '2024-03-01 10:00:00', '2024-03-01 10:00:00', 'admin', 'admin'),
(2, 2000.00, 'APPROVED', '人员工资', 1, 1, 1, 1, NULL, NULL, '3月份工资', '待支付', '2024-03-02 10:00:00', '2024-03-02 10:00:00', 'admin', 'admin'),
(3, 1000.00, 'PENDING', '办公设备', 1, 1, 1, NULL, NULL, NULL, '打印机采购', '待审批', '2024-03-03 10:00:00', '2024-03-03 10:00:00', 'admin', 'admin'); 