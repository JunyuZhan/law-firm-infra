package com.lawfirm.model.client.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.client.entity.Client;
import com.lawfirm.model.client.enums.*;
import com.lawfirm.model.base.enums.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户VO对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientVO extends BaseVO {
    
    private String clientNumber;        // 客户编号
    private String clientName;          // 客户名称
    private ClientTypeEnum clientType;  // 客户类型
    private String clientTypeDesc;      // 客户类型描述
    private ClientStatusEnum clientStatus; // 客户状态
    private String clientStatusDesc;    // 客户状态描述
    private StatusEnum status;          // 系统状态
    private String statusDesc;          // 系统状态描述
    private ClientLevelEnum clientLevel;// 客户等级
    private String clientLevelDesc;     // 客户等级描述
    private ClientSourceEnum clientSource; // 客户来源
    private String clientSourceDesc;    // 客户来源描述
    private String contactName;         // 联系人姓名
    private String contactPhone;        // 联系人电话
    private String contactEmail;        // 联系人邮箱
    private IdTypeEnum idType;          // 证件类型
    private String idTypeDesc;          // 证件类型描述
    private String idNumber;            // 证件号码
    private String province;            // 省份
    private String city;                // 城市
    private String address;             // 详细地址
    private String postalCode;          // 邮政编码
    private String industry;            // 所属行业
    private String description;         // 描述信息
    
    public static ClientVO from(Client client) {
        if (client == null) {
            return null;
        }
        
        ClientVO vo = new ClientVO();
        vo.setId(client.getId());
        vo.setClientNumber(client.getClientNumber());
        vo.setClientName(client.getClientName());
        vo.setClientType(client.getClientType());
        vo.setClientTypeDesc(client.getClientType().getDescription());
        vo.setClientStatus(client.getClientStatus());
        vo.setClientStatusDesc(client.getClientStatus().getDescription());
        vo.setStatus(client.getStatus());
        vo.setStatusDesc(client.getStatus().getDescription());
        vo.setClientLevel(client.getClientLevel());
        vo.setClientLevelDesc(client.getClientLevel() != null ? client.getClientLevel().getDescription() : null);
        vo.setClientSource(client.getClientSource());
        vo.setClientSourceDesc(client.getClientSource() != null ? client.getClientSource().getDescription() : null);
        vo.setContactName(client.getContactName());
        vo.setContactPhone(client.getContactPhone());
        vo.setContactEmail(client.getContactEmail());
        vo.setIdType(client.getIdType());
        vo.setIdTypeDesc(client.getIdType() != null ? client.getIdType().getDescription() : null);
        vo.setIdNumber(client.getIdNumber());
        vo.setProvince(client.getProvince());
        vo.setCity(client.getCity());
        vo.setAddress(client.getAddress());
        vo.setPostalCode(client.getPostalCode());
        vo.setIndustry(client.getIndustry());
        vo.setDescription(client.getDescription());
        vo.setCreateTime(client.getCreateTime());
        vo.setUpdateTime(client.getUpdateTime());
        return vo;
    }
} 