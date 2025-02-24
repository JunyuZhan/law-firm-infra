package com.lawfirm.common.util.excel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelWriterTest {
    
    @TempDir
    Path tempDir;

    @Test
    void testWrite() {
        // 准备测试数据
        List<List<String>> data = Arrays.asList(
            Arrays.asList("姓名", "年龄", "性别"),
            Arrays.asList("张三", "25", "男"),
            Arrays.asList("李四", "30", "女")
        );

        // 测试写入到输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExcelWriter.write(data, outputStream);

        // 验证输出流不为空且有数据
        byte[] excelData = outputStream.toByteArray();
        assertTrue(excelData.length > 0);
    }

    @Test
    void testWriteToFile() throws Exception {
        // 准备测试数据
        List<TestPerson> data = Arrays.asList(
            new TestPerson("张三", 25, "男"),
            new TestPerson("李四", 30, "女")
        );

        // 测试写入到文件
        File tempFile = tempDir.resolve("test_write.xlsx").toFile();
        ExcelWriter.writeToFile(tempFile.getAbsolutePath(), data);

        // 验证文件存在且不为空
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);

        // 验证文件内容
        List<TestPerson> readResult = ExcelReader.readToBean(tempFile.getAbsolutePath(), TestPerson.class);
        assertEquals(2, readResult.size());
        assertEquals("张三", readResult.get(0).getName());
        assertEquals(30, readResult.get(1).getAge());
    }

    @Test
    void testWriteToSheet() throws Exception {
        // 准备测试数据
        List<TestPerson> data = Arrays.asList(
            new TestPerson("张三", 25, "男"),
            new TestPerson("李四", 30, "女")
        );

        // 测试写入到指定Sheet
        File tempFile = tempDir.resolve("test_sheet.xlsx").toFile();
        ExcelWriter.writeToSheet(tempFile.getAbsolutePath(), data, "人员信息");

        // 验证文件存在且不为空
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);
    }

    @Test
    void testMergeCells() throws Exception {
        // 准备测试文件
        File tempFile = tempDir.resolve("test_merge.xlsx").toFile();
        List<List<String>> data = Arrays.asList(
            Arrays.asList("标题", "", ""),
            Arrays.asList("数据1", "数据2", "数据3")
        );
        
        // 写入初始数据
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ExcelWriter.write(data, out);
            org.apache.commons.io.FileUtils.writeByteArrayToFile(tempFile, out.toByteArray());
        }

        // 测试合并单元格
        ExcelWriter.mergeCells(tempFile.getAbsolutePath(), 0, 0, 0, 2);

        // 验证文件存在且不为空
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);
    }

    @Test
    void testSetColumnWidth() throws Exception {
        // 准备测试文件
        File tempFile = tempDir.resolve("test_width.xlsx").toFile();
        List<List<String>> data = Arrays.asList(
            Arrays.asList("姓名", "年龄", "性别"),
            Arrays.asList("张三", "25", "男")
        );
        
        // 写入初始数据
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ExcelWriter.write(data, out);
            org.apache.commons.io.FileUtils.writeByteArrayToFile(tempFile, out.toByteArray());
        }

        // 测试设置列宽
        ExcelWriter.setColumnWidth(tempFile.getAbsolutePath(), 0, 20);

        // 验证文件存在且不为空
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);
    }

    // 测试用的Bean类
    public static class TestPerson {
        private String name;
        private int age;
        private String gender;

        public TestPerson() {}

        public TestPerson(String name, int age, String gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        // getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
    }
} 