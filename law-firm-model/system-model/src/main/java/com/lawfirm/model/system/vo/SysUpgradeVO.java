package com.lawfirm.model.system.vo;

import com.lawfirm.common.data.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "系统升级VO")
@EqualsAndHashCode(callSuper = true)
public class SysUpgradeVO extends BaseVO {

    @Schema(description = "升级包名称")
    private String name;

    @Schema(description = "升级包版本")
    private String version;

    @Schema(description = "升级包路径")
    private String path;

    @Schema(description = "升级包大小（字节）")
    private Long size;

    @Schema(description = "升级包MD5")
    private String md5;

    @Schema(description = "升级包描述")
    private String description;

    @Schema(description = "升级状态（0-未升级，1-升级中，2-升级成功，3-升级失败）")
    private Integer status;

    @Schema(description = "升级日志")
    private String log;

    @Schema(description = "升级时间")
    private Long upgradeTime;

    @Schema(description = "备注")
    private String remark;
} 