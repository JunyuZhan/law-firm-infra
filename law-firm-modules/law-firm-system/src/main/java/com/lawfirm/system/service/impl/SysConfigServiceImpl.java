package com.lawfirm.system.service.impl;

import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.system.mapper.SysConfigMapper;
import com.lawfirm.system.model.dto.SysConfigDTO;
import com.lawfirm.system.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统配置服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig, SysConfigDTO> implements SysConfigService {

    private final SysConfigMapper configMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected SysConfigDTO createDTO() {
        return new SysConfigDTO();
    }

    @Override
    protected SysConfig createEntity() {
        return new SysConfig();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createConfig(SysConfigDTO configDTO) {
        // 校验配置键是否已存在
        if (configMapper.existsByKey(configDTO.getConfigKey())) {
            throw new BusinessException("配置键已存在");
        }
        
        // 保存配置
        save(toEntity(configDTO));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConfig(SysConfigDTO configDTO) {
        // 校验配置是否存在
        if (!exists(configDTO.getId())) {
            throw new BusinessException("配置不存在");
        }

        // 校验配置键是否已存在
        SysConfig existingConfig = getById(configDTO.getId());
        if (!existingConfig.getConfigKey().equals(configDTO.getConfigKey())) {
            if (configMapper.existsByKey(configDTO.getConfigKey())) {
                throw new BusinessException("配置键已存在");
            }
        }
        
        // 更新配置
        updateById(toEntity(configDTO));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteConfig(Long id) {
        // 校验配置是否存在
        if (!exists(id)) {
            throw new BusinessException("配置不存在");
        }
        
        // 删除配置
        removeById(id);
    }

    @Override
    public SysConfigDTO getByKey(String key) {
        return toDTO(configMapper.selectByKey(key));
    }

    @Override
    public List<SysConfigDTO> listByGroup(String group) {
        return toDTOList(configMapper.selectByGroup(group));
    }

    @Override
    public List<String> listAllGroups() {
        return configMapper.selectAllGroups();
    }

    @Override
    public void refreshCache() {
        List<SysConfig> configs = list();
        for (SysConfig config : configs) {
            String key = "sys:config:" + config.getConfigKey();
            redisTemplate.opsForValue().set(key, config.getConfigValue());
        }
    }

    /**
     * 判断记录是否存在
     */
    private boolean exists(Long id) {
        return lambdaQuery().eq(SysConfig::getId, id).exists();
    }
} 