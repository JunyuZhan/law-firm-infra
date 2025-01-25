package com.lawfirm.personnel.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 员工查询条件
 */
@Data
public class EmployeeQueryDTO {
    
    /**
     * 员工编号
     */
    private String employeeNo;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 所属分所ID
     */
    private Long branchId;
    
    /**
     * 所属部门ID
     */
    private Long departmentId;
    
    /**
     * 职位ID
     */
    private Long positionId;
    
    /**
     * 员工状态
     */
    private Integer status;
    
    /**
     * 入职开始日期
     */
    private LocalDateTime entryDateStart;
    
    /**
     * 入职结束日期
     */
    private LocalDateTime entryDateEnd;
    
    /**
     * 当前页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页记录数
     */
    private Integer pageSize = 10;
} 