package com.lawfirm.system.service.impl.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.util.StringUtils;
import com.lawfirm.common.security.util.SecurityUtils;
import com.lawfirm.model.system.dto.config.ConfigCreateDTO;
import com.lawfirm.model.system.dto.config.ConfigQueryDTO;
import com.lawfirm.model.system.dto.config.ConfigUpdateDTO;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.model.system.mapper.SysConfigMapper;
import com.lawfirm.model.system.service.ConfigService;
import com.lawfirm.model.system.vo.ConfigVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.log.annotation.Log;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 系统配置服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ConfigService {

    private final SysConfigMapper configMapper;

    /**
     * 查询配置列表
     */
    @Override
    public List<ConfigVO> selectConfigList(ConfigQueryDTO queryDTO) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (StringUtils.isNotEmpty(queryDTO.getConfigName())) {
            wrapper.like(SysConfig::getConfigName, queryDTO.getConfigName());
        }
        
        if (StringUtils.isNotEmpty(queryDTO.getConfigKey())) {
            wrapper.like(SysConfig::getConfigKey, queryDTO.getConfigKey());
        }
        
        if (StringUtils.isNotEmpty(queryDTO.getConfigType())) {
            wrapper.eq(SysConfig::getConfigType, queryDTO.getConfigType());
        }
        
        // 分页查询
        Page<SysConfig> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<SysConfig> resultPage = configMapper.selectPage(page, wrapper);
        
        // 转换为VO
        return resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 根据ID查询配置
     */
    @Override
    public ConfigVO selectConfigById(Long id) {
        SysConfig config = configMapper.selectById(id);
        return convertToVO(config);
    }

    /**
     * 根据键名查询配置值
     */
    @Override
    @Cacheable(value = "sys_config", key = "#configKey")
    public String selectConfigByKey(String configKey) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigKey, configKey);
        wrapper.eq(SysConfig::getDeleted, 0);
        
        SysConfig config = configMapper.selectOne(wrapper);
        return config != null ? config.getConfigValue() : "";
    }

    /**
     * 新增配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "INSERT")
    @CacheEvict(value = "sys_config", allEntries = true)
    public void insertConfig(ConfigCreateDTO createDTO) {
        // 检查键名是否已存在
        checkConfigKeyUnique(createDTO.getConfigKey());
        
        // 构建实体
        SysConfig config = new SysConfig();
        BeanUtils.copyProperties(createDTO, config);
        config.setCreateBy(SecurityUtils.getUsername());
        
        // 保存
        configMapper.insert(config);
    }

    /**
     * 修改配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "UPDATE")
    @CacheEvict(value = "sys_config", allEntries = true)
    public void updateConfig(ConfigUpdateDTO updateDTO) {
        // 检查是否存在
        SysConfig config = configMapper.selectById(updateDTO.getId());
        if (config == null) {
            throw new BusinessException("配置不存在");
        }
        
        // 检查键名是否已存在（排除自身）
        if (!config.getConfigKey().equals(updateDTO.getConfigKey())) {
            checkConfigKeyUnique(updateDTO.getConfigKey());
        }
        
        // 更新实体
        BeanUtils.copyProperties(updateDTO, config);
        config.setUpdateBy(SecurityUtils.getUsername());
        config.setUpdateTime(new java.util.Date());
        
        // 保存
        configMapper.updateById(config);
    }

    /**
     * 删除配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "DELETE")
    @CacheEvict(value = "sys_config", allEntries = true)
    public void deleteConfigById(Long id) {
        // 检查是否存在
        SysConfig config = configMapper.selectById(id);
        if (config == null) {
            throw new BusinessException("配置不存在");
        }
        
        // 检查是否为系统内置
        if (config.getIsSystem() != null && config.getIsSystem() == 1) {
            throw new BusinessException("系统内置配置不能删除");
        }
        
        // 逻辑删除
        config.setDeleted(1);
        config.setUpdateBy(SecurityUtils.getUsername());
        config.setUpdateTime(new java.util.Date());
        
        configMapper.updateById(config);
    }

    /**
     * 刷新缓存
     */
    @Override
    @CacheEvict(value = "sys_config", allEntries = true)
    public void refreshCache() {
        log.info("刷新系统配置缓存");
    }

    /**
     * 检查键名是否唯一
     */
    private void checkConfigKeyUnique(String configKey) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigKey, configKey);
        wrapper.eq(SysConfig::getDeleted, 0);
        
        if (configMapper.selectCount(wrapper) > 0) {
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
        
        // 设置配置类型名称
        if (StringUtils.isNotEmpty(config.getConfigType())) {
            switch (config.getConfigType()) {
                case "SYSTEM":
                    vo.setConfigTypeName("系统配置");
                    break;
                case "BUSINESS":
                    vo.setConfigTypeName("业务配置");
                    break;
                default:
                    vo.setConfigTypeName(config.getConfigType());
                    break;
            }
        }
        
        return vo;
    }
    
    /**
     * 创建配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "INSERT")
    @CacheEvict(value = "sys_config", allEntries = true)
    public Long createConfig(ConfigCreateDTO createDTO) {
        // 检查键名是否已存在
        checkConfigKeyUnique(createDTO.getConfigKey());
        
        // 构建实体
        SysConfig config = new SysConfig();
        BeanUtils.copyProperties(createDTO, config);
        config.setCreateBy(SecurityUtils.getUsername());
        
        // 保存
        configMapper.insert(config);
        return config.getId();
    }

    /**
     * 更新配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "UPDATE")
    @CacheEvict(value = "sys_config", allEntries = true)
    public void updateConfig(Long id, ConfigUpdateDTO updateDTO) {
        // 检查是否存在
        SysConfig config = configMapper.selectById(id);
        if (config == null) {
            throw new BusinessException("配置不存在");
        }
        
        // 检查键名是否已存在（排除自身）
        if (!config.getConfigKey().equals(updateDTO.getConfigKey())) {
            checkConfigKeyUnique(updateDTO.getConfigKey());
        }
        
        // 更新实体
        BeanUtils.copyProperties(updateDTO, config);
        config.setUpdateBy(SecurityUtils.getUsername());
        config.setUpdateTime(new java.util.Date());
        
        // 保存
        configMapper.updateById(config);
    }

    /**
     * 删除配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "DELETE")
    @CacheEvict(value = "sys_config", allEntries = true)
    public void deleteConfig(Long id) {
        deleteConfigById(id);
    }

    /**
     * 批量删除配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "系统配置", businessType = "DELETE")
    @CacheEvict(value = "sys_config", allEntries = true)
    public void deleteConfigs(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        
        for (Long id : ids) {
            deleteConfigById(id);
        }
    }

    /**
     * 获取配置详情
     */
    @Override
    @Cacheable(value = "sys_config", key = "'id:' + #id")
    public ConfigVO getConfigById(Long id) {
        return selectConfigById(id);
    }

    /**
     * 根据键名获取配置
     */
    @Override
    @Cacheable(value = "sys_config", key = "'key:' + #configKey")
    public ConfigVO getConfigByKey(String configKey) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigKey, configKey);
        wrapper.eq(SysConfig::getDeleted, 0);
        
        SysConfig config = configMapper.selectOne(wrapper);
        return convertToVO(config);
    }

    /**
     * 分页查询配置
     */
    @Override
    public Page<ConfigVO> pageConfigs(Page<SysConfig> page, SysConfig config) {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(config.getConfigName()), SysConfig::getConfigName, config.getConfigName())
                .like(StringUtils.isNotEmpty(config.getConfigKey()), SysConfig::getConfigKey, config.getConfigKey())
                .eq(StringUtils.isNotEmpty(config.getConfigType()), SysConfig::getConfigType, config.getConfigType())
                .eq(SysConfig::getDeleted, 0)
                .orderByDesc(SysConfig::getCreateTime);

        Page<SysConfig> configPage = configMapper.selectPage(page, queryWrapper);
        Page<ConfigVO> voPage = new Page<>();
        voPage.setCurrent(configPage.getCurrent());
        voPage.setSize(configPage.getSize());
        voPage.setTotal(configPage.getTotal());
        voPage.setRecords(configPage.getRecords().stream().map(this::convertToVO).collect(Collectors.toList()));
        return voPage;
    }

    /**
     * 获取所有配置
     */
    @Override
    @Cacheable(value = "sys_config", key = "'all'")
    public List<ConfigVO> listAllConfigs() {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysConfig::getDeleted, 0);
        
        List<SysConfig> configs = configMapper.selectList(queryWrapper);
        if (configs == null || configs.isEmpty()) {
            return new ArrayList<>();
        }
        return configs.stream().map(this::convertToVO).collect(Collectors.toList());
    }
} 