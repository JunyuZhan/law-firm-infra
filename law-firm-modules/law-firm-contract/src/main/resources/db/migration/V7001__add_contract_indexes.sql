-- 添加合同信息表索引
CREATE INDEX idx_contract_end_date ON contract_info(end_date);
CREATE INDEX idx_contract_type ON contract_info(contract_type);
CREATE INDEX idx_contract_amount ON contract_info(contract_amount);
CREATE INDEX idx_contract_department ON contract_info(department_id);

-- 添加合同条款表索引
CREATE INDEX idx_clause_type ON contract_clause(clause_type);
CREATE INDEX idx_clause_important ON contract_clause(is_important);

-- 添加合同附件表索引
CREATE INDEX idx_attachment_type ON contract_attachment(attachment_type);
CREATE INDEX idx_attachment_uploader ON contract_attachment(uploader_id); 