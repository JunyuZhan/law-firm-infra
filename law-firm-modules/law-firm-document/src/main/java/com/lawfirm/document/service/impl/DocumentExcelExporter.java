package com.lawfirm.document.service.impl;

import com.lawfirm.model.document.entity.Document;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
public class DocumentExcelExporter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private static final String[] HEADERS = {
        "文档编号", "文档名称", "文档类型", "文件大小(KB)", "文件类型",
        "版本", "状态", "上传时间", "上传人", "最后修改时间"
    };

    public byte[] export(List<Document> documents) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("文档列表");
            
            // 创建标题行样式
            CellStyle headerStyle = createHeaderStyle(workbook);
            
            // 创建标题行
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 15 * 256); // 设置列宽
            }
            
            // 创建数据行
            int rowNum = 1;
            CellStyle dataStyle = createDataStyle(workbook);
            
            for (Document document : documents) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(document.getDocumentNumber());
                row.createCell(1).setCellValue(document.getDocumentName());
                row.createCell(2).setCellValue(document.getDocumentType().toString());
                row.createCell(3).setCellValue(document.getFileSize() / 1024.0);
                row.createCell(4).setCellValue(document.getFileType());
                row.createCell(5).setCellValue(document.getVersion());
                row.createCell(6).setCellValue(document.getStatus().toString());
                row.createCell(7).setCellValue(document.getCreateTime().format(DATE_FORMATTER));
                row.createCell(8).setCellValue(document.getCreateBy());
                
                if (document.getLastModifiedTime() != null) {
                    row.createCell(9).setCellValue(document.getLastModifiedTime().format(DATE_FORMATTER));
                }
                
                // 应用数据行样式
                for (int i = 0; i < HEADERS.length; i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        cell.setCellStyle(dataStyle);
                    }
                }
            }
            
            // 写入输出流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
    
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
    
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }
} 