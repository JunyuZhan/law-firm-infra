package com.lawfirm.model.cases.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.cases.enums.CaseParticipantTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CaseParticipantDTO extends BaseDTO {

    private Long id;
    
    @NotNull(message = "案件ID不能为空")
    private Long caseId;
    
    @NotNull(message = "参与人类型不能为空")
    private CaseParticipantTypeEnum participantType;
    
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
    
    private String remark;

    @Override
    public CaseParticipantDTO setId(Long id) {
        super.setId(id);
        return this;
    }
    
    @Override
    public CaseParticipantDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
    
    @Override
    public CaseParticipantDTO setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }
    
    @Override
    public CaseParticipantDTO setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }
} 