package com.lawfirm.common.util.excel;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelUtils {

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

    public static void write(List<List<String>> data, OutputStream outputStream) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i);
                List<String> rowData = data.get(i);
                for (int j = 0; j < rowData.size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(rowData.get(j));
                }
            }
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("Write Excel error", e);
        }
    }

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

    // 读取Excel
    public static List<Map<String, Object>> readExcel(String filePath) {
        ExcelReader reader = ExcelUtil.getReader(filePath);
        return reader.readAll();
    }
    
    public static <T> List<T> readExcel(String filePath, Class<T> beanType) {
        ExcelReader reader = ExcelUtil.getReader(filePath);
        return reader.readAll(beanType);
    }
    
    // 写入Excel
    public static void writeExcel(String filePath, List<?> dataList) {
        ExcelWriter writer = ExcelUtil.getWriter(filePath);
        writer.write(dataList, true);
        writer.close();
    }
    
    public static void writeExcel(String filePath, List<?> dataList, String sheetName) {
        ExcelWriter writer = ExcelUtil.getWriter(filePath, sheetName);
        writer.write(dataList, true);
        writer.close();
    }
    
    // 合并单元格
    public static void mergeCells(String filePath, int firstRow, int lastRow, int firstCol, int lastCol) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            log.error("Merge cells error", e);
        }
    }
    
    // 设置列宽
    public static void setColumnWidth(String filePath, int columnIndex, int width) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            sheet.setColumnWidth(columnIndex, width * 256);
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            log.error("Set column width error", e);
        }
    }
    
    // 添加图片
    public static void addPicture(String filePath, File imageFile, int row, int column) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            int pictureIdx = workbook.addPicture(FileUtils.readFileToByteArray(imageFile),
                    Workbook.PICTURE_TYPE_PNG);
            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(column);
            anchor.setRow1(row);
            drawing.createPicture(anchor, pictureIdx);
            
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            log.error("Add picture error", e);
        }
    }
} 