package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.model.system.enums.ConfigTypeEnum;
import com.lawfirm.model.system.vo.SysConfigVO;
import com.lawfirm.model.system.dto.SysConfigDTO;
import com.lawfirm.system.mapper.SysConfigMapper;
import com.lawfirm.system.service.SysConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统配置服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig, SysConfigVO> implements SysConfigService {

    private final SysConfigMapper configMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CONFIG_KEY_PREFIX = "sys:config:";

    @Override
    protected SysConfig createEntity() {
        return new SysConfig();
    }

    @Override
    protected SysConfigVO createVO() {
        return new SysConfigVO();
    }

    private SysConfigVO dtoToVO(SysConfigDTO dto) {
        if (dto == null) {
            return null;
        }
        SysConfigVO vo = createVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }

    private SysConfig dtoToEntity(SysConfigDTO dto) {
        if (dto == null) {
            return null;
        }
        SysConfig entity = createEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public SysConfigVO entityToVO(SysConfig entity) {
        if (entity == null) {
            return null;
        }
        SysConfigVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public SysConfig voToEntity(SysConfigVO vo) {
        if (vo == null) {
            return null;
        }
        SysConfig entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    @Override
    public List<SysConfigVO> listByType(ConfigTypeEnum type) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigType, type)
               .orderByAsc(SysConfig::getSortOrder);
        List<SysConfig> list = list(wrapper);
        return entityListToVOList(list);
    }

    @Override
    public List<SysConfigVO> listByGroup(String group) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigType, ConfigTypeEnum.BUSINESS)
               .eq(SysConfig::getConfigValue, group)
               .orderByAsc(SysConfig::getSortOrder);
        List<SysConfig> list = list(wrapper);
        return entityListToVOList(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysConfigVO create(SysConfigDTO config) {
        // 检查配置键是否已存在
        if (getByKey(config.getConfigKey()) != null) {
            throw new BusinessException("配置键已存在");
        }
        
        SysConfig entity = dtoToEntity(config);
        save(entity);
        
        // 更新缓存
        String cacheKey = CONFIG_KEY_PREFIX + entity.getConfigKey();
        redisTemplate.opsForValue().set(cacheKey, entity.getConfigValue());
        
        return entityToVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysConfigVO update(SysConfigDTO config) {
        // 检查配置是否存在
        SysConfig existingConfig = getById(config.getId());
        if (existingConfig == null) {
            throw new BusinessException("配置不存在");
        }

        // 如果修改了配置键,检查新的配置键是否已存在
        if (!existingConfig.getConfigKey().equals(config.getConfigKey()) && 
            getByKey(config.getConfigKey()) != null) {
            throw new BusinessException("配置键已存在");
        }

        SysConfig entity = dtoToEntity(config);
        updateById(entity);

        // 更新缓存
        String oldCacheKey = CONFIG_KEY_PREFIX + existingConfig.getConfigKey();
        String newCacheKey = CONFIG_KEY_PREFIX + entity.getConfigKey();
        redisTemplate.delete(oldCacheKey);
        redisTemplate.opsForValue().set(newCacheKey, entity.getConfigValue());

        return entityToVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteConfig(Long id) {
        SysConfig config = getById(id);
        if (config != null) {
            removeById(id);
            // 删除缓存
            String cacheKey = CONFIG_KEY_PREFIX + config.getConfigKey();
            redisTemplate.delete(cacheKey);
        }
    }

    @Override
    public SysConfigVO getByKey(String key) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigKey, key);
        SysConfig entity = getOne(wrapper);
        return entityToVO(entity);
    }

    @Override
    public List<String> listAllGroups() {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysConfig::getConfigValue)
               .eq(SysConfig::getConfigType, ConfigTypeEnum.BUSINESS)
               .groupBy(SysConfig::getConfigValue);
        return list(wrapper).stream()
                .map(SysConfig::getConfigValue)
                .collect(Collectors.toList());
    }

    @Override
    public void refreshCache() {
        // 清除所有配置缓存
        String pattern = CONFIG_KEY_PREFIX + "*";
        redisTemplate.delete(redisTemplate.keys(pattern));

        // 重新加载所有配置到缓存
        List<SysConfig> configs = list();
        if (!CollectionUtils.isEmpty(configs)) {
            configs.forEach(config -> {
                String cacheKey = CONFIG_KEY_PREFIX + config.getConfigKey();
                redisTemplate.opsForValue().set(cacheKey, config.getConfigValue());
            });
        }
    }
} 