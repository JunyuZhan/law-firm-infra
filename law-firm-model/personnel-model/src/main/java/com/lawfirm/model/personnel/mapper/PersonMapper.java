package com.lawfirm.model.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.personnel.entity.Person;

/**
 * 人员基础信息数据访问接口
 */
public interface PersonMapper extends BaseMapper<Person> {

    /**
     * 根据证件号码查询人员
     *
     * @param idNumber 证件号码
     * @return 人员信息
     */
    Person selectByIdNumber(String idNumber);

    /**
     * 根据手机号查询人员
     *
     * @param mobile 手机号
     * @return 人员信息
     */
    Person selectByMobile(String mobile);

    /**
     * 根据邮箱查询人员
     *
     * @param email 邮箱
     * @return 人员信息
     */
    Person selectByEmail(String email);

    /**
     * 根据人员编号查询
     *
     * @param personCode 人员编号
     * @return 人员信息
     */
    Person selectByPersonCode(String personCode);
} 