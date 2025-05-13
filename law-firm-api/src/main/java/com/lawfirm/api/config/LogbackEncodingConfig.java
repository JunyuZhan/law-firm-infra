package com.lawfirm.api.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Logback编码配置
 * 确保日志输出使用UTF-8编码，避免中文乱码
 */
@Configuration
@Slf4j
public class LogbackEncodingConfig {

    /**
     * 初始化方法 - 在Spring容器初始化后调整Logback配置
     */
    @PostConstruct
    public void init() {
        try {
            // 设置系统级编码属性
            System.setProperty("file.encoding", "UTF-8");
            System.setProperty("sun.jnu.encoding", "UTF-8");
            
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            
            // 获取根日志记录器
            Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
            
            // 设置所有控制台附加器的编码
            List<Appender<ILoggingEvent>> appenders = new ArrayList<>();
            Iterator<Appender<ILoggingEvent>> iterator = rootLogger.iteratorForAppenders();
            while (iterator.hasNext()) {
                appenders.add(iterator.next());
            }
            
            for (Appender<ILoggingEvent> appender : appenders) {
                // 处理控制台输出
                if (appender instanceof ConsoleAppender<?>) {
                    ConsoleAppender<ILoggingEvent> consoleAppender = (ConsoleAppender<ILoggingEvent>) appender;
                    
                    // 启用带颜色的ANSI输出支持
                    consoleAppender.setWithJansi(true);
                    
                    // 设置编码器字符集
                    if (consoleAppender.getEncoder() instanceof PatternLayoutEncoder) {
                        PatternLayoutEncoder encoder = (PatternLayoutEncoder) consoleAppender.getEncoder();
                        // 确保使用UTF-8编码
                        encoder.setCharset(StandardCharsets.UTF_8);
                        
                        // 停止并重新启动编码器应用更改
                        encoder.stop();
                        encoder.start();
                        
                        log.info("已为控制台输出配置UTF-8编码");
                    }
                    
                    // 停止并重新启动附加器
                    consoleAppender.stop();
                    consoleAppender.start();
                }
                
                // 处理文件输出
                if (appender instanceof FileAppender<?>) {
                    FileAppender<ILoggingEvent> fileAppender = (FileAppender<ILoggingEvent>) appender;
                    
                    // 设置编码器字符集
                    if (fileAppender.getEncoder() instanceof PatternLayoutEncoder) {
                        PatternLayoutEncoder encoder = (PatternLayoutEncoder) fileAppender.getEncoder();
                        // 确保使用UTF-8编码
                        encoder.setCharset(StandardCharsets.UTF_8);
                        
                        // 停止并重新启动编码器应用更改
                        encoder.stop();
                        encoder.start();
                        
                        log.info("已为文件输出配置UTF-8编码: {}", fileAppender.getFile());
                    }
                    
                    // 停止并重新启动附加器
                    fileAppender.stop();
                    fileAppender.start();
                }
            }
            
            // 输出编码测试信息
            log.info("===== 日志编码测试 =====");
            log.info("当前系统编码: {}", System.getProperty("file.encoding"));
            log.info("中文测试: 这是一段中文测试文本");
            log.info("特殊字符测试: 特殊字符ÄÖÜäöüß");
            log.info("========================");
        } catch (Exception e) {
            log.error("日志编码配置失败: {}", e.getMessage(), e);
        }
    }
} 