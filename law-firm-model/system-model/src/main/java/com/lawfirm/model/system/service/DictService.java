package com.lawfirm.model.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.system.dto.dict.DictCreateDTO;
import com.lawfirm.model.system.dto.dict.DictUpdateDTO;
import com.lawfirm.model.system.entity.SysDict;
import com.lawfirm.model.system.vo.DictVO;

import java.util.List;

/**
 * 系统字典服务接口
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
     * 刷新字典缓存
     */
    void refreshCache();
} 