package com.lawfirm.common.test.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 测试数据生成器
 */
public class TestDataGenerator {

    private static final Random random = new Random();

    /**
     * 生成随机字符串
     */
    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成指定长度的随机字符串
     */
    public static String randomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 生成随机整数
     */
    public static int randomInt() {
        return random.nextInt();
    }

    /**
     * 生成指定范围内的随机整数
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 生成随机长整数
     */
    public static long randomLong() {
        return random.nextLong();
    }

    /**
     * 生成随机布尔值
     */
    public static boolean randomBoolean() {
        return random.nextBoolean();
    }

    /**
     * 生成随机日期时间
     */
    public static LocalDateTime randomDateTime() {
        return LocalDateTime.now().minusDays(randomInt(0, 365));
    }

    /**
     * 从列表中随机选择一个元素
     */
    public static <T> T randomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(random.nextInt(list.size()));
    }

    /**
     * 生成随机列表
     */
    public static <T> List<T> randomList(int size, T... elements) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(randomElement(List.of(elements)));
        }
        return result;
    }

    /**
     * 生成随机手机号
     */
    public static String randomMobile() {
        return "1" + randomInt(3, 9) + randomString(9);
    }

    /**
     * 生成随机邮箱
     */
    public static String randomEmail() {
        return randomString(10) + "@example.com";
    }
} 