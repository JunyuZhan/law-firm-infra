package com.lawfirm.model.system.vo.monitor;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统信息视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemInfoVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 系统版本
     */
    private String systemVersion;

    /**
     * 系统构建时间
     */
    private String buildTime;

    /**
     * JDK版本
     */
    private String jdkVersion;

    /**
     * JVM名称
     */
    private String jvmName;

    /**
     * JVM版本
     */
    private String jvmVersion;

    /**
     * 启动时间
     */
    private String startTime;

    /**
     * 运行时间
     */
    private String runTime;

    /**
     * 部署路径
     */
    private String deployPath;

    /**
     * 主机名
     */
    private String hostName;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 操作系统架构
     */
    private String osArch;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户主目录
     */
    private String userHome;

    /**
     * 用户工作目录
     */
    private String userDir;
} 