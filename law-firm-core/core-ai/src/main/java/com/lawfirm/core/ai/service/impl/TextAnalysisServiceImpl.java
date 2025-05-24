package com.lawfirm.core.ai.service.impl;

import com.lawfirm.core.ai.provider.AIProvider;
import com.lawfirm.core.ai.provider.AIProviderFactory;
import com.lawfirm.core.ai.exception.AIException;
import com.lawfirm.model.ai.service.TextAnalysisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本分析服务实现类
 */
@Slf4j
@Component("aiTextAnalysisServiceImpl")
@RequiredArgsConstructor
public class TextAnalysisServiceImpl implements TextAnalysisService {
    
    @Autowired
    private AIProviderFactory providerFactory;
    
    @Override
    public Map<String, Object> extractKeyInfo(String text) {
        log.info("提取文本关键信息");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            String prompt = "请从以下法律文本中提取关键信息，包括：涉案人员、案件类型、关键日期、关键金额、争议焦点等。" +
                    "以JSON格式返回结果。\n\n" + text;
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.2);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 从结果中解析JSON（实际实现中应使用JSON库解析）
            Map<String, Object> keyInfo = parseJsonFromText(result);
            
            return keyInfo;
        } catch (Exception e) {
            log.error("提取文本关键信息失败", e);
            throw new AIException("提取文本关键信息失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String generateSummary(String text, int maxLength) {
        log.info("生成文本摘要，最大长度: {}", maxLength);
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            String prompt = "请对以下法律文本生成一个简洁明了的摘要，控制在" + maxLength + "字以内：\n\n" + text;
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.3);
            options.put("max_tokens", maxLength * 2); // 控制输出长度
            
            String summary = provider.sendTextRequest(prompt, options);
            
            // 确保不超过最大长度
            if (summary.length() > maxLength) {
                summary = summary.substring(0, maxLength) + "...";
            }
            
            return summary;
        } catch (Exception e) {
            log.error("生成文本摘要失败", e);
            throw new AIException("生成文本摘要失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public double calculateSimilarity(String text1, String text2) {
        log.info("计算文本相似度");
        
        try {
            // 使用嵌入向量计算相似度
            AIProvider provider = providerFactory.getDefaultProvider();
            
            float[] embedding1 = provider.createEmbedding(text1);
            float[] embedding2 = provider.createEmbedding(text2);
            
            // 计算余弦相似度
            double similarity = cosineSimilarity(embedding1, embedding2);
            
            return similarity;
        } catch (Exception e) {
            log.error("计算文本相似度失败", e);
            throw new AIException("计算文本相似度失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Map<String, String> identifyTerms(String text) {
        log.info("识别文本中的专业术语");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            String prompt = "请从以下法律文本中识别出专业法律术语，并给出简明解释。以JSON格式返回，" +
                    "格式为：{\"术语1\": \"解释1\", \"术语2\": \"解释2\", ...}\n\n" + text;
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.2);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 从结果中解析JSON（实际实现中应使用JSON库解析）
            Map<String, String> terms = new HashMap<>();
            Map<String, Object> parsedJson = parseJsonFromText(result);
            
            for (Map.Entry<String, Object> entry : parsedJson.entrySet()) {
                terms.put(entry.getKey(), entry.getValue().toString());
            }
            
            return terms;
        } catch (Exception e) {
            log.error("识别文本中的专业术语失败", e);
            throw new AIException("识别文本中的专业术语失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Map<String, Double> classifyText(String text) {
        log.info("对文本进行分类");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            String prompt = "请对以下法律文本进行分类，确定其属于哪些法律领域(如刑法、民法、商法、行政法等)，" +
                    "并给出每个类别的置信度(0-1之间)。以JSON格式返回结果。\n\n" + text;
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.2);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 从结果中解析JSON
            Map<String, Object> parsedJson = parseJsonFromText(result);
            Map<String, Double> classifications = new HashMap<>();
            
            for (Map.Entry<String, Object> entry : parsedJson.entrySet()) {
                try {
                    Double confidence = Double.parseDouble(entry.getValue().toString());
                    classifications.put(entry.getKey(), confidence);
                } catch (NumberFormatException e) {
                    log.warn("解析分类置信度失败: {}", entry.getValue());
                    classifications.put(entry.getKey(), 0.0);
                }
            }
            
            return classifications;
        } catch (Exception e) {
            log.error("对文本进行分类失败", e);
            throw new AIException("对文本进行分类失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Map<String, List<String>> extractEntities(String text) {
        log.info("提取文本中的命名实体");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            String prompt = "请从以下法律文本中提取命名实体，包括人名、地名、组织机构名、日期、金额等。" +
                    "以JSON格式返回，格式为：{\"人名\": [\"张三\", \"李四\"], \"组织\": [\"某公司\"], ...}\n\n" + text;
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.1);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 从结果中解析JSON
            Map<String, Object> parsedJson = parseJsonFromText(result);
            Map<String, List<String>> entities = new HashMap<>();
            
            for (Map.Entry<String, Object> entry : parsedJson.entrySet()) {
                if (entry.getValue() instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<String> entityList = (List<String>) entry.getValue();
                    entities.put(entry.getKey(), entityList);
                } else if (entry.getValue() instanceof String) {
                    // 处理单个实体的情况
                    List<String> entityList = new ArrayList<>();
                    entityList.add(entry.getValue().toString());
                    entities.put(entry.getKey(), entityList);
                }
            }
            
            return entities;
        } catch (Exception e) {
            log.error("提取文本中的命名实体失败", e);
            throw new AIException("提取文本中的命名实体失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 计算两个向量的余弦相似度
     * 
     * @param vec1 向量1
     * @param vec2 向量2
     * @return 余弦相似度(0-1)
     */
    private double cosineSimilarity(float[] vec1, float[] vec2) {
        if (vec1.length != vec2.length) {
            throw new IllegalArgumentException("向量维度不匹配");
        }
        
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        
        for (int i = 0; i < vec1.length; i++) {
            dotProduct += vec1[i] * vec2[i];
            norm1 += vec1[i] * vec1[i];
            norm2 += vec2[i] * vec2[i];
        }
        
        norm1 = Math.sqrt(norm1);
        norm2 = Math.sqrt(norm2);
        
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }
        
        return dotProduct / (norm1 * norm2);
    }
    
    /**
     * 从文本中解析JSON
     * 简单实现，实际项目中应使用JSON库解析
     * 
     * @param text 包含JSON的文本
     * @return 解析后的Map
     */
    private Map<String, Object> parseJsonFromText(String text) {
        // 注意：这是非常简化的实现，仅用于示例
        // 实际项目中应使用Jackson或Gson等JSON库解析
        
        Map<String, Object> result = new HashMap<>();
        
        // 查找JSON开始和结束位置
        int start = text.indexOf('{');
        int end = text.lastIndexOf('}');
        
        if (start != -1 && end != -1 && end > start) {
            String jsonText = text.substring(start, end + 1);
            
            // 极其简单的键值对解析
            Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*\"([^\"]*)\"");
            Matcher matcher = pattern.matcher(jsonText);
            
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                result.put(key, value);
            }
        }
        
        return result;
    }
} 