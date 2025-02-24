package com.lawfirm.common.util.excel;

import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.FileUtils;
import com.lawfirm.common.util.BaseUtils;

import java.io.*;
import java.util.List;

/**
 * Excel写入工具类
 */
@Slf4j
public class ExcelWriter extends BaseUtils {
    
    /**
     * 写入数据到Excel输出流
     * @param data 要写入的数据
     * @param outputStream 输出流
     */
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

    /**
     * 写入数据到Excel文件
     * @param filePath 文件路径
     * @param dataList 数据列表
     */
    public static void writeToFile(String filePath, List<?> dataList) {
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getWriter(filePath);
        try {
            writer.write(dataList, true);
        } finally {
            writer.close();
        }
    }
    
    /**
     * 写入数据到指定Sheet的Excel文件
     * @param filePath 文件路径
     * @param dataList 数据列表
     * @param sheetName sheet名称
     */
    public static void writeToSheet(String filePath, List<?> dataList, String sheetName) {
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getWriter(filePath, sheetName);
        try {
            writer.write(dataList, true);
        } finally {
            writer.close();
        }
    }
    
    /**
     * 合并单元格
     * @param filePath 文件路径
     * @param firstRow 起始行
     * @param lastRow 结束行
     * @param firstCol 起始列
     * @param lastCol 结束列
     */
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
    
    /**
     * 设置列宽
     * @param filePath 文件路径
     * @param columnIndex 列索引
     * @param width 宽度
     */
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
    
    /**
     * 添加图片
     * @param filePath 文件路径
     * @param imageFile 图片文件
     * @param row 行号
     * @param column 列号
     */
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