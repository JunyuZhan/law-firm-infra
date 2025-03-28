package com.lawfirm.core.ai.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import jakarta.annotation.PostConstruct;

// 有条件导入
import com.lawfirm.model.system.service.ConfigService;
import com.lawfirm.model.system.vo.ConfigVO;
import com.lawfirm.common.security.crypto.SensitiveDataService;

/**
 * AI配置加载器
 * 从系统配置表加载AI相关配置
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AIConfigLoader {

    private final AIConfig aiConfig;
    private final ModelConfig modelConfig;
    
    @Autowired(required = false)
    private ConfigService configService;
    
    @Autowired(required = false)
    private SensitiveDataService sensitiveDataService;

    /**
     * 初始化方法，加载系统配置
     */
    @PostConstruct
    public void init() {
        if (configService == null) {
            log.warn("ConfigService未注入，无法从系统配置表加载AI配置");
            log.warn("请确保已添加system-model依赖，并且system模块已正确启动");
            return;
        }

        try {
            log.info("开始从系统配置表加载AI配置");
            loadDefaultProviderConfig();
            loadOpenAIConfig();
            loadBaiduAIConfig();
            loadGeneralConfig();
            log.info("AI配置加载完成");
        } catch (Exception e) {
            log.error("加载AI配置异常", e);
        }
    }

    /**
     * 脱敏展示API密钥
     * 仅保留前两位和后两位，中间用*代替
     */
    private String maskApiKey(String apiKey) {
        if (StringUtils.hasText(apiKey)) {
            if (sensitiveDataService != null) {
                // 使用SensitiveDataService进行脱敏
                return sensitiveDataService.mask(apiKey, 2, 2, '*');
            } else {
                // 如果没有注入SensitiveDataService，使用简单脱敏方式
                if (apiKey.length() <= 4) {
                    return "****";
                }
                return apiKey.substring(0, 2) + 
                       "*".repeat(apiKey.length() - 4) + 
                       apiKey.substring(apiKey.length() - 2);
            }
        }
        return apiKey;
    }

    /**
     * 加载默认提供商配置
     */
    private void loadDefaultProviderConfig() {
        try {
            ConfigVO config = configService.getConfigByKey("ai.default-provider");
            if (config != null && StringUtils.hasText(config.getConfigValue())) {
                aiConfig.setDefaultProvider(config.getConfigValue());
                log.info("已加载默认AI提供商配置: {}", config.getConfigValue());
            }
        } catch (Exception e) {
            log.warn("加载默认AI提供商配置失败", e);
        }
    }

    /**
     * 加载OpenAI配置
     */
    private void loadOpenAIConfig() {
        try {
            // 加载API密钥
            ConfigVO apiKeyConfig = configService.getConfigByKey("ai.openai.api-key");
            if (apiKeyConfig != null && StringUtils.hasText(apiKeyConfig.getConfigValue())) {
                modelConfig.getOpenai().setApiKey(apiKeyConfig.getConfigValue());
                log.info("已加载OpenAI API密钥配置：{}", maskApiKey(apiKeyConfig.getConfigValue()));
            }

            // 加载模型配置
            ConfigVO modelNameConfig = configService.getConfigByKey("ai.openai.model");
            if (modelNameConfig != null && StringUtils.hasText(modelNameConfig.getConfigValue())) {
                modelConfig.getOpenai().setDefaultModel(modelNameConfig.getConfigValue());
                log.info("已加载OpenAI模型配置: {}", modelNameConfig.getConfigValue());
            }
        } catch (Exception e) {
            log.warn("加载OpenAI配置失败", e);
        }
    }

    /**
     * 加载百度AI配置
     */
    private void loadBaiduAIConfig() {
        try {
            // 加载API密钥
            ConfigVO apiKeyConfig = configService.getConfigByKey("ai.baidu.api-key");
            if (apiKeyConfig != null && StringUtils.hasText(apiKeyConfig.getConfigValue())) {
                modelConfig.getBaidu().setApiKey(apiKeyConfig.getConfigValue());
                log.info("已加载百度AI API密钥配置：{}", maskApiKey(apiKeyConfig.getConfigValue()));
            }

            // 加载Secret密钥
            ConfigVO secretKeyConfig = configService.getConfigByKey("ai.baidu.secret-key");
            if (secretKeyConfig != null && StringUtils.hasText(secretKeyConfig.getConfigValue())) {
                modelConfig.getBaidu().setSecretKey(secretKeyConfig.getConfigValue());
                log.info("已加载百度AI Secret密钥配置：{}", maskApiKey(secretKeyConfig.getConfigValue()));
            }

            // 加载模型配置
            ConfigVO modelNameConfig = configService.getConfigByKey("ai.baidu.model");
            if (modelNameConfig != null && StringUtils.hasText(modelNameConfig.getConfigValue())) {
                modelConfig.getBaidu().setDefaultModel(modelNameConfig.getConfigValue());
                log.info("已加载百度AI模型配置: {}", modelNameConfig.getConfigValue());
            }
        } catch (Exception e) {
            log.warn("加载百度AI配置失败", e);
        }
    }

    /**
     * 加载通用配置
     */
    private void loadGeneralConfig() {
        try {
            // 加载超时配置
            ConfigVO timeoutConfig = configService.getConfigByKey("ai.timeout");
            if (timeoutConfig != null && StringUtils.hasText(timeoutConfig.getConfigValue())) {
                try {
                    aiConfig.setTimeout(Integer.parseInt(timeoutConfig.getConfigValue()));
                    log.info("已加载AI超时配置: {}秒", timeoutConfig.getConfigValue());
                } catch (NumberFormatException e) {
                    log.warn("AI超时配置格式错误: {}", timeoutConfig.getConfigValue());
                }
            }

            // 加载并发请求配置
            ConfigVO concurrentRequestsConfig = configService.getConfigByKey("ai.max-concurrent-requests");
            if (concurrentRequestsConfig != null && StringUtils.hasText(concurrentRequestsConfig.getConfigValue())) {
                try {
                    aiConfig.setMaxConcurrentRequests(Integer.parseInt(concurrentRequestsConfig.getConfigValue()));
                    log.info("已加载AI最大并发请求数配置: {}", concurrentRequestsConfig.getConfigValue());
                } catch (NumberFormatException e) {
                    log.warn("AI最大并发请求数配置格式错误: {}", concurrentRequestsConfig.getConfigValue());
                }
            }

            // 加载缓存配置
            ConfigVO cacheConfig = configService.getConfigByKey("ai.enable-cache");
            if (cacheConfig != null && StringUtils.hasText(cacheConfig.getConfigValue())) {
                aiConfig.setEnableCache(Boolean.parseBoolean(cacheConfig.getConfigValue()));
                log.info("已加载AI缓存配置: {}", cacheConfig.getConfigValue());
            }

            // 加载敏感信息过滤配置
            ConfigVO sensitiveFilterConfig = configService.getConfigByKey("ai.enable-sensitive-filter");
            if (sensitiveFilterConfig != null && StringUtils.hasText(sensitiveFilterConfig.getConfigValue())) {
                aiConfig.setEnableSensitiveFilter(Boolean.parseBoolean(sensitiveFilterConfig.getConfigValue()));
                log.info("已加载AI敏感信息过滤配置: {}", sensitiveFilterConfig.getConfigValue());
            }
        } catch (Exception e) {
            log.warn("加载AI通用配置失败", e);
        }
    }
} 