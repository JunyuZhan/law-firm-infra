package com.lawfirm.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.model.personnel.dto.employee.CreateEmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.UpdateEmployeeDTO;
import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import com.lawfirm.model.personnel.enums.LawyerLevelEnum;
import com.lawfirm.model.personnel.enums.StaffFunctionEnum;
import com.lawfirm.model.personnel.mapper.EmployeeMapper;
import com.lawfirm.model.personnel.service.EmployeeService;
import com.lawfirm.model.personnel.vo.EmployeeVO;
import com.lawfirm.personnel.converter.EmployeeConverter;
import com.lawfirm.common.core.exception.ValidationException;
import com.lawfirm.model.auth.dto.user.UserCreateDTO;
import com.lawfirm.model.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 人事员工服务实现类
 */
@Slf4j
@Service("personnelEmployeeServiceImpl")
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    
    private final EmployeeConverter employeeConverter;
    
    private final ApplicationEventPublisher eventPublisher;
    
    private final ApplicationContext applicationContext;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createEmployee(CreateEmployeeDTO createDTO) {
        // 1. 验证参数
        validateCreateEmployeeDTO(createDTO);
        
        // 2. 检查工号唯一性
        checkWorkNumberUnique(createDTO.getWorkNumber());
        
        // 3. 转换DTO为实体
        Employee employee = employeeConverter.toEntity(createDTO);
        
        // 4. 设置默认值
        if (employee.getEmployeeStatus() == null) {
            employee.setEmployeeStatus(EmployeeStatusEnum.PROBATION);
        }
        
        // 5. 保存员工信息
        employeeMapper.insert(employee);
        
        // 6. 发布员工创建事件
        // eventPublisher.publishEvent(new EmployeeCreatedEvent(employee));
        
        return employee.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateEmployee(Long id, UpdateEmployeeDTO updateDTO) {
        // 1. 验证参数
        if (id == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        // 2. 查询员工信息
        Employee employee = getById(id);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 检查工号唯一性（如果修改了工号）
        if (StringUtils.hasText(updateDTO.getWorkNumber()) 
                && !updateDTO.getWorkNumber().equals(employee.getWorkNumber())) {
            checkWorkNumberUnique(updateDTO.getWorkNumber());
        }
        
        // 4. 转换并更新字段
        Employee updatedEmployee = employeeConverter.updateEntity(employee, updateDTO);
        
        // 5. 执行更新
        employeeMapper.updateById(updatedEmployee);
        
        // 6. 发布员工更新事件
        // eventPublisher.publishEvent(new EmployeeUpdatedEvent(updatedEmployee));
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteEmployee(Long id) {
        // 1. 验证参数
        if (id == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        // 2. 查询员工信息
        Employee employee = getById(id);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 执行逻辑删除
        employeeMapper.deleteById(id);
        
        // 4. 发布员工删除事件
        // eventPublisher.publishEvent(new EmployeeDeletedEvent(employee));
        
        return true;
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = getById(id);
        return employee != null ? employeeConverter.toDTO(employee) : null;
    }

    @Override
    public Employee getById(Long id) {
        if (id == null) {
            return null;
        }
        return employeeMapper.selectById(id);
    }

    @Override
    public List<EmployeeDTO> listEmployees(Map<String, Object> params) {
        // 构建查询条件
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        
        // 添加查询条件
        if (params != null) {
            // 按员工类型查询
            if (params.containsKey("employeeType")) {
                queryWrapper.eq(Employee::getEmployeeType, params.get("employeeType"));
            }
            
            // 按部门ID查询
            if (params.containsKey("departmentId")) {
                queryWrapper.eq(Employee::getDepartmentId, params.get("departmentId"));
            }
            
            // 按状态查询
            if (params.containsKey("status")) {
                queryWrapper.eq(Employee::getEmployeeStatus, params.get("status"));
            }
            
            // 按姓名模糊查询
            if (params.containsKey("name")) {
                queryWrapper.like(Employee::getName, params.get("name"));
            }
        }
        
        // 执行查询
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        
        // 转换结果
        return employees.stream()
                .map(employeeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeByWorkNumber(String workNumber) {
        if (!StringUtils.hasText(workNumber)) {
            return null;
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getWorkNumber, workNumber);
        
        Employee employee = employeeMapper.selectOne(queryWrapper);
        return employee != null ? employeeConverter.toDTO(employee) : null;
    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getEmail, email);
        
        Employee employee = employeeMapper.selectOne(queryWrapper);
        return employee != null ? employeeConverter.toDTO(employee) : null;
    }

    @Override
    public EmployeeDTO getEmployeeByMobile(String mobile) {
        if (!StringUtils.hasText(mobile)) {
            return null;
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getMobile, mobile);
        
        Employee employee = employeeMapper.selectOne(queryWrapper);
        return employee != null ? employeeConverter.toDTO(employee) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateEmployeeStatus(Long id, EmployeeStatusEnum status) {
        // 1. 验证参数
        if (id == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        if (status == null) {
            throw new ValidationException("员工状态不能为空");
        }
        
        // 2. 查询员工信息
        Employee employee = getById(id);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 检查状态变更的合法性
        validateStatusChange(employee.getEmployeeStatus(), status);
        
        // 记录旧状态用于事件发布
        EmployeeStatusEnum oldStatus = employee.getEmployeeStatus();
        
        // 4. 更新状态
        employee.setEmployeeStatus(status);
        employeeMapper.updateById(employee);
        
        // 5. 发布状态变更事件
        // eventPublisher.publishEvent(new EmployeeStatusChangedEvent(employee, oldStatus, status));
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resign(Long id, LocalDate resignDate, String reason) {
        // 1. 验证参数
        if (id == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        if (resignDate == null) {
            throw new ValidationException("离职日期不能为空");
        }
        
        // 2. 查询员工信息
        Employee employee = getById(id);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 设置离职信息
        employee.setEmployeeStatus(EmployeeStatusEnum.RESIGNED);
        employee.setResignDate(resignDate);
        employee.setResignReason(reason);
        
        // 4. 更新员工信息
        employeeMapper.updateById(employee);
        
        // 5. 发布离职事件
        // eventPublisher.publishEvent(new EmployeeResignedEvent(employee, resignDate, reason));
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchImport(List<CreateEmployeeDTO> employees) {
        if (employees == null || employees.isEmpty()) {
            return false;
        }
        
        for (CreateEmployeeDTO employeeDTO : employees) {
            try {
                createEmployee(employeeDTO);
            } catch (Exception e) {
                log.error("批量导入员工失败: {}", employeeDTO, e);
                // 继续处理下一个员工
            }
        }
        
        return true;
    }

    @Override
    public List<EmployeeDTO> listEmployeesByDepartmentId(Long departmentId) {
        if (departmentId == null) {
            throw new ValidationException("部门ID不能为空");
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getDepartmentId, departmentId);
        
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        
        return employees.stream()
                .map(employeeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> listEmployeesByDepartmentIdAndType(Long departmentId, EmployeeTypeEnum employeeType) {
        if (departmentId == null) {
            throw new ValidationException("部门ID不能为空");
        }
        
        if (employeeType == null) {
            throw new ValidationException("员工类型不能为空");
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getDepartmentId, departmentId)
                .eq(Employee::getEmployeeType, employeeType);
        
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        
        return employees.stream()
                .map(employeeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> listEmployeesByPositionId(Long positionId) {
        if (positionId == null) {
            throw new ValidationException("职位ID不能为空");
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getPositionId, positionId);
        
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        
        return employees.stream()
                .map(employeeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> listEmployeesByPositionIdAndType(Long positionId, EmployeeTypeEnum employeeType) {
        if (positionId == null) {
            throw new ValidationException("职位ID不能为空");
        }
        
        if (employeeType == null) {
            throw new ValidationException("员工类型不能为空");
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getPositionId, positionId)
                .eq(Employee::getEmployeeType, employeeType);
        
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        
        return employees.stream()
                .map(employeeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getLawyerByLicenseNumber(String licenseNumber) {
        if (!StringUtils.hasText(licenseNumber)) {
            return null;
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getEmployeeType, EmployeeTypeEnum.LAWYER)
                .eq(Employee::getLicenseNumber, licenseNumber);
        
        Employee lawyer = employeeMapper.selectOne(queryWrapper);
        return lawyer != null ? employeeConverter.toDTO(lawyer) : null;
    }

    @Override
    public List<EmployeeDTO> listLawyersByPracticeArea(String practiceArea) {
        if (!StringUtils.hasText(practiceArea)) {
            throw new ValidationException("专业领域不能为空");
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getEmployeeType, EmployeeTypeEnum.LAWYER)
                .like(Employee::getPracticeAreas, practiceArea);
        
        List<Employee> lawyers = employeeMapper.selectList(queryWrapper);
        
        return lawyers.stream()
                .map(employeeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> listLawyersByLevel(LawyerLevelEnum level) {
        if (level == null) {
            throw new ValidationException("律师级别不能为空");
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getEmployeeType, EmployeeTypeEnum.LAWYER)
                .eq(Employee::getLawyerLevel, level);
        
        List<Employee> lawyers = employeeMapper.selectList(queryWrapper);
        
        return lawyers.stream()
                .map(employeeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLawyerLevel(Long id, LawyerLevelEnum level) {
        // 1. 验证参数
        if (id == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        if (level == null) {
            throw new ValidationException("律师级别不能为空");
        }
        
        // 2. 查询员工信息
        Employee employee = getById(id);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 检查员工类型
        if (employee.getEmployeeType() != EmployeeTypeEnum.LAWYER) {
            throw new ValidationException("该员工不是律师类型");
        }
        
        // 4. 更新级别
        LawyerLevelEnum oldLevel = employee.getLawyerLevel();
        employee.setLawyerLevel(level);
        employeeMapper.updateById(employee);
        
        // 5. 发布级别变更事件
        // eventPublisher.publishEvent(new LawyerLevelChangedEvent(employee, oldLevel, level));
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setPartnerStatus(Long id, boolean isPartner, LocalDate partnerDate, Double equityRatio) {
        // 1. 验证参数
        if (id == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        if (isPartner && partnerDate == null) {
            throw new ValidationException("合伙人日期不能为空");
        }
        
        // 2. 查询员工信息
        Employee employee = getById(id);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 检查员工类型
        if (employee.getEmployeeType() != EmployeeTypeEnum.LAWYER) {
            throw new ValidationException("该员工不是律师类型");
        }
        
        // 4. 更新合伙人状态
        boolean oldStatus = employee.getIsPartner() != null && employee.getIsPartner();
        employee.setIsPartner(isPartner);
        
        if (isPartner) {
            employee.setPartnerDate(partnerDate);
            employee.setEquityRatio(equityRatio);
            
            // 如果成为合伙人，同时更新律师级别为合伙人
            if (employee.getLawyerLevel() != LawyerLevelEnum.PARTNER) {
                employee.setLawyerLevel(LawyerLevelEnum.PARTNER);
            }
        } else {
            employee.setPartnerDate(null);
            employee.setEquityRatio(null);
        }
        
        employeeMapper.updateById(employee);
        
        // 5. 发布合伙人状态变更事件
        // eventPublisher.publishEvent(new LawyerPartnerStatusChangedEvent(employee, oldStatus, isPartner));
        
        return true;
    }

    @Override
    public List<EmployeeDTO> listStaffsByFunctionType(StaffFunctionEnum functionType) {
        if (functionType == null) {
            throw new ValidationException("职能类型不能为空");
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getEmployeeType, EmployeeTypeEnum.STAFF)
                .eq(Employee::getStaffFunction, functionType);
        
        List<Employee> staffs = employeeMapper.selectList(queryWrapper);
        
        return staffs.stream()
                .map(employeeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStaffFunction(Long id, StaffFunctionEnum functionType, String functionDesc) {
        // 1. 验证参数
        if (id == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        if (functionType == null) {
            throw new ValidationException("职能类型不能为空");
        }
        
        // 2. 查询员工信息
        Employee employee = getById(id);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 检查员工类型
        if (employee.getEmployeeType() != EmployeeTypeEnum.STAFF) {
            throw new ValidationException("该员工不是行政人员类型");
        }
        
        // 4. 更新职能类型
        StaffFunctionEnum oldFunction = employee.getStaffFunction();
        employee.setStaffFunction(functionType);
        employee.setFunctionDesc(functionDesc);
        employeeMapper.updateById(employee);
        
        // 5. 发布职能变更事件
        // eventPublisher.publishEvent(new StaffFunctionChangedEvent(employee, oldFunction, functionType));
        
        return true;
    }
    
    /**
     * 检查状态变更的合法性
     */
    private void validateStatusChange(EmployeeStatusEnum oldStatus, EmployeeStatusEnum newStatus) {
        // 实现状态转换的合法性检查逻辑
        // 例如：离职状态不能直接变为在职状态
        if (oldStatus == EmployeeStatusEnum.RESIGNED && newStatus == EmployeeStatusEnum.REGULAR) {
            throw new ValidationException("离职员工不能直接变更为在职状态");
        }
    }
    
    /**
     * 检查工号唯一性
     */
    private void checkWorkNumberUnique(String workNumber) {
        if (!StringUtils.hasText(workNumber)) {
            return;
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getWorkNumber, workNumber);
        
        Long count = employeeMapper.selectCount(queryWrapper);
        if (count != null && count > 0) {
            throw new ValidationException("工号已存在，请更换工号");
        }
    }
    
    /**
     * 验证员工创建参数
     */
    private void validateCreateEmployeeDTO(CreateEmployeeDTO createDTO) {
        if (createDTO == null) {
            throw new ValidationException("员工创建参数不能为空");
        }
        
        if (!StringUtils.hasText(createDTO.getName())) {
            throw new ValidationException("员工姓名不能为空");
        }
        
        if (!StringUtils.hasText(createDTO.getWorkNumber())) {
            throw new ValidationException("工号不能为空");
        }
        
        if (createDTO.getEmployeeType() == null) {
            throw new ValidationException("员工类型不能为空");
        }
        
        // 根据员工类型验证特定字段
        if (createDTO.getEmployeeType() == EmployeeTypeEnum.LAWYER) {
            validateLawyerFields(createDTO);
        } else if (createDTO.getEmployeeType() == EmployeeTypeEnum.STAFF) {
            validateStaffFields(createDTO);
        }
    }
    
    /**
     * 验证律师特有字段
     */
    private void validateLawyerFields(CreateEmployeeDTO createDTO) {
        if (!StringUtils.hasText(createDTO.getLicenseNumber())) {
            throw new ValidationException("执业证号不能为空");
        }
        
        if (createDTO.getLawyerLevel() == null) {
            throw new ValidationException("律师级别不能为空");
        }
    }
    
    /**
     * 验证行政人员特有字段
     */
    private void validateStaffFields(CreateEmployeeDTO createDTO) {
        if (createDTO.getFunctionType() == null) {
            throw new ValidationException("职能类型不能为空");
        }
    }

    /**
     * 创建行政人员
     * 根据DTO中的信息创建一个行政人员类型的员工
     *
     * @param createDTO 行政人员创建参数
     * @return 行政人员ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createStaff(CreateEmployeeDTO createDTO) {
        // 1. 验证参数
        if (createDTO == null) {
            throw new ValidationException("创建行政人员参数不能为空");
        }
        
        // 2. 设置员工类型为行政人员
        createDTO.setEmployeeType(EmployeeTypeEnum.STAFF);
        
        // 3. 验证行政人员特有字段
        if (createDTO.getFunctionType() == null) {
            throw new ValidationException("行政人员职能类型不能为空");
        }
        
        // 4. 调用通用创建员工方法
        return createEmployee(createDTO);
    }

    @Override
    public Employee getEmployeeByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getUserId, userId);
        
        return employeeMapper.selectOne(queryWrapper);
    }
    
    @Override
    public EmployeeDTO getEmployeeDTOByUserId(Long userId) {
        Employee employee = getEmployeeByUserId(userId);
        return employee != null ? employeeConverter.toDTO(employee) : null;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUserAndLinkEmployee(Long employeeId, String username, String password, String email, String mobile) {
        // 1. 验证参数
        if (employeeId == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        if (!StringUtils.hasText(username)) {
            throw new ValidationException("用户名不能为空");
        }
        
        if (!StringUtils.hasText(password)) {
            throw new ValidationException("密码不能为空");
        }
        
        // 2. 检查员工是否存在
        Employee employee = getById(employeeId);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. A检查员工是否已关联用户
        if (employee.getUserId() != null) {
            throw new ValidationException("员工已关联用户账号，无法创建新用户");
        }
        
        // 4. 创建用户账号（通过调用认证模块的接口）
        // 这里假设已经在EmployeeAuthBridgeImpl中注入了UserService
        // 应该调用authBridge服务实现该功能
        // 由于EmployeeAuthBridgeImpl已经实现了createUserFromEmployee方法
        // 此处调用UserService创建用户，并关联到员工
        
        log.info("为员工 ID:{} 创建用户账号", employeeId);
        
        // 委托给 EmployeeAuthBridgeImpl 实现
        // 简化实现，实际情况下应该注入EmployeeAuthBridge接口
        EmployeeAuthBridgeImpl authBridge = applicationContext.getBean(EmployeeAuthBridgeImpl.class);
        
        // 直接使用提供的密码创建账号
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername(username);
        userCreateDTO.setPassword(password);
        userCreateDTO.setRealName(employee.getName());
        
        // 使用传入的email和mobile，如果为空则使用员工现有的
        userCreateDTO.setEmail(StringUtils.hasText(email) ? email : employee.getEmail());
        userCreateDTO.setMobile(StringUtils.hasText(mobile) ? mobile : employee.getMobile());
        
        // 调用认证服务创建用户
        UserService userService = applicationContext.getBean(UserService.class);
        Long userId = userService.createUser(userCreateDTO);
        
        // 关联员工与用户
        boolean linked = authBridge.linkEmployeeWithUser(employeeId, userId);
        
        return linked ? userId : null;
    }
}