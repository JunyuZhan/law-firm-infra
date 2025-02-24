package com.lawfirm.model.cases.dto.team;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.team.CaseParticipantTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件参与者DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseParticipantDTO extends BaseDTO {

    /**
     * 案件ID
     */
    @NotNull(message = "案件ID不能为空")
    private Long caseId;

    /**
     * 参与者ID
     */
    @NotNull(message = "参与者ID不能为空")
    private Long participantId;

    /**
     * 参与者类型（当事人、证人、专家等）
     */
    @NotBlank(message = "参与者类型不能为空")
    private String participantType;

    /**
     * 参与者角色（原告、被告、第三人等）
     */
    @NotBlank(message = "参与者角色不能为空")
    private String participantRole;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 备注
     */
    private String remark;

    @NotBlank(message = "参与人姓名不能为空")
    private String name;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Pattern(regexp = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)", message = "身份证号格式不正确")
    private String idCard;
    
    private String address;
    
    private String organization;
    
    private String position;
    
    private String createBy;
    
    private LocalDateTime createTime;
    
    private String updateBy;
    
    private LocalDateTime updateTime;
} 