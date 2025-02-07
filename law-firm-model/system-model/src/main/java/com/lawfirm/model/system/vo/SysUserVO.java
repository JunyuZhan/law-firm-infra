package com.lawfirm.model.system.vo;

import com.lawfirm.common.data.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统用户VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统用户VO")
public class SysUserVO extends BaseVO {
    
    @Schema(description = "用户ID")
    private Long id;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "昵称")
    private String nickname;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "手机号")
    private String mobile;
    
    @Schema(description = "性别(0:未知 1:男 2:女)")
    private Integer gender;
    
    @Schema(description = "头像")
    private String avatar;
    
    @Schema(description = "部门ID")
    private Long deptId;
    
    @Schema(description = "部门名称")
    private String deptName;
    
    @Schema(description = "状态(0:禁用 1:正常)")
    private Integer status;
    
    @Schema(description = "最后登录IP")
    private String loginIp;
    
    @Schema(description = "最后登录时间")
    private LocalDateTime loginTime;
    
    @Schema(description = "创建时间")
    @Override
    public Long getCreateTime() {
        return super.getCreateTime();
    }
    
    @Schema(description = "更新时间")
    @Override
    public Long getUpdateTime() {
        return super.getUpdateTime();
    }
    
    @Schema(description = "创建时间(格式化)")
    private LocalDateTime createDateTime;
    
    @Schema(description = "更新时间(格式化)")
    private LocalDateTime updateDateTime;
    
    @Schema(description = "备注")
    private String remark;
} 