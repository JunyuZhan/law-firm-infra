package com.lawfirm.model.personnel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "staff")
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "employee_id")
public class Staff extends Employee {

    @Size(max = 100, message = "工作职责长度不能超过100个字符")
    @Column(length = 100)
    private String jobDuties;  // 工作职责

    @Size(max = 100, message = "工作范围长度不能超过100个字符")
    @Column(length = 100)
    private String workScope;  // 工作范围

    @Size(max = 50, message = "教育背景长度不能超过50个字符")
    @Column(length = 50)
    private String education;  // 教育背景

    @Size(max = 50, message = "专业长度不能超过50个字符")
    @Column(length = 50)
    private String major;  // 专业

    @Size(max = 500, message = "工作经历长度不能超过500个字符")
    @Column(length = 500)
    private String workExperience;  // 工作经历

    @Size(max = 500, message = "技能证书长度不能超过500个字符")
    @Column(length = 500)
    private String skillsCertificates;  // 技能证书

    private LocalDate contractStartDate;  // 合同开始日期

    private LocalDate contractEndDate;  // 合同结束日期

    @Size(max = 20, message = "合同类型长度不能超过20个字符")
    @Column(length = 20)
    private String contractType;  // 合同类型

    @Size(max = 500, message = "绩效记录长度不能超过500个字符")
    @Column(length = 500)
    private String performanceRecord;  // 绩效记录
} 