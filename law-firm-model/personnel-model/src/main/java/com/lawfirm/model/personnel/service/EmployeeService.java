package com.lawfirm.model.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.personnel.dto.employee.CreateEmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.UpdateEmployeeDTO;
import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.vo.EmployeeVO;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工服务接口
 */
public interface EmployeeService extends PersonService {

    /**
     * 创建员工
     *
     * @param createDTO 创建参数
     * @return 员工ID
     */
    Long createEmployee(CreateEmployeeDTO createDTO);

    /**
     * 更新员工
     *
     * @param updateDTO 更新参数
     * @return 是否成功
     */
    boolean updateEmployee(UpdateEmployeeDTO updateDTO);

    /**
     * 获取员工详情
     *
     * @param id 员工ID
     * @return 员工详情
     */
    EmployeeVO getEmployeeDetail(Long id);

    /**
     * 获取员工列表
     *
     * @param departmentId 部门ID
     * @param status 员工状态
     * @return 员工列表
     */
    List<EmployeeVO> listEmployees(Long departmentId, Integer status);

    /**
     * 分页查询员工
     *
     * @param page 分页参数
     * @param departmentId 部门ID
     * @param status 员工状态
     * @param keyword 关键字
     * @return 分页结果
     */
    Page<EmployeeVO> pageEmployees(Page<Employee> page, Long departmentId, Integer status, String keyword);

    /**
     * 员工转正
     *
     * @param id 员工ID
     * @param regularDate 转正日期
     * @return 是否成功
     */
    boolean regularEmployee(Long id, LocalDate regularDate);

    /**
     * 员工离职
     *
     * @param id 员工ID
     * @param resignDate 离职日期
     * @param reason 离职原因
     * @return 是否成功
     */
    boolean resignEmployee(Long id, LocalDate resignDate, String reason);

    /**
     * 调整部门
     *
     * @param id 员工ID
     * @param departmentId 新部门ID
     * @param reason 调整原因
     * @return 是否成功
     */
    boolean changeDepartment(Long id, Long departmentId, String reason);

    /**
     * 调整职位
     *
     * @param id 员工ID
     * @param positionId 新职位ID
     * @param reason 调整原因
     * @return 是否成功
     */
    boolean changePosition(Long id, Long positionId, String reason);

    /**
     * 检查工号是否存在
     *
     * @param workNumber 工号
     * @param excludeId 排除的员工ID
     * @return 是否存在
     */
    boolean checkWorkNumberExists(String workNumber, Long excludeId);

    /**
     * 根据工号获取员工
     *
     * @param workNumber 工号
     * @return 员工信息
     */
    Employee getEmployeeByWorkNumber(String workNumber);

    /**
     * 根据邮箱获取员工
     *
     * @param email 邮箱
     * @return 员工信息
     */
    Employee getEmployeeByEmail(String email);

    /**
     * 根据手机号获取员工
     *
     * @param mobile 手机号
     * @return 员工信息
     */
    Employee getEmployeeByMobile(String mobile);
} 