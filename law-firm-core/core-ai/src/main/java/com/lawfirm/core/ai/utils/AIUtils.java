package com.lawfirm.core.ai.utils;

import com.lawfirm.common.util.string.StringExtUtils;
import com.lawfirm.common.util.json.JsonUtils;
import com.lawfirm.common.util.id.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * AI工具类
 * 提供AI相关的通用工具方法
 */
public class AIUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(AIUtils.class);
    
    /**
     * 生成操作ID
     * 使用common层的IdUtils生成唯一标识
     * 
     * @return 操作ID
     */
    public static String generateOperationId() {
        return "ai_" + IdUtils.fastUUID();
    }
    
    /**
     * 构建提示词
     * 
     * @param template 提示词模板
     * @param params 参数
     * @return 构建后的提示词
     */
    public static String buildPrompt(String template, Map<String, Object> params) {
        if (StringUtils.isEmpty(template)) {
            return "";
        }
        
        String prompt = template;
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                prompt = prompt.replace("${" + key + "}", value == null ? "" : value.toString());
            }
        }
        
        return prompt;
    }
    
    /**
     * 解析AI返回的JSON结果
     * 使用common层的JsonUtils进行解析
     * 
     * @param jsonResult JSON字符串
     * @return 解析后的Map
     */
    @SuppressWarnings("unchecked")  // JsonUtils.parseObject返回类型已经确保是Map<String, Object>
    public static Map<String, Object> parseResult(String jsonResult) {
        if (StringUtils.isEmpty(jsonResult)) {
            return new HashMap<>();
        }
        
        try {
            return JsonUtils.parseObject(jsonResult, Map.class);
        } catch (Exception e) {
            logger.error("解析AI返回结果失败: {}", jsonResult, e);
            return new HashMap<>();
        }
    }
    
    /**
     * 计算文本相似度
     * 
     * @param text1 文本1
     * @param text2 文本2
     * @return 相似度(0-1)
     */
    public static double calculateTextSimilarity(String text1, String text2) {
        if (StringUtils.isEmpty(text1) || StringUtils.isEmpty(text2)) {
            return 0.0;
        }
        
        // 使用编辑距离算法计算相似度
        int distance = calculateLevenshteinDistance(text1, text2);
        int maxLength = Math.max(text1.length(), text2.length());
        
        return 1.0 - (double) distance / maxLength;
    }
    
    /**
     * 计算编辑距离
     */
    private static int calculateLevenshteinDistance(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        
        for (int i = 0; i <= text1.length(); i++) {
            dp[i][0] = i;
        }
        
        for (int j = 0; j <= text2.length(); j++) {
            dp[0][j] = j;
        }
        
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }
        
        return dp[text1.length()][text2.length()];
    }
    
    /**
     * 提取关键词
     * 
     * @param text 文本
     * @param limit 限制数量
     * @return 关键词列表
     */
    public static List<String> extractKeywords(String text, int limit) {
        if (StringUtils.isEmpty(text) || limit <= 0) {
            return new ArrayList<>();
        }
        // 简单TF-IDF实现（仅按词频排序，实际可结合全局词频）
        Map<String, Integer> freqMap = new HashMap<>();
        String[] words = text.split("\\W+");
        for (String word : words) {
            if (word.length() > 1) {
                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }
        List<String> sorted = new ArrayList<>(freqMap.keySet());
        sorted.sort((a, b) -> freqMap.get(b) - freqMap.get(a));
        return sorted.subList(0, Math.min(limit, sorted.size()));
    }
    
    /**
     * 检查敏感词（DFA算法实现）
     * 
     * @param text 文本
     * @return 是否包含敏感词
     */
    public static boolean containsSensitiveWords(String text) {
        if (StringUtils.isEmpty(text)) {
            return false;
        }
        // 示例敏感词库
        String[] sensitiveWords = {"敏感词1", "敏感词2", "敏感词3"};
        // 构建DFA字典树
        TrieNode root = new TrieNode();
        for (String word : sensitiveWords) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                node = node.children.computeIfAbsent(c, k -> new TrieNode());
            }
            node.isEnd = true;
        }
        // 检查文本
        for (int i = 0; i < text.length(); i++) {
            TrieNode node = root;
            int j = i;
            while (j < text.length() && node.children.containsKey(text.charAt(j))) {
                node = node.children.get(text.charAt(j));
                if (node.isEnd) {
                    return true;
                }
                j++;
            }
        }
        return false;
    }
    
    // DFA节点定义
    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEnd = false;
    }
    
    /**
     * 格式化错误信息
     * 
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     * @param params 参数
     * @return 格式化后的错误信息
     */
    public static String formatErrorMessage(String errorCode, String errorMsg, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(errorCode).append("] ");
        
        if (params != null && params.length > 0) {
            sb.append(String.format(errorMsg, params));
        } else {
            sb.append(errorMsg);
        }
        
        return sb.toString();
    }
} 