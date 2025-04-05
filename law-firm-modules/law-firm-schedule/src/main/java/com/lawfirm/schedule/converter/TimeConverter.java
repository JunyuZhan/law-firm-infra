package com.lawfirm.schedule.converter;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 时间转换工具类
 */
@Component
public class TimeConverter {

    /**
     * 将LocalDateTime转换为时间戳（毫秒）
     *
     * @param dateTime LocalDateTime对象
     * @return 时间戳（毫秒）
     */
    public static long toTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return 0L;
        }
        ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    /**
     * 将时间戳（毫秒）转换为LocalDateTime
     *
     * @param timestamp 时间戳（毫秒）
     * @return LocalDateTime对象
     */
    public static LocalDateTime fromTimestamp(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * 将LocalDateTime转换为时间戳（秒）
     *
     * @param dateTime LocalDateTime对象
     * @return 时间戳（秒）
     */
    public static long toEpochSecond(LocalDateTime dateTime) {
        if (dateTime == null) {
            return 0L;
        }
        ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault());
        return zdt.toEpochSecond();
    }

    /**
     * 将时间戳（秒）转换为LocalDateTime
     *
     * @param epochSecond 时间戳（秒）
     * @return LocalDateTime对象
     */
    public static LocalDateTime fromEpochSecond(long epochSecond) {
        return Instant.ofEpochSecond(epochSecond)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
} 