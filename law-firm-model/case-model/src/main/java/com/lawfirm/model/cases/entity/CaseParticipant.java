package com.lawfirm.model.cases.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.enums.CaseParticipantTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "case_participant")
public class CaseParticipant extends ModelBaseEntity<CaseParticipant> {

    @NotNull(message = "案件ID不能为空")
    @Column(nullable = false)
    @Comment("案件ID")
    private Long caseId;

    @NotNull(message = "参与人类型不能为空")
    @Column(nullable = false)
    @Comment("参与人类型")
    @Enumerated(EnumType.STRING)
    private CaseParticipantTypeEnum participantType;

    @NotBlank(message = "参与人姓名不能为空")
    @Column(nullable = false, length = 64)
    @Comment("参与人姓名")
    private String name;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Column(length = 11)
    @Comment("手机号")
    private String mobile;

    @Email(message = "邮箱格式不正确")
    @Column(length = 128)
    @Comment("邮箱")
    private String email;

    @Column(length = 18)
    @Comment("身份证号")
    @Pattern(regexp = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)", message = "身份证号格式不正确")
    private String idCard;

    @Column(length = 256)
    @Comment("地址")
    private String address;

    @Column(length = 64)
    @Comment("单位名称")
    private String organization;

    @Column(length = 64)
    @Comment("职务")
    private String position;

    @Column(length = 512)
    @Comment("备注")
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    private Case lawCase;
} 