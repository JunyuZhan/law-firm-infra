package com.lawfirm.model.cases.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 法院工具类
 */
public class CourtUtils {

    private static final Pattern COURT_CASE_NUMBER_PATTERN = 
        Pattern.compile("^\\(\\d{4}\\)[\\u4e00-\\u9fa5]{2,4}[民刑行知执赔认送调督管]\\d+号$");

    /**
     * 验证法院案号格式
     * 格式：(年份)XX法民初123号
     */
    public static boolean validateCourtCaseNumber(String courtCaseNumber) {
        if (StringUtils.isBlank(courtCaseNumber)) {
            return false;
        }
        return COURT_CASE_NUMBER_PATTERN.matcher(courtCaseNumber).matches();
    }

    /**
     * 从法院案号中提取年份
     */
    public static int extractYearFromCourtCaseNumber(String courtCaseNumber) {
        if (!validateCourtCaseNumber(courtCaseNumber)) {
            throw new IllegalArgumentException("Invalid court case number format");
        }
        String yearStr = courtCaseNumber.substring(1, 5);
        return Integer.parseInt(yearStr);
    }

    /**
     * 从法院案号中提取案件类型
     */
    public static String extractCaseTypeFromCourtCaseNumber(String courtCaseNumber) {
        if (!validateCourtCaseNumber(courtCaseNumber)) {
            throw new IllegalArgumentException("Invalid court case number format");
        }
        // 提取民刑行知执赔认送调督管等字符
        for (int i = 0; i < courtCaseNumber.length(); i++) {
            char c = courtCaseNumber.charAt(i);
            if ("民刑行知执赔认送调督管".indexOf(c) >= 0) {
                return String.valueOf(c);
            }
        }
        throw new IllegalArgumentException("Cannot find case type in court case number");
    }

    /**
     * 从法院案号中提取序号
     */
    public static int extractSequenceFromCourtCaseNumber(String courtCaseNumber) {
        if (!validateCourtCaseNumber(courtCaseNumber)) {
            throw new IllegalArgumentException("Invalid court case number format");
        }
        // 提取数字部分（去掉年份和"号"字）
        String numberPart = courtCaseNumber.replaceAll("^\\(\\d{4}\\)[\\u4e00-\\u9fa5]+", "")
                                         .replaceAll("号$", "");
        return Integer.parseInt(numberPart);
    }
} 