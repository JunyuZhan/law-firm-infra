package com.lawfirm.model.personnel.entity;

import com.lawfirm.model.personnel.enums.LawyerTitleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "lawyer")
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "employee_id")
public class Lawyer extends Employee {

    @NotNull(message = "律师职称不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LawyerTitleEnum title;  // 律师职称

    @Size(max = 50, message = "执业证号长度不能超过50个字符")
    @Column(length = 50)
    private String licenseNumber;  // 执业证号

    private LocalDate licenseDate;  // 执业证取得日期

    @Size(max = 100, message = "执业机构长度不能超过100个字符")
    @Column(length = 100)
    private String practicingInstitution;  // 执业机构

    @Size(max = 500, message = "专业领域长度不能超过500个字符")
    @Column(length = 500)
    private String specialties;  // 专业领域

    @Size(max = 500, message = "执业经历长度不能超过500个字符")
    @Column(length = 500)
    private String experience;  // 执业经历

    private Boolean isPartner = false;  // 是否合伙人

    @Size(max = 20, message = "执业状态长度不能超过20个字符")
    @Column(length = 20)
    private String practiceStatus;  // 执业状态

    private LocalDate partnerDate;  // 成为合伙人日期

    @Size(max = 500, message = "资质认证长度不能超过500个字符")
    @Column(length = 500)
    private String qualifications;  // 资质认证
} 