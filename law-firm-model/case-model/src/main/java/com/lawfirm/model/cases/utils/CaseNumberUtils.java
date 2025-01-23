package com.lawfirm.model.cases.utils;

import com.lawfirm.model.cases.enums.CaseTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * 案件编号工具类
 */
public class CaseNumberUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Pattern CASE_NUMBER_PATTERN = Pattern.compile("^[A-Z]{2}\\d{8}\\d{4}$");

    /**
     * 生成案件编号
     * 规则：案件类型缩写(2位) + 日期(8位) + 序号(4位)
     */
    public static String generateCaseNumber(CaseTypeEnum caseType, int sequence) {
        String prefix = caseType.getValue().substring(0, 2);
        String date = LocalDateTime.now().format(FORMATTER);
        String sequenceStr = String.format("%04d", sequence);
        return prefix + date + sequenceStr;
    }

    /**
     * 验证案件编号格式
     */
    public static boolean validateCaseNumber(String caseNumber) {
        if (StringUtils.isBlank(caseNumber)) {
            return false;
        }
        return CASE_NUMBER_PATTERN.matcher(caseNumber).matches();
    }

    /**
     * 从案件编号中提取日期
     */
    public static LocalDateTime extractDateFromCaseNumber(String caseNumber) {
        if (!validateCaseNumber(caseNumber)) {
            throw new IllegalArgumentException("Invalid case number format");
        }
        String dateStr = caseNumber.substring(2, 10);
        return LocalDateTime.parse(dateStr + "000000", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    /**
     * 从案件编号中提取序号
     */
    public static int extractSequenceFromCaseNumber(String caseNumber) {
        if (!validateCaseNumber(caseNumber)) {
            throw new IllegalArgumentException("Invalid case number format");
        }
        String sequenceStr = caseNumber.substring(10);
        return Integer.parseInt(sequenceStr);
    }
} 