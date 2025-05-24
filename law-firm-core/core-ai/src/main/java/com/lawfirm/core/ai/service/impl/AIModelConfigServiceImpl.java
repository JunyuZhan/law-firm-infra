package com.lawfirm.core.ai.service.impl;

import com.lawfirm.model.ai.entity.AIModelConfig;
import com.lawfirm.model.ai.mapper.AIModelConfigMapper;
import com.lawfirm.model.ai.service.AIModelConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AIModelConfigServiceImpl implements AIModelConfigService {

    @Autowired
    private AIModelConfigMapper mapper;

    @Override
    public List<AIModelConfig> listAll() {
        return mapper.selectAll();
    }

    @Override
    public AIModelConfig getById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void add(AIModelConfig config) {
        mapper.insert(config);
    }

    @Override
    public void update(AIModelConfig config) {
        mapper.update(config);
    }

    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }

    @Override
    public AIModelConfig getDefault() {
        return mapper.selectDefault();
    }

    @Override
    public void setDefault(Long id) {
        mapper.setDefault(id);
    }
} 