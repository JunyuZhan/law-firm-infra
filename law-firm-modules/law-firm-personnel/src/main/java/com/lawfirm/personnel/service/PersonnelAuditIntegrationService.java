package com.lawfirm.personnel.service;

import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import com.lawfirm.personnel.config.AuditConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import com.lawfirm.model.organization.entity.base.Position;

/**
 * 人事模块审计集成服务
 * 统一处理与core-audit模块的交互
 */
@Slf4j
@Service("personnelAuditService")
public class PersonnelAuditIntegrationService {

    @Autowired
    @Qualifier("coreAuditServiceImpl")
    private AuditService auditService;

    /**
     * 记录员工创建审计
     *
     * @param employee 员工信息
     */
    public void recordEmployeeCreation(Employee employee) {
        if (employee == null || employee.getId() == null) {
            log.error("员工信息或ID为空，无法记录审计");
            return;
        }

        try {
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule(AuditConfig.MODULE_NAME);
            auditLog.setBusinessType(getBusinessType(AuditConfig.BUSINESS_TYPE_EMPLOYEE));
            auditLog.setDescription("创建员工");
            auditLog.setOperateType(OperateTypeEnum.CREATE);
            auditLog.setId(employee.getId());
            auditLog.setBeforeData(null);
            auditLog.setAfterData(String.format("创建员工：[%s]，工号：[%s]，类型：[%s]",
                    employee.getName(), employee.getWorkNumber(), employee.getEmployeeType()));
            auditLog.setOperationTime(LocalDateTime.now());

            auditService.log(auditLog);
            log.info("记录员工创建审计日志成功: id={}, name={}", employee.getId(), employee.getName());
        } catch (Exception e) {
            log.error("记录员工创建审计日志失败: id={}, name={}, error={}", 
                    employee.getId(), employee.getName(), e.getMessage(), e);
        }
    }

    /**
     * 记录员工更新审计
     *
     * @param beforeEmployee 更新前员工信息
     * @param afterEmployee 更新后员工信息
     */
    public void recordEmployeeUpdate(Employee beforeEmployee, Employee afterEmployee) {
        if (beforeEmployee == null || afterEmployee == null || afterEmployee.getId() == null) {
            log.error("员工信息或ID为空，无法记录审计");
            return;
        }

        try {
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule(AuditConfig.MODULE_NAME);
            auditLog.setBusinessType(getBusinessType(AuditConfig.BUSINESS_TYPE_EMPLOYEE));
            auditLog.setDescription("更新员工信息");
            auditLog.setOperateType(OperateTypeEnum.UPDATE);
            auditLog.setId(afterEmployee.getId());
            auditLog.setBeforeData(String.format("员工原信息：[%s]，工号：[%s]，状态：[%s]",
                    beforeEmployee.getName(), beforeEmployee.getWorkNumber(), beforeEmployee.getEmployeeStatus()));
            auditLog.setAfterData(String.format("员工新信息：[%s]，工号：[%s]，状态：[%s]",
                    afterEmployee.getName(), afterEmployee.getWorkNumber(), afterEmployee.getEmployeeStatus()));
            auditLog.setOperationTime(LocalDateTime.now());

            auditService.log(auditLog);
            log.info("记录员工更新审计日志成功: id={}, name={}", afterEmployee.getId(), afterEmployee.getName());
        } catch (Exception e) {
            log.error("记录员工更新审计日志失败: id={}, name={}, error={}", 
                    afterEmployee.getId(), afterEmployee.getName(), e.getMessage(), e);
        }
    }

    /**
     * 记录员工删除审计
     *
     * @param employee 员工信息
     */
    public void recordEmployeeDeletion(Employee employee) {
        if (employee == null || employee.getId() == null) {
            log.error("员工信息或ID为空，无法记录审计");
            return;
        }

        try {
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule(AuditConfig.MODULE_NAME);
            auditLog.setBusinessType(getBusinessType(AuditConfig.BUSINESS_TYPE_EMPLOYEE));
            auditLog.setDescription("删除员工");
            auditLog.setOperateType(OperateTypeEnum.DELETE);
            auditLog.setId(employee.getId());
            auditLog.setBeforeData(String.format("员工信息：[%s]，工号：[%s]，类型：[%s]",
                    employee.getName(), employee.getWorkNumber(), employee.getEmployeeType()));
            auditLog.setAfterData(null);
            auditLog.setOperationTime(LocalDateTime.now());

            auditService.logAsync(auditLog);
            log.info("记录员工删除审计日志成功: id={}, name={}", employee.getId(), employee.getName());
        } catch (Exception e) {
            log.error("记录员工删除审计日志失败: id={}, name={}, error={}", 
                    employee.getId(), employee.getName(), e.getMessage(), e);
        }
    }

    /**
     * 记录员工状态变更审计
     *
     * @param employee 员工信息
     * @param oldStatus 原状态
     * @param newStatus 新状态
     */
    public void recordEmployeeStatusChange(Employee employee, EmployeeStatusEnum oldStatus, EmployeeStatusEnum newStatus) {
        if (employee == null || employee.getId() == null) {
            log.error("员工信息或ID为空，无法记录审计");
            return;
        }

        try {
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule(AuditConfig.MODULE_NAME);
            auditLog.setBusinessType(getBusinessType(AuditConfig.BUSINESS_TYPE_EMPLOYEE));
            auditLog.setDescription("员工状态变更");
            auditLog.setOperateType(OperateTypeEnum.UPDATE);
            auditLog.setId(employee.getId());
            auditLog.setBeforeData(String.format("员工：[%s]，原状态：[%s]",
                    employee.getName(), oldStatus));
            auditLog.setAfterData(String.format("员工：[%s]，新状态：[%s]",
                    employee.getName(), newStatus));
            auditLog.setOperationTime(LocalDateTime.now());

            auditService.log(auditLog);
            log.info("记录员工状态变更审计日志成功: id={}, name={}, oldStatus={}, newStatus={}", 
                    employee.getId(), employee.getName(), oldStatus, newStatus);
        } catch (Exception e) {
            log.error("记录员工状态变更审计日志失败: id={}, name={}, error={}", 
                    employee.getId(), employee.getName(), e.getMessage(), e);
        }
    }

    /**
     * 记录员工离职审计
     *
     * @param employee 员工信息
     * @param resignDate 离职日期
     * @param reason 离职原因
     */
    public void recordEmployeeResignation(Employee employee, LocalDate resignDate, String reason) {
        if (employee == null || employee.getId() == null) {
            log.error("员工信息或ID为空，无法记录审计");
            return;
        }

        try {
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule(AuditConfig.MODULE_NAME);
            auditLog.setBusinessType(getBusinessType(AuditConfig.BUSINESS_TYPE_EMPLOYEE));
            auditLog.setDescription("员工离职");
            auditLog.setOperateType(OperateTypeEnum.OTHER);
            auditLog.setId(employee.getId());
            auditLog.setBeforeData(String.format("员工：[%s]，工号：[%s]，原状态：[%s]",
                    employee.getName(), employee.getWorkNumber(), employee.getEmployeeStatus()));
            auditLog.setAfterData(String.format("员工：[%s]，离职日期：[%s]，离职原因：[%s]",
                    employee.getName(), resignDate, StringUtils.hasText(reason) ? reason : "未提供"));
            auditLog.setOperationTime(LocalDateTime.now());

            auditService.log(auditLog);
            log.info("记录员工离职审计日志成功: id={}, name={}, resignDate={}", 
                    employee.getId(), employee.getName(), resignDate);
        } catch (Exception e) {
            log.error("记录员工离职审计日志失败: id={}, name={}, error={}", 
                    employee.getId(), employee.getName(), e.getMessage(), e);
        }
    }

    /**
     * 记录职位创建审计
     *
     * @param position 职位信息
     */
    public void recordPositionCreation(Position position) {
        if (position == null || position.getId() == null) {
            log.error("职位信息或ID为空，无法记录审计");
            return;
        }

        try {
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule(AuditConfig.MODULE_NAME);
            auditLog.setBusinessType(getBusinessType(AuditConfig.BUSINESS_TYPE_POSITION));
            auditLog.setDescription("创建职位");
            auditLog.setOperateType(OperateTypeEnum.CREATE);
            auditLog.setId(position.getId());
            auditLog.setBeforeData(null);
            auditLog.setAfterData(String.format("创建职位：[%s]，编码：[%s]，部门：[%s]",
                    position.getName(), position.getCode(), position.getDepartmentId()));
            auditLog.setOperationTime(LocalDateTime.now());

            auditService.log(auditLog);
            log.info("记录职位创建审计日志成功: id={}, name={}", position.getId(), position.getName());
        } catch (Exception e) {
            log.error("记录职位创建审计日志失败: id={}, name={}, error={}", 
                    position.getId(), position.getName(), e.getMessage(), e);
        }
    }

    /**
     * 记录职位更新审计
     *
     * @param beforePosition 更新前职位信息
     * @param afterPosition 更新后职位信息
     */
    public void recordPositionUpdate(Position beforePosition, Position afterPosition) {
        if (beforePosition == null || afterPosition == null || afterPosition.getId() == null) {
            log.error("职位信息或ID为空，无法记录审计");
            return;
        }

        try {
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule(AuditConfig.MODULE_NAME);
            auditLog.setBusinessType(getBusinessType(AuditConfig.BUSINESS_TYPE_POSITION));
            auditLog.setDescription("更新职位");
            auditLog.setOperateType(OperateTypeEnum.UPDATE);
            auditLog.setId(afterPosition.getId());
            auditLog.setBeforeData(String.format("职位原信息：[%s]，编码：[%s]，部门：[%s]",
                    beforePosition.getName(), beforePosition.getCode(), beforePosition.getDepartmentId()));
            auditLog.setAfterData(String.format("职位新信息：[%s]，编码：[%s]，部门：[%s]",
                    afterPosition.getName(), afterPosition.getCode(), afterPosition.getDepartmentId()));
            auditLog.setOperationTime(LocalDateTime.now());

            auditService.log(auditLog);
            log.info("记录职位更新审计日志成功: id={}, name={}", afterPosition.getId(), afterPosition.getName());
        } catch (Exception e) {
            log.error("记录职位更新审计日志失败: id={}, name={}, error={}", 
                    afterPosition.getId(), afterPosition.getName(), e.getMessage(), e);
        }
    }

    /**
     * 记录职位删除审计
     *
     * @param position 职位信息
     */
    public void recordPositionDeletion(Position position) {
        if (position == null || position.getId() == null) {
            log.error("职位信息或ID为空，无法记录审计");
            return;
        }

        try {
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule(AuditConfig.MODULE_NAME);
            auditLog.setBusinessType(getBusinessType(AuditConfig.BUSINESS_TYPE_POSITION));
            auditLog.setDescription("删除职位");
            auditLog.setOperateType(OperateTypeEnum.DELETE);
            auditLog.setId(position.getId());
            auditLog.setBeforeData(String.format("职位信息：[%s]，编码：[%s]，部门：[%s]",
                    position.getName(), position.getCode(), position.getDepartmentId()));
            auditLog.setAfterData(null);
            auditLog.setOperationTime(LocalDateTime.now());

            auditService.logAsync(auditLog);
            log.info("记录职位删除审计日志成功: id={}, name={}", position.getId(), position.getName());
        } catch (Exception e) {
            log.error("记录职位删除审计日志失败: id={}, name={}, error={}", 
                    position.getId(), position.getName(), e.getMessage(), e);
        }
    }

    /**
     * 记录组织结构变更审计
     *
     * @param operationType 操作类型
     * @param description 描述
     * @param businessId 业务ID
     * @param beforeData 变更前数据
     * @param afterData 变更后数据
     */
    public void recordOrganizationOperation(String operationType, String description, Long businessId, 
                                         String beforeData, String afterData) {
        try {
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule(AuditConfig.MODULE_NAME);
            auditLog.setBusinessType(getBusinessType(AuditConfig.BUSINESS_TYPE_ORGANIZATION));
            auditLog.setDescription(description);
            auditLog.setOperateType(getOperateType(operationType));
            auditLog.setId(businessId);
            auditLog.setBeforeData(beforeData);
            auditLog.setAfterData(afterData);
            auditLog.setOperationTime(LocalDateTime.now());

            auditService.log(auditLog);
            log.info("记录组织结构变更审计日志成功: operationType={}, businessId={}", operationType, businessId);
        } catch (Exception e) {
            log.error("记录组织结构变更审计日志失败: operationType={}, businessId={}, error={}", 
                    operationType, businessId, e.getMessage(), e);
        }
    }
    
    /**
     * 根据字符串类型获取业务类型枚举
     * 
     * @param businessType 业务类型字符串
     * @return 业务类型枚举
     */
    private BusinessTypeEnum getBusinessType(String businessType) {
        try {
            return BusinessTypeEnum.valueOf(businessType);
        } catch (IllegalArgumentException e) {
            log.warn("无法找到业务类型枚举: {}, 使用OTHER类型替代", businessType);
            return BusinessTypeEnum.OTHER;
        }
    }
    
    /**
     * 根据字符串类型获取操作类型枚举
     * 
     * @param operationType 操作类型字符串
     * @return 操作类型枚举
     */
    private OperateTypeEnum getOperateType(String operationType) {
        try {
            return OperateTypeEnum.valueOf(operationType);
        } catch (IllegalArgumentException e) {
            log.warn("无法找到操作类型枚举: {}, 使用OTHER类型替代", operationType);
            return OperateTypeEnum.OTHER;
        }
    }
} 