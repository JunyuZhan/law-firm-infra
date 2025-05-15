package com.lawfirm.api.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Logback编码配置
 */
@Slf4j
@Configuration
public class LogbackEncodingConfig {

    @Value("${logging.file.name:./logs/law-firm-api.log}")
    private String logFilePath;

    @Value("${logging.charset.console:UTF-8}")
    private String consoleCharset;

    @Value("${logging.charset.file:UTF-8}")
    private String fileCharset;

    /**
     * 初始化Logback编码配置
     */
    @PostConstruct
    public void init() {
        // 配置文件编码
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        
        // 配置输出编码
        updateLogbackEncoding();
    }

    /**
     * 更新Logback编码设置
     */
    private void updateLogbackEncoding() {
        try {
            // 获取Logback上下文
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            
            // 更新控制台编码
            updateConsoleAppenderEncoding(loggerContext);
            
            // 更新文件编码
            updateFileAppenderEncoding(loggerContext);
            
            // 确保日志目录存在
            ensureLogDirectoryExists();
        } catch (Exception e) {
            log.error("配置日志编码失败", e);
        }
    }
    
    /**
     * 更新控制台Appender编码
     */
    private void updateConsoleAppenderEncoding(LoggerContext context) {
        context.getLoggerList().forEach(logger -> {
            logger.iteratorForAppenders().forEachRemaining(appender -> {
                if (appender instanceof ConsoleAppender) {
                    ConsoleAppender<?> consoleAppender = (ConsoleAppender<?>) appender;
                    
                    // 更新编码器编码
                    if (consoleAppender.getEncoder() instanceof PatternLayoutEncoder) {
                        PatternLayoutEncoder encoder = (PatternLayoutEncoder) consoleAppender.getEncoder();
                        encoder.setCharset(Charset.forName(consoleCharset));
                        encoder.start();
                    }
                    
                    log.info("已为控制台输出配置UTF-8编码");
                }
            });
        });
    }
    
    /**
     * 更新文件Appender编码
     */
    private void updateFileAppenderEncoding(LoggerContext context) {
        context.getLoggerList().forEach(logger -> {
            logger.iteratorForAppenders().forEachRemaining(appender -> {
                if (appender instanceof RollingFileAppender) {
                    RollingFileAppender<?> fileAppender = (RollingFileAppender<?>) appender;
                    
                    // 更新编码器编码
                    if (fileAppender.getEncoder() instanceof PatternLayoutEncoder) {
                        PatternLayoutEncoder encoder = (PatternLayoutEncoder) fileAppender.getEncoder();
                        encoder.setCharset(Charset.forName(fileCharset));
                        encoder.start();
                    }
                    
                    log.info("已为文件输出配置UTF-8编码: {}", fileAppender.getFile());
                }
            });
        });
    }
    
    /**
     * 确保日志目录存在
     */
    private void ensureLogDirectoryExists() {
        if (StringUtils.hasText(logFilePath)) {
            File logFile = new File(logFilePath);
            File logDir = logFile.getParentFile();
            if (logDir != null && !logDir.exists()) {
                boolean created = logDir.mkdirs();
                if (created) {
                    log.info("创建日志目录: {}", logDir.getAbsolutePath());
                } else {
                    log.warn("无法创建日志目录: {}", logDir.getAbsolutePath());
                }
            }
        }
    }
} 