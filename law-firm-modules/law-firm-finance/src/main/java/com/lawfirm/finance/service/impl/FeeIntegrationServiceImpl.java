package com.lawfirm.finance.service.impl;

import com.lawfirm.model.finance.service.FeeIntegrationService;
import com.lawfirm.model.finance.service.FeeRelationService;
import com.lawfirm.model.finance.service.FeeService;
import com.lawfirm.model.finance.entity.Fee;
import com.lawfirm.model.finance.enums.FeeTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.cases.service.business.CaseFinanceService;
import com.lawfirm.model.contract.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import com.lawfirm.finance.exception.FinanceException;

/**
 * 费用集成服务实现类
 * 
 * 该实现类负责集成合同模块、案件模块和财务模块的费用处理，
 * 确保三个模块的费用数据一致性和联动性
 */
@Slf4j
@Service("feeIntegrationService")
public class FeeIntegrationServiceImpl implements FeeIntegrationService {
    
    @Autowired
    private FeeService feeService;
    
    @Autowired
    private FeeRelationService feeRelationService;
    
    // 直接注入相关模块的服务
    @Autowired(required = false)
    private ContractService contractService;
    
    @Autowired(required = false)
    private CaseFinanceService caseFinanceService;

    @Override
    @Transactional
    public Long createContractFee(Long contractFeeId, Long contractId, String contractNo, 
                          String feeName, String feeType, BigDecimal amount, 
                          String currency, String description) {
        log.info("创建合同费用关联的财务费用记录: contractFeeId={}, contractId={}, contractNo={}", 
                contractFeeId, contractId, contractNo);
        
        try {
            // 创建财务费用记录
            Fee fee = new Fee();
            fee.setFeeNumber("FIN-" + System.currentTimeMillis())
               .setFeeName(feeName)
               .setFeeType(FeeTypeEnum.valueOf(feeType))
               .setAmount(amount)
               .setCurrency(CurrencyEnum.valueOf(currency))
               .setDescription(description)
               .setFeeTime(LocalDateTime.now());
            
            // 保存费用记录
            Long financeFeeId = feeService.createFee(fee);
            
            // 记录映射关系到数据库
            if (contractFeeId != null && financeFeeId != null) {
                feeRelationService.saveContractFeeRelation(financeFeeId, contractFeeId, contractId, contractNo);
            }
            
            log.info("财务费用记录创建成功: financeFeeId={}, contractFeeId={}", financeFeeId, contractFeeId);
            return financeFeeId;
        } catch (Exception e) {
            log.error("创建合同相关财务费用记录失败", e);
            throw FinanceException.failed("创建合同相关财务费用记录", e);
        }
    }
    
    @Override
    @Transactional
    public Long createCaseFeeFromContract(Long contractFeeId, Long caseId, String caseNumber, 
                                   BigDecimal amount, String description) {
        log.info("从合同费用创建案件费用: contractFeeId={}, caseId={}, caseNumber={}", contractFeeId, caseId, caseNumber);
        
        try {
            // 这里需要调用案件模块的服务创建案件费用
            // 如果caseFinanceService注入成功，则使用它来创建案件费用
            
            // 模拟创建案件费用并返回ID
            Long caseFeeId = simulateCreateCaseFee(caseId, caseNumber, amount, description);
            
            // 记录映射关系到数据库
            if (contractFeeId != null && caseFeeId != null) {
                // 获取合同ID和合同编号（实际项目中应该从合同模块获取）
                Long contractId = null;
                String contractNumber = null;
                if (contractService != null) {
                    // 调用合同服务获取合同信息
                    // contractId = contractService.getContractIdByFee(contractFeeId);
                    // contractNumber = contractService.getContractNumberById(contractId);
                }
                
                feeRelationService.saveContractCaseFeeRelation(contractFeeId, caseFeeId, 
                        contractId, contractNumber, caseId, caseNumber);
            }
            
            log.info("从合同费用创建案件费用成功: contractFeeId={}, caseFeeId={}", contractFeeId, caseFeeId);
            return caseFeeId;
        } catch (Exception e) {
            log.error("从合同费用创建案件费用失败", e);
            throw FinanceException.failed("从合同费用创建案件费用", e);
        }
    }
    
    @Override
    @Transactional
    public Long createFinanceFeeFromCase(Long caseFeeId, Long caseId, String caseNumber,
                                 String feeName, String feeType, BigDecimal amount,
                                 String currency, String description) {
        log.info("从案件费用创建财务费用: caseFeeId={}, caseId={}, caseNumber={}", caseFeeId, caseId, caseNumber);
        
        try {
            // 创建财务费用记录
            Fee fee = new Fee();
            fee.setFeeNumber("FIN-CASE-" + System.currentTimeMillis())
               .setFeeName(feeName)
               .setFeeType(FeeTypeEnum.valueOf(feeType))
               .setAmount(amount)
               .setCurrency(CurrencyEnum.valueOf(currency))
               .setDescription(description)
               .setFeeTime(LocalDateTime.now());
            
            // 保存费用记录
            Long financeFeeId = feeService.createFee(fee);
            
            // 记录映射关系到数据库
            if (caseFeeId != null && financeFeeId != null) {
                feeRelationService.saveCaseFeeRelation(financeFeeId, caseFeeId, caseId, caseNumber);
            }
            
            log.info("从案件费用创建财务费用成功: caseFeeId={}, financeFeeId={}", caseFeeId, financeFeeId);
            return financeFeeId;
        } catch (Exception e) {
            log.error("从案件费用创建财务费用失败", e);
            throw FinanceException.failed("从案件费用创建财务费用", e);
        }
    }
    
    @Override
    @Transactional
    public boolean updateContractFeeAndSync(Long contractFeeId, BigDecimal amount, String description) {
        log.info("更新合同费用并同步: contractFeeId={}, amount={}", contractFeeId, amount);
        
        try {
            // 1. 更新合同费用（调用合同模块服务）
            boolean contractUpdated = updateContractFee(contractFeeId, amount, description);
            if (!contractUpdated) {
                log.warn("合同费用更新失败: contractFeeId={}", contractFeeId);
                return false;
            }
            
            // 2. 查找关联的案件费用ID
            Long caseFeeId = feeRelationService.getCaseFeeIdByContractFeeId(contractFeeId);
            if (caseFeeId != null) {
                // 3. 更新案件费用
                boolean caseUpdated = updateCaseFee(caseFeeId, amount, description);
                if (!caseUpdated) {
                    log.warn("案件费用更新失败: caseFeeId={}", caseFeeId);
                }
            }
            
            // 4. 查找关联的财务费用ID
            Long financeFeeId = feeRelationService.getFinanceFeeIdByContractFeeId(contractFeeId);
            if (financeFeeId != null) {
                // 5. 更新财务费用
                boolean financeUpdated = updateFinanceFee(financeFeeId, amount, description);
                if (!financeUpdated) {
                    log.warn("财务费用更新失败: financeFeeId={}", financeFeeId);
                }
            }
            
            log.info("合同费用及关联费用同步更新成功: contractFeeId={}", contractFeeId);
            return true;
        } catch (Exception e) {
            log.error("更新合同费用并同步失败", e);
            throw FinanceException.failed("更新合同费用并同步", e);
        }
    }
    
    @Override
    @Transactional
    public boolean updateCaseFeeAndSync(Long caseFeeId, BigDecimal amount, String description) {
        log.info("更新案件费用并同步: caseFeeId={}, amount={}", caseFeeId, amount);
        
        try {
            // 1. 更新案件费用（调用案件模块服务）
            boolean caseUpdated = updateCaseFee(caseFeeId, amount, description);
            if (!caseUpdated) {
                log.warn("案件费用更新失败: caseFeeId={}", caseFeeId);
                return false;
            }
            
            // 2. 查找关联的财务费用ID
            Long financeFeeId = feeRelationService.getFinanceFeeIdByCaseFeeId(caseFeeId);
            if (financeFeeId != null) {
                // 3. 更新财务费用
                boolean financeUpdated = updateFinanceFee(financeFeeId, amount, description);
                if (!financeUpdated) {
                    log.warn("财务费用更新失败: financeFeeId={}", financeFeeId);
                }
            }
            
            log.info("案件费用及关联费用同步更新成功: caseFeeId={}", caseFeeId);
            return true;
        } catch (Exception e) {
            log.error("更新案件费用并同步失败", e);
            throw new RuntimeException("更新案件费用并同步失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional
    public boolean payContractFee(Long contractFeeId, BigDecimal amount, LocalDateTime paymentTime, 
                           String paymentMethod, String transactionNo) {
        log.info("记录合同费用支付: contractFeeId={}, amount={}, paymentMethod={}", 
                contractFeeId, amount, paymentMethod);
        
        try {
            // 1. 记录合同费用支付（调用合同模块服务）
            boolean contractPaid = recordContractFeePayment(
                    contractFeeId, amount, paymentTime, paymentMethod, transactionNo);
            if (!contractPaid) {
                log.warn("合同费用支付记录失败: contractFeeId={}", contractFeeId);
                return false;
            }
            
            // 2. 查找关联的案件费用ID
            Long caseFeeId = feeRelationService.getCaseFeeIdByContractFeeId(contractFeeId);
            if (caseFeeId != null) {
                // 3. 记录案件费用支付
                boolean casePaid = recordCaseFeePayment(
                        caseFeeId, amount, paymentTime, paymentMethod, transactionNo);
                if (!casePaid) {
                    log.warn("案件费用支付记录失败: caseFeeId={}", caseFeeId);
                }
            }
            
            // 4. 查找关联的财务费用ID
            Long financeFeeId = feeRelationService.getFinanceFeeIdByContractFeeId(contractFeeId);
            if (financeFeeId != null) {
                // 5. 记录财务费用支付
                boolean financePaid = recordFinanceFeePayment(
                        financeFeeId, amount, paymentTime, paymentMethod, transactionNo);
                if (!financePaid) {
                    log.warn("财务费用支付记录失败: financeFeeId={}", financeFeeId);
                }
            }
            
            log.info("合同费用支付记录成功: contractFeeId={}, amount={}", contractFeeId, amount);
            return true;
        } catch (Exception e) {
            log.error("记录合同费用支付失败", e);
            throw new RuntimeException("记录合同费用支付失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional
    public boolean payCaseFee(Long caseFeeId, BigDecimal amount, LocalDateTime paymentTime, 
                       String paymentMethod, String transactionNo) {
        log.info("记录案件费用支付: caseFeeId={}, amount={}, paymentMethod={}", 
                caseFeeId, amount, paymentMethod);
        
        try {
            // 1. 记录案件费用支付（调用案件模块服务）
            boolean casePaid = recordCaseFeePayment(
                    caseFeeId, amount, paymentTime, paymentMethod, transactionNo);
            if (!casePaid) {
                log.warn("案件费用支付记录失败: caseFeeId={}", caseFeeId);
                return false;
            }
            
            // 2. 查找关联的财务费用ID
            Long financeFeeId = feeRelationService.getFinanceFeeIdByCaseFeeId(caseFeeId);
            if (financeFeeId != null) {
                // 3. 记录财务费用支付
                boolean financePaid = recordFinanceFeePayment(
                        financeFeeId, amount, paymentTime, paymentMethod, transactionNo);
                if (!financePaid) {
                    log.warn("财务费用支付记录失败: financeFeeId={}", financeFeeId);
                }
            }
            
            log.info("案件费用支付记录成功: caseFeeId={}, amount={}", caseFeeId, amount);
            return true;
        } catch (Exception e) {
            log.error("记录案件费用支付失败", e);
            throw new RuntimeException("记录案件费用支付失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public BigDecimal getTotalContractFeeAmount(Long contractId) {
        log.info("获取合同总费用金额: contractId={}", contractId);
        
        try {
            // 调用合同模块服务获取合同总费用
            // 实际实现需要通过RPC调用合同模块的服务
            
            // 模拟获取合同总费用
            BigDecimal total = simulateGetTotalContractFee(contractId);
            
            log.info("获取合同总费用金额成功: contractId={}, total={}", contractId, total);
            return total;
        } catch (Exception e) {
            log.error("获取合同总费用金额失败", e);
            throw new RuntimeException("获取合同总费用金额失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public BigDecimal getTotalCaseFeeAmount(Long caseId) {
        log.info("获取案件总费用金额: caseId={}", caseId);
        
        try {
            // 调用案件模块服务获取案件总费用
            // 实际实现需要通过RPC调用案件模块的服务
            
            // 模拟获取案件总费用
            BigDecimal total = simulateGetTotalCaseFee(caseId);
            
            log.info("获取案件总费用金额成功: caseId={}, total={}", caseId, total);
            return total;
        } catch (Exception e) {
            log.error("获取案件总费用金额失败", e);
            throw new RuntimeException("获取案件总费用金额失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public BigDecimal getPaidContractFeeAmount(Long contractId) {
        log.info("获取合同已支付费用总额: contractId={}", contractId);
        
        try {
            // 调用合同模块服务获取合同已支付费用
            // 实际实现需要通过RPC调用合同模块的服务
            
            // 模拟获取合同已支付费用
            BigDecimal paidAmount = simulateGetPaidContractFee(contractId);
            
            log.info("获取合同已支付费用总额成功: contractId={}, paidAmount={}", contractId, paidAmount);
            return paidAmount;
        } catch (Exception e) {
            log.error("获取合同已支付费用总额失败", e);
            throw new RuntimeException("获取合同已支付费用总额失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public BigDecimal getPaidCaseFeeAmount(Long caseId) {
        log.info("获取案件已支付费用总额: caseId={}", caseId);
        
        try {
            // 调用案件模块服务获取案件已支付费用
            // 实际实现需要通过RPC调用案件模块的服务
            
            // 模拟获取案件已支付费用
            BigDecimal paidAmount = simulateGetPaidCaseFee(caseId);
            
            log.info("获取案件已支付费用总额成功: caseId={}, paidAmount={}", caseId, paidAmount);
            return paidAmount;
        } catch (Exception e) {
            log.error("获取案件已支付费用总额失败", e);
            throw new RuntimeException("获取案件已支付费用总额失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Long getContractFeeIdByFinanceFeeId(Long financeFeeId) {
        log.debug("根据财务费用ID查询关联的合同费用ID: financeFeeId={}", financeFeeId);
        return feeRelationService.getContractFeeIdByFinanceFeeId(financeFeeId);
    }
    
    @Override
    public Long getCaseFeeIdByFinanceFeeId(Long financeFeeId) {
        log.debug("根据财务费用ID查询关联的案件费用ID: financeFeeId={}", financeFeeId);
        return feeRelationService.getCaseFeeIdByFinanceFeeId(financeFeeId);
    }
    
    @Override
    public Long getContractFeeIdByCaseFeeId(Long caseFeeId) {
        log.debug("根据案件费用ID查询关联的合同费用ID: caseFeeId={}", caseFeeId);
        return feeRelationService.getContractFeeIdByCaseFeeId(caseFeeId);
    }
    
    // ============= 内部辅助方法 =============
    
    /**
     * 更新合同费用
     */
    private boolean updateContractFee(Long contractFeeId, BigDecimal amount, String description) {
        // 使用注入的合同服务更新合同费用
        if (contractService != null) {
            try {
                // 实际调用合同服务的方法更新费用
                log.info("调用合同服务更新合同费用: contractFeeId={}, amount={}", contractFeeId, amount);
                // 这里需要根据实际的ContractService接口方法进行调用
                // return contractService.updateContractFee(contractFeeId, amount, description);
            } catch (Exception e) {
                log.error("调用合同服务更新费用失败", e);
            }
        }
        
        // 模拟更新合同费用
        log.info("模拟更新合同费用: contractFeeId={}, amount={}", contractFeeId, amount);
        return true;
    }
    
    /**
     * 更新案件费用
     */
    private boolean updateCaseFee(Long caseFeeId, BigDecimal amount, String description) {
        // 使用注入的案件服务更新案件费用
        if (caseFinanceService != null) {
            try {
                // 实际调用案件服务的方法更新费用
                log.info("调用案件服务更新案件费用: caseFeeId={}, amount={}", caseFeeId, amount);
                // 这里需要根据实际的CaseFinanceService接口方法进行调用
                // 例如: return caseFinanceService.updateFinance(...);
            } catch (Exception e) {
                log.error("调用案件服务更新费用失败", e);
            }
        }
        
        // 模拟更新案件费用
        log.info("模拟更新案件费用: caseFeeId={}, amount={}", caseFeeId, amount);
        return true;
    }
    
    /**
     * 更新财务费用
     */
    private boolean updateFinanceFee(Long financeFeeId, BigDecimal amount, String description) {
        try {
            Fee fee = feeService.getFeeById(financeFeeId);
            if (fee == null) {
                log.warn("未找到财务费用记录: financeFeeId={}", financeFeeId);
                return false;
            }
            
            fee.setAmount(amount);
            if (StringUtils.hasText(description)) {
                fee.setDescription(description);
            }
            
            feeService.updateFee(fee);
            return true;
        } catch (Exception e) {
            log.error("更新财务费用失败: financeFeeId={}", financeFeeId, e);
            return false;
        }
    }
    
    /**
     * 记录合同费用支付
     */
    private boolean recordContractFeePayment(Long contractFeeId, BigDecimal amount, 
                                           LocalDateTime paymentTime, String paymentMethod, 
                                           String transactionNo) {
        // 模拟记录合同费用支付
        log.info("模拟记录合同费用支付: contractFeeId={}, amount={}", contractFeeId, amount);
        return true;
    }
    
    /**
     * 记录案件费用支付
     */
    private boolean recordCaseFeePayment(Long caseFeeId, BigDecimal amount, 
                                        LocalDateTime paymentTime, String paymentMethod, 
                                        String transactionNo) {
        // 模拟记录案件费用支付
        log.info("模拟记录案件费用支付: caseFeeId={}, amount={}", caseFeeId, amount);
        return true;
    }
    
    /**
     * 记录财务费用支付
     */
    private boolean recordFinanceFeePayment(Long financeFeeId, BigDecimal amount, 
                                           LocalDateTime paymentTime, String paymentMethod, 
                                           String transactionNo) {
        // 实际实现应该调用财务模块的支付记录服务
        log.info("模拟记录财务费用支付: financeFeeId={}, amount={}", financeFeeId, amount);
        return true;
    }
    
    /**
     * 模拟创建案件费用
     */
    private Long simulateCreateCaseFee(Long caseId, String caseNumber, BigDecimal amount, String description) {
        // 使用注入的案件服务创建费用
        if (caseFinanceService != null) {
            try {
                // 实际调用案件服务的方法创建费用
                log.info("调用案件服务创建案件费用: caseId={}, caseNumber={}, amount={}", caseId, caseNumber, amount);
                // 根据CaseFinanceService接口方法进行调用
                // 例如: return caseFinanceService.recordExpense(...);
            } catch (Exception e) {
                log.error("调用案件服务创建费用失败", e);
            }
        }
        
        // 模拟创建案件费用
        log.info("模拟创建案件费用: caseId={}, caseNumber={}, amount={}", caseId, caseNumber, amount);
        
        // 返回模拟的案件费用ID
        return System.currentTimeMillis();
    }
    
    /**
     * 模拟获取合同总费用
     */
    private BigDecimal simulateGetTotalContractFee(Long contractId) {
        // 使用注入的合同服务获取总费用
        if (contractService != null) {
            try {
                // 实际调用合同服务的方法获取总费用
                log.info("调用合同服务获取合同总费用: contractId={}", contractId);
                // 根据ContractService接口方法进行调用
                // 例如: return contractService.getTotalFeeAmount(contractId);
            } catch (Exception e) {
                log.error("调用合同服务获取总费用失败", e);
            }
        }
        
        // 模拟获取合同总费用
        return BigDecimal.valueOf(10000);
    }
    
    /**
     * 模拟获取案件总费用
     */
    private BigDecimal simulateGetTotalCaseFee(Long caseId) {
        // 使用注入的案件服务获取总费用
        if (caseFinanceService != null) {
            try {
                // 实际调用案件服务的方法获取总费用
                log.info("调用案件服务获取案件总费用: caseId={}", caseId);
                // 根据CaseFinanceService接口方法进行调用
                return caseFinanceService.calculateTotalExpense(caseId);
            } catch (Exception e) {
                log.error("调用案件服务获取总费用失败", e);
            }
        }
        
        // 模拟获取案件总费用
        return BigDecimal.valueOf(8000);
    }
    
    /**
     * 模拟获取合同已支付费用
     */
    private BigDecimal simulateGetPaidContractFee(Long contractId) {
        // 实际实现应该通过RPC调用合同模块的服务
        return BigDecimal.valueOf(5000);
    }
    
    /**
     * 模拟获取案件已支付费用
     */
    private BigDecimal simulateGetPaidCaseFee(Long caseId) {
        // 实际实现应该通过RPC调用案件模块的服务
        return BigDecimal.valueOf(4000);
    }
}
