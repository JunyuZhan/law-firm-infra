package com.lawfirm.model.client.query;

import com.lawfirm.common.data.query.PageQuery;
import com.lawfirm.model.client.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientQuery extends PageQuery {

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
} 