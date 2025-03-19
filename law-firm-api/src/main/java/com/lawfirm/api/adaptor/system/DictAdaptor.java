package com.lawfirm.api.adaptor.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.system.dto.dict.DictCreateDTO;
import com.lawfirm.model.system.dto.dict.DictItemCreateDTO;
import com.lawfirm.model.system.dto.dict.DictItemUpdateDTO;
import com.lawfirm.model.system.dto.dict.DictUpdateDTO;
import com.lawfirm.model.system.entity.dict.SysDict;
import com.lawfirm.model.system.entity.dict.SysDictItem;
import com.lawfirm.model.system.service.DictItemService;
import com.lawfirm.model.system.service.DictService;
import com.lawfirm.model.system.vo.dict.DictItemVO;
import com.lawfirm.model.system.vo.dict.DictVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典适配器
 */
@Component
@RequiredArgsConstructor
public class DictAdaptor extends BaseAdaptor {

    private final DictService dictService;
    private final DictItemService dictItemService;

    /**
     * 获取字典列表
     */
    public Page<DictVO> getDictList(Integer pageNum, Integer pageSize, String dictName, String dictCode, String dictType) {
        Page<SysDict> page = new Page<>(pageNum, pageSize);
        SysDict dict = new SysDict();
        dict.setDictName(dictName);
        dict.setDictCode(dictCode);
        dict.setDictType(dictType);
        
        return dictService.pageDicts(page, dict);
    }

    /**
     * 获取字典详情
     */
    public DictVO getDictDetail(Long id) {
        return dictService.getDictById(id);
    }

    /**
     * 根据编码获取字典
     */
    public DictVO getDictByCode(String dictCode) {
        return dictService.getDictByCode(dictCode);
    }

    /**
     * 根据类型获取字典列表
     */
    public List<DictVO> getDictsByType(String dictType) {
        return dictService.listDictsByType(dictType);
    }

    /**
     * 获取所有字典
     */
    public List<DictVO> getAllDicts() {
        return dictService.listAllDicts();
    }

    /**
     * 新增字典
     */
    public Long addDict(DictCreateDTO dictDTO) {
        return dictService.createDict(dictDTO);
    }

    /**
     * 修改字典
     */
    public void updateDict(Long id, DictUpdateDTO dictDTO) {
        dictService.updateDict(id, dictDTO);
    }

    /**
     * 删除字典
     */
    public void deleteDict(Long id) {
        dictService.deleteDict(id);
    }

    /**
     * 批量删除字典
     */
    public void deleteDicts(List<Long> ids) {
        dictService.deleteDicts(ids);
    }

    /**
     * 刷新字典缓存
     */
    public void refreshCache() {
        dictService.refreshCache();
    }

    /**
     * 获取字典项列表
     */
    public Page<DictItemVO> getDictItemList(Integer pageNum, Integer pageSize, Long dictId, String label, String value) {
        Page<SysDictItem> page = new Page<>(pageNum, pageSize);
        SysDictItem dictItem = new SysDictItem();
        dictItem.setDictId(dictId);
        dictItem.setLabel(label);
        dictItem.setValue(value);
        
        return dictItemService.pageDictItems(page, dictItem);
    }

    /**
     * 获取字典项详情
     */
    public DictItemVO getDictItemDetail(Long id) {
        return dictItemService.getDictItemById(id);
    }

    /**
     * 根据字典ID获取字典项列表
     */
    public List<DictItemVO> getDictItemsByDictId(Long dictId) {
        return dictItemService.listDictItemsByDictId(dictId);
    }

    /**
     * 新增字典项
     */
    public Long addDictItem(DictItemCreateDTO dictItemDTO) {
        return dictItemService.createDictItem(dictItemDTO);
    }

    /**
     * 修改字典项
     */
    public void updateDictItem(Long id, DictItemUpdateDTO dictItemDTO) {
        dictItemService.updateDictItem(id, dictItemDTO);
    }

    /**
     * 删除字典项
     */
    public void deleteDictItem(Long id) {
        dictItemService.deleteDictItem(id);
    }

    /**
     * 批量删除字典项
     */
    public void deleteDictItems(List<Long> ids) {
        dictItemService.deleteDictItems(ids);
    }
} 