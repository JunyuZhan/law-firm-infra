package com.lawfirm.common.security.crypto.impl;

import com.lawfirm.common.security.crypto.SensitiveDataService;
import com.lawfirm.common.util.string.StringExtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据脱敏服务实现类
 * 提供通用的数据脱敏操作
 */
@Service
public class SensitiveDataServiceImpl implements SensitiveDataService {
    
    /**
     * 手机号码脱敏
     * 保留前3位和后4位，中间用*替代
     * @param phoneNumber 手机号码
     * @return 脱敏后的手机号码
     */
    @Override
    public String maskPhoneNumber(String phoneNumber) {
        // 复用StringExtUtils中的实现
        return StringExtUtils.maskPhone(phoneNumber);
    }
    
    /**
     * 身份证号脱敏
     * 保留前6位和后4位，中间用*替代
     * @param idCard 身份证号
     * @return 脱敏后的身份证号
     */
    @Override
    public String maskIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return idCard;
        }
        
        // 处理长度不足的情况
        if (idCard.length() < 11) {
            return mask(idCard, 1, 1, '*');
        }
        
        // 保留前6位和后4位，中间用*替代
        return mask(idCard, 6, 4, '*');
    }
    
    /**
     * 银行卡号脱敏
     * 仅显示后4位，其他用*替代
     * @param bankCard 银行卡号
     * @return 脱敏后的银行卡号
     */
    @Override
    public String maskBankCard(String bankCard) {
        if (StringUtils.isBlank(bankCard)) {
            return bankCard;
        }
        
        // 处理长度不足的情况
        if (bankCard.length() < 5) {
            return "*".repeat(bankCard.length());
        }
        
        // 仅显示后4位，其他用*替代
        return mask(bankCard, 0, 4, '*');
    }
    
    /**
     * 邮箱地址脱敏
     * 仅显示首字符和@后的内容，其他用*替代
     * @param email 邮箱地址
     * @return 脱敏后的邮箱地址
     */
    @Override
    public String maskEmail(String email) {
        // 复用StringExtUtils中的实现
        return StringExtUtils.maskEmail(email);
    }
    
    /**
     * 姓名脱敏
     * 仅显示姓，其他用*替代
     * @param name 姓名
     * @return 脱敏后的姓名
     */
    @Override
    public String maskName(String name) {
        if (StringUtils.isBlank(name)) {
            return name;
        }
        
        // 处理长度不足的情况
        if (name.length() == 1) {
            return name;
        }
        
        if (name.length() == 2) {
            return name.charAt(0) + "*";
        }
        
        // 判断是否包含中文字符
        if (StringExtUtils.containsChinese(name)) {
            // 中文姓名：显示姓，其他用*替代
            return name.charAt(0) + "*".repeat(name.length() - 1);
        } else {
            // 西方姓名：显示首字母，其他用*替代
            return name.charAt(0) + "*".repeat(name.length() - 1);
        }
    }
    
    /**
     * 自定义脱敏
     * @param text 原文
     * @param prefixLength 保留前几位
     * @param suffixLength 保留后几位
     * @param maskChar 脱敏替换字符
     * @return 脱敏后的文本
     */
    @Override
    public String mask(String text, int prefixLength, int suffixLength, char maskChar) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        
        // 处理参数异常情况
        if (prefixLength < 0) {
            prefixLength = 0;
        }
        
        if (suffixLength < 0) {
            suffixLength = 0;
        }
        
        // 处理长度不足的情况
        if (prefixLength + suffixLength >= text.length()) {
            if (prefixLength >= text.length()) {
                return text;
            }
            if (suffixLength >= text.length()) {
                return text;
            }
            // 调整保留长度，确保至少有一个字符被掩码
            suffixLength = text.length() - prefixLength - 1;
        }
        
        // 计算掩码长度
        int maskLength = text.length() - prefixLength - suffixLength;
        
        StringBuilder sb = new StringBuilder();
        // 添加前缀部分
        if (prefixLength > 0) {
            sb.append(text, 0, prefixLength);
        }
        
        // 添加掩码部分
        sb.append(String.valueOf(maskChar).repeat(maskLength));
        
        // 添加后缀部分
        if (suffixLength > 0) {
            sb.append(text.substring(text.length() - suffixLength));
        }
        
        return sb.toString();
    }

    /**
     * 地址信息脱敏
     * 保留省市区信息，详细地址用*替代
     * @param address 地址信息
     * @return 脱敏后的地址
     */
    @Override
    public String maskAddress(String address) {
        if (StringUtils.isBlank(address)) {
            return address;
        }
        
        // 如果地址过短，直接返回全部*
        if (address.length() < 3) {
            return "*".repeat(address.length());
        }
        
        // 尝试找到最后一个"区"、"市"、"省"的位置
        int lastIndex = Math.max(
            Math.max(address.lastIndexOf("区"), address.lastIndexOf("市")),
            address.lastIndexOf("省")
        );
        
        if (lastIndex == -1) {
            // 如果找不到省市区标识，保留前1/3，其余用*替代
            int prefixLength = Math.max(address.length() / 3, 1);
            return mask(address, prefixLength, 0, '*');
        }
        
        // 保留省市区信息，后面的详细地址用*替代
        return address.substring(0, lastIndex + 1) + 
               "*".repeat(Math.max(address.length() - lastIndex - 1, 1));
    }

    /**
     * 案号脱敏
     * 保留年份和案件类型，其他用*替代
     * @param caseNumber 案号
     * @return 脱敏后的案号
     */
    @Override
    public String maskCaseNumber(String caseNumber) {
        if (StringUtils.isBlank(caseNumber)) {
            return caseNumber;
        }
        
        // 如果长度小于8，保留前2位
        if (caseNumber.length() < 8) {
            return mask(caseNumber, 2, 0, '*');
        }
        
        // 通常案号格式为：(2023)粤0106民初123号
        // 保留括号年份和案件类型，数字部分用*替代
        StringBuilder result = new StringBuilder();
        boolean inYear = false;
        boolean keepChar = true;
        
        for (char c : caseNumber.toCharArray()) {
            if (c == '(') {
                inYear = true;
                result.append(c);
            } else if (c == ')') {
                inYear = false;
                result.append(c);
            } else if (inYear || Character.isLetter(c)) {
                result.append(c);
            } else if (Character.isDigit(c) && keepChar) {
                result.append('*');
                keepChar = false;
            } else if (!Character.isDigit(c)) {
                result.append(c);
                keepChar = true;
            }
        }
        
        return result.toString();
    }

    /**
     * 组织机构代码脱敏
     * 保留前2位和后2位，中间用*替代
     * @param orgCode 组织机构代码
     * @return 脱敏后的组织机构代码
     */
    @Override
    public String maskOrgCode(String orgCode) {
        if (StringUtils.isBlank(orgCode)) {
            return orgCode;
        }
        
        return mask(orgCode, 2, 2, '*');
    }

    /**
     * 金额脱敏
     * 将金额转换为范围描述，如：1万以下、1-5万、5-10万等
     * @param amount 金额
     * @return 脱敏后的金额描述
     */
    @Override
    public String maskAmount(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        
        // 转换为万元，使用新的RoundingMode替代过时的ROUND_HALF_UP
        BigDecimal amountInWan = amount.divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP);
        
        if (amountInWan.compareTo(new BigDecimal("1")) < 0) {
            return "1万以下";
        } else if (amountInWan.compareTo(new BigDecimal("5")) < 0) {
            return "1-5万";
        } else if (amountInWan.compareTo(new BigDecimal("10")) < 0) {
            return "5-10万";
        } else if (amountInWan.compareTo(new BigDecimal("50")) < 0) {
            return "10-50万";
        } else if (amountInWan.compareTo(new BigDecimal("100")) < 0) {
            return "50-100万";
        } else if (amountInWan.compareTo(new BigDecimal("500")) < 0) {
            return "100-500万";
        } else if (amountInWan.compareTo(new BigDecimal("1000")) < 0) {
            return "500-1000万";
        } else {
            return "1000万以上";
        }
    }

    /**
     * 合同编号脱敏
     * 保留前4位和后4位，中间用*替代
     * @param contractNumber 合同编号
     * @return 脱敏后的合同编号
     */
    @Override
    public String maskContractNumber(String contractNumber) {
        if (StringUtils.isBlank(contractNumber)) {
            return contractNumber;
        }
        
        return mask(contractNumber, 4, 4, '*');
    }

    /**
     * 对Map中指定的字段进行脱敏处理
     * @param data 需要脱敏的数据Map
     * @param sensitiveFields 需要脱敏的字段数组
     * @return 脱敏后的Map
     */
    @Override
    public Map<String, Object> maskMap(Map<String, Object> data, String[] sensitiveFields) {
        if (data == null || sensitiveFields == null || sensitiveFields.length == 0) {
            return data;
        }
        
        Map<String, Object> result = new HashMap<>(data);
        
        for (String field : sensitiveFields) {
            Object value = result.get(field);
            if (value != null) {
                if (value instanceof String) {
                    String strValue = (String) value;
                    // 根据字段名选择合适的脱敏方法
                    String maskedValue = switch (field.toLowerCase()) {
                        case "phone", "mobile", "tel" -> maskPhoneNumber(strValue);
                        case "idcard", "id_card" -> maskIdCard(strValue);
                        case "email" -> maskEmail(strValue);
                        case "name" -> maskName(strValue);
                        case "address" -> maskAddress(strValue);
                        case "case_number", "caseno" -> maskCaseNumber(strValue);
                        case "org_code" -> maskOrgCode(strValue);
                        case "contract_number", "contractno" -> maskContractNumber(strValue);
                        default -> mask(strValue, 1, 1, '*');
                    };
                    result.put(field, maskedValue);
                } else if (value instanceof BigDecimal) {
                    // 如果是金额字段
                    if (field.toLowerCase().contains("amount") || 
                        field.toLowerCase().contains("money") || 
                        field.toLowerCase().contains("price")) {
                        result.put(field, maskAmount((BigDecimal) value));
                    }
                }
            }
        }
        
        return result;
    }
} 