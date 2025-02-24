package com.lawfirm.model.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.system.dto.config.ConfigCreateDTO;
import com.lawfirm.model.system.dto.config.ConfigUpdateDTO;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.model.system.vo.ConfigVO;

import java.util.List;

/**
 * 系统配置服务接口
 */
public interface ConfigService extends BaseService<SysConfig> {

    /**
     * 创建配置
     *
     * @param createDTO 创建参数
     * @return 配置ID
     */
    Long createConfig(ConfigCreateDTO createDTO);

    /**
     * 更新配置
     *
     * @param id 配置ID
     * @param updateDTO 更新参数
     */
    void updateConfig(Long id, ConfigUpdateDTO updateDTO);

    /**
     * 删除配置
     *
     * @param id 配置ID
     */
    void deleteConfig(Long id);

    /**
     * 批量删除配置
     *
     * @param ids 配置ID列表
     */
    void deleteConfigs(List<Long> ids);

    /**
     * 获取配置详情
     *
     * @param id 配置ID
     * @return 配置详情
     */
    ConfigVO getConfigById(Long id);

    /**
     * 根据键名获取配置
     *
     * @param configKey 配置键名
     * @return 配置详情
     */
    ConfigVO getConfigByKey(String configKey);

    /**
     * 分页查询配置
     *
     * @param page 分页参数
     * @param config 查询条件
     * @return 配置列表
     */
    Page<ConfigVO> pageConfigs(Page<SysConfig> page, SysConfig config);

    /**
     * 获取所有配置
     *
     * @return 配置列表
     */
    List<ConfigVO> listAllConfigs();

    /**
     * 刷新配置缓存
     */
    void refreshCache();
} 