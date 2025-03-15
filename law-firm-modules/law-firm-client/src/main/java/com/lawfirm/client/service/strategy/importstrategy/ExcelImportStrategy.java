package com.lawfirm.client.service.strategy.importstrategy;

import com.lawfirm.client.constant.ClientModuleConstant;
import com.lawfirm.client.util.ClientConverter;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.entity.common.ClientContact;
import com.lawfirm.model.client.mapper.ClientMapper;
import com.lawfirm.model.client.mapper.ContactMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel导入策略实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExcelImportStrategy implements ImportStrategy {
    
    private static final String TYPE = "excel";
    private static final String[] SUPPORTED_EXTENSIONS = {".xlsx", ".xls"};
    
    private final ClientMapper clientMapper;
    private final ContactMapper contactMapper;
    
    @Override
    public String getType() {
        return TYPE;
    }
    
    @Override
    public List<String> validate(InputStream inputStream) {
        List<String> errors = new ArrayList<>();
        
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            
            // 检查表头
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                errors.add("Excel文件格式错误：缺少表头");
                return errors;
            }
            
            // 检查必填列是否存在
            if (!hasRequiredColumns(headerRow)) {
                errors.add("Excel文件格式错误：缺少必填列");
                return errors;
            }
            
            // 检查数据行
            int rowCount = 0;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                
                rowCount++;
                
                // 检查数据行是否超过最大限制
                if (rowCount > ClientModuleConstant.ImportExport.MAX_IMPORT_BATCH_SIZE) {
                    errors.add("导入数据超过最大限制：" + ClientModuleConstant.ImportExport.MAX_IMPORT_BATCH_SIZE);
                    break;
                }
                
                // 检查必填字段
                String clientName = getCellValueAsString(row.getCell(0));
                if (StringUtils.isBlank(clientName)) {
                    errors.add("第" + (i + 1) + "行：客户名称不能为空");
                }
                
                // 检查客户类型
                String clientType = getCellValueAsString(row.getCell(1));
                if (StringUtils.isBlank(clientType)) {
                    errors.add("第" + (i + 1) + "行：客户类型不能为空");
                }
                
                // 其他字段验证...
            }
            
            // 检查是否有数据
            if (rowCount == 0) {
                errors.add("Excel文件中没有数据");
            }
            
        } catch (IOException e) {
            log.error("验证Excel文件失败", e);
            errors.add("Excel文件读取失败：" + e.getMessage());
        }
        
        return errors;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImportStrategy.ImportResult importClients(InputStream inputStream, Long operatorId) {
        ImportStrategy.ImportResult result = new ImportStrategy.ImportResult();
        List<String> errors = new ArrayList<>();
        
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            
            // 跳过表头，从第二行开始读取
            int totalCount = 0;
            int successCount = 0;
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                
                totalCount++;
                
                try {
                    // 解析客户数据
                    Client client = parseClient(row, operatorId);
                    
                    // 保存客户
                    clientMapper.insert(client);
                    
                    // 解析并保存联系人
                    ClientContact contact = parseContact(row, client.getId(), operatorId);
                    if (contact != null) {
                        contactMapper.insert(contact);
                    }
                    
                    successCount++;
                } catch (Exception e) {
                    log.error("导入第{}行数据失败", i + 1, e);
                    errors.add("第" + (i + 1) + "行：" + e.getMessage());
                }
            }
            
            result.setTotalCount(totalCount);
            result.setSuccessCount(successCount);
            result.setFailureCount(totalCount - successCount);
            result.setErrorMessages(errors);
            
        } catch (IOException e) {
            log.error("导入Excel文件失败", e);
            errors.add("Excel文件读取失败：" + e.getMessage());
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
     * @param headerRow 表头行
     * @return 是否包含必填列
     */
    private boolean hasRequiredColumns(Row headerRow) {
        // 检查必填列：客户名称、客户类型、联系电话
        String[] requiredColumns = {"客户名称", "客户类型", "联系电话"};
        
        for (int i = 0; i < requiredColumns.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || !requiredColumns[i].equals(getCellValueAsString(cell))) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 解析客户数据
     * @param row 数据行
     * @param operatorId 操作人ID
     * @return 客户实体
     */
    private Client parseClient(Row row, Long operatorId) {
        Client client = new Client();
        
        // 设置基本信息
        client.setClientName(getCellValueAsString(row.getCell(0)));
        client.setClientType(parseClientType(getCellValueAsString(row.getCell(1))));
        client.setPhone(getCellValueAsString(row.getCell(2)));
        client.setEmail(getCellValueAsString(row.getCell(3)));
        client.setIndustry(getCellValueAsString(row.getCell(4)));
        client.setScale(getCellValueAsString(row.getCell(5)));
        
        // 设置创建人 - Client继承自ModelBaseEntity，其createBy是String类型
        client.setCreateBy(String.valueOf(operatorId));
        
        return client;
    }
    
    /**
     * 解析联系人数据
     * @param row 数据行
     * @param clientId 客户ID
     * @param operatorId 操作人ID
     * @return 联系人实体
     */
    private ClientContact parseContact(Row row, Long clientId, Long operatorId) {
        // 检查是否有联系人信息
        String contactName = getCellValueAsString(row.getCell(6));
        if (StringUtils.isBlank(contactName)) {
            return null;
        }
        
        ClientContact contact = new ClientContact();
        contact.setClientId(clientId);
        contact.setContactName(contactName);
        contact.setMobile(getCellValueAsString(row.getCell(7)));
        contact.setEmail(getCellValueAsString(row.getCell(8)));
        contact.setPosition(getCellValueAsString(row.getCell(9)));
        contact.setDepartment(getCellValueAsString(row.getCell(10)));
        contact.setIsDefault(1); // 设为默认联系人
        
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
    
    /**
     * 获取单元格的字符串值
     * @param cell 单元格
     * @return 字符串值
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // 避免数字显示为科学计数法
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
} 