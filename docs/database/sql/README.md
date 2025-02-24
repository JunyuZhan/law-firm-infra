# SQL脚本说明

## 目录结构
```
sql/
├── init/                # 初始化脚本
│   ├── schema/         # 表结构脚本
│   └── data/           # 基础数据脚本
│
├── upgrade/            # 升级脚本
│   ├── v1.0.0/        # 1.0.0版本升级脚本
│   └── v1.1.0/        # 1.1.0版本升级脚本
│
└── procedure/          # 存储过程脚本
```

## 初始化脚本

### 1. 表结构脚本
- [01-system.sql](init/schema/01-system.sql)：系统管理相关表
- [02-organization.sql](init/schema/02-organization.sql)：组织架构相关表
- [03-case.sql](init/schema/03-case.sql)：案件管理相关表
- [04-client.sql](init/schema/04-client.sql)：客户管理相关表
- [05-contract.sql](init/schema/05-contract.sql)：合同管理相关表
- [06-document.sql](init/schema/06-document.sql)：文档管理相关表
- [07-finance.sql](init/schema/07-finance.sql)：财务管理相关表

### 2. 基础数据脚本
- [01-dict.sql](init/data/01-dict.sql)：字典数据
- [02-menu.sql](init/data/02-menu.sql)：菜单数据
- [03-role.sql](init/data/03-role.sql)：角色数据
- [04-config.sql](init/data/04-config.sql)：配置数据

## 升级脚本

### 1. v1.0.0升级脚本
```sql
-- 创建新表
CREATE TABLE sys_notice (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    type VARCHAR(20) COMMENT '类型',
    status VARCHAR(20) COMMENT '状态',
    create_time DATETIME COMMENT '创建时间',
    create_by BIGINT COMMENT '创建人',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统通知表';

-- 修改表结构
ALTER TABLE sys_user ADD COLUMN last_login_time DATETIME COMMENT '最后登录时间';
ALTER TABLE sys_user ADD COLUMN last_login_ip VARCHAR(50) COMMENT '最后登录IP';

-- 添加索引
CREATE INDEX idx_create_time ON sys_notice(create_time);
```

### 2. v1.1.0升级脚本
```sql
-- 创建新表
CREATE TABLE sys_message (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    type VARCHAR(20) COMMENT '类型',
    receiver_id BIGINT COMMENT '接收人ID',
    read_time DATETIME COMMENT '阅读时间',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统消息表';

-- 修改表结构
ALTER TABLE sys_user ADD COLUMN avatar VARCHAR(200) COMMENT '头像';
ALTER TABLE sys_user ADD COLUMN signature VARCHAR(500) COMMENT '个性签名';

-- 添加索引
CREATE INDEX idx_receiver_id ON sys_message(receiver_id);
```

## 存储过程

### 1. 案件统计
```sql
DELIMITER //

CREATE PROCEDURE proc_case_statistics(
    IN p_start_date DATE,
    IN p_end_date DATE,
    IN p_lawyer_id BIGINT
)
BEGIN
    -- 统计案件数量
    SELECT 
        case_type,
        COUNT(*) as case_count,
        SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) as completed_count
    FROM case_info
    WHERE lead_lawyer_id = p_lawyer_id
    AND filing_date BETWEEN p_start_date AND p_end_date
    GROUP BY case_type;
    
    -- 统计案件金额
    SELECT 
        MONTH(filing_date) as month,
        SUM(amount) as total_amount
    FROM case_info c
    LEFT JOIN contract_info ct ON c.id = ct.case_id
    WHERE lead_lawyer_id = p_lawyer_id
    AND filing_date BETWEEN p_start_date AND p_end_date
    GROUP BY MONTH(filing_date);
END //

DELIMITER ;
```

### 2. 客户分析
```sql
DELIMITER //

CREATE PROCEDURE proc_client_analysis(
    IN p_start_date DATE,
    IN p_end_date DATE
)
BEGIN
    -- 统计客户类型分布
    SELECT 
        client_type,
        COUNT(*) as client_count
    FROM client_info
    WHERE create_time BETWEEN p_start_date AND p_end_date
    GROUP BY client_type;
    
    -- 统计客户来源分布
    SELECT 
        source,
        COUNT(*) as client_count
    FROM client_info
    WHERE create_time BETWEEN p_start_date AND p_end_date
    GROUP BY source;
    
    -- 统计客户服务记录
    SELECT 
        service_type,
        COUNT(*) as service_count
    FROM client_service
    WHERE service_time BETWEEN p_start_date AND p_end_date
    GROUP BY service_type;
END //

DELIMITER ;
```

### 3. 财务统计
```sql
DELIMITER //

CREATE PROCEDURE proc_finance_statistics(
    IN p_start_date DATE,
    IN p_end_date DATE
)
BEGIN
    -- 统计收入
    SELECT 
        MONTH(receipt_date) as month,
        SUM(amount) as total_amount
    FROM finance_receipt
    WHERE receipt_date BETWEEN p_start_date AND p_end_date
    GROUP BY MONTH(receipt_date);
    
    -- 统计发票
    SELECT 
        invoice_type,
        COUNT(*) as invoice_count,
        SUM(amount) as total_amount
    FROM finance_invoice
    WHERE invoice_date BETWEEN p_start_date AND p_end_date
    GROUP BY invoice_type;
END //

DELIMITER ;
```

## 执行说明

### 1. 初始化数据库
```bash
# 创建数据库
mysql -u root -p -e "CREATE DATABASE law_firm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 执行表结构脚本
mysql -u root -p law_firm < sql/init/schema/01-system.sql
mysql -u root -p law_firm < sql/init/schema/02-organization.sql
mysql -u root -p law_firm < sql/init/schema/03-case.sql
mysql -u root -p law_firm < sql/init/schema/04-client.sql
mysql -u root -p law_firm < sql/init/schema/05-contract.sql
mysql -u root -p law_firm < sql/init/schema/06-document.sql
mysql -u root -p law_firm < sql/init/schema/07-finance.sql

# 执行基础数据脚本
mysql -u root -p law_firm < sql/init/data/01-dict.sql
mysql -u root -p law_firm < sql/init/data/02-menu.sql
mysql -u root -p law_firm < sql/init/data/03-role.sql
mysql -u root -p law_firm < sql/init/data/04-config.sql
```

### 2. 升级数据库
```bash
# 执行1.0.0版本升级脚本
mysql -u root -p law_firm < sql/upgrade/v1.0.0/upgrade.sql

# 执行1.1.0版本升级脚本
mysql -u root -p law_firm < sql/upgrade/v1.1.0/upgrade.sql
```

### 3. 备份数据库
```bash
# 备份数据库
mysqldump -u root -p law_firm > backup/law_firm_$(date +%Y%m%d).sql

# 还原数据库
mysql -u root -p law_firm < backup/law_firm_20240209.sql
```

## 注意事项
1. 执行脚本前先备份数据库
2. 按顺序执行初始化脚本
3. 升级脚本不可重复执行
4. 注意脚本的编码格式
5. 检查执行日志确保成功 