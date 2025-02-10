package com.lawfirm.personnel.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.personnel.constant.PersonnelConstants;
import com.lawfirm.personnel.converter.EmployeeConverter;
import com.lawfirm.personnel.dto.request.EmployeeAddRequest;
import com.lawfirm.personnel.dto.request.EmployeeUpdateRequest;
import com.lawfirm.personnel.dto.response.EmployeeResponse;
import com.lawfirm.personnel.entity.Employee;
import com.lawfirm.personnel.mapper.EmployeeMapper;
import com.lawfirm.common.core.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeMapper employeeMapper;

    @Spy
    private EmployeeConverter employeeConverter;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeAddRequest addRequest;
    private EmployeeUpdateRequest updateRequest;
    private Employee employee;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        addRequest = new EmployeeAddRequest();
        addRequest.setName("张三");
        addRequest.setGender(PersonnelConstants.Gender.MALE);
        addRequest.setIdCard("110101199001011234");
        addRequest.setMobile("13800138001");
        addRequest.setEmail("zhangsan@lawfirm.com");
        addRequest.setBranchId(1L);
        addRequest.setDepartmentId(1L);
        addRequest.setPositionId(1L);
        addRequest.setEntryDate(LocalDate.now());

        updateRequest = new EmployeeUpdateRequest();
        updateRequest.setId(1L);
        updateRequest.setName("张三");
        updateRequest.setEmail("zhangsan@lawfirm.com");

        employee = new Employee();
        employee.setId(1L);
        employee.setEmployeeNo("EMP202401001");
        employee.setName("张三");
        employee.setGender(PersonnelConstants.Gender.MALE);
        employee.setIdCard("110101199001011234");
        employee.setMobile("13800138001");
        employee.setEmail("zhangsan@lawfirm.com");
        employee.setBranchId(1L);
        employee.setDepartmentId(1L);
        employee.setPositionId(1L);
        employee.setEntryDate(LocalDate.now());
        employee.setStatus(PersonnelConstants.EmployeeStatus.PROBATION);
    }

    @Test
    void testAddEmployee() {
        when(employeeMapper.insert(any(Employee.class))).thenReturn(1);
        when(employeeMapper.selectCount(any())).thenReturn(0L);

        Long id = employeeService.addEmployee(addRequest);

        assertNotNull(id);
        verify(employeeMapper, times(1)).insert(any(Employee.class));
    }

    @Test
    void testAddEmployeeWithDuplicateIdCard() {
        when(employeeMapper.selectCount(any())).thenReturn(1L);

        assertThrows(BusinessException.class, () -> employeeService.addEmployee(addRequest));
        verify(employeeMapper, never()).insert(any(Employee.class));
    }

    @Test
    void testUpdateEmployee() {
        when(employeeMapper.selectById(1L)).thenReturn(employee);
        when(employeeMapper.updateById(any(Employee.class))).thenReturn(1);
        when(employeeMapper.selectCount(any())).thenReturn(0L);

        assertDoesNotThrow(() -> employeeService.updateEmployee(updateRequest));
        verify(employeeMapper, times(1)).updateById(any(Employee.class));
    }

    @Test
    void testUpdateEmployeeNotFound() {
        when(employeeMapper.selectById(1L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> employeeService.updateEmployee(updateRequest));
        verify(employeeMapper, never()).updateById(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {
        when(employeeMapper.selectById(1L)).thenReturn(employee);
        when(employeeMapper.deleteById(1L)).thenReturn(1);

        assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));
        verify(employeeMapper, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEmployeeNotFound() {
        when(employeeMapper.selectById(1L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> employeeService.deleteEmployee(1L));
        verify(employeeMapper, never()).deleteById(anyLong());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeMapper.selectById(1L)).thenReturn(employee);

        EmployeeResponse response = employeeService.getEmployeeById(1L);

        assertNotNull(response);
        assertEquals(employee.getId(), response.getId());
        assertEquals(employee.getName(), response.getName());
        verify(employeeMapper, times(1)).selectById(1L);
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        when(employeeMapper.selectById(1L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> employeeService.getEmployeeById(1L));
    }

    @Test
    void testPageEmployees() {
        Page<Employee> page = new Page<>(1, 10);
        page.setRecords(List.of(employee));
        page.setTotal(1);

        when(employeeMapper.selectPage(any(), any())).thenReturn(page);

        IPage<EmployeeResponse> result = employeeService.pageEmployees(1, 10, null, null, null, null, null, null);

        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());
        verify(employeeMapper, times(1)).selectPage(any(), any());
    }

    @Test
    void testRegularEmployee() {
        when(employeeMapper.selectById(1L)).thenReturn(employee);
        when(employeeMapper.updateById(any(Employee.class))).thenReturn(1);

        assertDoesNotThrow(() -> employeeService.regularEmployee(1L, LocalDate.now()));
        verify(employeeMapper, times(1)).updateById(any(Employee.class));
    }

    @Test
    void testRegularEmployeeNotFound() {
        when(employeeMapper.selectById(1L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> employeeService.regularEmployee(1L, LocalDate.now()));
        verify(employeeMapper, never()).updateById(any(Employee.class));
    }

    @Test
    void testRegularEmployeeNotProbation() {
        employee.setStatus(PersonnelConstants.EmployeeStatus.REGULAR);
        when(employeeMapper.selectById(1L)).thenReturn(employee);

        assertThrows(BusinessException.class, () -> employeeService.regularEmployee(1L, LocalDate.now()));
        verify(employeeMapper, never()).updateById(any(Employee.class));
    }

    @Test
    void testLeaveEmployee() {
        when(employeeMapper.selectById(1L)).thenReturn(employee);
        when(employeeMapper.updateById(any(Employee.class))).thenReturn(1);

        assertDoesNotThrow(() -> employeeService.leaveEmployee(1L, LocalDate.now()));
        verify(employeeMapper, times(1)).updateById(any(Employee.class));
    }

    @Test
    void testLeaveEmployeeNotFound() {
        when(employeeMapper.selectById(1L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> employeeService.leaveEmployee(1L, LocalDate.now()));
        verify(employeeMapper, never()).updateById(any(Employee.class));
    }

    @Test
    void testLeaveEmployeeAlreadyLeft() {
        employee.setStatus(PersonnelConstants.EmployeeStatus.LEAVE);
        when(employeeMapper.selectById(1L)).thenReturn(employee);

        assertThrows(BusinessException.class, () -> employeeService.leaveEmployee(1L, LocalDate.now()));
        verify(employeeMapper, never()).updateById(any(Employee.class));
    }
} 