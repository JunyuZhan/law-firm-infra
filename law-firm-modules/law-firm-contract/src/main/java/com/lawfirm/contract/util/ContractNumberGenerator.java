package com.lawfirm.contract.util;

import com.lawfirm.model.contract.enums.ContractTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 合同编号生成器
 * 根据合同类型生成唯一的合同编号
 */
@Slf4j
@Component
public class ContractNumberGenerator {

    /**
     * 序列号缓存
     * 实际项目中应使用Redis或数据库存储保证唯一性和持久化
     */
    private static final Map<String, Integer> sequenceMap = new ConcurrentHashMap<>();
    
    /**
     * 根据合同类型生成合同编号
     * 格式：前缀-年份月份-6位序列号
     * 例如：MS-202307-000001
     *
     * @param contractType 合同类型
     * @return 合同编号
     */
    public static String generateContractNumber(ContractTypeEnum contractType) {
        if (contractType == null) {
            contractType = ContractTypeEnum.OTHER;
        }
        
        String prefix = contractType.getPrefix();
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String sequence = getNextSequence(prefix, dateStr);
        
        return String.format("%s-%s-%s", prefix, dateStr, sequence);
    }
    
    /**
     * 根据合同类型代码生成合同编号
     *
     * @param typeCode 合同类型代码
     * @return 合同编号
     */
    public static String generateContractNumberByCode(Integer typeCode) {
        ContractTypeEnum contractType = ContractTypeEnum.getByCode(typeCode != null ? typeCode : 99);
        return generateContractNumber(contractType);
    }
    
    /**
     * 获取下一个序列号
     * 
     * @param prefix 前缀
     * @param dateStr 日期字符串
     * @return 序列号
     */
    private static String getNextSequence(String prefix, String dateStr) {
        // 在实际应用中，应该使用数据库或分布式缓存确保唯一性
        String key = prefix + "-" + dateStr;
        Integer currentSequence = sequenceMap.getOrDefault(key, 0);
        currentSequence++;
        sequenceMap.put(key, currentSequence);
        
        // 返回6位序列号，不足前面补0
        return String.format("%06d", currentSequence);
    }
}