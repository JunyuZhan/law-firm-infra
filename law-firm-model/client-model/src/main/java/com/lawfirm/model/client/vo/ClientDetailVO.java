package com.lawfirm.model.client.vo;

import com.lawfirm.model.client.enums.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientDetailVO {

    private Long id;
    private String clientNumber;
    private String clientName;
    private ClientTypeEnum clientType;
    private ClientStatusEnum status;
    private ClientLevelEnum clientLevel;
    private ClientSourceEnum clientSource;

    // 联系人信息
    private String contactName;
    private String contactPhone;
    private String contactEmail;

    // 证件信息
    private IdTypeEnum idType;
    private String idNumber;

    // 地址信息
    private String province;
    private String city;
    private String address;
    private String postalCode;

    // 行业信息
    private String industry;
    private String description;

    // 时间信息
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 统计信息
    private Integer totalCases;     // 总案件数
    private Integer activeCases;    // 活跃案件数
    private Integer completedCases; // 已完成案件数
} 