// 路径: law-firm-api/admin-api/src/main/java/com/lawfirm/admin/model/response/system/post/PostResponse.java
package com.lawfirm.admin.model.response.system.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "岗位响应")
public class PostResponse {

    @Schema(description = "岗位ID")
    private Long id;

    @Schema(description = "岗位编码")
    private String postCode;

    @Schema(description = "岗位名称")
    private String postName;

    @Schema(description = "显示顺序")
    private Integer orderNum;

    @Schema(description = "状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}