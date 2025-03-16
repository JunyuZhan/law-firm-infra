package com.lawfirm.contract.service.strategy.template;

import com.lawfirm.model.contract.entity.Contract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 标准合同模板策略实现
 */
@Slf4j
@Component
public class StandardContractTemplateStrategy implements ContractTemplateStrategy {

    private static final String STRATEGY_TYPE = "STANDARD";

    @Override
    public String getType() {
        return STRATEGY_TYPE;
    }

    @Override
    public String generateContractContent(Contract contract, Long templateId) {
        log.info("使用标准模板策略生成合同内容, 合同ID: {}, 模板ID: {}", contract.getId(), templateId);
        
        // TODO: 从模板库获取模板内容
        String templateContent = getTemplateContent(templateId);
        
        // 解析模板中的变量并替换
        return parseContractVariables(templateContent, contract);
    }

    @Override
    public boolean validateContractContent(String contractContent) {
        if (contractContent == null || contractContent.isEmpty()) {
            log.warn("合同内容为空，验证失败");
            return false;
        }
        
        // TODO: 实现更复杂的合同内容验证逻辑
        // 例如：检查必要条款是否存在，合同格式是否正确等
        
        return true;
    }

    @Override
    public String parseContractVariables(String contractContent, Contract contract) {
        if (contractContent == null || contract == null) {
            return contractContent;
        }
        
        log.debug("解析合同内容中的变量, 合同ID: {}", contract.getId());
        
        // 替换合同基本信息变量
        String result = contractContent
                .replace("${contractNo}", nullToEmpty(contract.getContractNo()))
                .replace("${contractName}", nullToEmpty(contract.getContractName()))
                .replace("${contractType}", nullToEmpty(contract.getContractType()))
                .replace("${signingDate}", formatDate(contract.getSigningDate()))
                .replace("${effectiveDate}", formatDate(contract.getEffectiveDate()))
                .replace("${expiryDate}", formatDate(contract.getExpiryDate()))
                .replace("${amount}", formatAmount(contract.getAmount()));
        
        // TODO: 替换更多变量，如客户信息、律师信息等
        
        return result;
    }
    
    /**
     * 获取模板内容
     */
    private String getTemplateContent(Long templateId) {
        // TODO: 从模板库获取模板内容
        // 这里简化处理，返回一个基础模板
        return "合同编号: ${contractNo}\n"
                + "合同名称: ${contractName}\n"
                + "合同类型: ${contractType}\n"
                + "签约日期: ${signingDate}\n"
                + "生效日期: ${effectiveDate}\n"
                + "到期日期: ${expiryDate}\n"
                + "合同金额: ${amount}\n\n"
                + "甲方: ${clientName}\n"
                + "乙方: 律师事务所\n\n"
                + "合同正文...\n\n"
                + "甲方签字: ____________       乙方签字: ____________\n";
    }
    
    /**
     * 空值转为空字符串
     */
    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
    
    /**
     * 格式化日期
     */
    private String formatDate(java.util.Date date) {
        if (date == null) {
            return "";
        }
        return new java.text.SimpleDateFormat("yyyy年MM月dd日").format(date);
    }
    
    /**
     * 格式化金额
     */
    private String formatAmount(Double amount) {
        if (amount == null) {
            return "0.00";
        }
        return String.format("%.2f", amount);
    }
} 