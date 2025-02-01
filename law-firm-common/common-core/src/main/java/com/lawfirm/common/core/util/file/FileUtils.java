package com.lawfirm.common.core.util.file;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.io.FileNotFoundException;
import java.util.UUID;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

/**
 * 文件处理工具类
 */
@Slf4j
public class FileUtils {
    
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final int BUFFER_SIZE = 4096;
    
    /**
     * 获取不带后缀的文件名
     */
    public static String getFileNameWithoutExtension(String fileName) {
        return FilenameUtils.getBaseName(fileName);
    }

    /**
     * 获取文件后缀
     */
    public static String getFileExtension(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }

    /**
     * 创建目录
     */
    public static void createDirectory(String directory) throws IOException {
        Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 检查文件是否存在
     */
    public static boolean exists(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }
        return new File(filePath).exists();
    }

    /**
     * 获取文件大小
     */
    public static long getFileSize(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return 0L;
        }
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        return 0L;
    }

    /**
     * 计算文件的MD5值
     */
    public static String calculateMD5(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            byte[] md5sum = digest.digest();
            return new BigInteger(1, md5sum).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("Failed to calculate MD5", e);
        }
    }

    /**
     * 解压ZIP文件
     */
    public static void unzip(String zipFile, String destDir) throws IOException {
        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        try (ZipFile zip = new ZipFile(zipFile)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File file = new File(destDir, entry.getName());
                
                if (entry.isDirectory()) {
                    file.mkdirs();
                    continue;
                }
                
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                
                try (InputStream in = zip.getInputStream(entry);
                     FileOutputStream out = new FileOutputStream(file)) {
                    IOUtils.copy(in, out);
                }
            }
        }
    }
    
    /**
     * 复制目录
     */
    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        if (srcDir.isDirectory()) {
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            
            String[] files = srcDir.list();
            if (files != null) {
                for (String file : files) {
                    File srcFile = new File(srcDir, file);
                    File destFile = new File(destDir, file);
                    copyDirectory(srcFile, destFile);
                }
            }
        } else {
            try (FileInputStream in = new FileInputStream(srcDir);
                 FileOutputStream out = new FileOutputStream(destDir)) {
                IOUtils.copy(in, out);
            }
        }
    }

    /**
     * 删除目录及其内容
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        
        cleanDirectory(directory);
        if (!directory.delete()) {
            throw new IOException("Unable to delete directory " + directory);
        }
    }
    
    /**
     * 清空目录内容
     */
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        }
        
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
        
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Failed to list contents of " + directory);
        }
        
        IOException exception = null;
        for (File file : files) {
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }
        
        if (exception != null) {
            throw exception;
        }
    }
    
    /**
     * 强制删除文件
     */
    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            if (!file.exists()) {
                throw new FileNotFoundException("File does not exist: " + file);
            }
            if (!file.delete()) {
                throw new IOException("Unable to delete file: " + file);
            }
        }
    }

    /**
     * 读取文件内容为字符串
     */
    public static String readFileToString(File file, String charset) throws IOException {
        try (FileInputStream input = new FileInputStream(file)) {
            return IOUtils.toString(input, charset);
        }
    }
    
    /**
     * 读取文件内容为字节数组
     */
    public static byte[] readFileToByteArray(File file) throws IOException {
        try (FileInputStream input = new FileInputStream(file)) {
            return IOUtils.toByteArray(input);
        }
    }
    
    /**
     * 读取文件内容为行列表
     */
    public static List<String> readLines(File file, String charset) throws IOException {
        try (FileInputStream input = new FileInputStream(file)) {
            return IOUtils.readLines(input, charset);
        }
    }
    
    /**
     * 将字符串写入文件
     */
    public static void writeStringToFile(File file, String data, String charset) throws IOException {
        try (FileOutputStream output = new FileOutputStream(file)) {
            IOUtils.write(data, output, charset);
        }
    }

    /**
     * 安全删除文件
     */
    public static boolean deleteQuietly(File file) {
        try {
            if (file != null) {
                if (file.isDirectory()) {
                    cleanDirectory(file);
                }
                return file.delete();
            }
        } catch (Exception e) {
            log.error("Error deleting file: " + file, e);
        }
        return false;
    }
    
    /**
     * 清理目录,保留指定文件
     */
    public static void cleanDirectory(File directory, FileFilter filter) throws IOException {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        }
        
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
        
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Failed to list contents of " + directory);
        }
        
        for (File file : files) {
            if (filter == null || !filter.accept(file)) {
                forceDelete(file);
            }
        }
    }

    /**
     * 获取文件扩展名
     */
    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf('.');
        if (index == -1) {
            return "";
        }
        return filename.substring(index + 1);
    }
    
    /**
     * 创建目录(如果不存在)
     */
    public static void createDirectoryIfNotExists(String directory) throws IOException {
        File dir = new File(directory);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create directory " + directory);
        }
    }
    
    /**
     * 获取文件大小(MB)
     */
    public static double getFileSizeInMB(File file) {
        if (!file.exists() || !file.isFile()) {
            return 0;
        }
        return file.length() / (1024.0 * 1024.0);
    }

    /**
     * 判断是否为图片文件
     */
    public static boolean isImageFile(String filename) {
        String ext = getExtension(filename).toLowerCase();
        return ext.matches("jpg|jpeg|png|gif|bmp");
    }
    
    /**
     * 判断是否为文档文件
     */
    public static boolean isDocumentFile(String filename) {
        String ext = getExtension(filename).toLowerCase();
        return ext.matches("doc|docx|xls|xlsx|ppt|pptx|pdf|txt");
    }
    
    /**
     * 生成唯一文件名
     */
    public static String generateUniqueFileName(String originalFilename) {
        String ext = getExtension(originalFilename);
        return UUID.randomUUID().toString() + (StringUtils.isNotBlank(ext) ? "." + ext : "");
    }
    
    /**
     * 获取文件的MIME类型
     */
    public static String getMimeType(String filename) {
        String extension = getExtension(filename);
        if (StringUtils.isBlank(extension)) {
            return "application/octet-stream";
        }
        
        switch (extension.toLowerCase()) {
            case "txt": return "text/plain";
            case "html": return "text/html";
            case "css": return "text/css";
            case "js": return "application/javascript";
            case "json": return "application/json";
            case "xml": return "application/xml";
            case "jpg":
            case "jpeg": return "image/jpeg";
            case "png": return "image/png";
            case "gif": return "image/gif";
            case "bmp": return "image/bmp";
            case "pdf": return "application/pdf";
            case "doc": return "application/msword";
            case "docx": return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls": return "application/vnd.ms-excel";
            case "xlsx": return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt": return "application/vnd.ms-powerpoint";
            case "pptx": return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "zip": return "application/zip";
            case "rar": return "application/x-rar-compressed";
            default: return "application/octet-stream";
        }
    }

    /**
     * 验证文件名是否合法
     */
    public static boolean isValidFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        // 文件名不能包含特殊字符
        return !fileName.matches(".*[\\\\/:*?\"<>|].*");
    }
    
    /**
     * 获取文件的相对路径
     */
    public static String getRelativePath(File base, File file) {
        String basePath = base.getAbsolutePath();
        String filePath = file.getAbsolutePath();
        if (filePath.startsWith(basePath)) {
            return filePath.substring(basePath.length() + 1);
        }
        return filePath;
    }
    
    /**
     * 规范化文件路径
     */
    public static String normalizePath(String path) {
        if (StringUtils.isBlank(path)) {
            return path;
        }
        return path.replaceAll("[/\\\\]+", "/");
    }

    /**
     * 获取文件内容的字符编码
     */
    public static String detectCharset(File file) throws IOException {
        try (InputStream input = new FileInputStream(file)) {
            byte[] bytes = new byte[4];
            int read = input.read(bytes);
            
            if (read >= 2) {
                if (bytes[0] == (byte)0xFF && bytes[1] == (byte)0xFE) {
                    return "UTF-16LE";
                }
                if (bytes[0] == (byte)0xFE && bytes[1] == (byte)0xFF) {
                    return "UTF-16BE";
                }
            }
            
            if (read >= 3) {
                if (bytes[0] == (byte)0xEF && bytes[1] == (byte)0xBB && bytes[2] == (byte)0xBF) {
                    return "UTF-8";
                }
            }
            
            return DEFAULT_CHARSET;
        }
    }
    
    /**
     * 判断目录是否为空
     */
    public static boolean isEmptyDirectory(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            return false;
        }
        String[] files = directory.list();
        return files == null || files.length == 0;
    }

    /**
     * 获取目录大小
     */
    public static long getDirectorySize(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            return 0;
        }
        
        long size = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    size += getDirectorySize(file);
                } else {
                    size += file.length();
                }
            }
        }
        return size;
    }
    
    /**
     * 获取文件最后修改时间
     */
    public static long getLastModifiedTime(File file) {
        if (!file.exists()) {
            return 0;
        }
        return file.lastModified();
    }
    
    /**
     * 判断文件是否可写
     */
    public static boolean isWritable(File file) {
        return file.exists() && file.canWrite();
    }

    /**
     * 创建临时文件
     */
    public static File createTempFile(String prefix, String suffix) throws IOException {
        if (StringUtils.isBlank(prefix)) {
            prefix = "tmp";
        }
        if (StringUtils.isBlank(suffix)) {
            suffix = ".tmp";
        }
        return File.createTempFile(prefix, suffix);
    }
    
    /**
     * 创建临时目录
     */
    public static File createTempDirectory(String prefix) throws IOException {
        if (StringUtils.isBlank(prefix)) {
            prefix = "tmp";
        }
        File tempDir = createTempFile(prefix, "");
        if (!tempDir.delete()) {
            throw new IOException("Could not delete temp file: " + tempDir.getAbsolutePath());
        }
        if (!tempDir.mkdir()) {
            throw new IOException("Could not create temp directory: " + tempDir.getAbsolutePath());
        }
        return tempDir;
    }
    
    /**
     * 获取系统临时目录
     */
    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 获取用户主目录
     */
    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }
    
    /**
     * 获取当前工作目录
     */
    public static String getCurrentDirectoryPath() {
        return System.getProperty("user.dir");
    }
    
    /**
     * 判断是否为隐藏文件
     */
    public static boolean isHidden(File file) {
        return file.exists() && file.isHidden();
    }
    
    /**
     * 判断是否为同一个文件
     */
    public static boolean isSameFile(File file1, File file2) throws IOException {
        if (file1 == null || file2 == null) {
            return false;
        }
        return Files.isSameFile(file1.toPath(), file2.toPath());
    }

    /**
     * 获取文件路径的各个部分
     */
    public static String[] splitPath(String path) {
        if (StringUtils.isBlank(path)) {
            return new String[0];
        }
        path = normalizePath(path);
        return path.split("/");
    }
    
    /**
     * 连接路径
     */
    public static String joinPath(String... paths) {
        if (paths == null || paths.length == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (String path : paths) {
            if (StringUtils.isNotBlank(path)) {
                if (result.length() > 0 && !result.toString().endsWith("/")) {
                    result.append("/");
                }
                result.append(path);
            }
        }
        return normalizePath(result.toString());
    }
    
    /**
     * 获取文件名(不含扩展名)
     */
    public static String getBaseName(String filename) {
        if (StringUtils.isBlank(filename)) {
            return filename;
        }
        int lastDot = filename.lastIndexOf('.');
        return lastDot == -1 ? filename : filename.substring(0, lastDot);
    }

    /**
     * 获取文件的父目录路径
     */
    public static String getParentPath(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        path = normalizePath(path);
        int lastSlash = path.lastIndexOf('/');
        return lastSlash == -1 ? null : path.substring(0, lastSlash);
    }
    
    /**
     * 判断路径是否为绝对路径
     */
    public static boolean isAbsolutePath(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }
        return new File(path).isAbsolute();
    }
    
    /**
     * 获取绝对路径
     */
    public static String getAbsolutePath(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        return new File(path).getAbsolutePath();
    }

    /**
     * 获取规范化的绝对路径
     */
    public static String getCanonicalPath(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        try {
            return new File(path).getCanonicalPath();
        } catch (IOException e) {
            log.error("Failed to get canonical path for: " + path, e);
            return null;
        }
    }
    
    /**
     * 判断文件是否为空
     */
    public static boolean isEmpty(File file) {
        return file == null || !file.exists() || file.length() == 0;
    }
    
    /**
     * 判断目录是否可访问
     */
    public static boolean isDirectoryAccessible(File directory) {
        return directory != null && directory.exists() && 
               directory.isDirectory() && directory.canRead();
    }

    /**
     * 列出目录下的所有文件
     */
    public static List<File> listFiles(File directory, boolean recursive) {
        List<File> files = new ArrayList<>();
        if (!isDirectoryAccessible(directory)) {
            return files;
        }
        
        File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (recursive && file.isDirectory()) {
                    files.addAll(listFiles(file, true));
                }
            }
        }
        return files;
    }
    
    /**
     * 列出目录下的指定类型文件
     */
    public static List<File> listFiles(File directory, String[] extensions, boolean recursive) {
        List<File> files = new ArrayList<>();
        if (!isDirectoryAccessible(directory)) {
            return files;
        }
        
        File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    String ext = getExtension(file.getName());
                    if (extensions == null || extensions.length == 0 || 
                        Arrays.asList(extensions).contains(ext.toLowerCase())) {
                        files.add(file);
                    }
                } else if (recursive && file.isDirectory()) {
                    files.addAll(listFiles(file, extensions, true));
                }
            }
        }
        return files;
    }

    /**
     * 比较两个文件内容是否相同
     */
    public static boolean contentEquals(File file1, File file2) throws IOException {
        if (file1 == null || file2 == null || !file1.exists() || !file2.exists()) {
            return false;
        }
        
        if (file1.length() != file2.length()) {
            return false;
        }
        
        try (FileInputStream fis1 = new FileInputStream(file1);
             FileInputStream fis2 = new FileInputStream(file2)) {
            byte[] buffer1 = new byte[BUFFER_SIZE];
            byte[] buffer2 = new byte[BUFFER_SIZE];
            int bytesRead1;
            while ((bytesRead1 = fis1.read(buffer1)) != -1) {
                int bytesRead2 = fis2.read(buffer2);
                if (bytesRead1 != bytesRead2) {
                    return false;
                }
                for (int i = 0; i < bytesRead1; i++) {
                    if (buffer1[i] != buffer2[i]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
    
    /**
     * 移动文件
     */
    public static void moveFile(File srcFile, File destFile) throws IOException {
        if (srcFile == null || destFile == null) {
            throw new NullPointerException("Source or destination file is null");
        }
        
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Source file does not exist: " + srcFile);
        }
        
        if (destFile.exists() && !destFile.delete()) {
            throw new IOException("Failed to delete existing destination file: " + destFile);
        }
        
        File parentFile = destFile.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            throw new IOException("Failed to create destination directory: " + parentFile);
        }
        
        if (!srcFile.renameTo(destFile)) {
            // 如果重命名失败，尝试复制后删除
            copyFile(srcFile, destFile);
            if (!srcFile.delete()) {
                throw new IOException("Failed to delete source file after copy: " + srcFile);
            }
        }
    }
    
    /**
     * 复制文件
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        if (srcFile == null || destFile == null) {
            throw new NullPointerException("Source or destination file is null");
        }
        
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Source file does not exist: " + srcFile);
        }
        
        if (srcFile.isDirectory()) {
            throw new IOException("Source file is a directory: " + srcFile);
        }
        
        File parentFile = destFile.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            throw new IOException("Failed to create destination directory: " + parentFile);
        }
        
        try (FileInputStream input = new FileInputStream(srcFile);
             FileOutputStream output = new FileOutputStream(destFile)) {
            IOUtils.copy(input, output);
        }
    }
    
    /**
     * 创建文件备份
     */
    public static File backup(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File does not exist: " + file);
        }
        
        String baseName = getBaseName(file.getName());
        String extension = getExtension(file.getName());
        String backupName = baseName + "_backup_" + System.currentTimeMillis() + 
                           (StringUtils.isNotBlank(extension) ? "." + extension : "");
        
        File backupFile = new File(file.getParentFile(), backupName);
        copyFile(file, backupFile);
        return backupFile;
    }

    /**
     * 设置文件权限
     */
    public static boolean setFilePermissions(File file, boolean readable, boolean writable, boolean executable) {
        if (!file.exists()) {
            return false;
        }
        
        return file.setReadable(readable) &&
               file.setWritable(writable) &&
               file.setExecutable(executable);
    }
    
    /**
     * 获取文件属性信息
     */
    public static FileInfo getFileInfo(File file) {
        FileInfo info = new FileInfo();
        if (file.exists()) {
            info.setName(file.getName());
            info.setPath(file.getAbsolutePath());
            info.setSize(file.length());
            info.setDirectory(file.isDirectory());
            info.setLastModified(file.lastModified());
            info.setReadable(file.canRead());
            info.setWritable(file.canWrite());
            info.setExecutable(file.canExecute());
            info.setHidden(file.isHidden());
        }
        return info;
    }
    
    /**
     * 文件信息类
     */
    @Data
    public static class FileInfo {
        private String name;
        private String path;
        private long size;
        private boolean isDirectory;
        private long lastModified;
        private boolean readable;
        private boolean writable;
        private boolean executable;
        private boolean hidden;
    }
    
    /**
     * 创建符号链接
     */
    public static void createSymbolicLink(File source, File link) throws IOException {
        if (!source.exists()) {
            throw new FileNotFoundException("Source file does not exist: " + source);
        }
        
        Files.createSymbolicLink(link.toPath(), source.toPath());
    }
    
    /**
     * 判断是否为符号链接
     */
    public static boolean isSymbolicLink(File file) {
        return Files.isSymbolicLink(file.toPath());
    }

    /**
     * 压缩文件或目录
     */
    public static void compress(File source, File zipFile) throws IOException {
        if (!source.exists()) {
            throw new FileNotFoundException("Source file does not exist: " + source);
        }
        
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            
            String basePath = source.getAbsolutePath();
            if (source.isDirectory()) {
                File[] files = source.listFiles();
                if (files != null) {
                    for (File file : files) {
                        addToZip(file, basePath, zos);
                    }
                }
            } else {
                addToZip(source, basePath, zos);
            }
        }
    }
    
    private static void addToZip(File file, String basePath, ZipOutputStream zos) throws IOException {
        String entryName = file.getAbsolutePath().substring(basePath.length() + 1);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    addToZip(child, basePath, zos);
                }
            }
        } else {
            ZipEntry entry = new ZipEntry(entryName);
            zos.putNextEntry(entry);
            try (FileInputStream fis = new FileInputStream(file)) {
                IOUtils.copy(fis, zos);
            }
            zos.closeEntry();
        }
    }

    /**
     * 解压缩ZIP文件到指定目录
     */
    public static void decompress(File zipFile, File destDir) throws IOException {
        if (!zipFile.exists()) {
            throw new FileNotFoundException("ZIP文件不存在: " + zipFile);
        }
        
        if (!destDir.exists() && !destDir.mkdirs()) {
            throw new IOException("无法创建目标目录: " + destDir);
        }
        
        try (ZipFile zip = new ZipFile(zipFile)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryFile = new File(destDir, entry.getName());
                
                if (entry.isDirectory()) {
                    if (!entryFile.exists() && !entryFile.mkdirs()) {
                        throw new IOException("无法创建目录: " + entryFile);
                    }
                    continue;
                }
                
                File parent = entryFile.getParentFile();
                if (!parent.exists() && !parent.mkdirs()) {
                    throw new IOException("无法创建目录: " + parent);
                }
                
                try (InputStream in = zip.getInputStream(entry);
                     FileOutputStream out = new FileOutputStream(entryFile)) {
                    IOUtils.copy(in, out);
                }
            }
        }
    }
} 