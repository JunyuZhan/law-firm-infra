package com.lawfirm.client.service.strategy.importstrategy;

import com.lawfirm.client.constant.ClientModuleConstant;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.entity.common.ClientContact;
import com.lawfirm.model.client.mapper.ClientMapper;
import com.lawfirm.model.client.mapper.ContactMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV导入策略实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CsvImportStrategy implements ImportStrategy {
    
    private static final String TYPE = "csv";
    private static final String[] SUPPORTED_EXTENSIONS = {".csv"};
    
    private final ClientMapper clientMapper;
    private final ContactMapper contactMapper;
    
    @Override
    public String getType() {
        return TYPE;
    }
    
    @Override
    public List<String> validate(InputStream inputStream) {
        List<String> errors = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser parser = CSVFormat.Builder.create()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)) {
            
            // 检查表头
            if (!hasRequiredColumns(parser)) {
                errors.add("CSV文件格式错误：缺少必填列");
                return errors;
            }
            
            // 检查数据行
            int rowCount = 0;
            for (CSVRecord record : parser) {
                rowCount++;
                
                // 检查数据行是否超过最大限制
                if (rowCount > ClientModuleConstant.ImportExport.MAX_IMPORT_BATCH_SIZE) {
                    errors.add("导入数据超过最大限制：" + ClientModuleConstant.ImportExport.MAX_IMPORT_BATCH_SIZE);
                    break;
                }
                
                // 检查必填字段
                String clientName = record.get("客户名称");
                if (StringUtils.isBlank(clientName)) {
                    errors.add("第" + (rowCount + 1) + "行：客户名称不能为空");
                }
                
                // 检查客户类型
                String clientType = record.get("客户类型");
                if (StringUtils.isBlank(clientType)) {
                    errors.add("第" + (rowCount + 1) + "行：客户类型不能为空");
                }
                
                // 检查联系电话
                String phone = record.get("联系电话");
                if (StringUtils.isBlank(phone)) {
                    errors.add("第" + (rowCount + 1) + "行：联系电话不能为空");
                }
                
                // 其他字段验证...
            }
            
            // 检查是否有数据
            if (rowCount == 0) {
                errors.add("CSV文件中没有数据");
            }
            
        } catch (IOException e) {
            log.error("验证CSV文件失败", e);
            errors.add("CSV文件读取失败：" + e.getMessage());
        }
        
        return errors;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImportStrategy.ImportResult importClients(InputStream inputStream, Long operatorId) {
        ImportStrategy.ImportResult result = new ImportStrategy.ImportResult();
        List<String> errors = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser parser = CSVFormat.Builder.create()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)) {
            
            int totalCount = 0;
            int successCount = 0;
            
            for (CSVRecord record : parser) {
                totalCount++;
                
                try {
                    // 解析客户数据
                    Client client = parseClient(record, operatorId);
                    
                    // 保存客户
                    clientMapper.insert(client);
                    
                    // 解析并保存联系人
                    ClientContact contact = parseContact(record, client.getId(), operatorId);
                    if (contact != null) {
                        contactMapper.insert(contact);
                    }
                    
                    successCount++;
                } catch (Exception e) {
                    log.error("导入第{}行数据失败", totalCount + 1, e);
                    errors.add("第" + (totalCount + 1) + "行：" + e.getMessage());
                }
            }
            
            result.setTotalCount(totalCount);
            result.setSuccessCount(successCount);
            result.setFailureCount(totalCount - successCount);
            result.setErrorMessages(errors);
            
        } catch (IOException e) {
            log.error("导入CSV文件失败", e);
            errors.add("CSV文件读取失败：" + e.getMessage());
            result.setErrorMessages(errors);
        }
        
        return result;
    }
    
    @Override
    public boolean supports(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        
        String lowerFileName = fileName.toLowerCase();
        for (String ext : SUPPORTED_EXTENSIONS) {
            if (lowerFileName.endsWith(ext)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 检查是否包含必填列
     * @param parser CSV解析器
     * @return 是否包含必填列
     */
    private boolean hasRequiredColumns(CSVParser parser) {
        // 检查必填列：客户名称、客户类型、联系电话
        String[] requiredColumns = {"客户名称", "客户类型", "联系电话"};
        
        for (String column : requiredColumns) {
            if (!parser.getHeaderMap().containsKey(column)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 解析客户数据
     * @param record CSV记录
     * @param operatorId 操作人ID
     * @return 客户实体
     */
    private Client parseClient(CSVRecord record, Long operatorId) {
        Client client = new Client();
        
        // 设置基本信息
        client.setClientName(record.get("客户名称"));
        client.setClientType(parseClientType(record.get("客户类型")));
        client.setPhone(record.get("联系电话"));
        
        // 可选字段
        if (record.isMapped("电子邮箱")) {
            client.setEmail(record.get("电子邮箱"));
        }
        if (record.isMapped("行业")) {
            client.setIndustry(record.get("行业"));
        }
        if (record.isMapped("规模")) {
            client.setScale(record.get("规模"));
        }
        
        // 设置创建人 - Client继承自ModelBaseEntity，其createBy是String类型
        client.setCreateBy(String.valueOf(operatorId));
        
        return client;
    }
    
    /**
     * 解析联系人数据
     * @param record CSV记录
     * @param clientId 客户ID
     * @param operatorId 操作人ID
     * @return 联系人实体
     */
    private ClientContact parseContact(CSVRecord record, Long clientId, Long operatorId) {
        // 检查是否有联系人信息
        if (!record.isMapped("联系人姓名") || StringUtils.isBlank(record.get("联系人姓名"))) {
            return null;
        }
        
        ClientContact contact = new ClientContact();
        contact.setClientId(clientId);
        contact.setContactName(record.get("联系人姓名"));
        
        // 可选字段
        if (record.isMapped("联系人电话")) {
            contact.setMobile(record.get("联系人电话"));
        }
        if (record.isMapped("联系人邮箱")) {
            contact.setEmail(record.get("联系人邮箱"));
        }
        if (record.isMapped("职位")) {
            contact.setPosition(record.get("职位"));
        }
        if (record.isMapped("部门")) {
            contact.setDepartment(record.get("部门"));
        }
        
        // 设为默认联系人
        contact.setIsDefault(1);
        
        // 设置创建人 - ClientContact继承自BaseModel，其createBy是Long类型
        contact.setCreateBy(operatorId);
        
        return contact;
    }
    
    /**
     * 解析客户类型
     * @param typeStr 类型字符串
     * @return 类型代码
     */
    private int parseClientType(String typeStr) {
        if ("企业".equals(typeStr)) {
            return 2;
        } else if ("个人".equals(typeStr)) {
            return 1;
        } else {
            throw new IllegalArgumentException("无效的客户类型：" + typeStr);
        }
    }
} 