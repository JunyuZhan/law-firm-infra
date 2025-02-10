package com.lawfirm.client.vo.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

/**
 * 客户添加请求
 */
@Data
public class ClientAddRequest {
    
    /**
     * 客户编号
     */
    @NotBlank(message = "客户编号不能为空")
    private String clientNo;
    
    /**
     * 客户名称
     */
    @NotBlank(message = "客户名称不能为空")
    private String clientName;
    
    /**
     * 客户类型
     */
    @NotNull(message = "客户类型不能为空")
    private Integer clientType;
    
    /**
     * 证件类型
     */
    @NotNull(message = "证件类型不能为空")
    private Integer certificateType;
    
    /**
     * 证件号码
     */
    @NotBlank(message = "证件号码不能为空")
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
    @NotNull(message = "客户状态不能为空")
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