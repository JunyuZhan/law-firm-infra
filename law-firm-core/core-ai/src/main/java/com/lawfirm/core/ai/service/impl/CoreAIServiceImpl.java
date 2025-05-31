package com.lawfirm.core.ai.service.impl;

import com.lawfirm.core.ai.provider.AIProvider;
import com.lawfirm.core.ai.provider.AIProviderFactory;
import com.lawfirm.core.ai.exception.AIException;
import com.lawfirm.model.ai.service.AiService;
import com.lawfirm.model.ai.dto.AIRequestDTO;
import com.lawfirm.model.ai.vo.AIResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 核心AI服务实现
 * 封装AI功能，便于业务模块集成
 */
@Slf4j
@Component("coreAIServiceImpl")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "law-firm.core.ai.enabled", havingValue = "true", matchIfMissing = false)
public class CoreAIServiceImpl implements AiService {

    @Autowired(required = false)
    private AIProviderFactory providerFactory;
    
    @Value("${law-firm.ai.default-provider:local}")
    private String defaultProviderName;
    
    @Value("${law-firm.ai.max-content-length:10000}")
    private int maxContentLength;
    
    @Value("${law-firm.ai.enable-sensitive-check:true}")
    private boolean enableSensitiveCheck;
    
    /**
     * 敏感词缓存
     */
    private static final Set<String> SENSITIVE_KEYWORDS = Set.of(
        "密码", "身份证", "银行卡", "手机号", "邮箱", "地址"
    );
    
    /**
     * 结果缓存
     */
    private final Map<String, Object> resultCache = new ConcurrentHashMap<>();

    @Override
    public AIResponseVO process(AIRequestDTO requestDTO) {
        log.info("处理AI请求: type={}, contentLength={}", 
                requestDTO.getRequestType(), 
                requestDTO.getContent() != null ? requestDTO.getContent().length() : 0);
        
        try {
            // 1. 参数验证
            if (!isValidRequest(requestDTO)) {
                return createErrorResponse("请求参数验证失败");
            }
            
            // 2. 安全检查
            if (!isSecureContent(requestDTO.getContent())) {
                return createErrorResponse("内容包含敏感信息，拒绝处理");
            }
            
            // 3. 获取AI提供商
            AIProvider provider = getAIProvider();
            if (provider == null) {
                return createFallbackResponse("AI服务暂时不可用，请稍后重试");
            }
            
            // 4. 执行AI处理
            String result = executeAIRequest(provider, requestDTO);
            if (result != null) {
                log.info("AI请求处理成功");
                return createSuccessResponse(result);
            } else {
                return createFallbackResponse("AI处理失败，请检查输入内容");
            }
            
        } catch (Exception e) {
            log.error("AI请求处理异常", e);
            return createErrorResponse("AI服务异常: " + e.getMessage());
        }
    }

    @Override
    public String generateSummary(String content) {
        log.info("生成内容摘要: contentLength={}", content != null ? content.length() : 0);
        
        try {
            // 参数验证
            if (content == null || content.trim().isEmpty()) {
                log.warn("摘要生成内容为空");
                return "";
            }
            
            if (content.length() > maxContentLength) {
                log.warn("内容过长，截取前{}个字符", maxContentLength);
                content = content.substring(0, maxContentLength);
            }
            
            // 安全检查
            if (!isSecureContent(content)) {
                log.warn("内容包含敏感信息，拒绝生成摘要");
                return "内容包含敏感信息，无法生成摘要";
            }
            
            // 检查缓存
            String cacheKey = "summary:" + content.hashCode();
            String cached = (String) resultCache.get(cacheKey);
            if (cached != null) {
                log.debug("使用缓存的摘要结果");
                return cached;
            }
            
            // 获取AI提供商并生成摘要
            AIProvider provider = getAIProvider();
            if (provider != null) {
                String prompt = "请为以下内容生成简洁的摘要：\n" + content;
                Map<String, Object> options = Map.of("max_tokens", 200, "temperature", 0.3);
                
                String summary = provider.sendTextRequest(prompt, options);
                if (summary != null && !summary.trim().isEmpty()) {
                    // 缓存结果
                    resultCache.put(cacheKey, summary);
                    log.info("摘要生成成功");
                    return summary;
                }
            }
            
            // 降级：生成简单摘要
            String fallbackSummary = generateFallbackSummary(content);
            log.info("使用降级摘要生成");
            return fallbackSummary;
            
        } catch (Exception e) {
            log.error("摘要生成异常", e);
            return "摘要生成失败: " + e.getMessage();
        }
    }

    @Override
    public Map<String, Object> extractKeyInfo(String content) {
        log.info("提取关键信息: contentLength={}", content != null ? content.length() : 0);
        
        try {
            if (content == null || content.trim().isEmpty()) {
                return Collections.emptyMap();
            }
            
            // 安全检查
            if (!isSecureContent(content)) {
                log.warn("内容包含敏感信息，拒绝提取关键信息");
                return Map.of("error", "内容包含敏感信息");
            }
            
            // 简化实现：提取基本信息
            Map<String, Object> keyInfo = new HashMap<>();
            keyInfo.put("length", content.length());
            keyInfo.put("wordCount", content.split("\\s+").length);
            keyInfo.put("hasNumbers", content.matches(".*\\d.*"));
            keyInfo.put("timestamp", System.currentTimeMillis());
            
            return keyInfo;
            
        } catch (Exception e) {
            log.error("提取关键信息异常", e);
            return Map.of("error", e.getMessage());
        }
    }

    @Override
    public Map<String, Double> classify(String content) {
        log.info("内容分类: contentLength={}", content != null ? content.length() : 0);
        
        try {
            if (content == null || content.trim().isEmpty()) {
                return Collections.emptyMap();
            }
            
            // 简化分类实现
            Map<String, Double> classification = new HashMap<>();
            
            // 基于关键词的简单分类
            if (content.contains("合同") || content.contains("协议")) {
                classification.put("合同类", 0.8);
            } else if (content.contains("案件") || content.contains("诉讼")) {
                classification.put("案件类", 0.7);
            } else if (content.contains("法律") || content.contains("法规")) {
                classification.put("法律类", 0.6);
            } else {
                classification.put("其他", 0.5);
            }
            
            return classification;
            
        } catch (Exception e) {
            log.error("内容分类异常", e);
            return Collections.emptyMap();
        }
    }

    @Override
    public double calculateSimilarity(String text1, String text2) {
        log.info("计算文本相似度");
        
        try {
            if (text1 == null || text2 == null) {
                return 0.0;
            }
            
            // 简化相似度计算：基于字符重叠率
            Set<Character> chars1 = new HashSet<>();
            Set<Character> chars2 = new HashSet<>();
            
            for (char c : text1.toCharArray()) {
                chars1.add(c);
            }
            for (char c : text2.toCharArray()) {
                chars2.add(c);
            }
            
            Set<Character> intersection = new HashSet<>(chars1);
            intersection.retainAll(chars2);
            
            Set<Character> union = new HashSet<>(chars1);
            union.addAll(chars2);
            
            return union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();
            
        } catch (Exception e) {
            log.error("计算相似度异常", e);
            return 0.0;
        }
    }

    @Override
    public List<String> extractKeywords(String content, int limit) {
        log.info("提取关键词: contentLength={}, limit={}", 
                content != null ? content.length() : 0, limit);
        
        try {
            if (content == null || content.trim().isEmpty()) {
                return Collections.emptyList();
            }
            
            // 简化关键词提取：基于词频
            String[] words = content.split("[\\s\\p{Punct}]+");
            Map<String, Integer> wordCount = new HashMap<>();
            
            for (String word : words) {
                if (word.length() > 1) { // 过滤单字符
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
            
            return wordCount.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(limit)
                    .map(Map.Entry::getKey)
                    .toList();
            
        } catch (Exception e) {
            log.error("提取关键词异常", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, List<String>> extractEntities(String content) {
        log.info("提取实体: contentLength={}", content != null ? content.length() : 0);
        
        try {
            if (content == null || content.trim().isEmpty()) {
                return Collections.emptyMap();
            }
            
            Map<String, List<String>> entities = new HashMap<>();
            
            // 简化实体提取：基于正则表达式
            List<String> dates = extractByPattern(content, "\\d{4}[-/]\\d{1,2}[-/]\\d{1,2}");
            List<String> numbers = extractByPattern(content, "\\d+");
            List<String> emails = extractByPattern(content, "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
            
            if (!dates.isEmpty()) entities.put("日期", dates);
            if (!numbers.isEmpty()) entities.put("数字", numbers);
            if (!emails.isEmpty()) entities.put("邮箱", emails);
            
            return entities;
            
        } catch (Exception e) {
            log.error("提取实体异常", e);
            return Collections.emptyMap();
        }
    }

    @Override
    public double analyzeSentiment(String content) {
        log.info("情感分析: contentLength={}", content != null ? content.length() : 0);
        
        try {
            if (content == null || content.trim().isEmpty()) {
                return 0.0;
            }
            
            // 简化情感分析：基于关键词
            String[] positiveWords = {"好", "优秀", "成功", "满意", "赞同"};
            String[] negativeWords = {"坏", "失败", "不满", "反对", "问题"};
            
            int positiveCount = 0;
            int negativeCount = 0;
            
            for (String word : positiveWords) {
                positiveCount += countOccurrences(content, word);
            }
            
            for (String word : negativeWords) {
                negativeCount += countOccurrences(content, word);
            }
            
            int total = positiveCount + negativeCount;
            if (total == 0) return 0.0;
            
            return (double) (positiveCount - negativeCount) / total;
            
        } catch (Exception e) {
            log.error("情感分析异常", e);
            return 0.0;
        }
    }
    
    /**
     * 验证请求参数
     */
    private boolean isValidRequest(AIRequestDTO requestDTO) {
        if (requestDTO == null) {
            log.warn("AI请求对象为空");
            return false;
        }
        
        if (requestDTO.getContent() == null || requestDTO.getContent().trim().isEmpty()) {
            log.warn("AI请求内容为空");
            return false;
        }
        
        if (requestDTO.getContent().length() > maxContentLength) {
            log.warn("AI请求内容过长: {}", requestDTO.getContent().length());
            return false;
        }
        
        return true;
    }
    
    /**
     * 安全内容检查
     */
    private boolean isSecureContent(String content) {
        if (!enableSensitiveCheck) {
            return true;
        }
        
        if (content == null) {
            return true;
        }
        
        String lowerContent = content.toLowerCase();
        for (String keyword : SENSITIVE_KEYWORDS) {
            if (lowerContent.contains(keyword)) {
                log.warn("检测到敏感词: {}", keyword);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 获取AI提供商
     */
    private AIProvider getAIProvider() {
        try {
            if (providerFactory == null) {
                log.warn("AIProviderFactory未初始化");
                return null;
            }
            
            // 优先使用配置的默认提供商
            try {
                return providerFactory.getProvider(defaultProviderName);
            } catch (Exception e) {
                log.warn("获取默认AI提供商失败: {}", defaultProviderName, e);
            }
            
            // 降级：使用默认提供商
            try {
                return providerFactory.getDefaultProvider();
            } catch (Exception e) {
                log.warn("获取默认AI提供商失败", e);
                return null;
            }
            
        } catch (Exception e) {
            log.error("获取AI提供商异常", e);
            return null;
        }
    }
    
    /**
     * 执行AI请求
     */
    private String executeAIRequest(AIProvider provider, AIRequestDTO requestDTO) {
        try {
            String prompt = requestDTO.getContent();
            Map<String, Object> options = Map.of(
                "max_tokens", 1000,
                "temperature", 0.7
            );
            
            return provider.sendTextRequest(prompt, options);
            
        } catch (Exception e) {
            log.error("执行AI请求异常", e);
            return null;
        }
    }
    
    /**
     * 创建成功响应
     */
    private AIResponseVO createSuccessResponse(String result) {
        AIResponseVO response = new AIResponseVO();
        response.setSuccess(true);
        response.setContent(result);
        response.setMessage("处理成功");
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
    
    /**
     * 创建错误响应
     */
    private AIResponseVO createErrorResponse(String message) {
        AIResponseVO response = new AIResponseVO();
        response.setSuccess(false);
        response.setContent("");
        response.setMessage(message);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
    
    /**
     * 创建降级响应
     */
    private AIResponseVO createFallbackResponse(String message) {
        AIResponseVO response = new AIResponseVO();
        response.setSuccess(true);
        response.setContent("AI服务暂时不可用，已使用备用处理方案");
        response.setMessage(message);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
    
    /**
     * 生成降级摘要
     */
    private String generateFallbackSummary(String content) {
        if (content.length() <= 100) {
            return content;
        }
        
        // 简单截取前100个字符作为摘要
        String summary = content.substring(0, 100);
        if (!summary.endsWith("。") && !summary.endsWith("！") && !summary.endsWith("？")) {
            summary += "...";
        }
        
        return summary;
    }
    
    /**
     * 正则提取
     */
    private List<String> extractByPattern(String content, String pattern) {
        List<String> results = new ArrayList<>();
        Pattern p = Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(content);
        
        while (m.find()) {
            results.add(m.group());
        }
        
        return results;
    }
    
    /**
     * 统计词频
     */
    private int countOccurrences(String content, String word) {
        int count = 0;
        int index = 0;
        
        while ((index = content.indexOf(word, index)) != -1) {
            count++;
            index += word.length();
        }
        
        return count;
    }
} 