package com.lawfirm.core.audit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.log.service.AuditQueryService;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.dto.AuditRecordDTO;
import com.lawfirm.model.log.dto.AuditLogQuery;
import com.lawfirm.model.log.entity.audit.AuditLog;
import com.lawfirm.model.log.entity.audit.AuditRecord;
import com.lawfirm.model.log.mapper.AuditLogMapper;
import com.lawfirm.model.log.mapper.AuditRecordMapper;
import com.lawfirm.model.log.converter.AuditLogConverter;
import com.lawfirm.model.log.converter.AuditRecordConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 审计查询服务实现
 * <p>
 * 提供审计日志和审计记录的查询服务，支持分页查询和条件过滤。
 * 使用log-model模块中定义的数据模型和转换器。
 * </p>
 *
 * @author JunyuZhan
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component("auditQueryServiceImpl")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm.core.audit", name = "enabled", havingValue = "true", matchIfMissing = false)
public class AuditQueryServiceImpl implements AuditQueryService {

    private final AuditLogMapper auditLogMapper;
    private final AuditRecordMapper auditRecordMapper;
    
    private final AuditLogConverter auditLogConverter;
    
    private final AuditRecordConverter auditRecordConverter;

    @Override
    public Page<AuditLogDTO> queryAuditLogs(AuditLogQuery query) {
        Page<AuditLog> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<AuditLog> wrapper = new LambdaQueryWrapper<AuditLog>()
            .eq(query.getOperatorId() != null, AuditLog::getOperatorId, query.getOperatorId())
            .eq(query.getOperateType() != null, AuditLog::getOperateType, query.getOperateType())
            .eq(query.getBusinessType() != null, AuditLog::getBusinessType, query.getBusinessType())
            .eq(query.getStatus() != null, AuditLog::getStatus, query.getStatus())
            .like(query.getModule() != null, AuditLog::getModule, query.getModule())
            .like(query.getOperatorName() != null, AuditLog::getOperatorName, query.getOperatorName())
            .ge(query.getStartTime() != null, AuditLog::getOperationTime, query.getStartTime())
            .le(query.getEndTime() != null, AuditLog::getOperationTime, query.getEndTime())
            .orderByDesc(AuditLog::getOperationTime);
            
        page = auditLogMapper.selectPage(page, wrapper);
        IPage<AuditLogDTO> iPage = page.convert(auditLogConverter::toDTO);
        Page<AuditLogDTO> resultPage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        resultPage.setRecords(iPage.getRecords());
        return resultPage;
    }

    @Override
    public List<AuditRecordDTO> queryAuditRecords(Long targetId, String targetType) {
        LambdaQueryWrapper<AuditRecord> wrapper = new LambdaQueryWrapper<AuditRecord>()
            .eq(targetId != null, AuditRecord::getAuditLogId, targetId)
            .eq(targetType != null, AuditRecord::getAuditNode, targetType)
            .orderByDesc(AuditRecord::getAuditTime);
        
        return auditRecordMapper.selectList(wrapper)
            .stream()
            .map(auditRecordConverter::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public AuditLogDTO getAuditLog(Long id) {
        return auditLogConverter.toDTO(auditLogMapper.selectById(id));
    }

    @Override
    public AuditRecordDTO getAuditRecord(Long id) {
        return auditRecordConverter.toDTO(auditRecordMapper.selectById(id));
    }
} 