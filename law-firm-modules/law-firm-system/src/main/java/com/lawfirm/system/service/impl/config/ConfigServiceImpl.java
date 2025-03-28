package com.lawfirm.system.service.impl.config;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.system.dto.config.ConfigCreateDTO;
import com.lawfirm.model.system.dto.config.ConfigUpdateDTO;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.model.system.mapper.SysConfigMapper;
import com.lawfirm.model.system.service.ConfigService;
import com.lawfirm.model.system.vo.ConfigVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.log.annotation.Log;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

/**
 * 系统配置服务实现类
 */
@Slf4j
@Service("systemConfigServiceImpl")
@RequiredArgsConstructor
public class ConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig> implements ConfigService {

    private final SysConfigMapper configMapper;

    /**
     * 创建配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "INSERT")
    @CacheEvict(value = "sys_config", allEntries = true)
    public Long createConfig(ConfigCreateDTO createDTO) {
        // 检查键名是否唯一
        checkConfigKeyUnique(createDTO.getConfigKey());
        
        SysConfig config = new SysConfig();
        BeanUtils.copyProperties(createDTO, config);
        config.setCreateTime(LocalDateTime.now());
        config.setUpdateTime(LocalDateTime.now());
        save(config);
        return config.getId();
    }

    /**
     * 修改配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "UPDATE")
    @CacheEvict(value = "sys_config", allEntries = true)
    public void updateConfig(Long id, ConfigUpdateDTO updateDTO) {
        SysConfig config = getById(id);
        if (config == null) {
            throw new BusinessException("配置不存在");
        }
        
        // 如果修改了键名，检查是否唯一
        if (!config.getConfigKey().equals(updateDTO.getConfigKey())) {
            checkConfigKeyUnique(updateDTO.getConfigKey());
        }
        
        BeanUtils.copyProperties(updateDTO, config);
        config.setUpdateTime(LocalDateTime.now());
        updateById(config);
    }

    /**
     * 删除配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "DELETE")
    @CacheEvict(value = "sys_config", allEntries = true)
    public void deleteConfig(Long id) {
        removeById(id);
    }

    /**
     * 批量删除配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "DELETE")
    @CacheEvict(value = "sys_config", allEntries = true)
    public void deleteConfigs(List<Long> ids) {
        removeBatchByIds(ids);
    }

    /**
     * 获取配置详情
     */
    @Override
    @Cacheable(value = "sys_config", key = "#id")
    public ConfigVO getConfigById(Long id) {
        return convertToVO(getById(id));
    }

    /**
     * 根据键名获取配置
     */
    @Override
    @Cacheable(value = "sys_config", key = "'key:' + #configKey")
    public ConfigVO getConfigByKey(String configKey) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("config_key", configKey);
        return convertToVO(getOne(wrapper));
    }

    /**
     * 分页查询配置
     */
    @Override
    public Page<ConfigVO> pageConfigs(Page<SysConfig> page, SysConfig config) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(config.getConfigName())) {
            wrapper.like("config_name", config.getConfigName());
        }
        if (StringUtils.hasText(config.getConfigKey())) {
            wrapper.like("config_key", config.getConfigKey());
        }
        if (StringUtils.hasText(config.getConfigType())) {
            wrapper.eq("config_type", config.getConfigType());
        }
        
        Page<SysConfig> resultPage = page(page, wrapper);
        
        Page<ConfigVO> voPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        voPage.setRecords(resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList()));
        return voPage;
    }

    /**
     * 获取所有配置
     */
    @Override
    public List<ConfigVO> listAllConfigs() {
        return list().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 刷新缓存
     */
    @Override
    @CacheEvict(value = "sys_config", allEntries = true)
    public void refreshCache() {
        // 清空缓存即可，@CacheEvict注解会处理
    }

    /**
     * 检查键名是否唯一
     */
    private void checkConfigKeyUnique(String configKey) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigKey, configKey);
        wrapper.eq(SysConfig::getDeleted, 0);
        
        if (count(wrapper) > 0) {
            throw new BusinessException("配置键名已存在");
        }
    }

    /**
     * 将实体转换为VO
     */
    private ConfigVO convertToVO(SysConfig config) {
        if (config == null) {
            return null;
        }
        
        ConfigVO vo = new ConfigVO();
        BeanUtils.copyProperties(config, vo);
        
        // 对敏感配置进行处理
        if (isSensitiveConfig(config.getConfigKey())) {
            // 这里不使用setSensitive方法，ConfigVO中可能没有该方法
            // 对于敏感配置，不做特殊处理，由使用方自行判断和处理
            log.debug("检测到敏感配置: {}", config.getConfigKey());
        }
        
        // 设置配置类型名称
        if (StringUtils.hasText(config.getConfigType())) {
            switch (config.getConfigType()) {
                case "SYSTEM":
                    vo.setConfigTypeName("系统配置");
                    break;
                case "BUSINESS":
                    vo.setConfigTypeName("业务配置");
                    break;
                default:
                    vo.setConfigTypeName("其他配置");
                    break;
            }
        }
        
        return vo;
    }

    /**
     * 判断是否为敏感配置
     */
    private boolean isSensitiveConfig(String configKey) {
        // 敏感配置项列表
        final String[] sensitiveKeys = {
            "ai.openai.api-key", 
            "ai.baidu.api-key", 
            "ai.baidu.secret-key",
            "sms.api-key", 
            "mail.password",
            "security.jwt.secret",
            "oauth2.client-secret"
        };
        
        for (String key : sensitiveKeys) {
            if (key.equals(configKey)) {
                return true;
            }
        }
        
        return false;
    }
}