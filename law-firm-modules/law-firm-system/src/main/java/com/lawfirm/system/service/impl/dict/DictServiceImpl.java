package com.lawfirm.system.service.impl.dict;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.util.StringUtils;
import com.lawfirm.common.security.util.SecurityUtils;
import com.lawfirm.system.model.dto.dict.DictCreateDTO;
import com.lawfirm.system.model.dto.dict.DictItemCreateDTO;
import com.lawfirm.system.model.dto.dict.DictItemUpdateDTO;
import com.lawfirm.system.model.dto.dict.DictQueryDTO;
import com.lawfirm.system.model.dto.dict.DictUpdateDTO;
import com.lawfirm.system.model.entity.SysDict;
import com.lawfirm.system.model.entity.SysDictItem;
import com.lawfirm.system.model.mapper.SysDictItemMapper;
import com.lawfirm.system.model.mapper.SysDictMapper;
import com.lawfirm.system.model.service.DictService;
import com.lawfirm.system.model.vo.DictItemVO;
import com.lawfirm.system.model.vo.DictVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * 字典服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements DictService {

    private final SysDictMapper dictMapper;
    private final SysDictItemMapper dictItemMapper;

    /**
     * 查询字典列表
     */
    @Override
    public List<DictVO> selectDictList(DictQueryDTO queryDTO) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (StringUtils.isNotEmpty(queryDTO.getDictName())) {
            wrapper.like(SysDict::getDictName, queryDTO.getDictName());
        }
        
        if (StringUtils.isNotEmpty(queryDTO.getDictType())) {
            wrapper.like(SysDict::getDictType, queryDTO.getDictType());
        }
        
        if (queryDTO.getStatus() != null) {
            wrapper.eq(SysDict::getStatus, queryDTO.getStatus());
        }
        
        // 分页查询
        Page<SysDict> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<SysDict> resultPage = dictMapper.selectPage(page, wrapper);
        
        // 转换为VO
        return resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 查询字典详情
     */
    @Override
    public DictVO selectDictById(Long id) {
        SysDict dict = dictMapper.selectById(id);
        return convertToVO(dict);
    }

    /**
     * 根据字典类型查询字典数据
     */
    @Override
    @Cacheable(value = "sys_dict", key = "#dictType")
    public List<DictItemVO> selectDictDataByType(String dictType) {
        // 查询字典
        LambdaQueryWrapper<SysDict> dictWrapper = new LambdaQueryWrapper<>();
        dictWrapper.eq(SysDict::getDictType, dictType);
        dictWrapper.eq(SysDict::getStatus, 0); // 只查询正常状态的字典
        dictWrapper.eq(SysDict::getDeleted, 0);
        SysDict dict = dictMapper.selectOne(dictWrapper);
        
        if (dict == null) {
            return List.of();
        }
        
        // 查询字典项
        LambdaQueryWrapper<SysDictItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(SysDictItem::getDictId, dict.getId());
        itemWrapper.eq(SysDictItem::getStatus, 0); // 只查询正常状态的字典项
        itemWrapper.eq(SysDictItem::getDeleted, 0);
        itemWrapper.orderByAsc(SysDictItem::getSort);
        
        List<SysDictItem> items = dictItemMapper.selectList(itemWrapper);
        return items.stream()
                .map(this::convertItemToVO)
                .collect(Collectors.toList());
    }

    /**
     * 查询字典项列表
     */
    @Override
    public List<DictItemVO> selectDictItemList(Long dictId) {
        LambdaQueryWrapper<SysDictItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictItem::getDictId, dictId);
        wrapper.eq(SysDictItem::getDeleted, 0);
        wrapper.orderByAsc(SysDictItem::getSort);
        
        List<SysDictItem> items = dictItemMapper.selectList(wrapper);
        return items.stream()
                .map(this::convertItemToVO)
                .collect(Collectors.toList());
    }

    /**
     * 新增字典
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDict(DictCreateDTO createDTO) {
        // 检查字典类型是否已存在
        checkDictTypeUnique(createDTO.getDictType());
        
        // 构建实体
        SysDict dict = new SysDict();
        dict.setDictName(createDTO.getDictName());
        dict.setDictType(createDTO.getDictType());
        dict.setStatus(createDTO.getStatus());
        dict.setRemark(createDTO.getRemark());
        dict.setCreateBy(SecurityUtils.getUsername());
        
        // 保存
        dictMapper.insert(dict);
    }

    /**
     * 修改字典
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "sys_dict", allEntries = true)
    public void updateDict(DictUpdateDTO updateDTO) {
        // 检查是否存在
        SysDict dict = dictMapper.selectById(updateDTO.getId());
        if (dict == null) {
            throw new RuntimeException("字典不存在");
        }
        
        // 如果字典类型变更，检查唯一性
        if (!dict.getDictType().equals(updateDTO.getDictType())) {
            checkDictTypeUnique(updateDTO.getDictType());
        }
        
        // 更新字段
        dict.setDictName(updateDTO.getDictName());
        dict.setDictType(updateDTO.getDictType());
        dict.setStatus(updateDTO.getStatus());
        dict.setRemark(updateDTO.getRemark());
        dict.setUpdateBy(SecurityUtils.getUsername());
        dict.setUpdateTime(new java.util.Date());
        
        // 保存
        dictMapper.updateById(dict);
    }

    /**
     * 删除字典
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "sys_dict", allEntries = true)
    public void deleteDictById(Long id) {
        // 检查是否存在
        SysDict dict = dictMapper.selectById(id);
        if (dict == null) {
            throw new RuntimeException("字典不存在");
        }
        
        // 检查是否有字典项
        LambdaQueryWrapper<SysDictItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictItem::getDictId, id);
        wrapper.eq(SysDictItem::getDeleted, 0);
        Integer count = dictItemMapper.selectCount(wrapper);
        if (count > 0) {
            throw new RuntimeException("字典下存在字典项，不能删除");
        }
        
        // 逻辑删除
        dict.setDeleted(1);
        dict.setUpdateBy(SecurityUtils.getUsername());
        dict.setUpdateTime(new java.util.Date());
        
        dictMapper.updateById(dict);
    }

    /**
     * 新增字典项
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "sys_dict", allEntries = true)
    public void insertDictItem(DictItemCreateDTO createDTO) {
        // 检查字典是否存在
        SysDict dict = dictMapper.selectById(createDTO.getDictId());
        if (dict == null) {
            throw new RuntimeException("字典不存在");
        }
        
        // 检查字典项值是否已存在
        checkDictItemValueUnique(createDTO.getDictId(), createDTO.getDictValue());
        
        // 构建实体
        SysDictItem item = new SysDictItem();
        item.setDictId(createDTO.getDictId());
        item.setDictLabel(createDTO.getDictLabel());
        item.setDictValue(createDTO.getDictValue());
        item.setDictSort(createDTO.getDictSort());
        item.setStatus(createDTO.getStatus());
        item.setCssClass(createDTO.getCssClass());
        item.setListClass(createDTO.getListClass());
        item.setIsDefault(createDTO.getIsDefault());
        item.setRemark(createDTO.getRemark());
        item.setCreateBy(SecurityUtils.getUsername());
        
        // 保存
        dictItemMapper.insert(item);
    }

    /**
     * 修改字典项
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "sys_dict", allEntries = true)
    public void updateDictItem(DictItemUpdateDTO updateDTO) {
        // 检查是否存在
        SysDictItem item = dictItemMapper.selectById(updateDTO.getId());
        if (item == null) {
            throw new RuntimeException("字典项不存在");
        }
        
        // 如果字典项值变更，检查唯一性
        if (!item.getDictValue().equals(updateDTO.getDictValue())) {
            checkDictItemValueUnique(updateDTO.getDictId(), updateDTO.getDictValue());
        }
        
        // 更新字段
        item.setDictLabel(updateDTO.getDictLabel());
        item.setDictValue(updateDTO.getDictValue());
        item.setDictSort(updateDTO.getDictSort());
        item.setStatus(updateDTO.getStatus());
        item.setCssClass(updateDTO.getCssClass());
        item.setListClass(updateDTO.getListClass());
        item.setIsDefault(updateDTO.getIsDefault());
        item.setRemark(updateDTO.getRemark());
        item.setUpdateBy(SecurityUtils.getUsername());
        item.setUpdateTime(new java.util.Date());
        
        // 保存
        dictItemMapper.updateById(item);
    }

    /**
     * 删除字典项
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "sys_dict", allEntries = true)
    public void deleteDictItemById(Long itemId) {
        // 检查是否存在
        SysDictItem item = dictItemMapper.selectById(itemId);
        if (item == null) {
            throw new RuntimeException("字典项不存在");
        }
        
        // 逻辑删除
        item.setDeleted(1);
        item.setUpdateBy(SecurityUtils.getUsername());
        item.setUpdateTime(new java.util.Date());
        
        dictItemMapper.updateById(item);
    }

    /**
     * 刷新字典缓存
     */
    @Override
    @CacheEvict(value = "sys_dict", allEntries = true)
    public void refreshCache() {
        log.info("刷新字典缓存");
    }

    /**
     * 检查字典类型是否唯一
     */
    private void checkDictTypeUnique(String dictType) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getDictType, dictType);
        wrapper.eq(SysDict::getDeleted, 0);
        
        Integer count = dictMapper.selectCount(wrapper);
        if (count > 0) {
            throw new RuntimeException("字典类型已存在");
        }
    }

    /**
     * 检查字典项值是否唯一
     */
    private void checkDictItemValueUnique(Long dictId, String dictValue) {
        LambdaQueryWrapper<SysDictItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictItem::getDictId, dictId);
        wrapper.eq(SysDictItem::getDictValue, dictValue);
        wrapper.eq(SysDictItem::getDeleted, 0);
        
        Integer count = dictItemMapper.selectCount(wrapper);
        if (count > 0) {
            throw new RuntimeException("字典项值已存在");
        }
    }

    /**
     * 将字典实体转换为VO
     */
    private DictVO convertToVO(SysDict dict) {
        if (dict == null) {
            return null;
        }
        
        DictVO vo = new DictVO();
        BeanUtils.copyProperties(dict, vo);
        
        // 设置字典类型名称
        if (StringUtils.isNotEmpty(dict.getDictType())) {
            switch (dict.getDictType()) {
                case "SYSTEM":
                    vo.setDictTypeName("系统字典");
                    break;
                case "BUSINESS":
                    vo.setDictTypeName("业务字典");
                    break;
                default:
                    vo.setDictTypeName(dict.getDictType());
                    break;
            }
        }
        
        return vo;
    }

    /**
     * 将字典项实体转换为VO
     */
    private DictItemVO convertItemToVO(SysDictItem item) {
        if (item == null) {
            return null;
        }
        
        DictItemVO vo = new DictItemVO();
        BeanUtils.copyProperties(item, vo);
        
        return vo;
    }
} 