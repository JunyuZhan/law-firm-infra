package com.lawfirm.model.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 员工数据访问接口
 * 整合了律师和行政人员的数据访问功能
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 根据工号查询员工
     *
     * @param workNumber 工号
     * @return 员工信息
     */
    Employee selectByWorkNumber(String workNumber);

    /**
     * 根据邮箱查询员工
     *
     * @param email 邮箱
     * @return 员工信息
     */
    Employee selectByEmail(String email);

    /**
     * 根据手机号查询员工
     *
     * @param mobile 手机号
     * @return 员工信息
     */
    Employee selectByMobile(String mobile);

    /**
     * 根据工号查询员工
     */
    @Select("SELECT * FROM employee WHERE work_number = #{username}")
    Employee selectByUsername(String username);

    /**
     * 根据手机号查询员工
     */
    @Select("SELECT * FROM employee WHERE mobile = #{phone}")
    Employee selectByPhone(String phone);
    
    /**
     * 根据律师执业证号查询员工
     *
     * @param licenseNumber 执业证号
     * @return 员工信息
     */
    @Select("SELECT * FROM employee WHERE license_number = #{licenseNumber} AND employee_type = 1")
    Employee selectByLicenseNumber(String licenseNumber);

    /**
     * 根据专业领域查询律师列表
     *
     * @param practiceArea 专业领域
     * @return 律师列表
     */
    List<Employee> selectByPracticeArea(String practiceArea);
    
    /**
     * 根据员工类型查询员工列表
     *
     * @param employeeType 员工类型
     * @return 员工列表
     */
    List<Employee> selectByEmployeeType(@Param("employeeType") EmployeeTypeEnum employeeType);

    /**
     * 根据部门ID和员工类型查询员工列表
     *
     * @param departmentId 部门ID
     * @param employeeType 员工类型
     * @return 员工列表
     */
    List<Employee> selectByDepartmentIdAndType(@Param("departmentId") Long departmentId, 
                                               @Param("employeeType") EmployeeTypeEnum employeeType);

    /**
     * 根据职位ID和员工类型查询员工列表
     *
     * @param positionId 职位ID
     * @param employeeType 员工类型
     * @return 员工列表
     */
    List<Employee> selectByPositionIdAndType(@Param("positionId") Long positionId, 
                                             @Param("employeeType") EmployeeTypeEnum employeeType);
    
    /**
     * 根据律师职级查询律师列表
     *
     * @param lawyerLevel 律师职级
     * @return 律师列表
     */
    List<Employee> selectByLawyerLevel(@Param("lawyerLevel") Integer lawyerLevel);
    
    /**
     * 根据行政职能类型查询行政人员列表
     *
     * @param functionType 职能类型
     * @return 行政人员列表
     */
    List<Employee> selectByFunctionType(@Param("functionType") Integer functionType);
} 