package com.lawfirm.common.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class FileUtil {
    private static final int MAX_FILENAME_LENGTH = 255;
    
    public static String sanitizeFilename(String filename) {
        if (StringUtils.isBlank(filename)) {
            return "unnamed";
        }
        String name = FilenameUtils.getName(filename);
        String cleanName = name.replaceAll("[^a-zA-Z0-9-_.]", "_");
        return cleanName.length() > MAX_FILENAME_LENGTH ? 
                cleanName.substring(0, MAX_FILENAME_LENGTH) : cleanName;
    }

    public static String getFileExtension(String filename) {
        return FilenameUtils.getExtension(filename).toLowerCase();
    }

    public static boolean isValidExtension(String filename, String[] allowedExtensions) {
        if (StringUtils.isBlank(filename)) return false;
        String extension = getFileExtension(filename);
        for (String allowed : allowedExtensions) {
            if (allowed.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
