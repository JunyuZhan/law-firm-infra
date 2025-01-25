package com.lawfirm.contract.util;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 合同编号生成器
 * 规则：年月+合同类型+4位序号，如：202401CT0001
 */
@Component
public class ContractNoGenerator {
    
    private static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter.ofPattern("yyyy");
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("MM");
    private static final int MAX_SEQUENCE = 9999;
    
    private final AtomicInteger sequence = new AtomicInteger(0);
    
    /**
     * 生成合同编号
     *
     * @param contractType 合同类型编码
     * @return 合同编号
     */
    public String generate(String contractType) {
        LocalDateTime now = LocalDateTime.now();
        String year = now.format(YEAR_FORMATTER);
        String month = now.format(MONTH_FORMATTER);
        
        // 获取序号并重置
        int currentSequence = sequence.incrementAndGet();
        if (currentSequence > MAX_SEQUENCE) {
            sequence.set(0);
            currentSequence = sequence.incrementAndGet();
        }
        
        // 格式化序号为4位数字
        String sequenceStr = String.format("%04d", currentSequence);
        
        // 拼接合同编号
        return year + month + contractType + sequenceStr;
    }
} 