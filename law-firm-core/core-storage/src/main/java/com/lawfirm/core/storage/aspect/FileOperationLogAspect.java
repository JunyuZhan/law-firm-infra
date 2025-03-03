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
 * 用于记录文件上传、下载、删除等操作的日志
 *
 * @author Claude
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class FileOperationLogAspect {

    /**
     * 定义文件上传切点
     */
    @Pointcut("execution(* com.lawfirm.core.storage.service.impl.FileServiceImpl.upload(..))")
    public void uploadPointcut() {
    }

    /**
     * 定义文件下载切点
     */
    @Pointcut("execution(* com.lawfirm.core.storage.service.impl.FileServiceImpl.download(..))")
    public void downloadPointcut() {
    }

    /**
     * 定义文件删除切点
     */
    @Pointcut("execution(* com.lawfirm.core.storage.service.impl.FileServiceImpl.delete(..))")
    public void deletePointcut() {
    }

    /**
     * 文件上传前记录日志
     */
    @Before("uploadPointcut()")
    public void beforeUpload(JoinPoint joinPoint) {
        log.info("准备上传文件: {}", Arrays.toString(joinPoint.getArgs()));
        // 实际项目中可以从SecurityContext获取当前用户信息
        // String username = SecurityUtils.getUsername();
        // log.info("操作用户: {}", username);
    }

    /**
     * 文件上传成功后记录日志
     */
    @AfterReturning(pointcut = "uploadPointcut()", returning = "result")
    public void afterUploadReturning(JoinPoint joinPoint, Object result) {
        log.info("文件上传成功: {}", result);
        // 实际项目中可以将操作日志保存到数据库
        // operationLogService.save(new OperationLog("上传文件", "成功", ...));
    }

    /**
     * 文件上传失败后记录日志
     */
    @AfterThrowing(pointcut = "uploadPointcut()", throwing = "e")
    public void afterUploadThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("文件上传失败: {}", e.getMessage(), e);
        // 实际项目中可以将操作日志保存到数据库
        // operationLogService.save(new OperationLog("上传文件", "失败", e.getMessage(), ...));
    }

    /**
     * 文件下载前记录日志
     */
    @Before("downloadPointcut()")
    public void beforeDownload(JoinPoint joinPoint) {
        log.info("准备下载文件: {}", Arrays.toString(joinPoint.getArgs()));
        // 实际项目中可以从SecurityContext获取当前用户信息
    }

    /**
     * 文件下载成功后记录日志
     */
    @AfterReturning(pointcut = "downloadPointcut()", returning = "result")
    public void afterDownloadReturning(JoinPoint joinPoint, Object result) {
        log.info("文件下载成功: {}", result);
        // 实际项目中可以将操作日志保存到数据库
    }

    /**
     * 文件下载失败后记录日志
     */
    @AfterThrowing(pointcut = "downloadPointcut()", throwing = "e")
    public void afterDownloadThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("文件下载失败: {}", e.getMessage(), e);
        // 实际项目中可以将操作日志保存到数据库
    }

    /**
     * 文件删除前记录日志
     */
    @Before("deletePointcut()")
    public void beforeDelete(JoinPoint joinPoint) {
        log.info("准备删除文件: {}", Arrays.toString(joinPoint.getArgs()));
        // 实际项目中可以从SecurityContext获取当前用户信息
    }

    /**
     * 文件删除成功后记录日志
     */
    @AfterReturning(pointcut = "deletePointcut()")
    public void afterDeleteReturning(JoinPoint joinPoint) {
        log.info("文件删除成功");
        // 实际项目中可以将操作日志保存到数据库
    }

    /**
     * 文件删除失败后记录日志
     */
    @AfterThrowing(pointcut = "deletePointcut()", throwing = "e")
    public void afterDeleteThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("文件删除失败: {}", e.getMessage(), e);
        // 实际项目中可以将操作日志保存到数据库
    }
} 