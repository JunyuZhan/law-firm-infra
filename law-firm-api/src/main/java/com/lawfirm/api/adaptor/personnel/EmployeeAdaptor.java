package com.lawfirm.api.adaptor.personnel;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.personnel.dto.employee.CreateEmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.UpdateEmployeeDTO;
import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import com.lawfirm.model.personnel.service.EmployeeService;
import com.lawfirm.model.personnel.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 员工管理适配器
 */
@Component
public class EmployeeAdaptor extends BaseAdaptor {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 创建员工
     */
    public EmployeeVO createEmployee(CreateEmployeeDTO dto) {
        Long id = employeeService.createEmployee(dto);
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return convert(employee, EmployeeVO.class);
    }

    /**
     * 更新员工信息
     */
    public EmployeeVO updateEmployee(Long id, UpdateEmployeeDTO dto) {
        employeeService.updateEmployee(id, dto);
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return convert(employee, EmployeeVO.class);
    }

    /**
     * 删除员工
     */
    public boolean deleteEmployee(Long id) {
        return employeeService.deleteEmployee(id);
    }

    /**
     * 获取员工详情
     */
    public EmployeeVO getEmployee(Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return convert(employee, EmployeeVO.class);
    }

    /**
     * 获取所有员工
     */
    public List<EmployeeVO> listEmployees() {
        Map<String, Object> params = new HashMap<>();
        List<EmployeeDTO> employees = employeeService.listEmployees(params);
        return employees.stream()
                .map(employee -> convert(employee, EmployeeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询员工
     */
    public List<EmployeeVO> getEmployeesByDepartmentId(Long departmentId) {
        List<EmployeeDTO> employees = employeeService.listEmployeesByDepartmentId(departmentId);
        return employees.stream()
                .map(employee -> convert(employee, EmployeeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID和员工类型查询员工
     */
    public List<EmployeeVO> getEmployeesByDepartmentIdAndType(Long departmentId, EmployeeTypeEnum employeeType) {
        List<EmployeeDTO> employees = employeeService.listEmployeesByDepartmentIdAndType(departmentId, employeeType);
        return employees.stream()
                .map(employee -> convert(employee, EmployeeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据职位ID查询员工
     */
    public List<EmployeeVO> getEmployeesByPositionId(Long positionId) {
        List<EmployeeDTO> employees = employeeService.listEmployeesByPositionId(positionId);
        return employees.stream()
                .map(employee -> convert(employee, EmployeeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据工号查询员工
     */
    public EmployeeVO getEmployeeByWorkNumber(String workNumber) {
        EmployeeDTO employee = employeeService.getEmployeeByWorkNumber(workNumber);
        return convert(employee, EmployeeVO.class);
    }

    /**
     * 根据邮箱查询员工
     */
    public EmployeeVO getEmployeeByEmail(String email) {
        EmployeeDTO employee = employeeService.getEmployeeByEmail(email);
        return convert(employee, EmployeeVO.class);
    }

    /**
     * 根据手机号查询员工
     */
    public EmployeeVO getEmployeeByMobile(String mobile) {
        EmployeeDTO employee = employeeService.getEmployeeByMobile(mobile);
        return convert(employee, EmployeeVO.class);
    }

    /**
     * 更新员工状态
     */
    public boolean updateEmployeeStatus(Long id, EmployeeStatusEnum status) {
        return employeeService.updateEmployeeStatus(id, status);
    }

    /**
     * 离职处理
     */
    public boolean resign(Long id, LocalDate resignDate, String reason) {
        return employeeService.resign(id, resignDate, reason);
    }

    /**
     * 批量导入员工
     */
    public boolean batchImport(List<CreateEmployeeDTO> employees) {
        return employeeService.batchImport(employees);
    }
} 