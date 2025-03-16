package com.lawfirm.model.personnel.event;

import com.lawfirm.model.personnel.entity.Employee;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工事件基类
 * 所有员工相关事件都应继承此类
 */
@Getter
@ToString
public abstract class EmployeeEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事件ID
     */
    private final String eventId;

    /**
     * 事件发生时间
     */
    private final LocalDateTime timestamp;

    /**
     * 员工实体
     */
    private final Employee employee;

    /**
     * 员工ID
     */
    private final Long employeeId;

    /**
     * 构造函数
     *
     * @param employee 员工实体
     */
    protected EmployeeEvent(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("员工不能为空");
        }
        
        this.eventId = generateEventId();
        this.timestamp = LocalDateTime.now();
        this.employee = employee;
        this.employeeId = employee.getId();
    }

    /**
     * 获取事件类型
     * 由子类实现，用于标识不同的事件类型
     *
     * @return 事件类型
     */
    public abstract String getEventType();

    /**
     * 生成事件ID
     * 格式：事件类型前缀 + 时间戳 + 随机数
     *
     * @return 事件ID
     */
    private String generateEventId() {
        return getEventType() + "-" + System.currentTimeMillis() + "-" + ((int) (Math.random() * 10000));
    }
} 