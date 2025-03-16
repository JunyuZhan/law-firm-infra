package com.lawfirm.personnel.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.event.EmployeeCreatedEvent;
import com.lawfirm.model.personnel.event.EmployeeStatusChangedEvent;
import com.lawfirm.personnel.config.MessageConfig;
import com.lawfirm.personnel.config.PersonnelConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息通知事件监听器
 * 专注于处理需要发送通知的事件
 */
@Slf4j
@Component
public class NotificationEventListener {

    @Autowired
    private PersonnelConfig personnelConfig;
    
    /**
     * 处理员工创建事件-发送通知
     *
     * @param event 员工创建事件
     */
    @EventListener
    @Order(10) // 确保在主要业务逻辑处理后执行
    public void handleEmployeeCreatedNotification(EmployeeCreatedEvent event) {
        if (!personnelConfig.getNotification().isEnabled()) {
            log.debug("通知功能已禁用，跳过员工创建通知");
            return;
        }
        
        Employee employee = event.getEmployee();
        log.info("准备发送员工入职通知: employeeId={}, name={}", 
                employee.getId(), employee.getName());
        
        // 构建通知内容
        String subject = MessageConfig.NEW_EMPLOYEE_SUBJECT;
        String content = String.format("新员工 %s 已加入，请大家欢迎！", employee.getName());
        
        // 发送通知
        // 实际实现中应该集成邮件服务
        // emailService.sendToHR(subject, content);
        // emailService.sendToDepartment(employee.getDepartmentId(), subject, content);
        
        log.info("员工入职通知已发送: employeeId={}, name={}", 
                employee.getId(), employee.getName());
    }
    
    /**
     * 处理员工状态变更事件-发送通知
     *
     * @param event 员工状态变更事件
     */
    @EventListener
    @Order(10) // 确保在主要业务逻辑处理后执行
    public void handleEmployeeStatusChangedNotification(EmployeeStatusChangedEvent event) {
        if (!personnelConfig.getNotification().isEnabled()) {
            log.debug("通知功能已禁用，跳过员工状态变更通知");
            return;
        }
        
        Employee employee = event.getEmployee();
        
        // 处理离职通知
        if (event.isResignation()) {
            sendResignationNotification(employee, event.getReason());
        }
    }
    
    /**
     * 发送离职通知
     *
     * @param employee 员工
     * @param reason 离职原因
     */
    private void sendResignationNotification(Employee employee, String reason) {
        log.info("准备发送员工离职通知: employeeId={}, name={}", 
                employee.getId(), employee.getName());
        
        // 构建通知内容
        String subject = MessageConfig.EMPLOYEE_RESIGN_SUBJECT;
        String content = String.format("员工 %s 已办理离职手续，离职原因：%s", 
                                      employee.getName(), 
                                      reason != null ? reason : "个人原因");
        
        // 发送通知
        // 实际实现中应该集成邮件服务
        // emailService.sendToHR(subject, content);
        // emailService.sendToDepartment(employee.getDepartmentId(), subject, content);
        
        log.info("员工离职通知已发送: employeeId={}, name={}", 
                employee.getId(), employee.getName());
    }
} 