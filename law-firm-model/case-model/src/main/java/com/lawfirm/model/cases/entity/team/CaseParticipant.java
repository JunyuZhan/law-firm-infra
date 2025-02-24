package com.lawfirm.model.cases.entity.team;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.enums.team.ParticipantTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.persistence.*;

/**
 * 案件参与方实体
 */
@Data
@Entity
@Table(name = "case_participant")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseParticipant extends ModelBaseEntity {

    /**
     * 关联案件
     */
    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private Case case;

    /**
     * 参与方类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipantTypeEnum type;

    /**
     * 参与方名称
     */
    @Column(length = 64, nullable = false)
    private String name;

    /**
     * 联系人
     */
    @Column(length = 32)
    private String contactPerson;

    /**
     * 联系电话
     */
    @Column(length = 32)
    private String contactPhone;

    /**
     * 电子邮箱
     */
    @Column(length = 64)
    private String email;

    /**
     * 通讯地址
     */
    @Column(length = 256)
    private String address;

    /**
     * 证件类型
     */
    @Column(length = 32)
    private String idType;

    /**
     * 证件号码
     */
    @Column(length = 32)
    private String idNumber;

    /**
     * 法定代表人（如果是组织）
     */
    @Column(length = 32)
    private String legalRepresentative;

    /**
     * 委托代理人
     */
    @Column(length = 32)
    private String attorney;

    /**
     * 参与方说明
     */
    @Column(length = 500)
    private String description;

    /**
     * 是否是主要参与方
     */
    private Boolean isPrimary;

    /**
     * 是否需要送达
     */
    private Boolean needService;

    /**
     * 送达方式
     */
    @Column(length = 32)
    private String serviceMethod;

    /**
     * 送达地址
     */
    @Column(length = 256)
    private String serviceAddress;
} 