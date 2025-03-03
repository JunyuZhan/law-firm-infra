package com.lawfirm.model.personnel.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 行政人员视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class StaffVO extends EmployeeVO {

    private static final long serialVersionUID = 1L;

    /**
     * 职能类型（1-人事 2-财务 3-行政 4-IT 5-其他）
     */
    private Integer functionType;

    /**
     * 职能类型描述
     */
    private String functionTypeDesc;

    /**
     * 职能描述
     */
    private String functionDesc;

    /**
     * 工作职责
     */
    private String jobDuties;

    /**
     * 服务范围
     */
    private String serviceScope;

    /**
     * 技能证书
     */
    private String skillCertificates;

    /**
     * 所属中心（1-人力资源中心 2-财务中心 3-行政中心 4-IT中心）
     */
    private Integer centerType;

    /**
     * 所属中心描述
     */
    private String centerTypeDesc;

    /**
     * 是否兼任其他职能
     */
    private Boolean hasOtherFunctions;

    /**
     * 兼任职能描述
     */
    private String otherFunctions;
} 