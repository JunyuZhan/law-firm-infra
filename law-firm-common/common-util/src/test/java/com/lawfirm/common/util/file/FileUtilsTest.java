package com.lawfirm.common.util.file;

import com.lawfirm.common.util.BaseUtilTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest extends BaseUtilTest {

    @TempDir
    Path tempDir;

    @Test
    void generateUniqueFileName_ShouldGenerateUniqueNames() {
        // 准备测试数据
        String filename1 = "test.jpg";
        String filename2 = "test.jpg";
        
        // 执行测试
        String result1 = FileUtils.generateUniqueFileName(filename1);
        String result2 = FileUtils.generateUniqueFileName(filename2);
        
        // 验证结果
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotEquals(result1, result2);
        assertTrue(result1.endsWith(".jpg"));
        assertTrue(result2.endsWith(".jpg"));
    }

    @Test
    void isValidImageFile_ShouldValidateImageTypes() {
        // 准备测试数据
        MockMultipartFile jpgFile = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", "test".getBytes());
        MockMultipartFile txtFile = new MockMultipartFile("test.txt", "test.txt", "text/plain", "test".getBytes());
        
        // 执行测试和验证结果
        assertTrue(FileUtils.isValidImageFile(jpgFile));
        assertFalse(FileUtils.isValidImageFile(txtFile));
    }

    @Test
    void isValidDocFile_ShouldValidateDocTypes() {
        // 准备测试数据
        MockMultipartFile docFile = new MockMultipartFile("test.doc", "test.doc", "application/msword", "test".getBytes());
        MockMultipartFile txtFile = new MockMultipartFile("test.txt", "test.txt", "text/plain", "test".getBytes());
        
        // 执行测试和验证结果
        assertTrue(FileUtils.isValidDocFile(docFile));
        assertFalse(FileUtils.isValidDocFile(txtFile));
    }

    @Test
    void readAndWriteFile_ShouldWorkCorrectly() throws IOException {
        // 准备测试数据
        Path filePath = tempDir.resolve("test.txt");
        String content = "测试内容";
        
        // 测试写入
        boolean writeResult = FileUtils.writeFile(filePath.toString(), content);
        assertTrue(writeResult);
        assertTrue(Files.exists(filePath));
        
        // 测试读取
        String readContent = FileUtils.readFile(filePath.toString());
        assertEquals(content, readContent);
    }

    @Test
    void copyAndDeleteFile_ShouldWorkCorrectly() throws IOException {
        // 准备测试数据
        Path sourcePath = tempDir.resolve("source.txt");
        Path targetPath = tempDir.resolve("target.txt");
        Files.writeString(sourcePath, "测试内容");
        
        // 测试复制
        boolean copyResult = FileUtils.copyFile(sourcePath.toString(), targetPath.toString());
        assertTrue(copyResult);
        assertTrue(Files.exists(targetPath));
        assertEquals(Files.readString(sourcePath), Files.readString(targetPath));
        
        // 测试删除
        boolean deleteResult = FileUtils.deleteFile(targetPath.toString());
        assertTrue(deleteResult);
        assertFalse(Files.exists(targetPath));
    }

    @Test
    void createDirectory_ShouldCreateDirectories() throws IOException {
        // 准备测试数据
        Path dirPath = tempDir.resolve("test/subdir");
        
        // 执行测试
        boolean result = FileUtils.createDirectory(dirPath.toString());
        
        // 验证结果
        assertTrue(result);
        assertTrue(Files.exists(dirPath));
        assertTrue(Files.isDirectory(dirPath));
    }

    @Test
    void getExtension_ShouldReturnCorrectExtension() {
        assertEquals("jpg", FileUtils.getExtension("test.jpg"));
        assertEquals("", FileUtils.getExtension("test"));
        assertEquals("gz", FileUtils.getExtension("test.tar.gz"));
    }

    @Test
    void zipFiles_ShouldCreateZipFile() throws IOException {
        // 准备测试数据
        Path file1 = tempDir.resolve("test1.txt");
        Path file2 = tempDir.resolve("test2.txt");
        Path zipFile = tempDir.resolve("test.zip");
        
        Files.writeString(file1, "测试内容1");
        Files.writeString(file2, "测试内容2");
        
        List<String> files = Arrays.asList(
            file1.toString(),
            file2.toString()
        );
        
        // 执行测试
        boolean result = FileUtils.zipFiles(files, zipFile.toString());
        
        // 验证结果
        assertTrue(result);
        assertTrue(Files.exists(zipFile));
        assertTrue(Files.size(zipFile) > 0);
    }

    @Test
    void getFileSizeInMB_ShouldReturnCorrectSize() throws IOException {
        // 准备测试数据
        Path file = tempDir.resolve("test.txt");
        byte[] content = new byte[1024 * 1024]; // 1MB
        Files.write(file, content);
        
        // 执行测试
        long size = FileUtils.getFileSizeInMB(file);
        
        // 验证结果
        assertEquals(1, size);
    }

    @Test
    void getFileType_ShouldDetectCorrectType() throws IOException {
        // 准备测试数据
        Path txtFile = tempDir.resolve("test.txt");
        Files.writeString(txtFile, "测试内容");
        
        // 执行测试
        String type = FileUtils.getFileType(txtFile);
        
        // 验证结果
        assertNotNull(type);
        // 注意：实际类型可能因系统而异，这里只验证非空
    }

    @Test
    void getFileType_ShouldHandleNonExistentFile() {
        // 准备测试数据
        Path nonExistentFile = tempDir.resolve("non-existent.txt");
        
        // 执行测试
        String type = FileUtils.getFileType(nonExistentFile);
        
        // 验证结果
        assertNull(type);
    }

    @Test
    void exists_ShouldCheckFileExistence() throws IOException {
        // 准备测试数据
        Path existingFile = tempDir.resolve("existing.txt");
        Path nonExistentFile = tempDir.resolve("non-existent.txt");
        Files.writeString(existingFile, "测试内容");
        
        // 执行测试和验证结果
        assertTrue(FileUtils.exists(existingFile));
        assertFalse(FileUtils.exists(nonExistentFile));
    }

    @Test
    void isDirectory_ShouldCheckDirectoryType() throws IOException {
        // 准备测试数据
        Path directory = tempDir.resolve("testDir");
        Path file = tempDir.resolve("test.txt");
        Files.createDirectory(directory);
        Files.writeString(file, "测试内容");
        
        // 执行测试和验证结果
        assertTrue(FileUtils.isDirectory(directory));
        assertFalse(FileUtils.isDirectory(file));
    }

    @Test
    void createDirectoryIfNotExists_ShouldHandleExistingDirectory() throws IOException {
        // 准备测试数据
        Path directory = tempDir.resolve("existingDir");
        Files.createDirectory(directory);
        
        // 执行测试
        FileUtils.createDirectoryIfNotExists(directory.toString());
        
        // 验证结果
        assertTrue(Files.exists(directory));
        assertTrue(Files.isDirectory(directory));
    }

    @Test
    void createDirectoryIfNotExists_ShouldCreateNewDirectory() throws IOException {
        // 准备测试数据
        Path directory = tempDir.resolve("newDir");
        
        // 执行测试
        FileUtils.createDirectoryIfNotExists(directory.toString());
        
        // 验证结果
        assertTrue(Files.exists(directory));
        assertTrue(Files.isDirectory(directory));
    }

    @Test
    void moveFile_ShouldMoveFile() throws IOException {
        // 准备测试数据
        Path source = tempDir.resolve("source.txt");
        Path target = tempDir.resolve("target.txt");
        Files.writeString(source, "测试内容");
        
        // 执行测试
        FileUtils.moveFile(source, target);
        
        // 验证结果
        assertFalse(Files.exists(source));
        assertTrue(Files.exists(target));
        assertEquals("测试内容", Files.readString(target));
    }

    @Test
    void copyFile_ShouldHandleNonExistentSource() {
        // 准备测试数据
        Path source = tempDir.resolve("non-existent.txt");
        Path target = tempDir.resolve("target.txt");
        
        // 执行测试和验证结果
        assertThrows(IOException.class, () -> 
            FileUtils.copyFile(source, target));
    }

    @Test
    void zipFiles_ShouldHandleNonExistentFiles() throws IOException {
        // 准备测试数据
        Path zipFile = tempDir.resolve("test.zip");
        List<String> files = Arrays.asList(
            tempDir.resolve("non-existent1.txt").toString(),
            tempDir.resolve("non-existent2.txt").toString()
        );
        
        // 执行测试
        boolean result = FileUtils.zipFiles(files, zipFile.toString());
        
        // 验证结果
        assertTrue(result);
        assertTrue(Files.exists(zipFile));
        // 空ZIP文件的大小应该很小
        assertTrue(Files.size(zipFile) < 100);
    }

    @Test
    void zipFiles_ShouldHandleLargeFiles() throws IOException {
        // 准备测试数据
        Path largeFile = tempDir.resolve("large.dat");
        Path zipFile = tempDir.resolve("large.zip");
        
        // 创建一个5MB的文件
        byte[] data = new byte[5 * 1024 * 1024];
        Files.write(largeFile, data);
        
        List<String> files = Arrays.asList(largeFile.toString());
        
        // 执行测试
        boolean result = FileUtils.zipFiles(files, zipFile.toString());
        
        // 验证结果
        assertTrue(result);
        assertTrue(Files.exists(zipFile));
        assertTrue(Files.size(zipFile) > 0);
    }

    @Test
    void getFileSizeInMB_ShouldHandleNonExistentFile() {
        // 准备测试数据
        Path nonExistentFile = tempDir.resolve("non-existent.txt");
        
        // 执行测试
        long size = FileUtils.getFileSizeInMB(nonExistentFile);
        
        // 验证结果
        assertEquals(-1, size);
    }

    @Test
    void getFileSizeInMB_ShouldHandleEmptyFile() throws IOException {
        // 准备测试数据
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.createFile(emptyFile);
        
        // 执行测试
        long size = FileUtils.getFileSizeInMB(emptyFile);
        
        // 验证结果
        assertEquals(0, size);
    }
} 