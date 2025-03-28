package com.lawfirm.core.ai.service.impl;

import com.lawfirm.core.ai.provider.AIProvider;
import com.lawfirm.core.ai.provider.AIProviderFactory;
import com.lawfirm.model.ai.service.DocProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文档处理服务实现类
 * 实现文档解析、分类、关键信息提取等功能
 */
@Component("aiDocProcessServiceImpl")
@RequiredArgsConstructor
public class DocProcessServiceImpl implements DocProcessService {
    
    private static final Logger logger = LoggerFactory.getLogger(DocProcessServiceImpl.class);
    
    private final AIProviderFactory providerFactory;
    
    @Value("${lawfirm.ai.default-provider:openai}")
    private String defaultProviderName;
    
    @Override
    public Map<String, Double> classifyDocument(String docContent) {
        logger.info("开始对文档进行分类分析，文档长度: {}", docContent.length());
        
        try {
            AIProvider provider = providerFactory.getProvider(defaultProviderName);
            
            // 构建提示词
            String prompt = "请对以下法律文档进行分类，返回文档类型及置信度（0-1之间的小数）。" +
                    "返回格式为JSON，包含类型及置信度，例如：{\"合同\":0.95,\"劳动合同\":0.85}。\n\n" +
                    "文档内容：\n" + docContent;
            
            // 调用AI服务
            String response = provider.sendTextRequest(prompt, Map.of("max_tokens", 500));
            
            // 解析结果（简化处理，实际项目中应使用JSON解析库）
            Map<String, Double> result = parseClassificationResult(response);
            
            logger.info("文档分类完成，识别为: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("文档分类过程中发生错误", e);
            return Map.of("未知类型", 0.0);
        }
    }
    
    @Override
    public Map<String, Object> extractDocumentInfo(String docContent, String docType) {
        logger.info("开始从文档中提取关键信息，文档类型: {}, 文档长度: {}", docType, docContent.length());
        
        try {
            AIProvider provider = providerFactory.getProvider(defaultProviderName);
            
            // 构建提示词
            String prompt = "请从以下" + (docType != null ? docType : "法律文档") + "中提取关键信息，" +
                    "返回格式为JSON。提取的信息应包括：当事人、日期、主要内容、关键条款等。\n\n" +
                    "文档内容：\n" + docContent;
            
            // 调用AI服务
            String response = provider.sendTextRequest(prompt, Map.of("max_tokens", 1000));
            
            // 解析结果（简化处理，实际项目中应使用JSON解析库）
            Map<String, Object> result = parseExtractedInfo(response);
            
            logger.info("文档信息提取完成，提取到 {} 项信息", result.size());
            return result;
        } catch (Exception e) {
            logger.error("文档信息提取过程中发生错误", e);
            return new HashMap<>();
        }
    }
    
    @Override
    public String generateDocSummary(String docContent, int maxLength) {
        logger.info("开始生成文档摘要，文档长度: {}, 最大摘要长度: {}", docContent.length(), maxLength);
        
        try {
            AIProvider provider = providerFactory.getProvider(defaultProviderName);
            
            // 构建提示词
            String prompt = "请对以下法律文档生成一个简洁的摘要，摘要字数不超过" + maxLength + "字。\n\n" +
                    "文档内容：\n" + docContent;
            
            // 调用AI服务
            String response = provider.sendTextRequest(prompt, Map.of("max_tokens", maxLength / 2));
            
            logger.info("文档摘要生成完成，摘要长度: {}", response.length());
            return response;
        } catch (Exception e) {
            logger.error("生成文档摘要过程中发生错误", e);
            return "摘要生成失败";
        }
    }
    
    @Override
    public List<Map<String, Object>> analyzeContractClauses(String contractContent) {
        logger.info("开始分析合同条款，合同长度: {}", contractContent.length());
        
        try {
            AIProvider provider = providerFactory.getProvider(defaultProviderName);
            
            // 构建提示词
            String prompt = "请分析以下合同的主要条款，对每个条款进行解析并评估风险。" +
                    "返回格式为JSON数组，每个条款包含：条款标题、条款内容、条款类型、重要性（high/medium/low）、" +
                    "风险评级（high/medium/low/none）、分析说明。\n\n" +
                    "合同内容：\n" + contractContent;
            
            // 调用AI服务
            String response = provider.sendTextRequest(prompt, Map.of("max_tokens", 2000));
            
            // 解析结果（简化处理，实际项目中应使用JSON解析库）
            List<Map<String, Object>> result = parseContractClausesResult(response);
            
            logger.info("合同条款分析完成，共分析 {} 个条款", result.size());
            return result;
        } catch (Exception e) {
            logger.error("分析合同条款过程中发生错误", e);
            return new ArrayList<>();
        }
    }
    
    @Override
    public String generateDocument(String templateId, Map<String, Object> params) {
        logger.info("开始生成文书，模板ID: {}, 参数数量: {}", templateId, params.size());
        
        try {
            AIProvider provider = providerFactory.getProvider(defaultProviderName);
            
            // 构建提示词
            StringBuilder prompt = new StringBuilder();
            prompt.append("请根据以下模板和参数生成一份法律文书。\n\n");
            prompt.append("模板ID: ").append(templateId).append("\n");
            prompt.append("参数:\n");
            
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                prompt.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            
            // 调用AI服务
            String response = provider.sendTextRequest(prompt.toString(), Map.of("max_tokens", 2000));
            
            logger.info("文书生成完成，生成文书长度: {}", response.length());
            return response;
        } catch (Exception e) {
            logger.error("生成文书过程中发生错误", e);
            return "文书生成失败";
        }
    }
    
    @Override
    public List<Map<String, Object>> compareDocuments(String doc1, String doc2) {
        logger.info("开始比较两份文档的差异，文档1长度: {}, 文档2长度: {}", doc1.length(), doc2.length());
        
        try {
            AIProvider provider = providerFactory.getProvider(defaultProviderName);
            
            // 构建提示词
            String prompt = "请比较以下两份法律文档的差异，返回主要差异点列表。" +
                    "返回格式为JSON数组，每个差异点包含：差异类型（新增/删除/修改）、位置描述、原文内容、新内容、影响分析。\n\n" +
                    "文档1:\n" + doc1 + "\n\n" +
                    "文档2:\n" + doc2;
            
            // 调用AI服务
            String response = provider.sendTextRequest(prompt, Map.of("max_tokens", 2000));
            
            // 解析结果（简化处理，实际项目中应使用JSON解析库）
            List<Map<String, Object>> result = parseDocumentComparisonResult(response);
            
            logger.info("文档比较完成，发现 {} 处差异", result.size());
            return result;
        } catch (Exception e) {
            logger.error("比较文档差异过程中发生错误", e);
            return new ArrayList<>();
        }
    }
    
    // 辅助方法：解析分类结果
    private Map<String, Double> parseClassificationResult(String response) {
        // 简化实现，实际项目中应使用JSON解析库
        Map<String, Double> result = new HashMap<>();
        
        // 模拟解析结果
        result.put("合同", 0.95);
        result.put("劳动合同", 0.85);
        
        return result;
    }
    
    // 辅助方法：解析提取的信息
    private Map<String, Object> parseExtractedInfo(String response) {
        // 简化实现，实际项目中应使用JSON解析库
        Map<String, Object> result = new HashMap<>();
        
        // 模拟解析结果
        result.put("当事人", "甲方：张三，乙方：李四");
        result.put("签订日期", "2023-01-15");
        result.put("合同金额", 50000);
        
        return result;
    }
    
    // 辅助方法：解析合同条款分析结果
    private List<Map<String, Object>> parseContractClausesResult(String response) {
        // 简化实现，实际项目中应使用JSON解析库
        List<Map<String, Object>> result = new ArrayList<>();
        
        // 模拟解析结果
        Map<String, Object> clause1 = new HashMap<>();
        clause1.put("clauseTitle", "违约责任");
        clause1.put("clauseContent", "如一方违约，应向对方支付合同金额20%的违约金");
        clause1.put("clauseType", "违约条款");
        clause1.put("importance", "high");
        clause1.put("risk", "medium");
        clause1.put("analysis", "违约金比例较高，但在合理范围内");
        
        result.add(clause1);
        
        return result;
    }
    
    // 辅助方法：解析文档比较结果
    private List<Map<String, Object>> parseDocumentComparisonResult(String response) {
        // 简化实现，实际项目中应使用JSON解析库
        List<Map<String, Object>> result = new ArrayList<>();
        
        // 模拟解析结果
        Map<String, Object> diff1 = new HashMap<>();
        diff1.put("type", "修改");
        diff1.put("location", "第3条");
        diff1.put("originalContent", "乙方应于2023年3月1日前交付");
        diff1.put("newContent", "乙方应于2023年5月1日前交付");
        diff1.put("impact", "延长了交付期限，降低了违约风险");
        
        result.add(diff1);
        
        return result;
    }
} 