package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.contract.dto.ContractConflictCheckDTO;
import com.lawfirm.model.contract.dto.ContractConflictResultDTO;
import com.lawfirm.model.contract.entity.ContractConflict;
import com.lawfirm.model.contract.enums.ContractConflictEnum;
import com.lawfirm.model.contract.mapper.ContractConflictMapper;
import com.lawfirm.model.contract.service.ContractConflictService;
import com.lawfirm.model.contract.vo.ContractConflictVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同冲突检查服务实现类
 */
@Slf4j
@Service("contractConflictService")
public class ContractConflictServiceImpl extends ServiceImpl<ContractConflictMapper, ContractConflict> 
        implements ContractConflictService, BaseService<ContractConflict> {

    @Override
    public boolean exists(QueryWrapper<ContractConflict> queryWrapper) {
        return count(queryWrapper) > 0;
    }

    @Override
    public long count(QueryWrapper<ContractConflict> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public Page<ContractConflict> page(Page<ContractConflict> page, QueryWrapper<ContractConflict> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<ContractConflict> list(QueryWrapper<ContractConflict> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public ContractConflict getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public ContractConflictResultDTO checkConflict(ContractConflictCheckDTO checkDTO) {
        // 实现冲突检查逻辑
        return null;
    }

    @Override
    public List<ContractConflictVO> getConflictHistory(Long contractId) {
        LambdaQueryWrapper<ContractConflict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContractConflict::getContractId, contractId)
               .orderByDesc(ContractConflict::getCheckTime);
        List<ContractConflict> conflicts = list(wrapper);
        return conflicts.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ContractConflictVO> pageConflicts(Page<ContractConflictVO> page, Long contractId, 
            String conflictType, Integer conflictLevel, Boolean isResolved) {
        LambdaQueryWrapper<ContractConflict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(contractId != null, ContractConflict::getContractId, contractId)
               .eq(conflictType != null, ContractConflict::getConflictType, conflictType)
               .eq(conflictLevel != null, ContractConflict::getConflictLevel, conflictLevel)
               .eq(isResolved != null, ContractConflict::getIsResolved, isResolved)
               .orderByDesc(ContractConflict::getCheckTime);
        
        Page<ContractConflict> conflictPage = page(new Page<>(page.getCurrent(), page.getSize()), wrapper);
        List<ContractConflictVO> voList = conflictPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        page.setRecords(voList);
        page.setTotal(conflictPage.getTotal());
        return page;
    }

    @Override
    public boolean resolveConflict(Long conflictId, String resolution) {
        ContractConflict conflict = getById(conflictId);
        if (conflict == null) {
            return false;
        }
        conflict.setResolution(resolution);
        conflict.setIsResolved(true);
        return updateById(conflict);
    }

    @Override
    public boolean batchResolveConflicts(List<Long> conflictIds, String resolution) {
        List<ContractConflict> conflicts = listByIds(conflictIds);
        if (conflicts.isEmpty()) {
            return false;
        }
        conflicts.forEach(conflict -> {
            conflict.setResolution(resolution);
            conflict.setIsResolved(true);
        });
        return updateBatchById(conflicts);
    }

    @Override
    public ContractConflictVO getConflictDetail(Long conflictId) {
        ContractConflict conflict = getById(conflictId);
        return conflict != null ? convertToVO(conflict) : null;
    }

    @Override
    public boolean hasConflict(Long contractId) {
        LambdaQueryWrapper<ContractConflict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContractConflict::getContractId, contractId)
               .eq(ContractConflict::getIsResolved, false);
        return count(wrapper) > 0;
    }

    @Override
    public int getUnresolvedConflictCount(Long contractId) {
        LambdaQueryWrapper<ContractConflict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContractConflict::getContractId, contractId)
               .eq(ContractConflict::getIsResolved, false);
        return (int) count(wrapper);
    }

    @Override
    public String getCurrentUsername() {
        return SecurityUtils.getUsername();
    }

    @Override
    public Long getCurrentUserId() {
        return SecurityUtils.getUserId();
    }

    @Override
    public Long getCurrentTenantId() {
        return 1L; // TODO: 从租户上下文获取
    }
    
    @Override
    public boolean updateBatch(List<ContractConflict> entities) {
        return updateBatchById(entities);
    }

    @Override
    public boolean update(ContractConflict entity) {
        return updateById(entity);
    }

    @Override
    public boolean save(ContractConflict entity) {
        return save(entity);
    }

    @Override
    public boolean saveBatch(List<ContractConflict> entities) {
        return saveBatch(entities);
    }
    
    /**
     * 将实体转换为VO
     */
    private ContractConflictVO convertToVO(ContractConflict entity) {
        if (entity == null) {
            return null;
        }
        
        ContractConflictVO vo = new ContractConflictVO();
        vo.setId(entity.getId());
        vo.setContractId(entity.getContractId());
        vo.setCheckStatus(entity.getCheckStatus());
        vo.setCheckStatusDesc(getCheckStatusDesc(entity.getCheckStatus()));
        vo.setCheckTime(entity.getCheckTime());
        vo.setCheckerId(entity.getCheckerId());
        vo.setConflictType(entity.getConflictType());
        vo.setConflictTypeDesc(getConflictTypeDesc(entity.getConflictType()));
        vo.setConflictLevel(entity.getConflictLevel());
        vo.setConflictLevelDesc(getConflictLevelDesc(entity.getConflictLevel()));
        vo.setConflictDesc(entity.getConflictDesc());
        vo.setConflictDetails(entity.getConflictDetails());
        vo.setRelatedContractId(entity.getRelatedContractId());
        vo.setResolution(entity.getResolution());
        vo.setIsResolved(entity.getIsResolved());
        
        // 处理日期转换
        LocalDateTime createTime = entity.getCreateTime();
        LocalDateTime updateTime = entity.getUpdateTime();
        if (createTime != null) {
            vo.setCreateTime(Date.from(createTime.atZone(ZoneId.systemDefault()).toInstant()));
        }
        if (updateTime != null) {
            vo.setUpdateTime(Date.from(updateTime.atZone(ZoneId.systemDefault()).toInstant()));
        }
        
        return vo;
    }
    
    /**
     * 获取检查状态描述
     */
    private String getCheckStatusDesc(Integer status) {
        if (status == null) {
            return null;
        }
        for (ContractConflictEnum.CheckStatus checkStatus : ContractConflictEnum.CheckStatus.values()) {
            if (checkStatus.getCode().equals(status)) {
                return checkStatus.getDesc();
            }
        }
        return null;
    }
    
    /**
     * 获取冲突类型描述
     */
    private String getConflictTypeDesc(String type) {
        if (type == null) {
            return null;
        }
        for (ContractConflictEnum.ConflictType conflictType : ContractConflictEnum.ConflictType.values()) {
            if (conflictType.getCode().equals(type)) {
                return conflictType.getDesc();
            }
        }
        return null;
    }
    
    /**
     * 获取冲突级别描述
     */
    private String getConflictLevelDesc(Integer level) {
        if (level == null) {
            return null;
        }
        for (ContractConflictEnum.ConflictLevel conflictLevel : ContractConflictEnum.ConflictLevel.values()) {
            if (conflictLevel.getCode().equals(level)) {
                return conflictLevel.getDesc();
            }
        }
        return null;
    }
} 