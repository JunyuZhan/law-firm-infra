package com.lawfirm.common.util.excel;

import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.lawfirm.common.util.BaseUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Excel读取工具类
 */
@Slf4j
public class ExcelReader extends BaseUtils {
    
    /**
     * 从输入流读取Excel数据
     * @param inputStream Excel文件输入流
     * @return 读取到的数据列表
     */
    public static List<List<String>> read(InputStream inputStream) {
        List<List<String>> result = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(getCellValue(cell));
                }
                result.add(rowData);
            }
        } catch (IOException e) {
            log.error("Read Excel error", e);
        }
        return result;
    }

    /**
     * 读取Excel文件为Map列表
     * @param filePath Excel文件路径
     * @return 读取到的数据列表
     */
    public static List<Map<String, Object>> readToMap(String filePath) {
        cn.hutool.poi.excel.ExcelReader reader = ExcelUtil.getReader(filePath);
        return reader.readAll();
    }
    
    /**
     * 读取Excel文件并转换为指定类型的对象列表
     * @param filePath Excel文件路径
     * @param beanType 目标类型
     * @return 读取到的数据列表
     */
    public static <T> List<T> readToBean(String filePath, Class<T> beanType) {
        cn.hutool.poi.excel.ExcelReader reader = ExcelUtil.getReader(filePath);
        return reader.readAll(beanType);
    }

    /**
     * 获取单元格的值
     * @param cell 单元格
     * @return 单元格的值
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                }
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
} 