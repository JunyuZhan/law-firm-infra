package com.lawfirm.model.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.personnel.dto.employee.CreateEmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.UpdateEmployeeDTO;
import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import com.lawfirm.model.personnel.enums.LawyerLevelEnum;
import com.lawfirm.model.personnel.enums.StaffFunctionEnum;
import com.lawfirm.model.personnel.vo.EmployeeVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 员工服务接口
 * <p>
 * 该接口整合了律师和行政人员的服务功能，使用employeeType字段区分不同类型的员工。
 * 服务层实现应根据employeeType判断哪些字段有效，并提供相应的业务处理逻辑。
 * </p>
 * 
 * <p>
 * 接口分为三类：
 * <ul>
 *   <li>通用方法：适用于所有类型员工的基本操作</li>
 *   <li>律师特有方法：仅适用于律师类型员工的操作</li>
 *   <li>行政人员特有方法：仅适用于行政人员类型员工的操作</li>
 * </ul>
 * </p>
 * 
 * <p>
 * 注意：在处理特定类型员工时，应先检查employeeType是否匹配，例如：
 * <pre>
 * if (employee.getEmployeeType() != EmployeeTypeEnum.LAWYER) {
 *     throw new IllegalArgumentException("该员工不是律师类型");
 * }
 * </pre>
 * </p>
 */
public interface EmployeeService {

    /**
     * 创建员工
     * <p>
     * 根据CreateEmployeeDTO中的employeeType创建不同类型的员工，
     * 并根据employeeType验证和处理相应的特有属性。
     * </p>
     *
     * @param createDTO 创建员工参数
     * @return 员工ID
     */
    Long createEmployee(CreateEmployeeDTO createDTO);

    /**
     * 更新员工
     * <p>
     * 根据UpdateEmployeeDTO中的employeeType更新不同类型的员工，
     * 并根据employeeType验证和处理相应的特有属性。
     * 注意：employeeType字段一般不应允许修改，以避免数据不一致。
     * </p>
     *
     * @param id        员工ID
     * @param updateDTO 更新员工参数
     * @return 是否成功
     */
    boolean updateEmployee(Long id, UpdateEmployeeDTO updateDTO);

    /**
     * 删除员工
     *
     * @param id 员工ID
     * @return 是否成功
     */
    boolean deleteEmployee(Long id);

    /**
     * 根据ID获取员工
     *
     * @param id 员工ID
     * @return 员工信息
     */
    EmployeeDTO getEmployeeById(Long id);

    /**
     * 根据ID获取员工实体
     *
     * @param id 员工ID
     * @return 员工实体
     */
    Employee getById(Long id);

    /**
     * 根据条件查询员工列表
     * <p>
     * 可以使用employeeType参数筛选特定类型的员工
     * </p>
     *
     * @param params 查询条件，可包含employeeType、departmentId等
     * @return 员工列表
     */
    List<EmployeeDTO> listEmployees(Map<String, Object> params);

    /**
     * 根据工号查询员工
     *
     * @param workNumber 工号
     * @return 员工信息
     */
    EmployeeDTO getEmployeeByWorkNumber(String workNumber);

    /**
     * 根据邮箱查询员工
     *
     * @param email 邮箱
     * @return 员工信息
     */
    EmployeeDTO getEmployeeByEmail(String email);

    /**
     * 根据手机号查询员工
     *
     * @param mobile 手机号
     * @return 员工信息
     */
    EmployeeDTO getEmployeeByMobile(String mobile);

    /**
     * 根据用户ID查询员工实体
     *
     * @param userId 用户ID
     * @return 员工实体
     */
    Employee getEmployeeByUserId(Long userId);

    /**
     * 根据用户ID查询员工DTO
     *
     * @param userId 用户ID
     * @return 员工DTO
     */
    EmployeeDTO getEmployeeDTOByUserId(Long userId);

    /**
     * 更新员工状态
     *
     * @param id     员工ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateEmployeeStatus(Long id, EmployeeStatusEnum status);

    /**
     * 离职处理
     *
     * @param id         员工ID
     * @param resignDate 离职日期
     * @param reason     离职原因
     * @return 是否成功
     */
    boolean resign(Long id, LocalDate resignDate, String reason);

    /**
     * 批量导入员工
     * <p>
     * 根据每个员工的employeeType处理不同类型员工的特有属性
     * </p>
     *
     * @param employees 员工列表
     * @return 是否成功
     */
    boolean batchImport(List<CreateEmployeeDTO> employees);
    
    /**
     * 根据部门ID查询员工列表
     *
     * @param departmentId 部门ID
     * @return 员工列表
     */
    List<EmployeeDTO> listEmployeesByDepartmentId(Long departmentId);
    
    /**
     * 根据部门ID和员工类型查询员工列表
     *
     * @param departmentId 部门ID
     * @param employeeType 员工类型
     * @return 员工列表
     */
    List<EmployeeDTO> listEmployeesByDepartmentIdAndType(Long departmentId, EmployeeTypeEnum employeeType);
    
    /**
     * 根据职位ID查询员工列表
     *
     * @param positionId 职位ID
     * @return 员工列表
     */
    List<EmployeeDTO> listEmployeesByPositionId(Long positionId);
    
    /**
     * 根据职位ID和员工类型查询员工列表
     *
     * @param positionId 职位ID
     * @param employeeType 员工类型
     * @return 员工列表
     */
    List<EmployeeDTO> listEmployeesByPositionIdAndType(Long positionId, EmployeeTypeEnum employeeType);
    
    // =============== 律师特有方法 ===============
    
    /**
     * 根据执业证号查询律师
     * <p>
     * 仅返回employeeType=LAWYER的员工
     * </p>
     *
     * @param licenseNumber 执业证号
     * @return 律师信息
     */
    EmployeeDTO getLawyerByLicenseNumber(String licenseNumber);
    
    /**
     * 根据专业领域查询律师列表
     * <p>
     * 仅返回employeeType=LAWYER的员工
     * </p>
     *
     * @param practiceArea 专业领域
     * @return 律师列表
     */
    List<EmployeeDTO> listLawyersByPracticeArea(String practiceArea);
    
    /**
     * 根据律师职级查询律师列表
     * <p>
     * 仅返回employeeType=LAWYER的员工
     * </p>
     *
     * @param level 律师职级
     * @return 律师列表
     */
    List<EmployeeDTO> listLawyersByLevel(LawyerLevelEnum level);
    
    /**
     * 更新律师职级
     * <p>
     * 仅适用于employeeType=LAWYER的员工
     * 实现时应先检查员工类型是否为LAWYER
     * </p>
     *
     * @param id    律师ID
     * @param level 职级
     * @return 是否成功
     * @throws IllegalArgumentException 如果员工类型不是LAWYER
     */
    boolean updateLawyerLevel(Long id, LawyerLevelEnum level);
    
    /**
     * 设置合伙人状态
     * <p>
     * 仅适用于employeeType=LAWYER的员工
     * 实现时应先检查员工类型是否为LAWYER
     * </p>
     *
     * @param id           律师ID
     * @param isPartner    是否合伙人
     * @param partnerDate  成为合伙人日期
     * @param equityRatio  股权比例
     * @return 是否成功
     * @throws IllegalArgumentException 如果员工类型不是LAWYER
     */
    boolean setPartnerStatus(Long id, boolean isPartner, LocalDate partnerDate, Double equityRatio);
    
    // =============== 行政人员特有方法 ===============
    
    /**
     * 根据职能类型查询行政人员列表
     * <p>
     * 仅返回employeeType=STAFF的员工
     * </p>
     *
     * @param functionType 职能类型
     * @return 行政人员列表
     */
    List<EmployeeDTO> listStaffsByFunctionType(StaffFunctionEnum functionType);
    
    /**
     * 更新行政人员职能类型
     * <p>
     * 仅适用于employeeType=STAFF的员工
     * 实现时应先检查员工类型是否为STAFF
     * </p>
     *
     * @param id           行政人员ID
     * @param functionType 职能类型
     * @param functionDesc 职能描述
     * @return 是否成功
     * @throws IllegalArgumentException 如果员工类型不是STAFF
     */
    boolean updateStaffFunction(Long id, StaffFunctionEnum functionType, String functionDesc);
    
    /**
     * 创建行政人员
     * <p>
     * 创建一个行政人员类型的员工，会自动设置employeeType=STAFF
     * </p>
     *
     * @param createDTO 行政人员创建参数
     * @return 行政人员ID
     */
    Long createStaff(CreateEmployeeDTO createDTO);
    
    /**
     * 创建用户并关联员工
     * <p>
     * 在认证模块创建用户，并关联到指定员工
     * </p>
     *
     * @param employeeId 员工ID
     * @param username   用户名
     * @param password   密码
     * @param email      邮箱
     * @param mobile     手机号
     * @return 创建的用户ID
     */
    Long createUserAndLinkEmployee(Long employeeId, String username, String password, String email, String mobile);
} 