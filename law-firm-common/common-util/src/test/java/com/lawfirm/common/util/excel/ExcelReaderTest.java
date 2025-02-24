package com.lawfirm.common.util.excel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExcelReaderTest {
    
    @TempDir
    Path tempDir;

    @Test
    void testRead() throws Exception {
        // 创建测试Excel文件
        File tempFile = tempDir.resolve("test.xlsx").toFile();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            ExcelWriter.write(Arrays.asList(
                Arrays.asList("姓名", "年龄", "性别"),
                Arrays.asList("张三", "25", "男"),
                Arrays.asList("李四", "30", "女")
            ), out);
        }

        // 测试读取
        List<Map<String, Object>> result = ExcelReader.readToMap(tempFile.getAbsolutePath());
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("张三", result.get(0).get("姓名"));
        assertEquals("30", result.get(1).get("年龄"));
    }

    @Test
    void testReadFromInputStream() throws Exception {
        // 创建一个Excel工作簿
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            
            // 创建测试数据
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("姓名");
            headerRow.createCell(1).setCellValue("年龄");
            
            Row dataRow1 = sheet.createRow(1);
            dataRow1.createCell(0).setCellValue("张三");
            dataRow1.createCell(1).setCellValue("25");
            
            Row dataRow2 = sheet.createRow(2);
            dataRow2.createCell(0).setCellValue("李四");
            dataRow2.createCell(1).setCellValue("30");
            
            // 将工作簿写入字节数组
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            
            // 从字节数组创建输入流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bos.toByteArray());
            
            // 测试读取
            List<List<String>> result = ExcelReader.read(inputStream);
            
            // 验证结果
            assertNotNull(result);
            assertEquals(3, result.size()); // 包含标题行
            assertEquals(Arrays.asList("姓名", "年龄"), result.get(0));
            assertEquals(Arrays.asList("张三", "25"), result.get(1));
            assertEquals(Arrays.asList("李四", "30"), result.get(2));
        }
    }

    @Test
    void testReadToBean() throws Exception {
        // 创建测试Excel文件
        File tempFile = tempDir.resolve("test.xlsx").toFile();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            ExcelWriter.write(Arrays.asList(
                Arrays.asList("name", "age", "gender"),
                Arrays.asList("张三", "25", "男"),
                Arrays.asList("李四", "30", "女")
            ), out);
        }

        // 测试读取为Bean
        List<TestPerson> result = ExcelReader.readToBean(tempFile.getAbsolutePath(), TestPerson.class);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("张三", result.get(0).getName());
        assertEquals(30, result.get(1).getAge());
    }

    // 测试用的Bean类
    public static class TestPerson {
        private String name;
        private int age;
        private String gender;

        // getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
    }
} 