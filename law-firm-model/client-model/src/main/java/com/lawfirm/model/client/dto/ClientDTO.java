package com.lawfirm.model.client.dto;

import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.client.enums.ClientStatusEnum;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import com.lawfirm.model.client.enums.ClientLevelEnum;
import com.lawfirm.model.client.enums.ClientSourceEnum;
import com.lawfirm.model.client.enums.IdTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 客户数据传输对象
 *
 * @author auto
 * @since 1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ClientDTO extends BaseDTO {
    
    /** 客户编号 */
    private String clientNumber;
    
    /** 客户名称 */
    private String clientName;
    
    /** 客户类型 */
    private ClientTypeEnum clientType;
    
    /** 客户状态 */
    private ClientStatusEnum clientStatus;
    
    /** 客户等级 */
    private ClientLevelEnum clientLevel;
    
    /** 客户来源 */
    private ClientSourceEnum clientSource;
    
    /** 联系人姓名 */
    private String contactName;
    
    /** 联系电话 */
    private String contactPhone;
    
    /** 联系邮箱 */
    private String contactEmail;
    
    /** 证件类型 */
    private IdTypeEnum idType;
    
    /** 证件号码 */
    private String idNumber;
    
    /** 省份 */
    private String province;
    
    /** 城市 */
    private String city;
    
    /** 行业 */
    private String industry;
    
    /** 备注 */
    private String remark;
    
    /** 操作人 */
    private String operator;
    
    /** 创建时间 */
    private LocalDateTime createTime;
    
    /** 更新时间 */
    private LocalDateTime updateTime;

    /**
     * 获取状态
     *
     * @return 状态枚举
     */
    @Override
    public StatusEnum getStatus() {
        return super.getStatus();
    }

    /**
     * 设置状态
     *
     * @param status 状态枚举
     * @return 当前对象
     */
    @Override
    public BaseDTO setStatus(StatusEnum status) {
        return super.setStatus(status);
    }
} 