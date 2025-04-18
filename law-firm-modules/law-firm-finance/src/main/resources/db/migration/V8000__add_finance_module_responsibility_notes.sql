-- 财务模块职责说明脚本
-- 版本: V8000
-- 模块: Finance
-- 创建时间: 2023-07-10
-- 说明: 本脚本不执行实际的数据库操作，仅用于记录模块职责说明

-- 注意：这是一个职责划分的说明文件，不对数据库结构进行任何更改

/*
财务模块职责说明：

1. 财务模块负责的表：
   财务模块应负责所有财务相关表的创建和维护：
   - fin_account：账户表
   - fin_transaction：交易表
   - fin_invoice：发票表
   - fin_receivable：应收账款表
   - fin_payment：付款表
   - fin_billing：账单表
   - fin_billing_record：账单记录表
   - fin_payment_plan：付款计划表
   - fin_income：收入表
   - fin_expense：支出表
   - fin_budget：预算表
   - fin_cost_center：成本中心表
   - fin_financial_report：财务报表表
   - fin_fee：费用表
   - 其他与财务相关的表

2. 现有脚本说明：
   - V8001__init_finance_tables.sql：创建财务相关表结构
   - V8002__init_finance_data.sql：初始化财务相关基础数据

3. 后续开发规范：
   a) 表命名规范：
      - 所有财务相关表应遵循"fin_xxx"命名规范
      - 表名应清晰表达表的业务含义
   
   b) 数据隔离：
      - 财务相关表必须包含tenant_id字段实现多租户隔离
      - 应设计适当的权限控制机制确保财务数据安全
   
   c) 关联规则：
      - 与其他模块的关联应通过ID引用实现，避免强耦合
      - 例如：通过case_id关联案件模块，通过contract_id关联合同模块
   
   d) 字典数据：
      - 财务特有的字典项应在Finance模块内维护
      - 公共字典项应使用API层的sys_dict/sys_dict_item表

4. 开发建议：
   - 财务模块应专注于财务管理的核心业务逻辑
   - 应提供清晰的API供其他模块调用，避免业务逻辑分散
   - 财务相关统计和报表功能应在Finance模块内实现
   - 应确保财务数据的准确性和一致性
   - 应实现完整的财务审计功能
*/

-- 此脚本不执行任何实际操作 