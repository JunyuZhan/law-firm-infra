package com.lawfirm.model.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.system.dto.dict.DictCreateDTO;
import com.lawfirm.model.system.dto.dict.DictUpdateDTO;
import com.lawfirm.model.system.entity.dict.SysDict;
import com.lawfirm.model.system.vo.dict.DictVO;

import java.util.List;
import java.util.Map;

/**
 * 系统字典服务接口
 * 提供字典的CRUD操作和获取字典项的服务
 */
public interface DictService extends BaseService<SysDict> {

    /**
     * 创建字典
     *
     * @param createDTO 创建参数
     * @return 字典ID
     */
    Long createDict(DictCreateDTO createDTO);

    /**
     * 更新字典
     *
     * @param id 字典ID
     * @param updateDTO 更新参数
     */
    void updateDict(Long id, DictUpdateDTO updateDTO);

    /**
     * 删除字典
     *
     * @param id 字典ID
     */
    void deleteDict(Long id);

    /**
     * 批量删除字典
     *
     * @param ids 字典ID列表
     */
    void deleteDicts(List<Long> ids);

    /**
     * 获取字典详情
     *
     * @param id 字典ID
     * @return 字典详情
     */
    DictVO getDictById(Long id);

    /**
     * 根据编码获取字典
     *
     * @param dictCode 字典编码
     * @return 字典详情
     */
    DictVO getDictByCode(String dictCode);

    /**
     * 分页查询字典
     *
     * @param page 分页参数
     * @param dict 查询条件
     * @return 字典列表
     */
    Page<DictVO> pageDicts(Page<SysDict> page, SysDict dict);

    /**
     * 获取所有字典
     *
     * @return 字典列表
     */
    List<DictVO> listAllDicts();

    /**
     * 根据类型获取字典列表
     *
     * @param dictType 字典类型
     * @return 字典列表
     */
    List<DictVO> listDictsByType(String dictType);

    /**
     * 根据字典编码获取字典项
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    List<Map<String, Object>> getDictItems(String dictCode);

    /**
     * 根据字典编码获取键值对
     *
     * @param dictCode 字典编码
     * @return 键值对，key为字典项值，value为字典项标签
     */
    Map<String, String> getDictMap(String dictCode);

    /**
     * 刷新字典缓存
     */
    void refreshCache();
} 