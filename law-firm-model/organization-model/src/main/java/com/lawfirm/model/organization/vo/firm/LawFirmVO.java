package com.lawfirm.model.organization.vo.firm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.organization.enums.FirmTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 律师事务所视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LawFirmVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    
    /**
     * 律所ID
     */
    private Long id;

    /**
     * 律所编码
     */
    private String code;

    /**
     * 律所名称
     */
    private String name;

    /**
     * 律所类型
     */
    private FirmTypeEnum type;

    /**
     * 律所类型描述
     */
    private String typeDesc;

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
     * 法定代表人
     */
    private String legalRepresentative;

    /**
     * 成立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private transient LocalDate establishDate;

    /**
     * 是否总所
     */
    private Boolean headOffice;

    /**
     * 总所信息（如果是分所）
     */
    private transient HeadOfficeInfo headOfficeInfo;

    /**
     * 分所数量（如果是总所）
     */
    private Integer branchCount;

    /**
     * 分所列表（如果是总所）
     */
    private transient List<BranchVO> branches;

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
     * 状态（0-禁用 1-启用）
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private transient LocalDateTime createTime;

    /**
     * 更新时间
     */
    private transient LocalDateTime updateTime;

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

    /**
     * 总所信息
     */
    @Data
    public static class HeadOfficeInfo {
        /**
         * 总所ID
         */
        private Long id;

        /**
         * 总所编码
         */
        private String code;

        /**
         * 总所名称
         */
        private String name;

        /**
         * 总所联系电话
         */
        private String phone;

        /**
         * 总所联系邮箱
         */
        private String email;
    }
} 
