package com.lawfirm.personnel.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.model.organization.entity.Branch;
import com.lawfirm.model.organization.entity.Department;
import com.lawfirm.model.organization.entity.Position;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 员工实体
 */
@Data
@TableName("personnel_employee")
public class Employee {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 员工编号
     */
    private String employeeNo;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 性别（0-女，1-男）
     */
    private Integer gender;
    
    /**
     * 出生日期
     */
    private LocalDateTime birthday;
    
    /**
     * 身份证号
     */
    private String idCard;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 所属分所ID
     * @see Branch#getId()
     */
    private Long branchId;
    
    /**
     * 所属部门ID
     * @see Department#getId()
     */
    private Long departmentId;
    
    /**
     * 职位ID
     * @see Position#getId()
     */
    private Long positionId;
    
    /**
     * 入职日期
     */
    private LocalDateTime entryDate;
    
    /**
     * 转正日期
     */
    private LocalDateTime regularDate;
    
    /**
     * 离职日期
     */
    private LocalDateTime leaveDate;
    
    /**
     * 员工状态（1-试用期 2-正式 3-离职）
     */
    private Integer status;
    
    /**
     * 紧急联系人
     */
    private String emergencyContact;
    
    /**
     * 紧急联系人电话
     */
    private String emergencyPhone;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    
    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;
} 