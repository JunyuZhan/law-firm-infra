package com.lawfirm.core.ai.handler;

import com.lawfirm.core.ai.provider.AIProvider;
import com.lawfirm.core.ai.provider.AIProviderFactory;
import com.lawfirm.core.ai.exception.AIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 问答处理器
 * 负责处理法律问答相关功能
 */
@Component
public class QAHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(QAHandler.class);
    
    @Autowired
    private AIProviderFactory providerFactory;
    
    /**
     * 法律问题回答
     * 
     * @param question 用户问题
     * @param context 相关上下文信息（可选）
     * @return 回答结果
     */
    public Map<String, Object> answerLegalQuestion(String question, String context) {
        logger.info("回答法律问题: {}", question);
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            StringBuilder promptBuilder = new StringBuilder();
            promptBuilder.append("请回答以下法律问题，给出详细的解释和法律依据。回答应当准确、专业，并以JSON格式返回。\n\n");
            promptBuilder.append("问题：").append(question).append("\n\n");
            
            if (context != null && !context.trim().isEmpty()) {
                promptBuilder.append("相关背景：\n").append(context).append("\n\n");
            }
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.3);
            
            String result = provider.sendTextRequest(promptBuilder.toString(), options);
            
            // 简化示例，返回模拟数据
            Map<String, Object> answer = new HashMap<>();
            answer.put("question", question);
            answer.put("answer", "根据《中华人民共和国民法典》第XX条规定，...");
            answer.put("legalBasis", Arrays.asList(
                "《中华人民共和国民法典》第XX条",
                "最高人民法院关于XX的司法解释第X条"
            ));
            answer.put("suggestions", Arrays.asList(
                "建议收集相关证据",
                "建议尽快采取法律行动"
            ));
            answer.put("confidence", 0.85);
            
            return answer;
        } catch (Exception e) {
            logger.error("回答法律问题失败", e);
            throw new AIException("回答法律问题失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 多轮对话
     * 
     * @param question 当前问题
     * @param dialogHistory 对话历史记录
     * @return 回答结果
     */
    public Map<String, Object> handleDialogue(String question, List<Map<String, String>> dialogHistory) {
        logger.info("处理多轮对话问题: {}", question);
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            StringBuilder promptBuilder = new StringBuilder();
            promptBuilder.append("请基于以下对话历史，回答用户的法律问题。回答应当准确、专业，并考虑上下文信息。以JSON格式返回结果。\n\n");
            
            // 添加对话历史
            promptBuilder.append("对话历史：\n");
            for (Map<String, String> dialog : dialogHistory) {
                promptBuilder.append("用户：").append(dialog.get("question")).append("\n");
                promptBuilder.append("助手：").append(dialog.get("answer")).append("\n\n");
            }
            
            promptBuilder.append("当前问题：").append(question);
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.4);
            
            String result = provider.sendTextRequest(promptBuilder.toString(), options);
            
            // 简化示例，返回模拟数据
            Map<String, Object> response = new HashMap<>();
            response.put("question", question);
            response.put("answer", "基于您之前提到的情况，我建议...");
            response.put("relatedQuestions", Arrays.asList(
                "您是否已经...",
                "建议您考虑..."
            ));
            response.put("confidence", 0.8);
            
            return response;
        } catch (Exception e) {
            logger.error("处理多轮对话失败", e);
            throw new AIException("处理多轮对话失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 法律咨询建议
     * 
     * @param caseInfo 案件信息
     * @return 咨询建议
     */
    public Map<String, Object> provideLegalAdvice(Map<String, Object> caseInfo) {
        logger.info("提供法律咨询建议");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            StringBuilder promptBuilder = new StringBuilder();
            promptBuilder.append("请根据以下案件信息，提供专业的法律咨询建议。建议应当具体、可行，并以JSON格式返回。\n\n");
            promptBuilder.append("案件信息：\n");
            
            for (Map.Entry<String, Object> entry : caseInfo.entrySet()) {
                promptBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.3);
            
            String result = provider.sendTextRequest(promptBuilder.toString(), options);
            
            // 简化示例，返回模拟数据
            Map<String, Object> advice = new HashMap<>();
            
            // 总体评估
            advice.put("caseAnalysis", "根据您提供的信息，这是一个典型的合同纠纷案件...");
            advice.put("riskLevel", "中等");
            
            // 具体建议
            List<Map<String, Object>> recommendations = new ArrayList<>();
            
            Map<String, Object> rec1 = new HashMap<>();
            rec1.put("aspect", "证据收集");
            rec1.put("suggestion", "建议立即收集以下证据：合同原件、付款凭证、往来函件等");
            rec1.put("priority", "高");
            recommendations.add(rec1);
            
            Map<String, Object> rec2 = new HashMap<>();
            rec2.put("aspect", "时效考虑");
            rec2.put("suggestion", "建议在XX日期前采取法律行动，以免超过诉讼时效");
            rec2.put("priority", "高");
            recommendations.add(rec2);
            
            Map<String, Object> rec3 = new HashMap<>();
            rec3.put("aspect", "调解方案");
            rec3.put("suggestion", "可以考虑先行调解，建议的调解方案包括：...");
            rec3.put("priority", "中");
            recommendations.add(rec3);
            
            advice.put("recommendations", recommendations);
            
            // 法律依据
            advice.put("legalBasis", Arrays.asList(
                "《中华人民共和国民法典》第XX条",
                "最高人民法院关于审理XX纠纷案件适用法律若干问题的解释第X条"
            ));
            
            // 下一步建议
            advice.put("nextSteps", Arrays.asList(
                "立即收集和整理相关证据",
                "考虑启动诉前调解程序",
                "准备起诉材料"
            ));
            
            return advice;
        } catch (Exception e) {
            logger.error("提供法律咨询建议失败", e);
            throw new AIException("提供法律咨询建议失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 法规解释
     * 
     * @param lawName 法规名称
     * @param articleNumber 条款编号
     * @return 解释说明
     */
    public Map<String, Object> explainLegalProvision(String lawName, String articleNumber) {
        logger.info("解释法律条款: {} 第{}条", lawName, articleNumber);
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            String prompt = String.format(
                "请解释%s第%s条的具体含义，包括立法目的、适用情况、相关案例等。以JSON格式返回结果。",
                lawName,
                articleNumber
            );
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.2);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 简化示例，返回模拟数据
            Map<String, Object> explanation = new HashMap<>();
            explanation.put("law", lawName);
            explanation.put("article", articleNumber);
            explanation.put("content", "本条规定...");
            explanation.put("purpose", "立法目的在于...");
            explanation.put("application", "本条适用于以下情况：...");
            explanation.put("interpretation", "根据最高人民法院的司法解释...");
            explanation.put("cases", Arrays.asList(
                "案例1：XX诉XX案 (2023)最高法民终123号",
                "案例2：XX诉XX案 (2022)最高法民终456号"
            ));
            explanation.put("relatedArticles", Arrays.asList(
                "《中华人民共和国民法典》第XX条",
                "《最高人民法院关于适用《中华人民共和国民法典》若干问题的解释（一）》第X条"
            ));
            
            return explanation;
        } catch (Exception e) {
            logger.error("解释法律条款失败", e);
            throw new AIException("解释法律条款失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 相似案例推荐
     * 
     * @param caseDescription 案件描述
     * @param limit 推荐数量
     * @return 相似案例列表
     */
    public List<Map<String, Object>> recommendSimilarCases(String caseDescription, int limit) {
        logger.info("推荐相似案例，数量限制: {}", limit);
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            String prompt = String.format(
                "请根据以下案件描述，推荐%d个最相关的典型案例，并说明其参考价值。以JSON格式返回结果。\n\n案件描述：\n%s",
                limit,
                caseDescription
            );
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.3);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 简化示例，返回模拟数据
            List<Map<String, Object>> similarCases = new ArrayList<>();
            
            for (int i = 1; i <= Math.min(limit, 5); i++) {
                Map<String, Object> caseInfo = new HashMap<>();
                caseInfo.put("title", "XX公司诉XX公司合同纠纷案");
                caseInfo.put("caseNumber", String.format("（2023）最高法民终%d号", 1000 + i));
                caseInfo.put("court", "最高人民法院");
                caseInfo.put("date", "2023-" + (i < 10 ? "0" + i : i) + "-15");
                caseInfo.put("similarity", 0.95 - (i * 0.05));
                caseInfo.put("keyPoints", Arrays.asList(
                    "关于合同效力认定的裁判要点",
                    "关于违约金调整的裁判标准"
                ));
                caseInfo.put("referenceValue", "本案对类似情况下的合同效力认定具有重要参考价值...");
                
                similarCases.add(caseInfo);
            }
            
            return similarCases;
        } catch (Exception e) {
            logger.error("推荐相似案例失败", e);
            throw new AIException("推荐相似案例失败: " + e.getMessage(), e);
        }
    }
} 