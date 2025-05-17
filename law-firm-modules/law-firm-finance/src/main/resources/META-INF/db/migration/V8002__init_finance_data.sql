-- 财务模块初始化数据
-- 版本: V8002
-- 模块: finance
-- 创建时间: 2023-10-01
-- 说明: 创建财务管理模块初始化数据

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 财务相关字典数据
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, create_time, create_by, remark)
VALUES 
-- 账户类型
(NULL, NULL, 'account_type', '现金账户', 'CASH', 1, 0, NOW(), 'system', '账户类型 - 现金账户'),
(NULL, NULL, 'account_type', '银行账户', 'BANK', 2, 0, NOW(), 'system', '账户类型 - 银行账户'),
(NULL, NULL, 'account_type', '支付宝账户', 'ALIPAY', 3, 0, NOW(), 'system', '账户类型 - 支付宝账户'),
(NULL, NULL, 'account_type', '微信账户', 'WECHAT', 4, 0, NOW(), 'system', '账户类型 - 微信账户'),
(NULL, NULL, 'account_type', '虚拟账户', 'VIRTUAL', 5, 0, NOW(), 'system', '账户类型 - 虚拟账户'),
(NULL, NULL, 'account_type', '信用账户', 'CREDIT', 6, 0, NOW(), 'system', '账户类型 - 信用账户'),

-- 账户状态
(NULL, NULL, 'account_status', '正常', 'NORMAL', 1, 0, NOW(), 'system', '账户状态 - 正常'),
(NULL, NULL, 'account_status', '冻结', 'FROZEN', 2, 0, NOW(), 'system', '账户状态 - 冻结'),
(NULL, NULL, 'account_status', '注销', 'CANCELLED', 3, 0, NOW(), 'system', '账户状态 - 注销'),
(NULL, NULL, 'account_status', '锁定', 'LOCKED', 4, 0, NOW(), 'system', '账户状态 - 锁定'),

-- 币种
(NULL, NULL, 'currency', '人民币', 'CNY', 1, 0, NOW(), 'system', '币种 - 人民币'),
(NULL, NULL, 'currency', '美元', 'USD', 2, 0, NOW(), 'system', '币种 - 美元'),
(NULL, NULL, 'currency', '欧元', 'EUR', 3, 0, NOW(), 'system', '币种 - 欧元'),
(NULL, NULL, 'currency', '英镑', 'GBP', 4, 0, NOW(), 'system', '币种 - 英镑'),
(NULL, NULL, 'currency', '日元', 'JPY', 5, 0, NOW(), 'system', '币种 - 日元'),
(NULL, NULL, 'currency', '港币', 'HKD', 6, 0, NOW(), 'system', '币种 - 港币'),

-- 交易类型
(NULL, NULL, 'transaction_type', '收款', 'RECEIPT', 1, 0, NOW(), 'system', '交易类型 - 收款'),
(NULL, NULL, 'transaction_type', '付款', 'PAYMENT', 2, 0, NOW(), 'system', '交易类型 - 付款'),
(NULL, NULL, 'transaction_type', '转账', 'TRANSFER', 3, 0, NOW(), 'system', '交易类型 - 转账'),
(NULL, NULL, 'transaction_type', '退款', 'REFUND', 4, 0, NOW(), 'system', '交易类型 - 退款'),
(NULL, NULL, 'transaction_type', '预付款', 'ADVANCE', 5, 0, NOW(), 'system', '交易类型 - 预付款'),
(NULL, NULL, 'transaction_type', '押金', 'DEPOSIT', 6, 0, NOW(), 'system', '交易类型 - 押金'),
(NULL, NULL, 'transaction_type', '调整', 'ADJUSTMENT', 7, 0, NOW(), 'system', '交易类型 - 调整'),

-- 发票类型
(NULL, NULL, 'invoice_type', '增值税专用发票', 'VAT_SPECIAL', 1, 0, NOW(), 'system', '发票类型 - 增值税专用发票'),
(NULL, NULL, 'invoice_type', '增值税普通发票', 'VAT_ORDINARY', 2, 0, NOW(), 'system', '发票类型 - 增值税普通发票'),
(NULL, NULL, 'invoice_type', '电子发票', 'ELECTRONIC', 3, 0, NOW(), 'system', '发票类型 - 电子发票'),
(NULL, NULL, 'invoice_type', '普通收据', 'RECEIPT', 4, 0, NOW(), 'system', '发票类型 - 普通收据'),

-- 发票状态
(NULL, NULL, 'invoice_status', '待开票', 'PENDING', 1, 0, NOW(), 'system', '发票状态 - 待开票'),
(NULL, NULL, 'invoice_status', '已开票', 'ISSUED', 2, 0, NOW(), 'system', '发票状态 - 已开票'),
(NULL, NULL, 'invoice_status', '已作废', 'CANCELLED', 3, 0, NOW(), 'system', '发票状态 - 已作废'),
(NULL, NULL, 'invoice_status', '已寄出', 'SENT', 4, 0, NOW(), 'system', '发票状态 - 已寄出'),
(NULL, NULL, 'invoice_status', '已签收', 'RECEIVED', 5, 0, NOW(), 'system', '发票状态 - 已签收'),

-- 应收款状态
(NULL, NULL, 'receivable_status', '待收款', '0', 1, 0, NOW(), 'system', '应收款状态 - 待收款'),
(NULL, NULL, 'receivable_status', '部分收款', '1', 2, 0, NOW(), 'system', '应收款状态 - 部分收款'),
(NULL, NULL, 'receivable_status', '已收款', '2', 3, 0, NOW(), 'system', '应收款状态 - 已收款'),
(NULL, NULL, 'receivable_status', '逾期', '3', 4, 0, NOW(), 'system', '应收款状态 - 逾期'),
(NULL, NULL, 'receivable_status', '坏账', '4', 5, 0, NOW(), 'system', '应收款状态 - 坏账'),

-- 付款类型
(NULL, NULL, 'payment_type', '合同付款', 'CONTRACT', 1, 0, NOW(), 'system', '付款类型 - 合同付款'),
(NULL, NULL, 'payment_type', '案件支出', 'CASE', 2, 0, NOW(), 'system', '付款类型 - 案件支出'),
(NULL, NULL, 'payment_type', '日常开支', 'ROUTINE', 3, 0, NOW(), 'system', '付款类型 - 日常开支'),
(NULL, NULL, 'payment_type', '工资发放', 'SALARY', 4, 0, NOW(), 'system', '付款类型 - 工资发放'),
(NULL, NULL, 'payment_type', '税费缴纳', 'TAX', 5, 0, NOW(), 'system', '付款类型 - 税费缴纳'),
(NULL, NULL, 'payment_type', '其他支出', 'OTHER', 6, 0, NOW(), 'system', '付款类型 - 其他支出'),

-- 付款状态
(NULL, NULL, 'payment_status', '待支付', 'PENDING', 1, 0, NOW(), 'system', '付款状态 - 待支付'),
(NULL, NULL, 'payment_status', '已支付', 'PAID', 2, 0, NOW(), 'system', '付款状态 - 已支付'),
(NULL, NULL, 'payment_status', '支付失败', 'FAILED', 3, 0, NOW(), 'system', '付款状态 - 支付失败'),
(NULL, NULL, 'payment_status', '已取消', 'CANCELLED', 4, 0, NOW(), 'system', '付款状态 - 已取消'),
(NULL, NULL, 'payment_status', '部分支付', 'PARTIAL_PAID', 5, 0, NOW(), 'system', '付款状态 - 部分支付'),

-- 付款方式
(NULL, NULL, 'payment_method', '银行转账', 'BANK_TRANSFER', 1, 0, NOW(), 'system', '付款方式 - 银行转账'),
(NULL, NULL, 'payment_method', '现金支付', 'CASH', 2, 0, NOW(), 'system', '付款方式 - 现金支付'),
(NULL, NULL, 'payment_method', '支票', 'CHECK', 3, 0, NOW(), 'system', '付款方式 - 支票'),
(NULL, NULL, 'payment_method', '支付宝', 'ALIPAY', 4, 0, NOW(), 'system', '付款方式 - 支付宝'),
(NULL, NULL, 'payment_method', '微信支付', 'WECHAT', 5, 0, NOW(), 'system', '付款方式 - 微信支付'),
(NULL, NULL, 'payment_method', '抵扣', 'OFFSET', 6, 0, NOW(), 'system', '付款方式 - 抵扣'),

-- 账单类型
(NULL, NULL, 'billing_type', '律师费', 'LEGAL_FEE', 1, 0, NOW(), 'system', '账单类型 - 律师费'),
(NULL, NULL, 'billing_type', '诉讼费', 'LITIGATION_FEE', 2, 0, NOW(), 'system', '账单类型 - 诉讼费'),
(NULL, NULL, 'billing_type', '顾问费', 'CONSULTING_FEE', 3, 0, NOW(), 'system', '账单类型 - 顾问费'),
(NULL, NULL, 'billing_type', '代理费', 'AGENCY_FEE', 4, 0, NOW(), 'system', '账单类型 - 代理费'),
(NULL, NULL, 'billing_type', '综合账单', 'COMPREHENSIVE', 5, 0, NOW(), 'system', '账单类型 - 综合账单'),

-- 账单状态
(NULL, NULL, 'billing_status', '草稿', 'DRAFT', 1, 0, NOW(), 'system', '账单状态 - 草稿'),
(NULL, NULL, 'billing_status', '已确认', 'CONFIRMED', 2, 0, NOW(), 'system', '账单状态 - 已确认'),
(NULL, NULL, 'billing_status', '已发送', 'SENT', 3, 0, NOW(), 'system', '账单状态 - 已发送'),
(NULL, NULL, 'billing_status', '部分付款', 'PARTIAL_PAID', 4, 0, NOW(), 'system', '账单状态 - 部分付款'),
(NULL, NULL, 'billing_status', '已付款', 'PAID', 5, 0, NOW(), 'system', '账单状态 - 已付款'),
(NULL, NULL, 'billing_status', '已取消', 'CANCELLED', 6, 0, NOW(), 'system', '账单状态 - 已取消'),
(NULL, NULL, 'billing_status', '逾期', 'OVERDUE', 7, 0, NOW(), 'system', '账单状态 - 逾期'),

-- 付款计划类型
(NULL, NULL, 'payment_plan_type', '一次性付款', 'ONE_TIME', 1, 0, NOW(), 'system', '付款计划类型 - 一次性付款'),
(NULL, NULL, 'payment_plan_type', '分期付款', 'INSTALLMENT', 2, 0, NOW(), 'system', '付款计划类型 - 分期付款'),
(NULL, NULL, 'payment_plan_type', '阶段付款', 'MILESTONE', 3, 0, NOW(), 'system', '付款计划类型 - 阶段付款'),
(NULL, NULL, 'payment_plan_type', '循环付款', 'RECURRING', 4, 0, NOW(), 'system', '付款计划类型 - 循环付款'),

-- 付款计划状态
(NULL, NULL, 'payment_plan_status', '待执行', 'PENDING', 1, 0, NOW(), 'system', '付款计划状态 - 待执行'),
(NULL, NULL, 'payment_plan_status', '执行中', 'IN_PROGRESS', 2, 0, NOW(), 'system', '付款计划状态 - 执行中'),
(NULL, NULL, 'payment_plan_status', '已完成', 'COMPLETED', 3, 0, NOW(), 'system', '付款计划状态 - 已完成'),
(NULL, NULL, 'payment_plan_status', '已取消', 'CANCELLED', 4, 0, NOW(), 'system', '付款计划状态 - 已取消'),

-- 收入类型
(NULL, NULL, 'income_type', '案件收入', 'CASE', 1, 0, NOW(), 'system', '收入类型 - 案件收入'),
(NULL, NULL, 'income_type', '顾问收入', 'CONSULTING', 2, 0, NOW(), 'system', '收入类型 - 顾问收入'),
(NULL, NULL, 'income_type', '代理收入', 'AGENCY', 3, 0, NOW(), 'system', '收入类型 - 代理收入'),
(NULL, NULL, 'income_type', '利息收入', 'INTEREST', 4, 0, NOW(), 'system', '收入类型 - 利息收入'),
(NULL, NULL, 'income_type', '其他收入', 'OTHER', 5, 0, NOW(), 'system', '收入类型 - 其他收入'),

-- 收入状态
(NULL, NULL, 'income_status', '已确认', 'CONFIRMED', 1, 0, NOW(), 'system', '收入状态 - 已确认'),
(NULL, NULL, 'income_status', '已入账', 'RECORDED', 2, 0, NOW(), 'system', '收入状态 - 已入账'),
(NULL, NULL, 'income_status', '已对账', 'RECONCILED', 3, 0, NOW(), 'system', '收入状态 - 已对账'),

-- 支出类型
(NULL, NULL, 'expense_type', '办公支出', 'OFFICE', 1, 0, NOW(), 'system', '支出类型 - 办公支出'),
(NULL, NULL, 'expense_type', '人力支出', 'HUMAN_RESOURCE', 2, 0, NOW(), 'system', '支出类型 - 人力支出'),
(NULL, NULL, 'expense_type', '差旅支出', 'TRAVEL', 3, 0, NOW(), 'system', '支出类型 - 差旅支出'),
(NULL, NULL, 'expense_type', '案件支出', 'CASE', 4, 0, NOW(), 'system', '支出类型 - 案件支出'),
(NULL, NULL, 'expense_type', '税费支出', 'TAX', 5, 0, NOW(), 'system', '支出类型 - 税费支出'),
(NULL, NULL, 'expense_type', '其他支出', 'OTHER', 6, 0, NOW(), 'system', '支出类型 - 其他支出'),

-- 支出状态
(NULL, NULL, 'expense_status', '待审批', 'PENDING_APPROVAL', 1, 0, NOW(), 'system', '支出状态 - 待审批'),
(NULL, NULL, 'expense_status', '已审批', 'APPROVED', 2, 0, NOW(), 'system', '支出状态 - 已审批'),
(NULL, NULL, 'expense_status', '已支付', 'PAID', 3, 0, NOW(), 'system', '支出状态 - 已支付'),
(NULL, NULL, 'expense_status', '已拒绝', 'REJECTED', 4, 0, NOW(), 'system', '支出状态 - 已拒绝'),
(NULL, NULL, 'expense_status', '已取消', 'CANCELLED', 5, 0, NOW(), 'system', '支出状态 - 已取消'),

-- 预算类型
(NULL, NULL, 'budget_type', '部门预算', 'DEPARTMENT', 1, 0, NOW(), 'system', '预算类型 - 部门预算'),
(NULL, NULL, 'budget_type', '项目预算', 'PROJECT', 2, 0, NOW(), 'system', '预算类型 - 项目预算'),
(NULL, NULL, 'budget_type', '年度预算', 'ANNUAL', 3, 0, NOW(), 'system', '预算类型 - 年度预算'),
(NULL, NULL, 'budget_type', '季度预算', 'QUARTERLY', 4, 0, NOW(), 'system', '预算类型 - 季度预算'),
(NULL, NULL, 'budget_type', '月度预算', 'MONTHLY', 5, 0, NOW(), 'system', '预算类型 - 月度预算'),

-- 预算状态
(NULL, NULL, 'budget_status', '草稿', 'DRAFT', 1, 0, NOW(), 'system', '预算状态 - 草稿'),
(NULL, NULL, 'budget_status', '已审批', 'APPROVED', 2, 0, NOW(), 'system', '预算状态 - 已审批'),
(NULL, NULL, 'budget_status', '执行中', 'IN_PROGRESS', 3, 0, NOW(), 'system', '预算状态 - 执行中'),
(NULL, NULL, 'budget_status', '已完成', 'COMPLETED', 4, 0, NOW(), 'system', '预算状态 - 已完成'),
(NULL, NULL, 'budget_status', '已取消', 'CANCELLED', 5, 0, NOW(), 'system', '预算状态 - 已取消'),

-- 费用类型
(NULL, NULL, 'fee_type', '律师费', 'LAWYER_FEE', 1, 0, NOW(), 'system', '费用类型 - 律师费'),
(NULL, NULL, 'fee_type', '诉讼费', 'LITIGATION_FEE', 2, 0, NOW(), 'system', '费用类型 - 诉讼费'),
(NULL, NULL, 'fee_type', '差旅费', 'TRAVEL_EXPENSE', 3, 0, NOW(), 'system', '费用类型 - 差旅费'),
(NULL, NULL, 'fee_type', '调查费', 'INVESTIGATION_FEE', 4, 0, NOW(), 'system', '费用类型 - 调查费'),
(NULL, NULL, 'fee_type', '公证费', 'NOTARY_FEE', 5, 0, NOW(), 'system', '费用类型 - 公证费'),
(NULL, NULL, 'fee_type', '代理费', 'AGENCY_FEE', 6, 0, NOW(), 'system', '费用类型 - 代理费'),
(NULL, NULL, 'fee_type', '其他费用', 'OTHER', 7, 0, NOW(), 'system', '费用类型 - 其他费用'),

-- 费用状态
(NULL, NULL, 'fee_status', '待确认', 'PENDING', 1, 0, NOW(), 'system', '费用状态 - 待确认'),
(NULL, NULL, 'fee_status', '已确认', 'CONFIRMED', 2, 0, NOW(), 'system', '费用状态 - 已确认'),
(NULL, NULL, 'fee_status', '已入账', 'RECORDED', 3, 0, NOW(), 'system', '费用状态 - 已入账'),
(NULL, NULL, 'fee_status', '已结算', 'SETTLED', 4, 0, NOW(), 'system', '费用状态 - 已结算')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化财务相关字典类型
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, create_time, create_by, remark)
VALUES
(NULL, NULL, '账户类型', 'account_type', 0, NOW(), 'system', '账户类型字典'),
(NULL, NULL, '账户状态', 'account_status', 0, NOW(), 'system', '账户状态字典'),
(NULL, NULL, '币种', 'currency', 0, NOW(), 'system', '币种字典'),
(NULL, NULL, '交易类型', 'transaction_type', 0, NOW(), 'system', '交易类型字典'),
(NULL, NULL, '发票类型', 'invoice_type', 0, NOW(), 'system', '发票类型字典'),
(NULL, NULL, '发票状态', 'invoice_status', 0, NOW(), 'system', '发票状态字典'),
(NULL, NULL, '应收款状态', 'receivable_status', 0, NOW(), 'system', '应收款状态字典'),
(NULL, NULL, '付款类型', 'payment_type', 0, NOW(), 'system', '付款类型字典'),
(NULL, NULL, '付款状态', 'payment_status', 0, NOW(), 'system', '付款状态字典'),
(NULL, NULL, '付款方式', 'payment_method', 0, NOW(), 'system', '付款方式字典'),
(NULL, NULL, '账单类型', 'billing_type', 0, NOW(), 'system', '账单类型字典'),
(NULL, NULL, '账单状态', 'billing_status', 0, NOW(), 'system', '账单状态字典'),
(NULL, NULL, '付款计划类型', 'payment_plan_type', 0, NOW(), 'system', '付款计划类型字典'),
(NULL, NULL, '付款计划状态', 'payment_plan_status', 0, NOW(), 'system', '付款计划状态字典'),
(NULL, NULL, '收入类型', 'income_type', 0, NOW(), 'system', '收入类型字典'),
(NULL, NULL, '收入状态', 'income_status', 0, NOW(), 'system', '收入状态字典'),
(NULL, NULL, '支出类型', 'expense_type', 0, NOW(), 'system', '支出类型字典'),
(NULL, NULL, '支出状态', 'expense_status', 0, NOW(), 'system', '支出状态字典'),
(NULL, NULL, '预算类型', 'budget_type', 0, NOW(), 'system', '预算类型字典'),
(NULL, NULL, '预算状态', 'budget_status', 0, NOW(), 'system', '预算状态字典'),
(NULL, NULL, '费用类型', 'fee_type', 0, NOW(), 'system', '费用类型字典'),
(NULL, NULL, '费用状态', 'fee_status', 0, NOW(), 'system', '费用状态字典')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;
