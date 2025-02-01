package com.lawfirm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.log.enums.BusinessType;
import com.lawfirm.model.system.entity.SysDict;

import java.util.List;

/**
 * 系统字典服务接口
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 创建字典
     */
    @Log(title = "字典管理", businessType = BusinessType.INSERT)
    void createDict(SysDict dict);

    /**
     * 更新字典
     */
    @Log(title = "字典管理", businessType = BusinessType.UPDATE)
    void updateDict(SysDict dict);

    /**
     * 删除字典
     */
    @Log(title = "字典管理", businessType = BusinessType.DELETE)
    void deleteDict(Long id);

    /**
     * 根据类型查询字典列表
     */
    List<SysDict> listByType(String type);

    /**
     * 根据类型和值查询字典
     */
    SysDict getByTypeAndValue(String type, String value);

    /**
     * 查询所有字典类型
     */
    List<String> listAllTypes();

    /**
     * 刷新字典缓存
     */
    @Log(title = "字典管理", businessType = BusinessType.OTHER)
    void refreshCache();
} 