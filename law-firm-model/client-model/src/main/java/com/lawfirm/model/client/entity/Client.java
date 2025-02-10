package com.lawfirm.model.client.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.client.enums.ClientStatusEnum;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 客户实体类
 *
 * @author auto
 * @since 1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_client")
public class Client extends ModelBaseEntity<Client> {

    /**
     * 客户编号
     */
    private String clientNumber;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 客户类型
     */
    private ClientTypeEnum clientType;

    /**
     * 客户状态
     */
    private ClientStatusEnum clientStatus;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 设置备注信息
     *
     * @param remark 备注信息
     * @return 当前对象
     */
    @Override
    public Client setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    /**
     * 设置更新人
     *
     * @param updateBy 更新人
     * @return 当前对象
     */
    @Override
    public Client setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }
} 