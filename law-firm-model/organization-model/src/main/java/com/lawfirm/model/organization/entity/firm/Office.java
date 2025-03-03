package com.lawfirm.model.organization.entity.firm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.organization.constants.FirmFieldConstants;
import com.lawfirm.model.organization.constants.OrganizationFieldConstants;
import com.lawfirm.model.organization.entity.base.BaseOrganizationEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 办公室实体
 */
@Data
@TableName("org_office")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Office extends BaseOrganizationEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 所属律所ID
     */
    @TableField(value = "firm_id")
    private Long firmId;

    /**
     * 所在楼层
     */
    @TableField(value = "floor")
    private String floor;

    /**
     * 房间号
     */
    @TableField(value = "room_number")
    private String roomNumber;

    /**
     * 面积（平方米）
     */
    @TableField(value = "area")
    private Double area;

    /**
     * 容纳人数
     */
    @TableField(value = "capacity")
    @Min(value = FirmFieldConstants.Office.MIN_CAPACITY, message = "容纳人数不能小于{value}")
    @Max(value = FirmFieldConstants.Office.MAX_CAPACITY, message = "容纳人数不能大于{value}")
    private Integer capacity;

    /**
     * 使用状态（0-空闲 1-使用中 2-维护中）
     */
    @TableField(value = "use_status")
    private Integer useStatus;

    /**
     * 使用部门ID
     */
    @TableField(value = "department_id")
    private Long departmentId;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    @Size(max = OrganizationFieldConstants.Contact.PHONE_MAX_LENGTH, message = "联系电话长度不能超过{max}")
    private String phone;

    /**
     * 设施说明
     */
    @TableField(value = "facilities")
    @Size(max = 500, message = "设施说明长度不能超过{max}")
    private String facilities;

    /**
     * 所在省份
     */
    @TableField(value = "province")
    private String province;

    /**
     * 所在城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 所在区县
     */
    @TableField(value = "district")
    private String district;

    /**
     * 详细地址
     */
    @TableField(value = "address")
    @Size(max = OrganizationFieldConstants.Contact.ADDRESS_MAX_LENGTH, message = "详细地址长度不能超过{max}")
    private String address;
} 
