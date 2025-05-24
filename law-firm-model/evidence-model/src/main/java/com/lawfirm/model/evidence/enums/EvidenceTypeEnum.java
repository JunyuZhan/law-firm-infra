package com.lawfirm.model.evidence.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "证据类型枚举")
public enum EvidenceTypeEnum {
    @Schema(description = "书证")
    DOCUMENTARY, // 书证
    @Schema(description = "物证")
    PHYSICAL,    // 物证
    @Schema(description = "视听资料")
    AUDIO_VIDEO, // 视听资料
    @Schema(description = "电子数据")
    ELECTRONIC,  // 电子数据
    @Schema(description = "证人证言")
    WITNESS,     // 证人证言
    @Schema(description = "鉴定意见")
    EXPERT,      // 鉴定意见
    @Schema(description = "勘验笔录")
    INSPECTION   // 勘验笔录
} 