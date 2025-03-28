package com.lawfirm.api.adaptor.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.system.dto.config.ConfigCreateDTO;
import com.lawfirm.model.system.dto.config.ConfigUpdateDTO;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.model.system.service.ConfigService;
import com.lawfirm.model.system.vo.ConfigVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统配置适配器
 * 负责系统配置相关的数据转换和服务调用
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ConfigAdaptor extends BaseAdaptor {

    private final ConfigService configService;

    /**
     * 获取配置列表
     */
    public Page<ConfigVO> getConfigList(Integer pageNum, Integer pageSize, String configName, String configKey, String configType) {
        log.info("获取配置列表，页码：{}，每页条数：{}", pageNum, pageSize);
        Page<SysConfig> page = new Page<>(pageNum, pageSize);
        SysConfig config = new SysConfig();
        config.setConfigName(configName);
        config.setConfigKey(configKey);
        config.setConfigType(configType);
        
        return configService.pageConfigs(page, config);
    }

    /**
     * 获取配置详情
     */
    public ConfigVO getConfigDetail(Long id) {
        log.info("获取配置详情，ID：{}", id);
        return configService.getConfigById(id);
    }

    /**
     * 根据键名获取配置
     */
    public ConfigVO getConfigByKey(String configKey) {
        log.info("根据键名获取配置，键名：{}", configKey);
        return configService.getConfigByKey(configKey);
    }

    /**
     * 新增配置
     */
    public Long addConfig(ConfigCreateDTO createDTO) {
        log.info("新增配置：{}", createDTO.getConfigName());
        return configService.createConfig(createDTO);
    }

    /**
     * 修改配置
     */
    public void updateConfig(Long id, ConfigUpdateDTO updateDTO) {
        log.info("修改配置，ID：{}", id);
        configService.updateConfig(id, updateDTO);
    }

    /**
     * 删除配置
     */
    public void deleteConfig(Long id) {
        log.info("删除配置，ID：{}", id);
        configService.deleteConfig(id);
    }

    /**
     * 批量删除配置
     */
    public void deleteConfigs(List<Long> ids) {
        log.info("批量删除配置，ID列表：{}", ids);
        configService.deleteConfigs(ids);
    }

    /**
     * 刷新配置缓存
     */
    public void refreshCache() {
        log.info("刷新配置缓存");
        configService.refreshCache();
    }
} 