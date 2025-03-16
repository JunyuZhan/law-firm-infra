package com.lawfirm.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.model.auth.dto.user.UserCreateDTO;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.model.auth.vo.UserVO;
import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import com.lawfirm.model.personnel.mapper.EmployeeMapper;
import com.lawfirm.model.personnel.service.EmployeeAuthBridge;
import com.lawfirm.model.personnel.service.EmployeeService;
import com.lawfirm.personnel.converter.EmployeeConverter;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工与认证系统桥接服务实现类
 * 实现personnel-model的EmployeeAuthBridge接口
 */
@Slf4j
@Service
public class EmployeeAuthBridgeImpl implements EmployeeAuthBridge {

    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmployeeConverter employeeConverter;

    @Override
    public Long getEmployeeIdByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getUserId, userId);
        
        Employee employee = employeeMapper.selectOne(queryWrapper);
        return employee != null ? employee.getId() : null;
    }

    @Override
    public Long getUserIdByEmployeeId(Long employeeId) {
        if (employeeId == null) {
            return null;
        }
        
        Employee employee = employeeService.getById(employeeId);
        return employee != null ? employee.getUserId() : null;
    }

    @Override
    public List<String> getEmployeeRoleCodes(Long employeeId) {
        Long userId = getUserIdByEmployeeId(employeeId);
        if (userId == null) {
            return List.of();
        }
        
        // 通过UserService获取用户权限，过滤出角色代码
        return userService.getUserPermissions(userId).stream()
                .filter(perm -> perm.startsWith("ROLE_"))
                .map(role -> role.substring(5))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getEmployeePermissionCodes(Long employeeId) {
        Long userId = getUserIdByEmployeeId(employeeId);
        if (userId == null) {
            return List.of();
        }
        
        return userService.getUserPermissions(userId);
    }
    
    @Override
    public boolean hasPermission(Long employeeId, String permissionCode) {
        List<String> permissions = getEmployeePermissionCodes(employeeId);
        return permissions.contains(permissionCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean linkEmployeeWithUser(Long employeeId, Long userId) {
        // 1. 验证参数
        if (employeeId == null || userId == null) {
            throw new ValidationException("员工ID和用户ID不能为空");
        }
        
        // 2. 检查员工是否存在
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 检查员工是否已关联用户
        if (employee.getUserId() != null) {
            throw new ValidationException("员工已关联用户账号");
        }
        
        // 4. 检查用户是否已关联员工
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getUserId, userId);
        Employee existingEmployee = employeeMapper.selectOne(queryWrapper);
        
        if (existingEmployee != null) {
            throw new ValidationException("该用户已关联其他员工");
        }
        
        // 5. 关联员工与用户
        employee.setUserId(userId);
        employeeMapper.updateById(employee);
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlinkEmployeeFromUser(Long employeeId) {
        // 1. 验证参数
        if (employeeId == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        // 2. 检查员工是否存在
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 检查员工是否已关联用户
        if (employee.getUserId() == null) {
            return true; // 已经没有关联，直接返回成功
        }
        
        // 4. 解除关联
        employee.setUserId(null);
        employeeMapper.updateById(employee);
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean syncEmployeeToUser(Long employeeId) {
        // 1. 验证参数
        if (employeeId == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        // 2. 检查员工是否存在
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 检查员工是否已关联用户
        if (employee.getUserId() == null) {
            throw new ValidationException("员工未关联用户账号");
        }
        
        // 4. 查询用户信息
        UserVO userVO = userService.getUserById(employee.getUserId());
        if (userVO == null) {
            throw new ValidationException("关联的用户不存在");
        }
        
        // 5. 使用UserService更新用户信息(简化处理)
        log.info("同步员工[{}]信息到用户账号[{}]", employee.getName(), userVO.getUsername());
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUserFromEmployee(Long employeeId, String initialPassword) {
        // 1. 验证参数
        if (employeeId == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        if (!StringUtils.hasText(initialPassword)) {
            throw new ValidationException("初始密码不能为空");
        }
        
        // 2. 检查员工是否存在
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new ValidationException("员工不存在");
        }
        
        // 3. 检查员工是否已关联用户
        if (employee.getUserId() != null) {
            throw new ValidationException("员工已关联用户账号，无法创建新用户");
        }
        
        // 4. 员工基本信息验证
        if (employee.getEmployeeStatus() != EmployeeStatusEnum.REGULAR) {
            throw new ValidationException("只能为正式员工创建用户账号");
        }
        
        if (!StringUtils.hasText(employee.getWorkNumber())) {
            throw new ValidationException("员工工号不能为空");
        }
        
        // 5. 创建用户账号
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername(employee.getWorkNumber()); // 使用工号作为用户名
        userCreateDTO.setPassword(initialPassword); // 设置初始密码
        userCreateDTO.setRealName(employee.getName());
        userCreateDTO.setMobile(employee.getMobile());
        userCreateDTO.setEmail(employee.getEmail());
        
        Long userId = userService.createUser(userCreateDTO);
        
        // 6. 关联员工与用户
        return linkEmployeeWithUser(employeeId, userId) ? userId : null;
    }
}