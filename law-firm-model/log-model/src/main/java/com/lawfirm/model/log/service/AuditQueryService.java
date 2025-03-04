package com.lawfirm.model.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.dto.AuditRecordDTO;
import com.lawfirm.model.log.dto.AuditLogQuery;
import com.lawfirm.model.log.entity.audit.AuditLog;
import com.lawfirm.model.log.entity.audit.AuditRecord;

import java.util.List;

/**
 * 审计查询服务接口
 */
public interface AuditQueryService {

    /**
     * 分页查询审计日志
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<AuditLogDTO> queryAuditLogs(AuditLogQuery query);

    /**
     * 查询指定目标的审计记录
     *
     * @param targetId 目标ID
     * @param targetType 目标类型
     * @return 审计记录列表
     */
    List<AuditRecordDTO> queryAuditRecords(Long targetId, String targetType);

    /**
     * 获取审计日志详情
     *
     * @param id 审计日志ID
     * @return 审计日志
     */
    AuditLogDTO getAuditLog(Long id);

    /**
     * 获取审计记录详情
     *
     * @param id 审计记录ID
     * @return 审计记录
     */
    AuditRecordDTO getAuditRecord(Long id);
} 