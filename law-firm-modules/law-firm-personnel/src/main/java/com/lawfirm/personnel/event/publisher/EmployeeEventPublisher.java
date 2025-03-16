package com.lawfirm.personnel.event.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import com.lawfirm.model.personnel.event.EmployeeCreatedEvent;
import com.lawfirm.model.personnel.event.EmployeeStatusChangedEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * 员工事件发布器
 * 负责发布员工相关的事件
 */
@Slf4j
@Component
public class EmployeeEventPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    /**
     * 发布员工创建事件
     *
     * @param employee 新创建的员工
     * @param creationSource 创建来源
     * @param createdBy 创建者ID
     */
    public void publishEmployeeCreatedEvent(Employee employee, String creationSource, Long createdBy) {
        log.debug("发布员工创建事件: employeeId={}, name={}, source={}", 
                 employee.getId(), employee.getName(), creationSource);
        
        EmployeeCreatedEvent event = new EmployeeCreatedEvent(employee, creationSource, createdBy);
        eventPublisher.publishEvent(event);
    }
    
    /**
     * 发布员工创建事件（简化版）
     *
     * @param employee 新创建的员工
     */
    public void publishEmployeeCreatedEvent(Employee employee) {
        publishEmployeeCreatedEvent(employee, "SYSTEM", null);
    }
    
    /**
     * 发布员工状态变更事件
     *
     * @param employee 员工
     * @param oldStatus 变更前状态
     * @param newStatus 变更后状态
     * @param reason 变更原因
     * @param operatorId 操作人ID
     */
    public void publishEmployeeStatusChangedEvent(Employee employee, EmployeeStatusEnum oldStatus,
                                               EmployeeStatusEnum newStatus, String reason, Long operatorId) {
        log.debug("发布员工状态变更事件: employeeId={}, name={}, oldStatus={}, newStatus={}", 
                 employee.getId(), employee.getName(), oldStatus, newStatus);
        
        EmployeeStatusChangedEvent event = new EmployeeStatusChangedEvent(
                employee, oldStatus, newStatus, reason, operatorId);
        eventPublisher.publishEvent(event);
    }
    
    /**
     * 发布员工状态变更事件（简化版）
     *
     * @param employee 员工
     * @param oldStatus 变更前状态
     * @param newStatus 变更后状态
     */
    public void publishEmployeeStatusChangedEvent(Employee employee, EmployeeStatusEnum oldStatus,
                                               EmployeeStatusEnum newStatus) {
        publishEmployeeStatusChangedEvent(employee, oldStatus, newStatus, null, null);
    }
} 