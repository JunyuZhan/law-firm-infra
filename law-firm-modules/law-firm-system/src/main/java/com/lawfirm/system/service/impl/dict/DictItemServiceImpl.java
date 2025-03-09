package com.lawfirm.system.service.impl.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.cache.annotation.CacheEvict;
import com.lawfirm.common.cache.annotation.Cacheable;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.utils.StringUtils;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.system.dto.dict.DictItemCreateDTO;
import com.lawfirm.model.system.dto.dict.DictItemUpdateDTO;
import com.lawfirm.model.system.entity.SysDict;
import com.lawfirm.model.system.entity.SysDictItem;
import com.lawfirm.model.system.mapper.SysDictItemMapper;
import com.lawfirm.model.system.mapper.SysDictMapper;
import com.lawfirm.model.system.service.DictItemService;
import com.lawfirm.model.system.vo.DictItemVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统字典项服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements DictItemService {

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
        // 检查字典是否存在
        SysDict dict = dictMapper.selectById(createDTO.getDictId());
        if (dict == null || dict.getDeleted() == 1) {
            throw new BusinessException("字典不存在");
        }
        
        // 检查字典项值是否已存在
        checkDictItemValueUnique(createDTO.getDictId(), createDTO.getValue());
        
        // 创建字典项
        SysDictItem dictItem = new SysDictItem();
        BeanUtils.copyProperties(createDTO, dictItem);
        dictItem.setCreateBy(SecurityUtils.getUsername());
        
        dictItemMapper.insert(dictItem);
        return dictItem.getId();
    }

    /**
     * 更新字典项
     *
     * @param id        字典项ID
     * @param updateDTO 更新参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "字典项管理", businessType = "UPDATE")
    @CacheEvict(value = {"sys_dict", "sys_dict_item"}, allEntries = true)
    public void updateDictItem(Long id, DictItemUpdateDTO updateDTO) {
        // 检查字典项是否存在
        SysDictItem dictItem = dictItemMapper.selectById(id);
        if (dictItem == null || dictItem.getDeleted() == 1) {
            throw new BusinessException("字典项不存在");
        }
        
        // 检查字典是否存在
        SysDict dict = dictMapper.selectById(updateDTO.getDictId());
        if (dict == null || dict.getDeleted() == 1) {
            throw new BusinessException("字典不存在");
        }
        
        // 检查字典项值是否已存在（排除自身）
        if (!dictItem.getValue().equals(updateDTO.getValue()) || !dictItem.getDictId().equals(updateDTO.getDictId())) {
            checkDictItemValueUnique(updateDTO.getDictId(), updateDTO.getValue());
        }
        
        // 更新字典项
        BeanUtils.copyProperties(updateDTO, dictItem);
        dictItem.setUpdateBy(SecurityUtils.getUsername());
        
        dictItemMapper.updateById(dictItem);
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
        // 检查字典项是否存在
        SysDictItem dictItem = dictItemMapper.selectById(id);
        if (dictItem == null || dictItem.getDeleted() == 1) {
            throw new BusinessException("字典项不存在");
        }
        
        // 逻辑删除
        dictItem.setDeleted(1);
        dictItem.setUpdateBy(SecurityUtils.getUsername());
        
        dictItemMapper.updateById(dictItem);
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
        if (ids == null || ids.isEmpty()) {
            return;
        }
        
        for (Long id : ids) {
            deleteDictItem(id);
        }
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
        SysDictItem dictItem = dictItemMapper.selectById(id);
        if (dictItem == null || dictItem.getDeleted() == 1) {
            return null;
        }
        
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
        LambdaQueryWrapper<SysDictItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dictItem.getDictId() != null, SysDictItem::getDictId, dictItem.getDictId())
                .like(StringUtils.isNotEmpty(dictItem.getLabel()), SysDictItem::getLabel, dictItem.getLabel())
                .like(StringUtils.isNotEmpty(dictItem.getValue()), SysDictItem::getValue, dictItem.getValue())
                .eq(SysDictItem::getDeleted, 0)
                .orderByAsc(SysDictItem::getSort);
        
        Page<SysDictItem> dictItemPage = dictItemMapper.selectPage(page, queryWrapper);
        Page<DictItemVO> voPage = new Page<>();
        voPage.setCurrent(dictItemPage.getCurrent());
        voPage.setSize(dictItemPage.getSize());
        voPage.setTotal(dictItemPage.getTotal());
        voPage.setRecords(dictItemPage.getRecords().stream().map(this::convertToVO).collect(Collectors.toList()));
        
        return voPage;
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
        if (dictId == null) {
            return new ArrayList<>();
        }
        
        LambdaQueryWrapper<SysDictItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictItem::getDictId, dictId)
                .eq(SysDictItem::getDeleted, 0)
                .orderByAsc(SysDictItem::getSort);
        
        List<SysDictItem> dictItems = dictItemMapper.selectList(queryWrapper);
        if (dictItems == null || dictItems.isEmpty()) {
            return new ArrayList<>();
        }
        
        return dictItems.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 检查字典项值是否唯一
     */
    private void checkDictItemValueUnique(Long dictId, String value) {
        LambdaQueryWrapper<SysDictItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictItem::getDictId, dictId)
                .eq(SysDictItem::getValue, value)
                .eq(SysDictItem::getDeleted, 0);
        
        if (dictItemMapper.selectCount(queryWrapper) > 0) {
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
        BeanUtils.copyProperties(dictItem, vo);
        
        return vo;
    }
} 