package com.lawfirm.system.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.system.model.dto.SysConfigDTO;

import java.util.List;

/**
 * 系统配置服务接口
 */
public interface SysConfigService extends BaseService<SysConfig, SysConfigDTO> {

    /**
     * 创建配置
     */
    @OperationLog(description = "创建配置", operationType = "CREATE")
    void createConfig(SysConfigDTO config);

    /**
     * 更新配置
     */
    @OperationLog(description = "更新配置", operationType = "UPDATE")
    void updateConfig(SysConfigDTO config);

    /**
     * 删除配置
     */
    @OperationLog(description = "删除配置", operationType = "DELETE")
    void deleteConfig(Long id);

    /**
     * 根据键名查询配置
     */
    SysConfigDTO getByKey(String key);

    /**
     * 根据分组查询配置列表
     */
    List<SysConfigDTO> listByGroup(String group);

    /**
     * 查询所有参数分组
     */
    List<String> listAllGroups();

    /**
     * 刷新缓存
     */
    @OperationLog(description = "刷新配置缓存", operationType = "UPDATE")
    void refreshCache();
} 