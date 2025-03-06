package com.lawfirm.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.organization.entity.Branch;
import com.lawfirm.model.organization.entity.Department;
import com.lawfirm.model.organization.entity.Position;
import com.lawfirm.personnel.dto.EmployeeQueryDTO;
import com.lawfirm.personnel.entity.Employee;
import com.lawfirm.personnel.mapper.EmployeeMapper;
import com.lawfirm.personnel.service.EmployeeService;
import com.lawfirm.personnel.service.OrganizationService;
import com.lawfirm.personnel.utils.EmployeeNoGenerator;
import com.lawfirm.personnel.vo.EmployeeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 员工服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    private final OrganizationService organizationService;
    private final EmployeeNoGenerator employeeNoGenerator;

    @Override
    public IPage<EmployeeVO> pageEmployees(EmployeeQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(queryDTO.getEmployeeNo()), Employee::getEmployeeNo, queryDTO.getEmployeeNo())
                .like(StringUtils.isNotBlank(queryDTO.getName()), Employee::getName, queryDTO.getName())
                .like(StringUtils.isNotBlank(queryDTO.getMobile()), Employee::getMobile, queryDTO.getMobile())
                .eq(Objects.nonNull(queryDTO.getBranchId()), Employee::getBranchId, queryDTO.getBranchId())
                .eq(Objects.nonNull(queryDTO.getDepartmentId()), Employee::getDepartmentId, queryDTO.getDepartmentId())
                .eq(Objects.nonNull(queryDTO.getPositionId()), Employee::getPositionId, queryDTO.getPositionId())
                .eq(Objects.nonNull(queryDTO.getStatus()), Employee::getStatus, queryDTO.getStatus())
                .ge(Objects.nonNull(queryDTO.getEntryDateStart()), Employee::getEntryDate, queryDTO.getEntryDateStart())
                .le(Objects.nonNull(queryDTO.getEntryDateEnd()), Employee::getEntryDate, queryDTO.getEntryDateEnd())
                .orderByDesc(Employee::getCreateTime);

        // 执行分页查询
        Page<Employee> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<Employee> employeePage = page(page, wrapper);

        // 转换为VO
        return employeePage.convert(this::convertToVO);
    }

    @Override
    public EmployeeVO getEmployeeDetail(Long id) {
        Employee employee = getById(id);
        return convertToVO(employee);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createEmployee(Employee employee) {
        // 校验组织信息
        validateEmployeeOrganization(employee);
        
        // 校验手机号唯一性
        validateMobileUnique(employee.getMobile(), null);
        
        // 校验身份证号唯一性
        validateIdCardUnique(employee.getIdCard(), null);
        
        // 生成员工编号
        Branch branch = organizationService.getBranch(employee.getBranchId());
        employee.setEmployeeNo(employeeNoGenerator.generate(branch.getBranchCode()));
        
        // 设置初始状态
        if (employee.getStatus() == null) {
            employee.setStatus(1); // 试用期
        }
        
        // 保存员工信息
        save(employee);
        return employee.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmployee(Employee employee) {
        // 校验组织信息
        validateEmployeeOrganization(employee);
        
        // 校验手机号唯一性
        validateMobileUnique(employee.getMobile(), employee.getId());
        
        // 校验身份证号唯一性
        validateIdCardUnique(employee.getIdCard(), employee.getId());
        
        // 更新员工信息
        updateById(employee);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEmployee(Long id) {
        // 校验员工状态
        Employee employee = getById(id);
        if (employee != null && employee.getStatus() != 3) { // 非离职状态
            throw new IllegalStateException("只能删除离职员工");
        }
        
        // 逻辑删除员工
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmployeeStatus(Long id, Integer status) {
        // 校验状态值
        if (status < 1 || status > 3) {
            throw new IllegalArgumentException("无效的状态值");
        }
        
        Employee employee = new Employee();
        employee.setId(id);
        employee.setStatus(status);
        updateById(employee);
    }

    /**
     * 校验员工组织信息
     */
    private void validateEmployeeOrganization(Employee employee) {
        if (!organizationService.validateOrganization(
                employee.getBranchId(),
                employee.getDepartmentId(),
                employee.getPositionId())) {
            throw new IllegalArgumentException("无效的组织信息");
        }
    }

    /**
     * 校验手机号唯一性
     */
    private void validateMobileUnique(String mobile, Long employeeId) {
        if (StringUtils.isBlank(mobile)) {
            return;
        }
        
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getMobile, mobile)
                .ne(employeeId != null, Employee::getId, employeeId);
        
        if (count(wrapper) > 0) {
            throw new IllegalArgumentException("手机号已存在");
        }
    }

    /**
     * 校验身份证号唯一性
     */
    private void validateIdCardUnique(String idCard, Long employeeId) {
        if (StringUtils.isBlank(idCard)) {
            return;
        }
        
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getIdCard, idCard)
                .ne(employeeId != null, Employee::getId, employeeId);
        
        if (count(wrapper) > 0) {
            throw new IllegalArgumentException("身份证号已存在");
        }
    }

    /**
     * 转换为VO对象
     */
    private EmployeeVO convertToVO(Employee employee) {
        if (employee == null) {
            return null;
        }
        
        EmployeeVO vo = new EmployeeVO();
        BeanUtils.copyProperties(employee, vo);
        
        // 获取组织信息
        Branch branch = organizationService.getBranch(employee.getBranchId());
        if (branch != null) {
            vo.setBranchName(branch.getName());
        }
        
        Department department = organizationService.getDepartment(employee.getDepartmentId());
        if (department != null) {
            vo.setDepartmentName(department.getName());
        }
        
        Position position = organizationService.getPosition(employee.getPositionId());
        if (position != null) {
            vo.setPositionName(position.getName());
        }
        
        return vo;
    }
} 