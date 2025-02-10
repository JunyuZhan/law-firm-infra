package com.lawfirm.common.web.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP地址工具类
 */
@Slf4j
public class IpUtils {
    
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String SEPARATOR = ",";
    
    private static final String[] HEADERS = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_CLIENT_IP",
        "HTTP_X_FORWARDED_FOR"
    };

    /**
     * 获取请求的真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }

        String ip = null;
        for (String header : HEADERS) {
            ip = request.getHeader(header);
            if (StringUtils.isNotEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                break;
            }
        }

        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (StringUtils.isEmpty(ip)) {
            return UNKNOWN;
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if (ip.contains(SEPARATOR)) {
            ip = ip.split(SEPARATOR)[0];
        }

        if (LOCALHOST.equals(ip)) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error("获取IP地址失败", e);
            }
        }

        return ip;
    }

    /**
     * 获取本机IP地址
     */
    public static String getLocalIP() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取本机IP地址失败", e);
            return UNKNOWN;
        }
    }

    /**
     * 检查IP地址是否合法
     */
    public static boolean isValidIP(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }
        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是内网IP
     */
    public static boolean isInternalIP(String ip) {
        if (!isValidIP(ip)) {
            return false;
        }
        String[] parts = ip.split("\\.");
        int firstPart = Integer.parseInt(parts[0]);
        int secondPart = Integer.parseInt(parts[1]);
        
        // 10.0.0.0 - 10.255.255.255
        if (firstPart == 10) {
            return true;
        }
        
        // 172.16.0.0 - 172.31.255.255
        if (firstPart == 172 && (secondPart >= 16 && secondPart <= 31)) {
            return true;
        }
        
        // 192.168.0.0 - 192.168.255.255
        return firstPart == 192 && secondPart == 168;
    }

    /**
     * 判断是否是公网IP
     */
    public static boolean isPublicIP(String ip) {
        return isValidIP(ip) && !isInternalIP(ip);
    }

    /**
     * 根据IP地址获取真实地理位置
     * TODO: 集成第三方IP地址库获取真实地理位置
     */
    public static String getRealAddressByIP(String ip) {
        // 暂时返回IP地址
        return ip;
    }
} 