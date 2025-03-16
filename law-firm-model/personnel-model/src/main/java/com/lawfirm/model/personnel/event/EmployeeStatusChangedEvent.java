package com.lawfirm.model.personnel.event;

import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;

import lombok.Getter;
import lombok.ToString;

/**
 * 员工状态变更事件
 * 当员工状态发生变化时触发
 */
@Getter
@ToString(callSuper = true)
public class EmployeeStatusChangedEvent extends EmployeeEvent {

    private static final long serialVersionUID = 1L;

    /**
     * 事件类型
     */
    public static final String EVENT_TYPE = "EMPLOYEE_STATUS_CHANGED";

    /**
     * 变更前状态
     */
    private final EmployeeStatusEnum oldStatus;

    /**
     * 变更后状态
     */
    private final EmployeeStatusEnum newStatus;

    /**
     * 变更原因
     */
    private final String reason;

    /**
     * 操作人ID
     */
    private final Long operatorId;

    /**
     * 构造函数
     *
     * @param employee 状态变更的员工
     * @param oldStatus 变更前状态
     * @param newStatus 变更后状态
     * @param reason 变更原因
     * @param operatorId 操作人ID
     */
    public EmployeeStatusChangedEvent(Employee employee, EmployeeStatusEnum oldStatus, 
                                     EmployeeStatusEnum newStatus, String reason, Long operatorId) {
        super(employee);
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.reason = reason;
        this.operatorId = operatorId;
    }
    
    /**
     * 构造函数（简化版）
     *
     * @param employee 状态变更的员工
     * @param oldStatus 变更前状态
     * @param newStatus 变更后状态
     */
    public EmployeeStatusChangedEvent(Employee employee, EmployeeStatusEnum oldStatus, 
                                     EmployeeStatusEnum newStatus) {
        this(employee, oldStatus, newStatus, null, null);
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }
    
    /**
     * 判断是否是离职事件
     *
     * @return 是否离职
     */
    public boolean isResignation() {
        return newStatus == EmployeeStatusEnum.RESIGNED && oldStatus != EmployeeStatusEnum.RESIGNED;
    }
    
    /**
     * 判断是否是入职事件
     *
     * @return 是否入职
     */
    public boolean isJoining() {
        return (newStatus == EmployeeStatusEnum.REGULAR || newStatus == EmployeeStatusEnum.PROBATION) 
                && (oldStatus == null || oldStatus == EmployeeStatusEnum.SUSPENDED);
    }
} 