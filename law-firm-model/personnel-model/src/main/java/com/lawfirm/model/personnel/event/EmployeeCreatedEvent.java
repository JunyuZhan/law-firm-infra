package com.lawfirm.model.personnel.event;

import com.lawfirm.model.personnel.entity.Employee;

import lombok.Getter;
import lombok.ToString;

/**
 * 员工创建事件
 * 当新员工被创建时触发
 */
@Getter
@ToString(callSuper = true)
public class EmployeeCreatedEvent extends EmployeeEvent {

    private static final long serialVersionUID = 1L;

    /**
     * 事件类型
     */
    public static final String EVENT_TYPE = "EMPLOYEE_CREATED";

    /**
     * 创建途径
     * 例如: 'UI', 'API', 'IMPORT', 'SYSTEM'
     */
    private final String creationSource;

    /**
     * 创建者ID
     */
    private final Long createdBy;

    /**
     * 构造函数
     *
     * @param employee 新创建的员工
     * @param creationSource 创建途径
     * @param createdBy 创建者ID
     */
    public EmployeeCreatedEvent(Employee employee, String creationSource, Long createdBy) {
        super(employee);
        this.creationSource = creationSource;
        this.createdBy = createdBy;
    }

    /**
     * 构造函数（简化版）
     *
     * @param employee 新创建的员工
     */
    public EmployeeCreatedEvent(Employee employee) {
        this(employee, "SYSTEM", null);
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }
} 