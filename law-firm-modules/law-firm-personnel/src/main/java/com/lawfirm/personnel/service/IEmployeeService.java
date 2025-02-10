package com.lawfirm.personnel.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.personnel.entity.Employee;
import com.lawfirm.personnel.dto.request.EmployeeAddRequest;
import com.lawfirm.personnel.dto.request.EmployeeUpdateRequest;
import com.lawfirm.personnel.dto.response.EmployeeResponse;

public interface IEmployeeService extends IService<Employee> {
    
    /**
     * 添加员工
     */
    Long addEmployee(EmployeeAddRequest request);
    
    /**
     * 更新员工信息
     */
    void updateEmployee(EmployeeUpdateRequest request);
    
    /**
     * 删除员工
     */
    void deleteEmployee(Long id);
    
    /**
     * 获取员工详情
     */
    EmployeeResponse getEmployeeById(Long id);
    
    /**
     * 分页查询员工列表
     */
    IPage<EmployeeResponse> pageEmployees(Integer pageNum, Integer pageSize, String name, Integer gender, 
                                        Long branchId, Long departmentId, Long positionId, Integer status);
    
    /**
     * 员工转正
     */
    void regularEmployee(Long id, LocalDate regularDate);
    
    /**
     * 员工离职
     */
    void leaveEmployee(Long id, LocalDate leaveDate);
} 