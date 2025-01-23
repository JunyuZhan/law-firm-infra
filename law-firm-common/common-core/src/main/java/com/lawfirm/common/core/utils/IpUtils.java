package com.lawfirm.common.core.utils;

import org.springframework.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP地址工具类
 */
public class IpUtils {
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String SEPARATOR = ",";

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (isUnknown(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isUnknown(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isUnknown(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isUnknown(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isUnknown(ip)) {
            ip = request.getRemoteAddr();
            if (LOCALHOST.equals(ip)) {
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    // ignore
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if (ip != null && ip.length() > 15 && ip.contains(SEPARATOR)) {
            ip = StringUtils.split(ip, SEPARATOR)[0];
        }
        return ip;
    }

    /**
     * 根据IP地址获取真实地理位置
     * TODO: 实现真实的IP地址解析，可以使用第三方服务如高德地图、百度地图等API
     *
     * @param ip IP地址
     * @return 地理位置
     */
    public static String getRealAddressByIP(String ip) {
        if (isUnknown(ip) || LOCALHOST.equals(ip)) {
            return "内网IP";
        }
        // TODO: 调用第三方API获取地理位置
        return "未知位置";
    }

    private static boolean isUnknown(String ip) {
        return !StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip);
    }
} 