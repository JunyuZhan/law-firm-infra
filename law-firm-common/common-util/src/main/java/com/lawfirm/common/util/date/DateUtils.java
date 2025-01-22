package com.lawfirm.common.util.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    public static String formatTime(LocalTime time) {
        return time != null ? time.format(TIME_FORMATTER) : null;
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FORMATTER) : null;
    }

    public static LocalDate parseDate(String dateStr) {
        return StringUtils.isNotEmpty(dateStr) ? LocalDate.parse(dateStr, DATE_FORMATTER) : null;
    }

    public static LocalTime parseTime(String timeStr) {
        return StringUtils.isNotEmpty(timeStr) ? LocalTime.parse(timeStr, TIME_FORMATTER) : null;
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return StringUtils.isNotEmpty(dateTimeStr) ? LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER) : null;
    }

    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static long getDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static boolean isWeekend(LocalDate date) {
        switch (date.getDayOfWeek()) {
            case SATURDAY:
            case SUNDAY:
                return true;
            default:
                return false;
        }
    }

    public static LocalDateTime convertTimeZone(LocalDateTime dateTime, ZoneId fromZone, ZoneId toZone) {
        ZonedDateTime zonedTime = dateTime.atZone(fromZone);
        return zonedTime.withZoneSameInstant(toZone).toLocalDateTime();
    }
    
    public static LocalDateTime utcToLocal(LocalDateTime utcDateTime) {
        return convertTimeZone(utcDateTime, ZoneOffset.UTC, ZoneId.systemDefault());
    }
    
    public static LocalDateTime localToUtc(LocalDateTime localDateTime) {
        return convertTimeZone(localDateTime, ZoneId.systemDefault(), ZoneOffset.UTC);
    }
    
    public static String formatWithTimeZone(LocalDateTime dateTime, String pattern, ZoneId zoneId) {
        return dateTime.atZone(zoneId).format(DateTimeFormatter.ofPattern(pattern));
    }
} 