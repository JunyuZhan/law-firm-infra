package com.lawfirm.client.vo.request;

import com.lawfirm.common.core.base.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientQueryRequest extends PageParam {
    
    /**
     * 客户编号
     */
    private String clientNo;
    
    /**
     * 客户名称
     */
    private String clientName;
    
    /**
     * 客户类型
     */
    private Integer clientType;
    
    /**
     * 证件号码
     */
    private String certificateNo;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 客户状态
     */
    private Integer status;
    
    /**
     * 所属律师ID
     */
    private Long lawyerId;
    
    /**
     * 来源渠道
     */
    private Integer sourceChannel;
} 