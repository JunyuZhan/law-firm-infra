package com.lawfirm.client.vo.response;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 客户响应
 */
@Data
public class ClientResponse {
    
    /**
     * ID
     */
    private Long id;
    
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
     * 客户类型名称
     */
    private String clientTypeName;
    
    /**
     * 证件类型
     */
    private Integer certificateType;
    
    /**
     * 证件类型名称
     */
    private String certificateTypeName;
    
    /**
     * 证件号码
     */
    private String certificateNo;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 电子邮箱
     */
    private String email;
    
    /**
     * 联系地址
     */
    private String address;
    
    /**
     * 客户状态
     */
    private Integer status;
    
    /**
     * 客户状态名称
     */
    private String statusName;
    
    /**
     * 所属律师ID
     */
    private Long lawyerId;
    
    /**
     * 所属律师姓名
     */
    private String lawyerName;
    
    /**
     * 来源渠道
     */
    private Integer sourceChannel;
    
    /**
     * 来源渠道名称
     */
    private String sourceChannelName;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 