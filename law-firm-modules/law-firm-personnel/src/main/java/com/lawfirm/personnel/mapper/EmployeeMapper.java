package com.lawfirm.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.personnel.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工Mapper接口
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
} 