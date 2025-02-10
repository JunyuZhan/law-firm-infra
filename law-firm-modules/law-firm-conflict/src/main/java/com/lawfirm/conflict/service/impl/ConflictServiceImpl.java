package com.lawfirm.conflict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.conflict.entity.Conflict;
import com.lawfirm.conflict.enums.ConflictStatus;
import com.lawfirm.conflict.enums.ConflictType;
import com.lawfirm.conflict.mapper.ConflictMapper;
import com.lawfirm.conflict.service.IConflictService;
import com.lawfirm.common.core.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 利益冲突服务实现类
 */
@Service
public class ConflictServiceImpl extends ServiceImpl<ConflictMapper, Conflict> implements IConflictService {

    @Override
    public List<Conflict> checkPartyConflict(String partyId) {
        LambdaQueryWrapper<Conflict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Conflict::getConflictType, ConflictType.PARTY_CONFLICT.getCode())
                .and(w -> w.eq(Conflict::getPartyA, partyId)
                        .or()
                        .eq(Conflict::getPartyB, partyId))
                .orderByDesc(Conflict::getCreateTime);
        return list(wrapper);
    }

    @Override
    public List<Conflict> checkCaseConflict(String caseId) {
        LambdaQueryWrapper<Conflict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Conflict::getConflictType, ConflictType.CASE_CONFLICT.getCode())
                .and(w -> w.eq(Conflict::getPartyA, caseId)
                        .or()
                        .eq(Conflict::getPartyB, caseId))
                .orderByDesc(Conflict::getCreateTime);
        return list(wrapper);
    }

    @Override
    public List<Conflict> checkLawyerConflict(Long lawyerId) {
        LambdaQueryWrapper<Conflict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Conflict::getConflictType, ConflictType.LAWYER_CONFLICT.getCode())
                .and(w -> w.eq(Conflict::getPartyA, lawyerId.toString())
                        .or()
                        .eq(Conflict::getPartyB, lawyerId.toString()))
                .orderByDesc(Conflict::getCreateTime);
        return list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleConflict(Long id, Long handlerId, Integer status, String opinion) {
        // 检查冲突是否存在
        Conflict conflict = getById(id);
        if (conflict == null) {
            throw new BusinessException("冲突记录不存在");
        }
        
        // 检查状态是否合法
        if (!isValidStatus(status)) {
            throw new BusinessException("无效的处理状态");
        }
        
        // 更新冲突状态
        conflict.setStatus(status)
                .setHandlerId(handlerId)
                .setHandleOpinion(opinion)
                .setHandleTime(LocalDateTime.now());
        
        updateById(conflict);
    }

    @Override
    public IPage<Conflict> pageConflicts(Integer type, Integer status, LocalDateTime startTime, LocalDateTime endTime, long page, long size) {
        LambdaQueryWrapper<Conflict> wrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (type != null) {
            wrapper.eq(Conflict::getConflictType, type);
        }
        if (status != null) {
            wrapper.eq(Conflict::getStatus, status);
        }
        if (startTime != null) {
            wrapper.ge(Conflict::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Conflict::getCreateTime, endTime);
        }
        
        wrapper.orderByDesc(Conflict::getCreateTime);
        
        return page(new Page<>(page, size), wrapper);
    }
    
    /**
     * 检查状态是否合法
     */
    private boolean isValidStatus(Integer status) {
        if (status == null) {
            return false;
        }
        return ConflictStatus.getDesc(status) != null;
    }
}
