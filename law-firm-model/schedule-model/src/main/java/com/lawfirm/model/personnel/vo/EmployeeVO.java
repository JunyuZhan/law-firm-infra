package com.lawfirm.model.personnel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 员工VO
 */
@Data
@Schema(description = "员工VO")
public class EmployeeVO {

    @Schema(description = "员工ID")
    private Long id;

    @Schema(description = "员工姓名")
    private String name;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "部门名称")
    private String departmentName;

    @Schema(description = "职位ID")
    private Long positionId;

    @Schema(description = "职位名称")
    private String positionName;

    @Schema(description = "员工头像URL")
    private String avatar;

    /**
     * 获取员工头像URL
     * @return 员工头像URL
     */
    public String getAvatar() {
        return this.avatar;
    }

    /**
     * 设置员工头像URL
     * @param avatar 员工头像URL
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
} 