package com.lawfirm.contract.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模板解析工具类
 * 用于解析合同模板中的变量
 */
@Slf4j
@Component
public class TemplateParser {

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^}]+)\\}");

    /**
     * 解析模板变量
     *
     * @param template 模板内容
     * @param variables 变量映射
     * @return 解析后的内容
     */
    public String parse(String template, Map<String, Object> variables) {
        if (template == null || template.isEmpty() || variables == null || variables.isEmpty()) {
            return template;
        }

        log.debug("开始解析模板变量, 变量数量: {}", variables.size());
        
        // 使用正则表达式匹配模板中的变量
        Matcher matcher = VARIABLE_PATTERN.matcher(template);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String variableName = matcher.group(1);
            Object value = variables.get(variableName);
            
            String replacement = (value != null) ? value.toString() : "";
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);

        return result.toString();
    }

    /**
     * 提取模板中的变量名
     *
     * @param template 模板内容
     * @return 变量名集合
     */
    public Map<String, String> extractVariables(String template) {
        Map<String, String> variables = new HashMap<>();
        
        if (template == null || template.isEmpty()) {
            return variables;
        }

        // 使用正则表达式匹配模板中的变量
        Matcher matcher = VARIABLE_PATTERN.matcher(template);
        
        while (matcher.find()) {
            String variableName = matcher.group(1);
            variables.put(variableName, "");
        }

        log.debug("从模板中提取变量, 变量数量: {}", variables.size());
        return variables;
    }
    
    /**
     * 验证模板变量是否完整
     *
     * @param template 模板内容
     * @param variables 变量映射
     * @return 是否所有变量都有值
     */
    public boolean validateTemplateVariables(String template, Map<String, Object> variables) {
        if (template == null || template.isEmpty()) {
            return true;
        }
        
        if (variables == null) {
            variables = new HashMap<>();
        }

        // 提取模板中的所有变量
        Map<String, String> templateVariables = extractVariables(template);
        
        // 检查每个变量是否在variables中有值
        for (String variableName : templateVariables.keySet()) {
            if (!variables.containsKey(variableName)) {
                log.warn("模板变量缺失值: {}", variableName);
                return false;
            }
        }

        return true;
    }
    
    /**
     * 构建自动通过合同内容的变量映射
     * 
     * @param contract 合同实体
     * @return 变量映射
     */
    public Map<String, Object> buildContractVariables(com.lawfirm.model.contract.entity.Contract contract) {
        Map<String, Object> variables = new HashMap<>();
        
        if (contract == null) {
            return variables;
        }
        
        // 填充合同基本信息
        variables.put("contractNo", nullToEmpty(contract.getContractNo()));
        variables.put("contractName", nullToEmpty(contract.getContractName()));
        variables.put("contractType", nullToEmpty(contract.getContractType()));
        variables.put("signingDate", formatDate(contract.getSigningDate()));
        variables.put("effectiveDate", formatDate(contract.getEffectiveDate()));
        variables.put("expiryDate", formatDate(contract.getExpiryDate()));
        variables.put("amount", formatAmount(contract.getAmount()));
        
        // TODO: 填充客户信息
        // variables.put("clientName", "客户名称");
        
        // TODO: 填充律师信息
        // variables.put("lawyerName", "律师姓名");
        
        // TODO: 填充更多变量
        
        return variables;
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