package com.lawfirm.model.personnel.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.personnel.enums.PersonTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 人员视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PersonVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 人员编号
     */
    private String personCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 英文名
     */
    private String englishName;

    /**
     * 性别（0-未知 1-男 2-女）
     */
    private Integer gender;

    /**
     * 性别描述
     */
    private String genderDesc;

    /**
     * 出生日期
     */
    private LocalDate birthDate;

    /**
     * 证件类型（1-身份证 2-护照 3-其他）
     */
    private Integer idType;

    /**
     * 证件类型描述
     */
    private String idTypeDesc;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 人员类型
     */
    private PersonTypeEnum type;

    /**
     * 所属律所ID
     */
    private Long firmId;

    /**
     * 所属律所名称
     */
    private String firmName;

    /**
     * 紧急联系人
     */
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    private String emergencyMobile;

    /**
     * 照片URL
     */
    private String photoUrl;
} 