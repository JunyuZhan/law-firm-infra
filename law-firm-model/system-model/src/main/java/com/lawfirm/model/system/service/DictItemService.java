package com.lawfirm.model.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.system.dto.dict.DictItemCreateDTO;
import com.lawfirm.model.system.dto.dict.DictItemUpdateDTO;
import com.lawfirm.model.system.entity.SysDictItem;
import com.lawfirm.model.system.vo.DictItemVO;

import java.util.List;

/**
 * 系统字典项服务接口
 */
public interface DictItemService extends BaseService<SysDictItem> {

    /**
     * 创建字典项
     *
     * @param createDTO 创建参数
     * @return 字典项ID
     */
    Long createDictItem(DictItemCreateDTO createDTO);

    /**
     * 更新字典项
     *
     * @param id        字典项ID
     * @param updateDTO 更新参数
     */
    void updateDictItem(Long id, DictItemUpdateDTO updateDTO);

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     */
    void deleteDictItem(Long id);

    /**
     * 批量删除字典项
     *
     * @param ids 字典项ID列表
     */
    void deleteDictItems(List<Long> ids);

    /**
     * 获取字典项详情
     *
     * @param id 字典项ID
     * @return 字典项详情
     */
    DictItemVO getDictItemById(Long id);

    /**
     * 分页查询字典项
     *
     * @param page     分页参数
     * @param dictItem 查询条件
     * @return 字典项列表
     */
    Page<DictItemVO> pageDictItems(Page<SysDictItem> page, SysDictItem dictItem);

    /**
     * 根据字典ID获取字典项列表
     *
     * @param dictId 字典ID
     * @return 字典项列表
     */
    List<DictItemVO> listDictItemsByDictId(Long dictId);
} 