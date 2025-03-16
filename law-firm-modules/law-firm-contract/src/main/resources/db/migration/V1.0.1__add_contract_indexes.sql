-- 为合同表添加额外索引
CREATE INDEX idx_contract_status ON contract(status);
CREATE INDEX idx_contract_sign_date ON contract(sign_date);
CREATE INDEX idx_contract_effective_date ON contract(effective_date);
CREATE INDEX idx_contract_expiry_date ON contract(expiry_date);
CREATE INDEX idx_contract_create_time ON contract(create_time);

-- 为合同审批表添加组合索引
CREATE INDEX idx_contract_approval_status ON contract_approval(contract_id, status);

-- 为合同条款表添加索引
CREATE INDEX idx_contract_clause_type ON contract_clause(contract_id, type); 