package com.lawfirm.common.util.compress;

import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CompressUtils {
    
    public static void zip(String srcPath, String destPath) {
        try {
            ZipUtil.zip(srcPath, destPath);
        } catch (Exception e) {
            log.error("Zip compression failed", e);
        }
    }
    
    public static void zip(File srcFile, File destFile) {
        try {
            File[] files = new File[]{srcFile};
            ZipUtil.zip(destFile, false, files);
        } catch (Exception e) {
            log.error("Zip compression failed", e);
        }
    }
    
    public static File unzip(String zipPath, String destPath) {
        return ZipUtil.unzip(zipPath, destPath, StandardCharsets.UTF_8);
    }
    
    public static File unzip(File zipFile, File destDir) {
        return ZipUtil.unzip(zipFile, destDir, StandardCharsets.UTF_8);
    }
    
    public static byte[] gzip(String content) {
        try {
            return ZipUtil.gzip(content, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            log.error("Gzip compression failed", e);
            return new byte[0];
        }
    }
    
    public static byte[] gzip(InputStream in) {
        try {
            return ZipUtil.gzip(in);
        } catch (Exception e) {
            log.error("Gzip compression failed", e);
            return new byte[0];
        }
    }
    
    public static String unGzip(byte[] bytes) {
        try {
            return ZipUtil.unGzip(bytes, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            log.error("Gzip decompression failed", e);
            return "";
        }
    }
    
    public static byte[] unGzip(InputStream in) {
        try {
            return ZipUtil.unGzip(in);
        } catch (Exception e) {
            log.error("Gzip decompression failed", e);
            return new byte[0];
        }
    }
} 