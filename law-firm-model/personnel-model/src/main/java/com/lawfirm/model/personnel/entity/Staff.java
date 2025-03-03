package com.lawfirm.model.personnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.personnel.constant.PersonnelConstant;
import com.lawfirm.model.personnel.enums.CenterTypeEnum;
import com.lawfirm.model.personnel.enums.FunctionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 行政人员信息实体
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(PersonnelConstant.Table.STAFF)
public class Staff extends Employee {

    private static final long serialVersionUID = 1L;

    /**
     * 职能类型
     */
    @TableField("function_type")
    private FunctionTypeEnum functionType;

    /**
     * 职能描述
     */
    @TableField("function_desc")
    private String functionDesc;

    /**
     * 工作职责
     */
    @TableField("job_duties")
    private String jobDuties;

    /**
     * 服务范围
     */
    @TableField("service_scope")
    private String serviceScope;

    /**
     * 技能证书
     */
    @TableField("skill_certificates")
    private String skillCertificates;

    /**
     * 所属中心
     */
    @TableField("center_type")
    private CenterTypeEnum centerType;

    /**
     * 是否兼任其他职能
     */
    @TableField("has_other_functions")
    private Boolean hasOtherFunctions;

    /**
     * 兼任职能描述
     */
    @TableField("other_functions")
    private String otherFunctions;
} 