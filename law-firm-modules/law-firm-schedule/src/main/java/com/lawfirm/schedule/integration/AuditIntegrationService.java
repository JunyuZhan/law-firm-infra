package com.lawfirm.schedule.integration;

import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import com.lawfirm.model.log.service.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 日程模块审计集成服务
 * 统一处理与core-audit模块的交互
 */
@Slf4j
@Service("scheduleAuditIntegrationService")
public class AuditIntegrationService {

    @Autowired
    @Qualifier("coreAuditServiceImpl")
    private AuditService auditService;

    /**
     * 记录日程相关操作审计日志
     * @param businessType 业务类型
     * @param operateType 操作类型
     * @param description 操作描述
     * @param businessId 业务主键
     * @param beforeData 操作前数据
     * @param afterData 操作后数据
     */
    public void recordScheduleOperation(BusinessTypeEnum businessType, OperateTypeEnum operateType, String description, Long businessId, String beforeData, String afterData) {
        try {
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule("日程管理");
            auditLog.setBusinessType(businessType);
            auditLog.setOperateType(operateType);
            auditLog.setDescription(description);
            auditLog.setId(businessId);
            auditLog.setBeforeData(beforeData);
            auditLog.setAfterData(afterData);
            auditLog.setOperationTime(LocalDateTime.now());
            auditService.log(auditLog);
            log.info("记录日程审计日志成功: businessType={}, operateType={}, businessId={}", businessType, operateType, businessId);
        } catch (Exception e) {
            log.error("记录日程审计日志失败: businessType={}, operateType={}, businessId={}, error={}", businessType, operateType, businessId, e.getMessage(), e);
        }
    }

    /**
     * 记录创建日程审计
     */
    public void recordCreateSchedule(Long scheduleId, String title, String timeRange, String participants) {
        String afterData = String.format("标题：%s，时间：%s，参与人：%s", title, timeRange, participants);
        recordScheduleOperation(
            BusinessTypeEnum.SCHEDULE,
            OperateTypeEnum.CREATE,
            "创建日程",
            scheduleId,
            null,
            afterData
        );
    }

    /**
     * 记录修改日程审计
     */
    public void recordUpdateSchedule(Long scheduleId, String before, String after) {
        recordScheduleOperation(
            BusinessTypeEnum.SCHEDULE,
            OperateTypeEnum.UPDATE,
            "修改日程",
            scheduleId,
            before,
            after
        );
    }

    /**
     * 记录删除日程审计
     */
    public void recordDeleteSchedule(Long scheduleId, String before) {
        recordScheduleOperation(
            BusinessTypeEnum.SCHEDULE,
            OperateTypeEnum.DELETE,
            "删除日程",
            scheduleId,
            before,
            null
        );
    }

    /**
     * 记录会议室预订相关审计
     */
    public void recordMeetingRoomOperation(OperateTypeEnum operateType, Long bookingId, String description, String before, String after) {
        recordScheduleOperation(
            BusinessTypeEnum.MEETING_ROOM,
            operateType,
            description,
            bookingId,
            before,
            after
        );
    }
} 