package com.lawfirm.personnel.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.personnel.dto.EmployeeQueryDTO;
import com.lawfirm.personnel.entity.Employee;
import com.lawfirm.personnel.vo.EmployeeVO;

/**
 * 员工服务接口
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 分页查询员工列表
     *
     * @param queryDTO 查询条件
     * @return 员工列表
     */
    IPage<EmployeeVO> pageEmployees(EmployeeQueryDTO queryDTO);

    /**
     * 获取员工详情
     *
     * @param id 员工ID
     * @return 员工详情
     */
    EmployeeVO getEmployeeDetail(Long id);

    /**
     * 新增员工
     *
     * @param employee 员工信息
     * @return 员工ID
     */
    Long createEmployee(Employee employee);

    /**
     * 更新员工信息
     *
     * @param employee 员工信息
     */
    void updateEmployee(Employee employee);

    /**
     * 删除员工
     *
     * @param id 员工ID
     */
    void deleteEmployee(Long id);

    /**
     * 更新员工状态
     *
     * @param id 员工ID
     * @param status 状态
     */
    void updateEmployeeStatus(Long id, Integer status);
} 