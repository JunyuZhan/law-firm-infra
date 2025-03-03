package com.lawfirm.model.organization.entity.team;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * 项目组实体
 */
@Data
@TableName("org_project_group")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProjectGroup extends Team {

    private static final long serialVersionUID = 1L;

    /**
     * 项目编号
     */
    @TableField(value = "project_code")
    @Size(max = 32, message = "项目编号长度不能超过{max}")
    private String projectCode;

    /**
     * 项目名称
     */
    @TableField(value = "project_name")
    @Size(max = 128, message = "项目名称长度不能超过{max}")
    private String projectName;

    /**
     * 项目描述
     */
    @TableField(value = "project_desc")
    @Size(max = 500, message = "项目描述长度不能超过{max}")
    private String projectDesc;

    /**
     * 项目开始日期
     */
    @TableField(value = "start_date")
    private transient LocalDate startDate;

    /**
     * 预计结束日期
     */
    @TableField(value = "expected_end_date")
    private transient LocalDate expectedEndDate;

    /**
     * 实际结束日期
     */
    @TableField(value = "actual_end_date")
    private transient LocalDate actualEndDate;

    /**
     * 项目状态（0-未开始 1-进行中 2-已完成 3-已终止）
     */
    @TableField(value = "project_status")
    private Integer projectStatus;

    /**
     * 项目成员ID列表
     */
    @TableField(value = "member_ids")
    private transient List<Long> memberIds;

    /**
     * 关联案件ID列表
     */
    @TableField(value = "case_ids")
    private transient List<Long> caseIds;

    /**
     * 关联客户ID列表
     */
    @TableField(value = "client_ids")
    private transient List<Long> clientIds;

    /**
     * 项目进度（0-100）
     */
    @TableField(value = "progress")
    private Integer progress;

    /**
     * 项目优先级（1-低 2-中 3-高）
     */
    @TableField(value = "priority")
    private Integer priority;
} 