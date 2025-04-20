package com.lawfirm.system.service.impl.dict;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.system.dto.dict.DictCreateDTO;
import com.lawfirm.model.system.dto.dict.DictItemCreateDTO;
import com.lawfirm.model.system.dto.dict.DictItemUpdateDTO;
import com.lawfirm.model.system.dto.dict.DictQueryDTO;
import com.lawfirm.model.system.dto.dict.DictUpdateDTO;
import com.lawfirm.model.system.entity.dict.SysDict;
import com.lawfirm.model.system.entity.dict.SysDictItem;
import com.lawfirm.model.system.mapper.dict.SysDictItemMapper;
import com.lawfirm.model.system.mapper.dict.SysDictMapper;
import com.lawfirm.model.system.service.DictService;
import com.lawfirm.model.system.vo.dict.DictItemVO;
import com.lawfirm.model.system.vo.dict.DictVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lawfirm.common.cache.annotation.SimpleCache;
import com.lawfirm.common.log.annotation.AuditLog;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.system.dto.dict.DictCreateDTO;
import com.lawfirm.model.system.dto.dict.DictQueryDTO;
import com.lawfirm.model.system.dto.dict.DictUpdateDTO;
import com.lawfirm.model.system.entity.dict.SysDict;
import com.lawfirm.model.system.mapper.dict.SysDictMapper;
import com.lawfirm.model.system.service.DictService;
import com.lawfirm.model.system.vo.dict.DictVO;

/**
 * 字典服务实现类
 */
@Slf4j
@Service("systemDictServiceImpl")
@RequiredArgsConstructor
public class DictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements DictService {

    private final SysDictMapper dictMapper;
    private final SysDictItemMapper dictItemMapper;

    @Override
    public List<DictVO> listAllDicts() {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getDeleted, 0);
        return list(wrapper).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DictVO> pageDicts(Page<SysDict> page, SysDict dict) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(dict.getDictName())) {
            wrapper.like(SysDict::getDictName, dict.getDictName());
        }
        if (StringUtils.isNotEmpty(dict.getDictType())) {
            wrapper.like(SysDict::getDictType, dict.getDictType());
        }
        if (dict.getStatus() != null) {
            wrapper.eq(SysDict::getStatus, dict.getStatus());
        }
        wrapper.eq(SysDict::getDeleted, 0);
        
        Page<SysDict> resultPage = page(page, wrapper);
        Page<DictVO> voPage = new Page<>();
        voPage.setCurrent(resultPage.getCurrent());
        voPage.setSize(resultPage.getSize());
        voPage.setTotal(resultPage.getTotal());
        voPage.setRecords(resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList()));
        return voPage;
    }

    @Override
    public DictVO getDictById(Long id) {
        SysDict dict = getById(id);
        return convertToVO(dict);
    }

    @Override
    public DictVO getDictByCode(String code) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getDictType, code);
        wrapper.eq(SysDict::getDeleted, 0);
        SysDict dict = getOne(wrapper);
        return convertToVO(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDict(DictCreateDTO createDTO) {
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
        save(dict);
        return dict.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "sys_dict", allEntries = true)
    public void updateDict(Long id, DictUpdateDTO updateDTO) {
        // 检查是否存在
        SysDict dict = getById(id);
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
        dict.setUpdateTime(LocalDateTime.now());
        
        // 保存
        updateById(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "sys_dict", allEntries = true)
    public void deleteDict(Long id) {
        // 检查是否存在
        SysDict dict = getById(id);
        if (dict == null) {
            throw new RuntimeException("字典不存在");
        }
        
        // 检查是否有字典项
        LambdaQueryWrapper<SysDictItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictItem::getDictId, id);
        wrapper.eq(SysDictItem::getDeleted, 0);
        long count = dictItemMapper.selectCount(wrapper);
        if (count > 0) {
            throw new RuntimeException("字典下存在字典项，不能删除");
        }
        
        // 逻辑删除
        dict.setDeleted(1);
        dict.setUpdateBy(SecurityUtils.getUsername());
        dict.setUpdateTime(LocalDateTime.now());
        
        updateById(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "sys_dict", allEntries = true)
    public void deleteDicts(List<Long> ids) {
        for (Long id : ids) {
            deleteDict(id);
        }
    }

    @Override
    public List<DictVO> listDictsByType(String type) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getDictType, type);
        wrapper.eq(SysDict::getDeleted, 0);
        return list(wrapper).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "sys_dict", allEntries = true)
    public void refreshCache() {
        log.info("刷新字典缓存");
    }

    /**
     * 检查字典类型是否唯一
     */
    private void checkDictTypeUnique(String dictType) {
        QueryWrapper<SysDict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type", dictType);
        wrapper.eq("deleted", 0);
        
        if (exists(wrapper)) {
            throw new RuntimeException("字典类型已存在");
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

    @Override
    public List<Map<String, Object>> getDictItems(String dictCode) {
        // 获取字典ID
        LambdaQueryWrapper<SysDict> dictWrapper = new LambdaQueryWrapper<>();
        dictWrapper.eq(SysDict::getDictType, dictCode);
        dictWrapper.eq(SysDict::getDeleted, 0);
        SysDict dict = getOne(dictWrapper);
        
        if (dict == null) {
            return List.of();
        }
        
        // 查询字典项
        QueryWrapper<SysDictItem> itemWrapper = new QueryWrapper<>();
        itemWrapper.eq("dict_id", dict.getId());
        itemWrapper.eq("deleted", 0);
        itemWrapper.orderByAsc("sort_order");
        
        return dictItemMapper.selectList(itemWrapper).stream()
                .map(item -> {
                    Map<String, Object> map = new java.util.HashMap<>();
                    map.put("label", item.getLabel());
                    map.put("value", item.getValue());
                    map.put("id", item.getId());
                    return map;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    @Cacheable(value = "sys_dict", key = "#dictCode")
    public Map<String, String> getDictMap(String dictCode) {
        // 获取字典ID
        LambdaQueryWrapper<SysDict> dictWrapper = new LambdaQueryWrapper<>();
        dictWrapper.eq(SysDict::getDictType, dictCode);
        dictWrapper.eq(SysDict::getDeleted, 0);
        SysDict dict = getOne(dictWrapper);
        
        if (dict == null) {
            return Map.of();
        }
        
        // 查询字典项
        QueryWrapper<SysDictItem> itemWrapper = new QueryWrapper<>();
        itemWrapper.eq("dict_id", dict.getId());
        itemWrapper.eq("deleted", 0);
        itemWrapper.orderByAsc("sort_order");
        
        return dictItemMapper.selectList(itemWrapper).stream()
                .collect(Collectors.toMap(
                    SysDictItem::getValue, 
                    SysDictItem::getLabel,
                    (v1, v2) -> v1 // 如果有重复键，保留第一个
                ));
    }
} 