package com.lawfirm.common.security.service;

import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.security.utils.QrCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 二维码登录服务
 */
@Slf4j
@Service
public class QrCodeLoginService {

    /**
     * 二维码有效期（分钟）
     */
    private static final long QR_CODE_EXPIRE_MINUTES = 5;

    /**
     * 二维码状态：未扫描
     */
    public static final String STATUS_NOT_SCAN = "0";

    /**
     * 二维码状态：已扫描
     */
    public static final String STATUS_SCANNED = "1";

    /**
     * 二维码状态：已确认
     */
    public static final String STATUS_CONFIRMED = "2";

    /**
     * 二维码状态：已过期
     */
    public static final String STATUS_EXPIRED = "3";

    /**
     * Redis Key前缀
     */
    private static final String REDIS_KEY_PREFIX = "qrcode:login:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private QrCodeUtils qrCodeUtils;

    /**
     * 生成二维码登录的Token
     */
    public String generateQrCodeToken() {
        String token = UUID.randomUUID().toString();
        String key = REDIS_KEY_PREFIX + token;
        
        // 存储二维码状态
        redisTemplate.opsForHash().put(key, "status", STATUS_NOT_SCAN);
        redisTemplate.expire(key, QR_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        
        return token;
    }

    /**
     * 生成二维码图片
     */
    public byte[] generateQrCodeImage(String token) {
        // 生成二维码内容（可以包含更多信息，如时间戳等）
        String content = String.format("qrcode_login:%s", token);
        return qrCodeUtils.generateQrCode(content);
    }

    /**
     * 更新二维码状态
     */
    public void updateQrCodeStatus(String token, String status, String username) {
        String key = REDIS_KEY_PREFIX + token;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForHash().put(key, "status", status);
            if (username != null) {
                redisTemplate.opsForHash().put(key, "username", username);
            }
        } else {
            throw new BusinessException("二维码已过期");
        }
    }

    /**
     * 验证二维码Token
     */
    public String verifyQrCodeToken(String token) {
        String key = REDIS_KEY_PREFIX + token;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            String status = (String) redisTemplate.opsForHash().get(key, "status");
            if (STATUS_CONFIRMED.equals(status)) {
                String username = (String) redisTemplate.opsForHash().get(key, "username");
                // 使用后删除
                redisTemplate.delete(key);
                return username;
            } else if (STATUS_EXPIRED.equals(status)) {
                throw new BusinessException("二维码已过期");
            } else if (STATUS_NOT_SCAN.equals(status)) {
                throw new BusinessException("二维码未扫描");
            } else if (STATUS_SCANNED.equals(status)) {
                throw new BusinessException("等待确认");
            }
        }
        throw new BusinessException("无效的二维码");
    }

    /**
     * 获取二维码状态
     */
    public String getQrCodeStatus(String token) {
        String key = REDIS_KEY_PREFIX + token;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return (String) redisTemplate.opsForHash().get(key, "status");
        }
        return STATUS_EXPIRED;
    }
} 