package com.lawfirm.model.client.vo;

import com.lawfirm.common.data.vo.BaseVO;
import com.lawfirm.model.client.enums.ClientStatusEnum;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户VO类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientVO extends BaseVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 客户编号
     */
    private String clientNumber;
    
    /**
     * 客户名称
     */
    private String clientName;
    
    /**
     * 客户类型
     */
    private ClientTypeEnum clientType;
    
    /**
     * 客户状态
     */
    private ClientStatusEnum clientStatus;
    
    /**
     * 证件号码
     */
    private String idNumber;
    
    /**
     * 联系电话
     */
    private String contactPhone;
    
    /**
     * 联系地址
     */
    private String address;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 更新人
     */
    private String updateBy;
} 