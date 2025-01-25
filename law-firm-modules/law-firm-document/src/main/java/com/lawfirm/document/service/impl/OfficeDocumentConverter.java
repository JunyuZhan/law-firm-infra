package com.lawfirm.document.service.impl;

import com.lawfirm.document.exception.DocumentException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.AreaBreakType;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Rectangle;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.PageBreak;
import com.itextpdf.layout.property.PageSize;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Table;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Office文档转换器
 * 负责将Office文档转换为PDF格式
 */
@Slf4j
@Component
public class OfficeDocumentConverter {
    
    /**
     * 将Office文档转换为PDF
     *
     * @param file Office文档文件
     * @param fileType 文件类型
     * @return PDF文件的字节数组
     * @throws IOException IO异常
     */
    public byte[] convertToPdf(File file, String fileType) throws IOException {
        try {
            switch (fileType.toLowerCase()) {
                case "doc":
                case "docx":
                    return convertWordToPdf(file);
                case "xls":
                case "xlsx":
                    return convertExcelToPdf(file);
                case "ppt":
                case "pptx":
                    return convertPowerPointToPdf(file);
                default:
                    throw new DocumentException(
                        DocumentException.ERROR_FILE_TYPE_NOT_SUPPORTED,
                        "不支持的Office文件类型：" + fileType
                    );
            }
        } catch (Exception e) {
            log.error("Office文档转换失败", e);
            throw new DocumentException(
                DocumentException.ERROR_PREVIEW_NOT_SUPPORTED,
                "Office文档转换失败：" + e.getMessage(),
                e
            );
        }
    }
    
    private byte[] convertWordToPdf(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, out, options);
            
            return out.toByteArray();
        }
    }
    
    private byte[] convertExcelToPdf(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // 遍历每个工作表
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if (i > 0) {
                    document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }
                
                Table table = convertSheetToTable(workbook.getSheetAt(i));
                document.add(table);
            }
            
            document.close();
            return out.toByteArray();
        }
    }
    
    private byte[] convertPowerPointToPdf(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XMLSlideShow ppt = new XMLSlideShow(fis);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // 设置页面大小为PPT尺寸
            Rectangle pageSize = new Rectangle(
                ppt.getPageSize().getWidth(),
                ppt.getPageSize().getHeight()
            );
            pdf.setDefaultPageSize(new PageSize(pageSize));
            
            // 遍历每个幻灯片
            for (XSLFSlide slide : ppt.getSlides()) {
                if (slide.getSlideNumber() > 1) {
                    document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }
                
                // 将幻灯片渲染为图片
                BufferedImage image = slide.getImage();
                ImageData imageData = ImageDataFactory.create(
                    bufferedImageToBytes(image, "PNG")
                );
                Image slideImage = new Image(imageData);
                slideImage.setFixedPosition(0, 0);
                slideImage.scaleToFit(pageSize.getWidth(), pageSize.getHeight());
                
                document.add(slideImage);
            }
            
            document.close();
            return out.toByteArray();
        }
    }
    
    private Table convertSheetToTable(XSSFSheet sheet) {
        Table table = new Table(sheet.getRow(0).getLastCellNum());
        
        for (Row row : sheet) {
            for (Cell cell : row) {
                table.addCell(new Cell().add(
                    new Paragraph(getCellValueAsString(cell))
                ));
            }
        }
        
        return table;
    }
    
    private String getCellValueAsString(Cell cell) {
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
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    private byte[] bufferedImageToBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }
} 