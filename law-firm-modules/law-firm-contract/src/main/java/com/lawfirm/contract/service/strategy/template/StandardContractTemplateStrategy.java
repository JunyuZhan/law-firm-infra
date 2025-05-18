package com.lawfirm.contract.service.strategy.template;

import com.lawfirm.model.contract.entity.Contract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 标准合同模板策略实现
 */
@Slf4j
@Component
public class StandardContractTemplateStrategy implements ContractTemplateStrategy {

    private static final String STRATEGY_TYPE = "STANDARD";

    @Autowired(required = false)
    private com.lawfirm.model.contract.mapper.ContractTemplateMapper contractTemplateMapper;

    @Override
    public String getType() {
        return STRATEGY_TYPE;
    }

    @Override
    public String generateContractContent(Contract contract, Long templateId) {
        log.info("使用标准模板策略生成合同内容, 合同ID: {}, 模板ID: {}", contract.getId(), templateId);
        
        // 从模板库获取模板内容
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
        
        // 检查必要条款
        String[] mustContain = {"甲方", "乙方", "合同金额", "签字"};
        for (String key : mustContain) {
            if (!contractContent.contains(key)) {
                log.warn("合同内容缺少必要条款: {}", key);
                return false;
            }
        }
        
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
        
        // 替换客户信息（仅用ID占位，实际应由业务层查好客户名/联系人）
        result = result.replace("${clientName}", contract.getClientId() != null ? String.valueOf(contract.getClientId()) : "")
                       .replace("${clientContact}", "");
        // 替换律师信息（仅用ID占位，实际应由业务层查好律师名/电话）
        result = result.replace("${lawyerName}", contract.getLeadAttorneyId() != null ? String.valueOf(contract.getLeadAttorneyId()) : "")
                       .replace("${lawyerPhone}", "");
        
        return result;
    }
    
    /**
     * 获取模板内容
     */
    private String getTemplateContent(Long templateId) {
        // 从模板库获取模板内容
        if (contractTemplateMapper != null && templateId != null) {
            com.lawfirm.model.contract.entity.ContractTemplate template = contractTemplateMapper.selectById(templateId);
            if (template != null && template.getContent() != null) {
                return template.getContent();
            }
        }
        // 兜底返回默认模板
        return "合同编号: ${contractNo}\n"
                + "合同名称: ${contractName}\n"
                + "合同类型: ${contractType}\n"
                + "签约日期: ${signingDate}\n"
                + "生效日期: ${effectiveDate}\n"
                + "到期日期: ${expiryDate}\n"
                + "合同金额: ${amount}\n\n"
                + "甲方: ${clientName}\n"
                + "联系人: ${clientContact}\n"
                + "乙方: 律师事务所\n"
                + "经办律师: ${lawyerName}\n"
                + "律师电话: ${lawyerPhone}\n\n"
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