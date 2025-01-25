package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.exception.BusinessException;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.system.mapper.SysConfigMapper;
import com.lawfirm.system.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统参数配置服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final SysConfigMapper configMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "config", allEntries = true)
    public void createConfig(SysConfig config) {
        // 校验参数键是否已存在
        if (isKeyExists(config.getConfigKey())) {
            throw new BusinessException("参数键已存在");
        }
        
        // 保存参数配置
        save(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "config", allEntries = true)
    public void updateConfig(SysConfig config) {
        // 校验参数是否存在
        if (!existsById(config.getId())) {
            throw new BusinessException("参数不存在");
        }

        // 校验参数键是否已存在
        SysConfig existingConfig = getById(config.getId());
        if (!existingConfig.getConfigKey().equals(config.getConfigKey())) {
            if (isKeyExists(config.getConfigKey())) {
                throw new BusinessException("参数键已存在");
            }
        }
        
        // 更新参数配置
        updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "config", allEntries = true)
    public void deleteConfig(Long id) {
        // 校验参数是否存在
        if (!existsById(id)) {
            throw new BusinessException("参数不存在");
        }
        
        // 删除参数配置
        removeById(id);
    }

    @Override
    @Cacheable(value = "config", key = "#configKey")
    public SysConfig getByKey(String configKey) {
        return configMapper.selectByKey(configKey);
    }

    @Override
    @Cacheable(value = "config", key = "#groupName")
    public List<SysConfig> listByGroup(String groupName) {
        return configMapper.selectByGroup(groupName);
    }

    @Override
    @Cacheable(value = "config", key = "'groups'")
    public List<String> listAllGroups() {
        return configMapper.selectAllGroups();
    }

    @Override
    @CacheEvict(value = "config", allEntries = true)
    public void refreshCache() {
        // 刷新缓存,无需实际操作
    }

    /**
     * 判断参数键是否已存在
     */
    private boolean isKeyExists(String configKey) {
        return lambdaQuery()
                .eq(SysConfig::getConfigKey, configKey)
                .exists();
    }
} 