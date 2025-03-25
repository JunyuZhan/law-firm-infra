package com.lawfirm.system.service.impl.dict;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.system.dto.dict.DictItemCreateDTO;
import com.lawfirm.model.system.dto.dict.DictItemUpdateDTO;
import com.lawfirm.model.system.entity.dict.SysDict;
import com.lawfirm.model.system.entity.dict.SysDictItem;
import com.lawfirm.model.system.mapper.dict.SysDictItemMapper;
import com.lawfirm.model.system.mapper.dict.SysDictMapper;
import com.lawfirm.model.system.service.DictItemService;
import com.lawfirm.model.system.vo.dict.DictItemVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.log.annotation.AuditLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.CollectionUtils;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;

/**
 * 系统字典项服务实现类
 */
@Slf4j
@Service("systemDictItemServiceImpl")
@RequiredArgsConstructor
public class DictItemServiceImpl extends BaseServiceImpl<SysDictItemMapper, SysDictItem> implements DictItemService {

    private final SysDictItemMapper dictItemMapper;
    private final SysDictMapper dictMapper;

    /**
     * 创建字典项
     *
     * @param createDTO 创建参数
     * @return 字典项ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "字典项管理", businessType = "INSERT")
    @CacheEvict(value = {"sys_dict", "sys_dict_item"}, allEntries = true)
    public Long createDictItem(DictItemCreateDTO createDTO) {
        // 检查同一字典下是否存在相同的值
        QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id", createDTO.getDictId())
                .eq("value", createDTO.getDictValue());
        if (exists(queryWrapper)) {
            throw new BusinessException("字典项值已存在");
        }

        SysDictItem dictItem = new SysDictItem();
        BeanUtils.copyProperties(createDTO, dictItem);
        save(dictItem);
        return dictItem.getId();
    }

    /**
     * 更新字典项
     *
     * @param id      字典项ID
     * @param updateDTO 更新参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "字典项管理", businessType = "UPDATE")
    @CacheEvict(value = {"sys_dict", "sys_dict_item"}, allEntries = true)
    public void updateDictItem(Long id, DictItemUpdateDTO updateDTO) {
        // 检查同一字典下是否存在相同的值（排除自身）
        QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id", updateDTO.getDictId())
                .eq("value", updateDTO.getDictValue())
                .ne("id", id);
        if (exists(queryWrapper)) {
            throw new BusinessException("字典项值已存在");
        }

        SysDictItem dictItem = getById(id);
        if (dictItem == null) {
            throw new BusinessException("字典项不存在");
        }

        BeanUtils.copyProperties(updateDTO, dictItem);
        dictItem.setUpdateTime(LocalDateTime.now());
        updateById(dictItem);
    }

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "字典项管理", businessType = "DELETE")
    @CacheEvict(value = {"sys_dict", "sys_dict_item"}, allEntries = true)
    public void deleteDictItem(Long id) {
        SysDictItem dictItem = getById(id);
        if (dictItem == null) {
            throw new BusinessException("字典项不存在");
        }

        dictItem.setUpdateTime(LocalDateTime.now());
        dictItem.setDeleted(1);
        updateById(dictItem);
    }

    /**
     * 批量删除字典项
     *
     * @param ids 字典项ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "字典项管理", businessType = "DELETE")
    @CacheEvict(value = {"sys_dict", "sys_dict_item"}, allEntries = true)
    public void deleteDictItems(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        List<SysDictItem> dictItems = listByIds(ids);
        if (CollectionUtils.isEmpty(dictItems)) {
            return;
        }
        dictItems.forEach(item -> {
            item.setUpdateTime(LocalDateTime.now());
            item.setDeleted(1);
        });
        updateBatchById(dictItems);
    }

    /**
     * 获取字典项详情
     *
     * @param id 字典项ID
     * @return 字典项详情
     */
    @Override
    @Cacheable(value = "sys_dict_item", key = "'id:' + #id")
    public DictItemVO getDictItemById(Long id) {
        SysDictItem dictItem = getById(id);
        return convertToVO(dictItem);
    }

    /**
     * 分页查询字典项
     *
     * @param page     分页参数
     * @param dictItem 查询条件
     * @return 字典项列表
     */
    @Override
    public Page<DictItemVO> pageDictItems(Page<SysDictItem> page, SysDictItem dictItem) {
        QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
        if (dictItem != null) {
            queryWrapper.eq(dictItem.getDictId() != null, "dict_id", dictItem.getDictId())
                    .like(StringUtils.isNotBlank(dictItem.getLabel()), "label", dictItem.getLabel())
                    .like(StringUtils.isNotBlank(dictItem.getValue()), "value", dictItem.getValue())
                    .eq(dictItem.getIsDefault() != null, "is_default", dictItem.getIsDefault())
                    .eq(dictItem.getStatus() != null, "status", dictItem.getStatus());
        }
        queryWrapper.eq("deleted", 0).orderByAsc("sort");

        Page<SysDictItem> resultPage = page(page, queryWrapper);
        return new Page<DictItemVO>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal())
            .setRecords(resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList()));
    }

    /**
     * 根据字典ID获取字典项列表
     *
     * @param dictId 字典ID
     * @return 字典项列表
     */
    @Override
    @Cacheable(value = "sys_dict_item", key = "'dictId:' + #dictId")
    public List<DictItemVO> listDictItemsByDictId(Long dictId) {
        QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id", dictId)
                .eq("deleted", 0)
                .orderByAsc("sort");
        return list(queryWrapper).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 检查字典项值是否唯一
     */
    private void checkDictItemValueUnique(Long dictId, String value) {
        QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id", dictId)
                .eq("value", value)
                .eq("deleted", 0);
        
        if (exists(queryWrapper)) {
            throw new BusinessException("字典项值已存在");
        }
    }

    /**
     * 将实体转换为VO
     */
    private DictItemVO convertToVO(SysDictItem dictItem) {
        if (dictItem == null) {
            return null;
        }
        DictItemVO vo = new DictItemVO();
        vo.setId(dictItem.getId());
        vo.setDictId(dictItem.getDictId());
        vo.setLabel(dictItem.getLabel());
        vo.setValue(dictItem.getValue());
        vo.setDescription(dictItem.getDescription());
        vo.setIsDefault(dictItem.getIsDefault());
        vo.setColorType(dictItem.getColorType());
        vo.setCssClass(dictItem.getCssClass());
        vo.setSort(dictItem.getSort());
        vo.setStatus(dictItem.getStatus());
        return vo;
    }

    @Override
    public boolean exists(QueryWrapper<SysDictItem> queryWrapper) {
        return count(queryWrapper) > 0;
    }

    @Override
    public SysDictItem getById(Long id) {
        return super.getById(id);
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return super.removeBatchByIds(ids);
    }

    @Override
    public Page<SysDictItem> page(Page<SysDictItem> page, QueryWrapper<SysDictItem> queryWrapper) {
        return super.page(page, queryWrapper);
    }

    @Override
    public boolean update(SysDictItem entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean remove(Long id) {
        return super.removeById(id);
    }

    @Override
    public long count(QueryWrapper<SysDictItem> queryWrapper) {
        return super.count(queryWrapper);
    }

    @Override
    public List<SysDictItem> list(QueryWrapper<SysDictItem> queryWrapper) {
        return super.list(queryWrapper);
    }

    @Override
    public boolean save(SysDictItem entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateBatch(List<SysDictItem> entityList) {
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean saveBatch(List<SysDictItem> entityList) {
        return super.saveBatch(entityList);
    }
} 