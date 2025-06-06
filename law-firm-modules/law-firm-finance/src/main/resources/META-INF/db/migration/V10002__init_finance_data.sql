-- 财务管理模块基础数据
-- 版本: V10002
-- 模块: 财务管理模块 (V10000-V10999)
-- 创建时间: 2023-06-01
-- 说明: 财务管理功能的基础字典数据和初始化数据

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 系统字典类型 =======================

-- 财务相关字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) VALUES
('账户类型', 'finance_account_type', 1, 'system', NOW(), '财务账户类型字典'),
('账户状态', 'finance_account_status', 1, 'system', NOW(), '财务账户状态字典'),
('币种', 'finance_currency', 1, 'system', NOW(), '财务币种字典'),
('交易类型', 'finance_transaction_type', 1, 'system', NOW(), '财务交易类型字典'),
('交易状态', 'finance_transaction_status', 1, 'system', NOW(), '财务交易状态字典'),
('付款方式', 'finance_payment_method', 1, 'system', NOW(), '财务付款方式字典'),
('审批状态', 'finance_approval_status', 1, 'system', NOW(), '财务审批状态字典'),
('发票类型', 'finance_invoice_type', 1, 'system', NOW(), '财务发票类型字典'),
('发票状态', 'finance_invoice_status', 1, 'system', NOW(), '财务发票状态字典'),
('应收状态', 'finance_receivable_status', 1, 'system', NOW(), '财务应收状态字典'),
('催收状态', 'finance_collection_status', 1, 'system', NOW(), '财务催收状态字典'),
('账单类型', 'finance_bill_type', 1, 'system', NOW(), '财务账单类型字典'),
('账单状态', 'finance_bill_status', 1, 'system', NOW(), '财务账单状态字典'),
('预算类型', 'finance_budget_type', 1, 'system', NOW(), '财务预算类型字典'),
('预算状态', 'finance_budget_status', 1, 'system', NOW(), '财务预算状态字典'),
('收入类型', 'finance_income_type', 1, 'system', NOW(), '财务收入类型字典'),
('收入状态', 'finance_income_status', 1, 'system', NOW(), '财务收入状态字典');

-- ======================= 系统字典数据 =======================

-- 账户类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '现金账户', '1', 'finance_account_type', '', 'primary', 1, 1, 'system', NOW(), '现金账户'),
(2, '银行账户', '2', 'finance_account_type', '', 'success', 0, 1, 'system', NOW(), '银行账户'),
(3, '支付宝账户', '3', 'finance_account_type', '', 'info', 0, 1, 'system', NOW(), '支付宝账户'),
(4, '微信账户', '4', 'finance_account_type', '', 'warning', 0, 1, 'system', NOW(), '微信账户'),
(5, '虚拟账户', '5', 'finance_account_type', '', 'default', 0, 1, 'system', NOW(), '虚拟账户'),
(6, '信用账户', '6', 'finance_account_type', '', 'danger', 0, 1, 'system', NOW(), '信用账户');

-- 账户状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '正常', '1', 'finance_account_status', '', 'success', 1, 1, 'system', NOW(), '账户状态正常'),
(2, '冻结', '2', 'finance_account_status', '', 'warning', 0, 1, 'system', NOW(), '账户已冻结'),
(3, '注销', '3', 'finance_account_status', '', 'danger', 0, 1, 'system', NOW(), '账户已注销'),
(4, '锁定', '4', 'finance_account_status', '', 'dark', 0, 1, 'system', NOW(), '账户已锁定');

-- 币种
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '人民币', 'CNY', 'finance_currency', '', 'primary', 1, 1, 'system', NOW(), '人民币'),
(2, '美元', 'USD', 'finance_currency', '', 'success', 0, 1, 'system', NOW(), '美元'),
(3, '欧元', 'EUR', 'finance_currency', '', 'info', 0, 1, 'system', NOW(), '欧元'),
(4, '英镑', 'GBP', 'finance_currency', '', 'warning', 0, 1, 'system', NOW(), '英镑'),
(5, '日元', 'JPY', 'finance_currency', '', 'default', 0, 1, 'system', NOW(), '日元'),
(6, '港币', 'HKD', 'finance_currency', '', 'dark', 0, 1, 'system', NOW(), '港币');

-- 交易类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '收款', '1', 'finance_transaction_type', '', 'success', 0, 1, 'system', NOW(), '收款交易'),
(2, '付款', '2', 'finance_transaction_type', '', 'danger', 0, 1, 'system', NOW(), '付款交易'),
(3, '转账', '3', 'finance_transaction_type', '', 'primary', 1, 1, 'system', NOW(), '转账交易'),
(4, '退款', '4', 'finance_transaction_type', '', 'warning', 0, 1, 'system', NOW(), '退款交易'),
(5, '预付款', '5', 'finance_transaction_type', '', 'info', 0, 1, 'system', NOW(), '预付款交易'),
(6, '押金', '6', 'finance_transaction_type', '', 'dark', 0, 1, 'system', NOW(), '押金交易'),
(7, '调整', '7', 'finance_transaction_type', '', 'secondary', 0, 1, 'system', NOW(), '调整交易');

-- 交易状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '待处理', '1', 'finance_transaction_status', '', 'warning', 1, 1, 'system', NOW(), '交易待处理'),
(2, '处理中', '2', 'finance_transaction_status', '', 'info', 0, 1, 'system', NOW(), '交易处理中'),
(3, '成功', '3', 'finance_transaction_status', '', 'success', 0, 1, 'system', NOW(), '交易成功'),
(4, '失败', '4', 'finance_transaction_status', '', 'danger', 0, 1, 'system', NOW(), '交易失败'),
(5, '取消', '5', 'finance_transaction_status', '', 'dark', 0, 1, 'system', NOW(), '交易取消');

-- 付款方式
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '银行转账', '1', 'finance_payment_method', '', 'primary', 1, 1, 'system', NOW(), '银行转账'),
(2, '现金支付', '2', 'finance_payment_method', '', 'success', 0, 1, 'system', NOW(), '现金支付'),
(3, '支票', '3', 'finance_payment_method', '', 'info', 0, 1, 'system', NOW(), '支票支付'),
(4, '支付宝', '4', 'finance_payment_method', '', 'warning', 0, 1, 'system', NOW(), '支付宝支付'),
(5, '微信支付', '5', 'finance_payment_method', '', 'dark', 0, 1, 'system', NOW(), '微信支付'),
(6, '抵扣', '6', 'finance_payment_method', '', 'secondary', 0, 1, 'system', NOW(), '抵扣支付');

-- 审批状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '无需审批', '1', 'finance_approval_status', '', 'success', 1, 1, 'system', NOW(), '无需审批'),
(2, '待审批', '2', 'finance_approval_status', '', 'warning', 0, 1, 'system', NOW(), '待审批'),
(3, '已审批', '3', 'finance_approval_status', '', 'primary', 0, 1, 'system', NOW(), '已审批'),
(4, '已拒绝', '4', 'finance_approval_status', '', 'danger', 0, 1, 'system', NOW(), '已拒绝');

-- 发票类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '增值税专用发票', '1', 'finance_invoice_type', '', 'primary', 1, 1, 'system', NOW(), '增值税专用发票'),
(2, '增值税普通发票', '2', 'finance_invoice_type', '', 'success', 0, 1, 'system', NOW(), '增值税普通发票'),
(3, '电子发票', '3', 'finance_invoice_type', '', 'info', 0, 1, 'system', NOW(), '电子发票'),
(4, '普通收据', '4', 'finance_invoice_type', '', 'warning', 0, 1, 'system', NOW(), '普通收据');

-- 发票状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '待开票', '1', 'finance_invoice_status', '', 'warning', 1, 1, 'system', NOW(), '待开票'),
(2, '已开票', '2', 'finance_invoice_status', '', 'primary', 0, 1, 'system', NOW(), '已开票'),
(3, '已作废', '3', 'finance_invoice_status', '', 'danger', 0, 1, 'system', NOW(), '已作废'),
(4, '已寄出', '4', 'finance_invoice_status', '', 'info', 0, 1, 'system', NOW(), '已寄出'),
(5, '已签收', '5', 'finance_invoice_status', '', 'success', 0, 1, 'system', NOW(), '已签收');

-- 应收状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '待收款', '1', 'finance_receivable_status', '', 'warning', 1, 1, 'system', NOW(), '待收款'),
(2, '部分收款', '2', 'finance_receivable_status', '', 'info', 0, 1, 'system', NOW(), '部分收款'),
(3, '已收款', '3', 'finance_receivable_status', '', 'success', 0, 1, 'system', NOW(), '已收款'),
(4, '逾期', '4', 'finance_receivable_status', '', 'danger', 0, 1, 'system', NOW(), '逾期应收'),
(5, '坏账', '5', 'finance_receivable_status', '', 'dark', 0, 1, 'system', NOW(), '坏账');

-- 催收状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '无需催收', '1', 'finance_collection_status', '', 'success', 1, 1, 'system', NOW(), '无需催收'),
(2, '待催收', '2', 'finance_collection_status', '', 'warning', 0, 1, 'system', NOW(), '待催收'),
(3, '催收中', '3', 'finance_collection_status', '', 'info', 0, 1, 'system', NOW(), '催收中'),
(4, '催收完成', '4', 'finance_collection_status', '', 'primary', 0, 1, 'system', NOW(), '催收完成');

-- 账单类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '律师费', '1', 'finance_bill_type', '', 'primary', 1, 1, 'system', NOW(), '律师费账单'),
(2, '诉讼费', '2', 'finance_bill_type', '', 'success', 0, 1, 'system', NOW(), '诉讼费账单'),
(3, '顾问费', '3', 'finance_bill_type', '', 'info', 0, 1, 'system', NOW(), '顾问费账单'),
(4, '代理费', '4', 'finance_bill_type', '', 'warning', 0, 1, 'system', NOW(), '代理费账单'),
(5, '综合账单', '5', 'finance_bill_type', '', 'dark', 0, 1, 'system', NOW(), '综合账单');

-- 账单状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '草稿', '1', 'finance_bill_status', '', 'secondary', 1, 1, 'system', NOW(), '账单草稿'),
(2, '已确认', '2', 'finance_bill_status', '', 'primary', 0, 1, 'system', NOW(), '账单已确认'),
(3, '已发送', '3', 'finance_bill_status', '', 'info', 0, 1, 'system', NOW(), '账单已发送'),
(4, '部分付款', '4', 'finance_bill_status', '', 'warning', 0, 1, 'system', NOW(), '部分付款'),
(5, '已付款', '5', 'finance_bill_status', '', 'success', 0, 1, 'system', NOW(), '已付款'),
(6, '已取消', '6', 'finance_bill_status', '', 'dark', 0, 1, 'system', NOW(), '已取消'),
(7, '逾期', '7', 'finance_bill_status', '', 'danger', 0, 1, 'system', NOW(), '逾期账单');

-- 预算类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '部门预算', '1', 'finance_budget_type', '', 'primary', 1, 1, 'system', NOW(), '部门预算'),
(2, '项目预算', '2', 'finance_budget_type', '', 'success', 0, 1, 'system', NOW(), '项目预算'),
(3, '年度预算', '3', 'finance_budget_type', '', 'info', 0, 1, 'system', NOW(), '年度预算'),
(4, '季度预算', '4', 'finance_budget_type', '', 'warning', 0, 1, 'system', NOW(), '季度预算'),
(5, '月度预算', '5', 'finance_budget_type', '', 'dark', 0, 1, 'system', NOW(), '月度预算');

-- 预算状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '草稿', '1', 'finance_budget_status', '', 'secondary', 1, 1, 'system', NOW(), '预算草稿'),
(2, '已审批', '2', 'finance_budget_status', '', 'primary', 0, 1, 'system', NOW(), '预算已审批'),
(3, '执行中', '3', 'finance_budget_status', '', 'info', 0, 1, 'system', NOW(), '预算执行中'),
(4, '已完成', '4', 'finance_budget_status', '', 'success', 0, 1, 'system', NOW(), '预算已完成'),
(5, '已取消', '5', 'finance_budget_status', '', 'danger', 0, 1, 'system', NOW(), '预算已取消');

-- 收入类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '案件收入', '1', 'finance_income_type', '', 'primary', 1, 1, 'system', NOW(), '案件收入'),
(2, '顾问收入', '2', 'finance_income_type', '', 'success', 0, 1, 'system', NOW(), '顾问收入'),
(3, '代理收入', '3', 'finance_income_type', '', 'info', 0, 1, 'system', NOW(), '代理收入'),
(4, '利息收入', '4', 'finance_income_type', '', 'warning', 0, 1, 'system', NOW(), '利息收入'),
(5, '其他收入', '5', 'finance_income_type', '', 'dark', 0, 1, 'system', NOW(), '其他收入');

-- 收入状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '已确认', '1', 'finance_income_status', '', 'primary', 1, 1, 'system', NOW(), '收入已确认'),
(2, '已入账', '2', 'finance_income_status', '', 'success', 0, 1, 'system', NOW(), '收入已入账'),
(3, '已对账', '3', 'finance_income_status', '', 'info', 0, 1, 'system', NOW(), '收入已对账');

-- ======================= 初始账户数据 =======================

-- 初始化系统默认账户
INSERT INTO finance_account (tenant_id, account_number, account_name, account_type, account_status, currency, balance, available_amount, is_default, is_enabled, create_by, create_time, remark) VALUES
(0, 'ACCT-01-2023-0001', '系统现金账户', 1, 1, 'CNY', 0.00, 0.00, 1, 1, 'system', NOW(), '系统默认现金账户'),
(0, 'ACCT-02-2023-0001', '系统银行账户', 2, 1, 'CNY', 0.00, 0.00, 0, 1, 'system', NOW(), '系统默认银行账户'),
(0, 'ACCT-05-2023-0001', '系统虚拟账户', 5, 1, 'CNY', 0.00, 0.00, 0, 1, 'system', NOW(), '系统虚拟账户，用于内部核算');

-- 初始化完成提示
SELECT '财务管理模块基础数据初始化完成' AS result;
SELECT CONCAT('已创建字典类型：', COUNT(*), '个') AS dict_type_count FROM sys_dict_type WHERE dict_type LIKE 'finance_%';
SELECT CONCAT('已创建字典数据：', COUNT(*), '个') AS dict_data_count FROM sys_dict_data WHERE dict_type LIKE 'finance_%';
SELECT CONCAT('已创建初始账户：', COUNT(*), '个') AS account_count FROM finance_account WHERE tenant_id = 0; 