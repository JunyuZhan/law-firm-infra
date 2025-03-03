package com.lawfirm.model.organization.vo.firm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 分所视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BranchVO extends BaseVO {

    private static final long serialVersionUID = 1L;


    /**
     * 分所编码
     */
    private String code;

    /**
     * 分所名称
     */
    private String name;

    /**
     * 总所ID
     */
    private Long headOfficeId;

    /**
     * 总所名称
     */
    private String headOfficeName;

    /**
     * 执业许可证号
     */
    private String licenseNumber;

    /**
     * 执业许可证有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private transient LocalDate licenseExpireDate;

    /**
     * 许可证状态（1-有效 2-即将过期 3-已过期）
     */
    private Integer licenseStatus;

    /**
     * 许可证状态描述
     */
    private String licenseStatusDesc;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 负责人
     */
    private String manager;

    /**
     * 成立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private transient LocalDate establishDate;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 所在区县
     */
    private String district;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 律师数量
     */
    private Integer lawyerCount;

    /**
     * 员工数量
     */
    private Integer staffCount;

    /**
     * 部门数量
     */
    private Integer departmentCount;

    /**
     * 团队数量
     */
    private Integer teamCount;
} 
