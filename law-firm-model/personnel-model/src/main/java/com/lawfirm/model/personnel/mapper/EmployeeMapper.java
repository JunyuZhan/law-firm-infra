package com.lawfirm.model.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.personnel.entity.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * 员工数据访问接口
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 根据工号查询员工
     *
     * @param workNumber 工号
     * @return 员工信息
     */
    Employee selectByWorkNumber(String workNumber);

    /**
     * 根据邮箱查询员工
     *
     * @param email 邮箱
     * @return 员工信息
     */
    Employee selectByEmail(String email);

    /**
     * 根据手机号查询员工
     *
     * @param mobile 手机号
     * @return 员工信息
     */
    Employee selectByMobile(String mobile);

    /**
     * 根据工号查询员工
     */
    @Select("SELECT * FROM employee WHERE work_number = #{username}")
    Employee selectByUsername(String username);

    /**
     * 根据手机号查询员工
     */
    @Select("SELECT * FROM employee WHERE mobile = #{phone}")
    Employee selectByPhone(String phone);
} 