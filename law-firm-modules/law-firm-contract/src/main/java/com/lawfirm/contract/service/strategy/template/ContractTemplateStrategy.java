package com.lawfirm.contract.service.strategy.template;

import com.lawfirm.model.contract.entity.Contract;

/**
 * 合同模板策略接口
 */
public interface ContractTemplateStrategy {

    /**
     * 获取策略类型
     * @return 策略类型标识
     */
    String getType();
    
    /**
     * 生成合同内容
     * @param contract 合同基本信息
     * @param templateId 模板ID
     * @return 生成的合同内容
     */
    String generateContractContent(Contract contract, Long templateId);
    
    /**
     * 验证合同内容
     * @param contractContent 合同内容
     * @return 验证结果，true表示通过验证
     */
    boolean validateContractContent(String contractContent);
    
    /**
     * 解析合同内容中的变量
     * @param contractContent 合同内容
     * @return 解析后的合同内容
     */
    String parseContractVariables(String contractContent, Contract contract);
}