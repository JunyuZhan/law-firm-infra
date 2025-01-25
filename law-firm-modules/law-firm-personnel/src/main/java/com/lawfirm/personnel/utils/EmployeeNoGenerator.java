package com.lawfirm.personnel.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 员工编号生成器
 * 规则：分所编码(2位) + 年份(2位) + 月份(2位) + 序号(4位)
 * 例如：SH24010001
 */
@Component
public class EmployeeNoGenerator {
    
    private static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter.ofPattern("yy");
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("MM");
    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);
    private static final int MAX_SEQUENCE = 9999;
    
    /**
     * 生成员工编号
     *
     * @param branchCode 分所编码
     * @return 员工编号
     */
    public String generate(String branchCode) {
        LocalDateTime now = LocalDateTime.now();
        String year = now.format(YEAR_FORMATTER);
        String month = now.format(MONTH_FORMATTER);
        
        // 获取序号并重置
        int sequence = SEQUENCE.getAndIncrement();
        if (sequence > MAX_SEQUENCE) {
            SEQUENCE.set(1);
            sequence = 1;
        }
        
        return String.format("%s%s%s%04d", 
                branchCode.substring(0, Math.min(branchCode.length(), 2)),
                year,
                month,
                sequence);
    }
} 