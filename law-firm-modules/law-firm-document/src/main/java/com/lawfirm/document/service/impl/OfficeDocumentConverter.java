package com.lawfirm.document.service.impl;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

@Slf4j
@Service
public class OfficeDocumentConverter {

    public byte[] convertWordToPdf(byte[] wordContent) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(wordContent);
             XWPFDocument document = new XWPFDocument(inputStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, outputStream, options);
            
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("转换Word到PDF失败", e);
            throw new RuntimeException("转换Word到PDF失败", e);
        }
    }

    public byte[] convertExcelToPdf(byte[] excelBytes) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelBytes));
             PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputStream));
             Document document = new Document(pdfDoc)) {

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                if (i > 0) {
                    document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }
                document.add(convertSheetToTable(sheet));
            }

            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("转换Excel到PDF失败", e);
            throw new RuntimeException("转换Excel到PDF失败", e);
        }
    }

    public byte[] convertPptToPdf(byte[] pptBytes) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             XMLSlideShow ppt = new XMLSlideShow(new ByteArrayInputStream(pptBytes));
             PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputStream));
             Document document = new Document(pdfDoc)) {

            for (XSLFSlide slide : ppt.getSlides()) {
                if (slide.getSlideNumber() > 1) {
                    document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }
                
                java.awt.Dimension pgsize = ppt.getPageSize();
                float width = (float) pgsize.getWidth();
                float height = (float) pgsize.getHeight();
                
                BufferedImage img = new BufferedImage(
                    (int) width, 
                    (int) height, 
                    BufferedImage.TYPE_INT_RGB
                );
                slide.draw(img.createGraphics());
                
                ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
                javax.imageio.ImageIO.write(img, "PNG", imgBytes);
                
                ImageData imageData = ImageDataFactory.create(imgBytes.toByteArray());
                Image pdfImg = new Image(imageData);
                pdfImg.setWidth(width);
                pdfImg.setHeight(height);
                document.add(pdfImg);
            }

            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("转换PPT到PDF失败", e);
            throw new RuntimeException("转换PPT到PDF失败", e);
        }
    }

    private com.itextpdf.layout.element.Table convertSheetToTable(XSSFSheet sheet) {
        int columnCount = getColumnCount(sheet);
        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(UnitValue.createPercentArray(columnCount));
        table.setWidth(UnitValue.createPercentValue(100));

        // 添加表头
        XSSFRow headerRow = sheet.getRow(0);
        if (headerRow != null) {
            for (int i = 0; i < columnCount; i++) {
                XSSFCell cell = headerRow.getCell(i);
                com.itextpdf.layout.element.Cell headerCell = new com.itextpdf.layout.element.Cell();
                headerCell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                headerCell.add(new Paragraph(getCellValueAsString(cell)));
                table.addCell(headerCell);
            }
        }

        // 添加数据行
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                for (int j = 0; j < columnCount; j++) {
                    XSSFCell cell = row.getCell(j);
                    com.itextpdf.layout.element.Cell dataCell = new com.itextpdf.layout.element.Cell();
                    dataCell.add(new Paragraph(getCellValueAsString(cell)));
                    table.addCell(dataCell);
                }
            }
        }

        return table;
    }

    private int getColumnCount(XSSFSheet sheet) {
        int maxColumns = 0;
        for (Row row : sheet) {
            maxColumns = Math.max(maxColumns, row.getLastCellNum());
        }
        return maxColumns;
    }

    private String getCellValueAsString(XSSFCell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    return cell.getStringCellValue();
                }
            default:
                return "";
        }
    }
} 