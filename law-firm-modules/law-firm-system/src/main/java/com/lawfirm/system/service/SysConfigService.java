package com.lawfirm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.log.enums.BusinessType;
import com.lawfirm.model.system.entity.SysConfig;

import java.util.List;

/**
 * 系统参数配置服务接口
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 创建参数配置
     */
    @Log(module = "参数配置", businessType = BusinessType.INSERT, description = "创建参数配置")
    void createConfig(SysConfig config);

    /**
     * 更新参数配置
     */
    @Log(module = "参数配置", businessType = BusinessType.UPDATE, description = "更新参数配置")
    void updateConfig(SysConfig config);

    /**
     * 删除参数配置
     */
    @Log(module = "参数配置", businessType = BusinessType.DELETE, description = "删除参数配置")
    void deleteConfig(Long id);

    /**
     * 根据参数键名查询参数配置
     */
    SysConfig getByKey(String configKey);

    /**
     * 根据参数分组查询参数配置列表
     */
    List<SysConfig> listByGroup(String groupName);

    /**
     * 查询所有参数分组
     */
    List<String> listAllGroups();

    /**
     * 刷新参数缓存
     */
    @Log(module = "参数配置", businessType = BusinessType.OTHER, description = "刷新参数缓存")
    void refreshCache();
} 