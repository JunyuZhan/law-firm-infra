package com.lawfirm.core.audit.service.impl;

import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.common.util.json.JsonUtils;
import com.lawfirm.core.audit.context.AuditContext;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.dto.AuditRecordDTO;
import com.lawfirm.model.log.entity.audit.AuditLog;
import com.lawfirm.model.log.entity.audit.AuditRecord;
import com.lawfirm.model.log.mapper.AuditLogMapper;
import com.lawfirm.model.log.mapper.AuditRecordMapper;
import com.lawfirm.model.log.converter.AuditLogConverter;
import com.lawfirm.model.log.converter.AuditRecordConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 审计服务实现
 * <p>
 * 提供审计日志和审计记录的写入服务，支持同步和异步记录方式。
 * 使用log-model模块中定义的数据模型和转换器。
 * </p>
 *
 * @author JunyuZhan
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm.core.audit", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AuditServiceImpl implements AuditService {

    private final AuditLogMapper auditLogMapper;
    private final AuditRecordMapper auditRecordMapper;
    
    @Autowired
    private AuditLogConverter auditLogConverter;
    
    @Autowired
    private AuditRecordConverter auditRecordConverter;
    
    /**
     * 设置审计日志转换器
     * 
     * @param auditLogConverter 审计日志转换器
     */
    public void setAuditLogConverter(AuditLogConverter auditLogConverter) {
        this.auditLogConverter = auditLogConverter;
    }
    
    /**
     * 设置审计记录转换器
     * 
     * @param auditRecordConverter 审计记录转换器
     */
    public void setAuditRecordConverter(AuditRecordConverter auditRecordConverter) {
        this.auditRecordConverter = auditRecordConverter;
    }

    @Override
    public void log(AuditLogDTO auditLogDTO) {
        AuditLog auditLog = auditLogConverter.toEntity(auditLogDTO);
        enrichAuditLog(auditLog);
        auditLogMapper.insert(auditLog);
        log.info("审计日志记录: {}", JsonUtils.toJsonString(auditLog));
    }

    @Async("auditAsyncExecutor")
    @Override
    public void logAsync(AuditLogDTO auditLogDTO) {
        log(auditLogDTO);
    }

    @Override
    public void record(AuditRecordDTO auditRecordDTO) {
        AuditRecord auditRecord = auditRecordConverter.toEntity(auditRecordDTO);
        enrichAuditRecord(auditRecord);
        auditRecordMapper.insert(auditRecord);
        log.info("审计记录保存: {}", JsonUtils.toJsonString(auditRecord));
    }

    @Async("auditAsyncExecutor")
    @Override
    public void recordAsync(AuditRecordDTO auditRecordDTO) {
        record(auditRecordDTO);
    }

    /**
     * 丰富审计日志信息
     *
     * @param auditLog 审计日志实体
     */
    private void enrichAuditLog(AuditLog auditLog) {
        // 优先使用上下文中的信息
        AuditContext.AuditInfo info = AuditContext.get();
        if (info.getOperatorId() != null) {
            auditLog.setOperatorId(Long.valueOf(info.getOperatorId()));
            auditLog.setOperatorName(info.getOperatorName());
            auditLog.setOperatorIp(info.getOperatorIp());
        } else {
            // 从SecurityUtils获取当前用户信息
            auditLog.setOperatorId(SecurityUtils.getUserId());
            auditLog.setOperatorName(SecurityUtils.getUsername());
        }
        auditLog.setOperationTime(LocalDateTime.now());
    }

    /**
     * 丰富审计记录信息
     *
     * @param auditRecord 审计记录实体
     */
    private void enrichAuditRecord(AuditRecord auditRecord) {
        // 优先使用上下文中的信息
        AuditContext.AuditInfo info = AuditContext.get();
        if (info.getOperatorId() != null) {
            auditRecord.setAuditorId(Long.valueOf(info.getOperatorId()));
            auditRecord.setAuditorName(info.getOperatorName());
        } else {
            // 从SecurityUtils获取当前用户信息
            auditRecord.setAuditorId(SecurityUtils.getUserId());
            auditRecord.setAuditorName(SecurityUtils.getUsername());
        }
        auditRecord.setAuditTime(LocalDateTime.now());
    }
} 