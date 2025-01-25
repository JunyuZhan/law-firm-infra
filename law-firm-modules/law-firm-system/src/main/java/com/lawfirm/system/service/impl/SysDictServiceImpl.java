package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.exception.BusinessException;
import com.lawfirm.model.system.entity.SysDict;
import com.lawfirm.system.mapper.SysDictMapper;
import com.lawfirm.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统字典服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    private final SysDictMapper dictMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "dict", allEntries = true)
    public void createDict(SysDict dict) {
        // 校验字典类型和值是否已存在
        if (isTypeAndValueExists(dict.getType(), dict.getValue())) {
            throw new BusinessException("字典类型和值已存在");
        }
        
        // 保存字典
        save(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "dict", allEntries = true)
    public void updateDict(SysDict dict) {
        // 校验字典是否存在
        if (!existsById(dict.getId())) {
            throw new BusinessException("字典不存在");
        }

        // 校验字典类型和值是否已存在
        SysDict existingDict = getById(dict.getId());
        if (!existingDict.getType().equals(dict.getType()) 
            || !existingDict.getValue().equals(dict.getValue())) {
            if (isTypeAndValueExists(dict.getType(), dict.getValue())) {
                throw new BusinessException("字典类型和值已存在");
            }
        }
        
        // 更新字典
        updateById(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "dict", allEntries = true)
    public void deleteDict(Long id) {
        // 校验字典是否存在
        if (!existsById(id)) {
            throw new BusinessException("字典不存在");
        }
        
        // 删除字典
        removeById(id);
    }

    @Override
    @Cacheable(value = "dict", key = "#type")
    public List<SysDict> listByType(String type) {
        return dictMapper.selectByType(type);
    }

    @Override
    @Cacheable(value = "dict", key = "#type + ':' + #value")
    public SysDict getByTypeAndValue(String type, String value) {
        return dictMapper.selectByTypeAndValue(type, value);
    }

    @Override
    @Cacheable(value = "dict", key = "'types'")
    public List<String> listAllTypes() {
        return dictMapper.selectAllTypes();
    }

    @Override
    @CacheEvict(value = "dict", allEntries = true)
    public void refreshCache() {
        // 刷新缓存,无需实际操作
    }

    /**
     * 判断字典类型和值是否已存在
     */
    private boolean isTypeAndValueExists(String type, String value) {
        return lambdaQuery()
                .eq(SysDict::getType, type)
                .eq(SysDict::getValue, value)
                .exists();
    }
} 