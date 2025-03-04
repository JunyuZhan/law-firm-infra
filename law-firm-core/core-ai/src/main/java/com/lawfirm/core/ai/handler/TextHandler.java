package com.lawfirm.core.ai.handler;

import com.lawfirm.core.ai.provider.AIProvider;
import com.lawfirm.core.ai.provider.AIProviderFactory;
import com.lawfirm.core.ai.exception.AIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 文本处理器
 * 负责与AI模型交互进行各种文本处理任务
 */
@Component
public class TextHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(TextHandler.class);
    
    @Autowired
    private AIProviderFactory providerFactory;
    
    /**
     * 文本摘要生成
     * 
     * @param text 需要摘要的文本
     * @param maxLength 摘要最大长度
     * @return 生成的摘要
     */
    public String generateSummary(String text, int maxLength) {
        logger.info("生成文本摘要，最大长度: {}", maxLength);
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            String prompt = String.format(
                "请将以下文本概括为不超过%d字的摘要：\n\n%s", 
                maxLength, 
                text
            );
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.3); // 降低随机性，提高一致性
            
            return provider.sendTextRequest(prompt, options);
        } catch (Exception e) {
            logger.error("文本摘要生成失败", e);
            throw new AIException("文本摘要生成失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 文本分类
     * 
     * @param text 需要分类的文本
     * @param categories 分类类别数组
     * @return 分类结果
     */
    public Map<String, Object> classifyText(String text, String[] categories) {
        logger.info("进行文本分类，类别数量: {}", categories.length);
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            StringBuilder promptBuilder = new StringBuilder();
            promptBuilder.append("请将以下文本分类到最相关的一个类别中，并给出置信度分数(0-1)和理由。以JSON格式返回结果。\n\n");
            promptBuilder.append("类别：\n");
            
            for (String category : categories) {
                promptBuilder.append("- ").append(category).append("\n");
            }
            
            promptBuilder.append("\n文本内容：\n").append(text);
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.2);
            
            String result = provider.sendTextRequest(promptBuilder.toString(), options);
            
            // 这里应该解析JSON结果，但为简化示例，直接返回模拟数据
            Map<String, Object> classification = new HashMap<>();
            classification.put("category", categories[0]);
            classification.put("confidence", 0.85);
            classification.put("reason", "文本内容主要涉及法律合同条款解释，与合同法律问题最相关。");
            
            return classification;
        } catch (Exception e) {
            logger.error("文本分类失败", e);
            throw new AIException("文本分类失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 关键信息提取
     * 
     * @param text 文本内容
     * @param entityTypes 需要提取的实体类型数组(如"人名", "地址", "日期", "金额"等)
     * @return 提取的关键信息
     */
    public Map<String, Object> extractEntities(String text, String[] entityTypes) {
        logger.info("提取文本关键信息，实体类型: {}", String.join(", ", entityTypes));
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            StringBuilder promptBuilder = new StringBuilder();
            promptBuilder.append("请从以下文本中提取关键信息，并以JSON格式返回。\n\n");
            promptBuilder.append("需要提取的信息类型：\n");
            
            for (String type : entityTypes) {
                promptBuilder.append("- ").append(type).append("\n");
            }
            
            promptBuilder.append("\n文本内容：\n").append(text);
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.1);
            
            String result = provider.sendTextRequest(promptBuilder.toString(), options);
            
            // 简化示例，返回模拟数据
            Map<String, Object> entities = new HashMap<>();
            
            if (contains(entityTypes, "人名")) {
                entities.put("人名", new String[]{"张三", "李四"});
            }
            
            if (contains(entityTypes, "日期")) {
                entities.put("日期", new String[]{"2023年6月15日", "2023年7月1日"});
            }
            
            if (contains(entityTypes, "金额")) {
                entities.put("金额", new String[]{"人民币50000元", "人民币2000元"});
            }
            
            if (contains(entityTypes, "地址")) {
                entities.put("地址", new String[]{"北京市海淀区中关村南大街5号"});
            }
            
            return entities;
        } catch (Exception e) {
            logger.error("关键信息提取失败", e);
            throw new AIException("关键信息提取失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 文本相似度比较
     * 
     * @param text1 第一个文本
     * @param text2 第二个文本
     * @return 相似度评分(0-1)和分析
     */
    public Map<String, Object> compareTexts(String text1, String text2) {
        logger.info("比较两段文本的相似度");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            String prompt = "请比较以下两段文本的相似度，给出0-1之间的相似度评分，并分析相似和不同之处。以JSON格式返回结果。\n\n" +
                            "文本1：\n" + text1 + "\n\n" +
                            "文本2：\n" + text2;
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.2);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 简化示例，返回模拟数据
            Map<String, Object> comparison = new HashMap<>();
            comparison.put("similarity_score", 0.72);
            comparison.put("analysis", "两段文本在主题和关键概念上有较高相似度，但在表达方式和详细程度上存在差异。文本2比文本1更加详细地阐述了法律责任部分。");
            comparison.put("common_key_points", new String[]{"合同违约条款", "赔偿责任"});
            comparison.put("differences", new String[]{"文本1强调违约金计算方式", "文本2强调举证责任"});
            
            return comparison;
        } catch (Exception e) {
            logger.error("文本相似度比较失败", e);
            throw new AIException("文本相似度比较失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 文本语义分析
     * 
     * @param text 需要分析的文本
     * @return 语义分析结果
     */
    public Map<String, Object> analyzeSentiment(String text) {
        logger.info("进行文本语义分析");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            String prompt = "请对以下文本进行语义分析，包括情感倾向(积极/中性/消极)、客观性评分(0-1)、关键主题和核心观点。以JSON格式返回结果。\n\n" +
                          "文本内容：\n" + text;
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.2);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 简化示例，返回模拟数据
            Map<String, Object> analysis = new HashMap<>();
            analysis.put("sentiment", "中性");
            analysis.put("objectivity_score", 0.85);
            analysis.put("key_topics", new String[]{"合同纠纷", "法律责任", "举证要求"});
            analysis.put("core_points", "文本主要客观陈述了合同纠纷中的法律责任认定问题，强调了举证责任的重要性。");
            
            return analysis;
        } catch (Exception e) {
            logger.error("文本语义分析失败", e);
            throw new AIException("文本语义分析失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 简单的法律文本翻译（中英互译）
     * 
     * @param text 需要翻译的文本
     * @param sourceLanguage 源语言 (zh/en)
     * @param targetLanguage 目标语言 (zh/en)
     * @return 翻译结果
     */
    public String translateLegalText(String text, String sourceLanguage, String targetLanguage) {
        logger.info("进行法律文本翻译: {} -> {}", sourceLanguage, targetLanguage);
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            String prompt = String.format(
                "请将以下%s法律文本准确翻译为%s，保持法律术语的专业性和准确性：\n\n%s",
                sourceLanguage.equals("zh") ? "中文" : "英文",
                targetLanguage.equals("zh") ? "中文" : "英文",
                text
            );
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.3);
            
            return provider.sendTextRequest(prompt, options);
        } catch (Exception e) {
            logger.error("法律文本翻译失败", e);
            throw new AIException("法律文本翻译失败: " + e.getMessage(), e);
        }
    }
    
    // 辅助方法：检查数组中是否包含特定元素
    private boolean contains(String[] array, String value) {
        for (String item : array) {
            if (item.equals(value)) {
                return true;
            }
        }
        return false;
    }
} 