package com.lawfirm.common.security.crypto;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 数据脱敏服务接口
 * 提供通用的数据脱敏操作
 */
public interface SensitiveDataService {
    
    /**
     * 手机号码脱敏
     * 保留前3位和后4位，中间用*替代
     * @param phoneNumber 手机号码
     * @return 脱敏后的手机号码
     */
    String maskPhoneNumber(String phoneNumber);
    
    /**
     * 身份证号脱敏
     * 保留前6位和后4位，中间用*替代
     * @param idCard 身份证号
     * @return 脱敏后的身份证号
     */
    String maskIdCard(String idCard);
    
    /**
     * 银行卡号脱敏
     * 仅显示后4位，其他用*替代
     * @param bankCard 银行卡号
     * @return 脱敏后的银行卡号
     */
    String maskBankCard(String bankCard);
    
    /**
     * 邮箱地址脱敏
     * 仅显示首字符和@后的内容，其他用*替代
     * @param email 邮箱地址
     * @return 脱敏后的邮箱地址
     */
    String maskEmail(String email);
    
    /**
     * 姓名脱敏
     * 仅显示姓，其他用*替代
     * @param name 姓名
     * @return 脱敏后的姓名
     */
    String maskName(String name);
    
    /**
     * 自定义脱敏
     * @param text 原文
     * @param prefixLength 保留前几位
     * @param suffixLength 保留后几位
     * @param maskChar 脱敏替换字符
     * @return 脱敏后的文本
     */
    String mask(String text, int prefixLength, int suffixLength, char maskChar);
    
    /**
     * 地址脱敏
     * 保留省市，隐藏详细地址
     * @param address 地址信息
     * @return 脱敏后的地址
     */
    String maskAddress(String address);
    
    /**
     * 案件号脱敏
     * 根据案件号格式进行智能脱敏
     * @param caseNumber 案件号
     * @return 脱敏后的案件号
     */
    String maskCaseNumber(String caseNumber);
    
    /**
     * 组织机构代码脱敏
     * 保留前3位和后3位，中间用*替代
     * @param orgCode 组织机构代码/统一社会信用代码
     * @return 脱敏后的组织机构代码
     */
    String maskOrgCode(String orgCode);
    
    /**
     * 金额脱敏
     * 根据金额大小进行智能脱敏
     * @param amount 金额
     * @return 脱敏后的金额字符串
     */
    String maskAmount(BigDecimal amount);
    
    /**
     * 合同编号脱敏
     * @param contractNumber 合同编号
     * @return 脱敏后的合同编号
     */
    String maskContractNumber(String contractNumber);
    
    /**
     * 批量脱敏Map中的指定字段
     * @param dataMap 数据Map
     * @param sensitiveKeys 需要脱敏的key数组
     * @return 脱敏后的Map
     */
    Map<String, Object> maskMap(Map<String, Object> dataMap, String[] sensitiveKeys);
} 