package com.lawfirm.model.organization.entity.team;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TreeEntity;
import com.lawfirm.model.organization.constants.DepartmentFieldConstants;
import com.lawfirm.model.organization.constants.OrganizationFieldConstants;
import com.lawfirm.model.organization.enums.TeamTypeEnum;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 团队基础实体
 */
@Data
@TableName("org_team")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Team extends TreeEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 所属律所ID
     */
    @TableField(value = "firm_id")
    private Long firmId;

    /**
     * 所属部门ID
     */
    @TableField(value = "department_id")
    private Long departmentId;

    /**
     * 团队类型
     */
    @TableField(value = "type")
    private TeamTypeEnum type;

    /**
     * 团队编码
     */
    @TableField(value = "team_code")
    @Size(max = DepartmentFieldConstants.Team.NAME_MAX_LENGTH, message = "团队编码长度不能超过{max}")
    private String teamCode;

    /**
     * 负责人ID
     */
    @TableField(value = "leader_id")
    private Long leaderId;

    /**
     * 负责人姓名
     */
    @TableField(value = "leader_name")
    @Size(max = 32, message = "负责人姓名长度不能超过{max}")
    private String leaderName;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    @Size(max = OrganizationFieldConstants.Contact.PHONE_MAX_LENGTH, message = "联系电话长度不能超过{max}")
    private String phone;

    /**
     * 联系邮箱
     */
    @TableField(value = "email")
    @Size(max = OrganizationFieldConstants.Contact.EMAIL_MAX_LENGTH, message = "联系邮箱长度不能超过{max}")
    private String email;

    /**
     * 团队规模（人数）
     */
    @TableField(value = "team_size")
    private Integer teamSize;

    /**
     * 团队职责
     */
    @TableField(value = "responsibility")
    @Size(max = 500, message = "团队职责长度不能超过{max}")
    private String responsibility;

    /**
     * 团队状态（0-筹备中 1-运行中 2-已解散）
     */
    @TableField(value = "team_status")
    private Integer teamStatus;
} 
