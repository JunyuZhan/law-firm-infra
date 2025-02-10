package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.common.core.enums.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

/**
 * 律师事务所DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
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

    @Override
    public StatusEnum getStatus() {
        return super.getStatus();
    }

    @Override
    public SysLawFirmDTO setStatus(StatusEnum status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public SysLawFirmDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public SysLawFirmDTO setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public SysLawFirmDTO setSort(Integer sort) {
        super.setSort(sort);
        return this;
    }
} 