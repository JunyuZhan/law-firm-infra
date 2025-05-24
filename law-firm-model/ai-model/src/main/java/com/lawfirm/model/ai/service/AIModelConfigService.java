package com.lawfirm.model.ai.service;

import com.lawfirm.model.ai.entity.AIModelConfig;
import java.util.List;

/**
 * AI模型配置服务接口
 */
public interface AIModelConfigService {
    List<AIModelConfig> listAll();
    AIModelConfig getById(Long id);
    void add(AIModelConfig config);
    void update(AIModelConfig config);
    void delete(Long id);
    AIModelConfig getDefault();
    void setDefault(Long id);
} 