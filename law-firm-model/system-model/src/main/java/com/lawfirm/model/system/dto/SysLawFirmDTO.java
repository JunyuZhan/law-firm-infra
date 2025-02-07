package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 律师事务所DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLawFirmDTO extends BaseDTO {

    /**
     * 事务所名称
     */
    private String name;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 法定代表人
     */
    private String legalRepresentative;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态（0-待审核 1-正常 2-禁用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
} 