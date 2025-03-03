package com.lawfirm.core.storage.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 文件内容工具类
 */
public class FileContentUtils {

    /**
     * 将Base64字符串转换为输入流
     *
     * @param base64Content Base64编码的字符串
     * @return 输入流
     */
    public static InputStream base64ToInputStream(String base64Content) {
        if (!StringUtils.hasText(base64Content)) {
            return null;
        }
        
        // 去除可能的前缀
        String actualContent = base64Content;
        if (base64Content.contains(",")) {
            actualContent = base64Content.split(",")[1];
        }
        
        byte[] bytes = Base64.getDecoder().decode(actualContent);
        return new ByteArrayInputStream(bytes);
    }
    
    /**
     * 将输入流转换为Base64字符串
     *
     * @param inputStream 输入流
     * @return Base64编码的字符串
     * @throws IOException IO异常
     */
    public static String inputStreamToBase64(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        
        byte[] bytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }
    
    /**
     * 将MultipartFile转换为File
     *
     * @param multipartFile multipart文件
     * @param targetFile 目标文件
     * @throws IOException IO异常
     */
    public static void multipartFileToFile(MultipartFile multipartFile, File targetFile) throws IOException {
        if (multipartFile == null || targetFile == null) {
            return;
        }
        
        // 确保目录存在
        File parentDir = targetFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        // 写入文件
        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            outputStream.write(multipartFile.getBytes());
        }
    }
    
    /**
     * 将File转换为ByteArray
     *
     * @param file 文件
     * @return 字节数组
     * @throws IOException IO异常
     */
    public static byte[] fileToByteArray(File file) throws IOException {
        if (file == null || !file.exists()) {
            return new byte[0];
        }
        
        try (FileInputStream inputStream = new FileInputStream(file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) file.length())) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            return outputStream.toByteArray();
        }
    }
    
    /**
     * 将InputStream转换为ByteArray
     *
     * @param inputStream 输入流
     * @return 字节数组
     * @throws IOException IO异常
     */
    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new byte[0];
        }
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            return outputStream.toByteArray();
        }
    }
    
    /**
     * 将ByteArray转换为InputStream
     *
     * @param bytes 字节数组
     * @return 输入流
     */
    public static InputStream byteArrayToInputStream(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        
        return new ByteArrayInputStream(bytes);
    }
    
    /**
     * 读取输入流内容为字符串
     *
     * @param inputStream 输入流
     * @return 字符串内容
     * @throws IOException IO异常
     */
    public static String readInputStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }
        
        byte[] bytes = inputStreamToByteArray(inputStream);
        return new String(bytes, StandardCharsets.UTF_8);
    }
    
    /**
     * 读取文件内容为字符串
     *
     * @param file 文件
     * @return 字符串内容
     * @throws IOException IO异常
     */
    public static String readFile(File file) throws IOException {
        if (file == null || !file.exists()) {
            return "";
        }
        
        byte[] bytes = fileToByteArray(file);
        return new String(bytes, StandardCharsets.UTF_8);
    }
    
    /**
     * 写字符串内容到文件
     *
     * @param file 文件
     * @param content 内容
     * @throws IOException IO异常
     */
    public static void writeFile(File file, String content) throws IOException {
        if (file == null || content == null) {
            return;
        }
        
        // 确保目录存在
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        // 写入文件
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }
    
    /**
     * 读取文件的部分内容
     *
     * @param file 文件
     * @param start 开始位置
     * @param length 读取长度
     * @return 字节数组
     * @throws IOException IO异常
     */
    public static byte[] readFileChunk(File file, long start, int length) throws IOException {
        if (file == null || !file.exists() || start < 0 || length <= 0) {
            return new byte[0];
        }
        
        try (FileInputStream inputStream = new FileInputStream(file)) {
            // 跳过开始部分
            long skipped = inputStream.skip(start);
            if (skipped < start) {
                return new byte[0];
            }
            
            // 读取指定长度
            byte[] buffer = new byte[length];
            int bytesRead = inputStream.read(buffer);
            
            if (bytesRead <= 0) {
                return new byte[0];
            } else if (bytesRead < length) {
                // 实际读取长度小于请求长度，返回实际读取的部分
                byte[] result = new byte[bytesRead];
                System.arraycopy(buffer, 0, result, 0, bytesRead);
                return result;
            } else {
                return buffer;
            }
        }
    }
} 