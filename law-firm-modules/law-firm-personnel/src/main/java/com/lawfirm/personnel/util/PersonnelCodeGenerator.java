package com.lawfirm.personnel.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.lawfirm.common.util.id.IdGenerator;
import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 人员编码生成器
 * 用于生成员工工号、律师执业证号等各类编码
 */
@Component
public class PersonnelCodeGenerator {
    
    /**
     * 员工编号前缀
     */
    private static final String EMPLOYEE_PREFIX = "EMP";
    
    /**
     * 律师编号前缀
     */
    private static final String LAWYER_PREFIX = "LAW";
    
    /**
     * 行政人员编号前缀
     */
    private static final String STAFF_PREFIX = "STF";
    
    /**
     * 实习生编号前缀
     */
    private static final String INTERN_PREFIX = "INT";
    
    /**
     * 顾问编号前缀
     */
    private static final String CONSULTANT_PREFIX = "CON";

    /**
     * 生成员工编号
     * 格式：前缀 + 年月日 + 4位随机数
     * 
     * @param employeeType 员工类型
     * @return 员工编号
     */
    public String generateEmployeeCode(EmployeeTypeEnum employeeType) {
        String prefix = getEmployeePrefix(employeeType);
        String dateStr = DateUtil.format(new Date(), "yyyyMMdd");
        String randomStr = RandomUtil.randomNumbers(4);
        
        return prefix + dateStr + randomStr;
    }
    
    /**
     * 生成律师执业证号
     * 格式：省份代码(2位) + 年份(4位) + 5位序列号
     * 
     * @param provinceCode 省份代码
     * @param year 年份
     * @return 律师执业证号
     */
    public String generateLawyerLicenseNumber(String provinceCode, int year) {
        if (provinceCode == null || provinceCode.length() != 2) {
            throw new IllegalArgumentException("省份代码必须为2位");
        }
        
        String randomStr = RandomUtil.randomNumbers(5);
        return provinceCode + year + randomStr;
    }
    
    /**
     * 生成随机工号
     * 基于雪花算法
     * 
     * @return 随机工号
     */
    public String generateRandomWorkNumber() {
        // 使用common-util中的IdGenerator获取雪花算法ID
        long id = IdGenerator.nextId();
        return String.valueOf(id).substring(10);
    }
    
    /**
     * 根据员工类型获取前缀
     * 
     * @param employeeType 员工类型
     * @return 前缀
     */
    private String getEmployeePrefix(EmployeeTypeEnum employeeType) {
        if (employeeType == null) {
            return EMPLOYEE_PREFIX;
        }
        
        switch (employeeType) {
            case LAWYER:
                return LAWYER_PREFIX;
            case STAFF:
                return STAFF_PREFIX;
            case INTERN:
                return INTERN_PREFIX;
            case CONSULTANT:
                return CONSULTANT_PREFIX;
            default:
                return EMPLOYEE_PREFIX;
        }
    }
} 