package com.lawfirm.core.storage.aspect;

import com.lawfirm.core.storage.service.impl.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 文件操作日志切面
 * 记录文件操作的日志信息
 * @author JunyuZhan
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class FileOperationLogAspect {

    /**
     * 定义文件上传切点
     */
    @Pointcut("execution(* com.lawfirm.core.storage.service.impl.FileServiceImpl.upload*(..))")
    public void uploadPointcut() {
    }

    /**
     * 定义文件下载切点
     */
    @Pointcut("execution(* com.lawfirm.core.storage.service.impl.FileServiceImpl.download*(..))")
    public void downloadPointcut() {
    }

    /**
     * 定义文件删除切点
     */
    @Pointcut("execution(* com.lawfirm.core.storage.service.impl.FileServiceImpl.delete*(..))")
    public void deletePointcut() {
    }

    /**
     * 文件上传前记录日志
     */
    @Before("uploadPointcut()")
    public void beforeUpload(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            log.info("准备上传文件: {}", args[0]);
        }
    }
    
    /**
     * 文件上传成功后记录日志
     */
    @AfterReturning(
        pointcut = "uploadPointcut()",
        returning = "result"
    )
    public void afterUpload(JoinPoint joinPoint, Object result) {
        log.info("文件上传成功: {}", result);
    }
    
    /**
     * 文件上传失败记录日志
     */
    @AfterThrowing(
        pointcut = "uploadPointcut()",
        throwing = "e"
    )
    public void uploadError(JoinPoint joinPoint, Exception e) {
        log.error("文件上传失败: {}", e.getMessage(), e);
    }
    
    /**
     * 文件下载前记录日志
     */
    @Before("downloadPointcut()")
    public void beforeDownload(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            log.info("准备下载文件: {}", args[0]);
        }
    }
    
    /**
     * 文件下载成功后记录日志
     */
    @AfterReturning(
        pointcut = "downloadPointcut()",
        returning = "result"
    )
    public void afterDownload(JoinPoint joinPoint, Object result) {
        log.info("文件下载成功: {}", result);
    }
    
    /**
     * 文件下载失败记录日志
     */
    @AfterThrowing(
        pointcut = "downloadPointcut()",
        throwing = "e"
    )
    public void downloadError(JoinPoint joinPoint, Exception e) {
        log.error("文件下载失败: {}", e.getMessage(), e);
    }
    
    /**
     * 文件删除前记录日志
     */
    @Before("deletePointcut()")
    public void beforeDelete(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            log.info("准备删除文件: {}", args[0]);
        }
    }
    
    /**
     * 文件删除成功后记录日志
     */
    @AfterReturning(
        pointcut = "deletePointcut()",
        returning = "result"
    )
    public void afterDelete(JoinPoint joinPoint, Object result) {
        log.info("文件删除成功: {}", result);
    }
    
    /**
     * 文件删除失败记录日志
     */
    @AfterThrowing(
        pointcut = "deletePointcut()",
        throwing = "e"
    )
    public void deleteError(JoinPoint joinPoint, Exception e) {
        log.error("文件删除失败: {}", e.getMessage(), e);
    }
} 
