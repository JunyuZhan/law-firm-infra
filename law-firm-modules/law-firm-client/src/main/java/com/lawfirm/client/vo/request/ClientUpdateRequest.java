package com.lawfirm.client.vo.request;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

/**
 * 客户更新请求
 */
@Data
public class ClientUpdateRequest {
    
    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;
    
    /**
     * 客户名称
     */
    private String clientName;
    
    /**
     * 客户类型
     */
    private Integer clientType;
    
    /**
     * 证件类型
     */
    private Integer certificateType;
    
    /**
     * 证件号码
     */
    private String certificateNo;
    
    /**
     * 联系电话
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    /**
     * 电子邮箱
     */
    @Email(message = "邮箱格式不正确")
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
     * 所属律师ID
     */
    private Long lawyerId;
    
    /**
     * 来源渠道
     */
    private Integer sourceChannel;
    
    /**
     * 备注
     */
    private String remark;
} 