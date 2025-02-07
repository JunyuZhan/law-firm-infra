package com.lawfirm.model.client.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.client.enums.ClientStatusEnum;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import com.lawfirm.model.client.enums.ClientLevelEnum;
import com.lawfirm.model.client.enums.ClientSourceEnum;
import com.lawfirm.model.client.enums.IdTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDTO extends BaseDTO {
    
    private String clientNumber;  // 客户编号
    
    private String clientName;    // 客户名称
    
    private ClientTypeEnum clientType;  // 客户类型
    
    private ClientStatusEnum status;    // 客户状态
    
    private ClientLevelEnum clientLevel;  // 客户等级
    
    private ClientSourceEnum clientSource;  // 客户来源
    
    private String contactName;   // 联系人姓名
    
    private String contactPhone;  // 联系电话
    
    private String contactEmail;  // 联系邮箱
    
    private IdTypeEnum idType;    // 证件类型
    
    private String idNumber;      // 证件号码
    
    private String province;      // 省份
    
    private String city;          // 城市
    
    private String industry;      // 行业
    
    private String remark;        // 备注
    
    private String operator;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
} 