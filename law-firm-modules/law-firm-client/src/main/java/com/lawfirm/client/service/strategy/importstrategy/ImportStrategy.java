package com.lawfirm.client.service.strategy.importstrategy;

import lombok.Data;

import java.io.InputStream;
import java.util.List;

/**
 * 客户导入策略接口
 */
public interface ImportStrategy {
    
    /**
     * 获取策略类型
     * @return 策略类型
     */
    String getType();
    
    /**
     * 是否支持该文件
     * @param fileName 文件名
     * @return 是否支持
     */
    boolean supports(String fileName);
    
    /**
     * 验证导入数据
     * @param inputStream 输入流
     * @return 错误信息列表
     */
    List<String> validate(InputStream inputStream);
    
    /**
     * 导入客户数据
     * @param inputStream 输入流
     * @param operatorId 操作人ID
     * @return 导入结果
     */
    ImportResult importClients(InputStream inputStream, Long operatorId);
    
    /**
     * 导入结果
     */
    @Data
    class ImportResult {
        
        /**
         * 总记录数
         */
        private int totalCount;
        
        /**
         * 成功记录数
         */
        private int successCount;
        
        /**
         * 失败记录数
         */
        private int failureCount;
        
        /**
         * 错误信息列表
         */
        private List<String> errorMessages;
    }
} 