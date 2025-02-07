package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户DTO")
public class SysUserDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 性别（0:未知 1:男 2:女）
     */
    @Schema(description = "性别（0:未知 1:男 2:女）")
    private Integer sex;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 角色ID列表
     */
    @Schema(description = "角色ID列表")
    private List<Long> roleIds;

    /**
     * 角色名称列表
     */
    @Schema(description = "角色名称列表")
    private List<String> roleNames;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private String loginTime;

    /**
     * 状态（0:禁用 1:正常）
     */
    @Schema(description = "状态（0:禁用 1:正常）")
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
} 