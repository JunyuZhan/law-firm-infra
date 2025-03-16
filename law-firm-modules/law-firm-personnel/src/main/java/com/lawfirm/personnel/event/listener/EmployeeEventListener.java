package com.lawfirm.personnel.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.event.EmployeeCreatedEvent;
import com.lawfirm.model.personnel.event.EmployeeStatusChangedEvent;
import com.lawfirm.personnel.config.PersonnelConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 员工事件监听器
 * 处理员工相关事件并执行相应的业务逻辑
 */
@Slf4j
@Component
public class EmployeeEventListener {

    @Autowired
    private PersonnelConfig personnelConfig;
    
    @Value("${personnel.notification.enabled:true}")
    private boolean notificationEnabled;
    
    /**
     * 处理员工创建事件
     *
     * @param event 员工创建事件
     */
    @EventListener
    public void handleEmployeeCreatedEvent(EmployeeCreatedEvent event) {
        Employee employee = event.getEmployee();
        log.info("接收到员工创建事件: employeeId={}, name={}, source={}", 
                employee.getId(), employee.getName(), event.getCreationSource());
        
        // 在这里实现员工创建后的业务逻辑
        // 例如：同步到其他系统、发送欢迎通知等
        
        if (notificationEnabled) {
            sendWelcomeNotification(employee);
        }
    }
    
    /**
     * 处理员工状态变更事件
     *
     * @param event 员工状态变更事件
     */
    @EventListener
    public void handleEmployeeStatusChangedEvent(EmployeeStatusChangedEvent event) {
        Employee employee = event.getEmployee();
        log.info("接收到员工状态变更事件: employeeId={}, name={}, oldStatus={}, newStatus={}", 
                employee.getId(), employee.getName(), event.getOldStatus(), event.getNewStatus());
        
        // 在这里实现员工状态变更后的业务逻辑
        
        // 处理离职逻辑
        if (event.isResignation()) {
            handleEmployeeResignation(employee, event.getReason());
        }
        
        // 处理入职逻辑
        if (event.isJoining()) {
            handleEmployeeJoining(employee);
        }
    }
    
    /**
     * 发送欢迎通知
     *
     * @param employee 员工
     */
    private void sendWelcomeNotification(Employee employee) {
        log.info("发送员工欢迎通知: employeeId={}, name={}, email={}", 
                employee.getId(), employee.getName(), employee.getEmail());
        
        // 这里应该集成邮件服务发送欢迎邮件
        // 例如：emailService.sendWelcomeEmail(employee.getEmail(), employee.getName());
    }
    
    /**
     * 处理员工离职逻辑
     *
     * @param employee 员工
     * @param reason 离职原因
     */
    private void handleEmployeeResignation(Employee employee, String reason) {
        log.info("处理员工离职逻辑: employeeId={}, name={}, reason={}", 
                employee.getId(), employee.getName(), reason);
        
        // 这里实现员工离职后的业务逻辑
        // 例如：禁用账号、移交工作等
    }
    
    /**
     * 处理员工入职逻辑
     *
     * @param employee 员工
     */
    private void handleEmployeeJoining(Employee employee) {
        log.info("处理员工入职逻辑: employeeId={}, name={}", 
                employee.getId(), employee.getName());
        
        // 这里实现员工入职后的业务逻辑
        // 例如：分配工作空间、添加到部门群组等
    }
} 