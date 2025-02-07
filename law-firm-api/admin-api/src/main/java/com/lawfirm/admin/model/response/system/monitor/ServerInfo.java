package com.lawfirm.admin.model.response.system.monitor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "服务器信息")
public class ServerInfo {

    @Schema(description = "CPU相关信息")
    private Cpu cpu;

    @Schema(description = "内存相关信息")
    private Mem mem;

    @Schema(description = "JVM相关信息")
    private Jvm jvm;

    @Schema(description = "系统相关信息")
    private Sys sys;

    @Schema(description = "磁盘相关信息")
    private Disk disk;

    @Data
    @Schema(description = "CPU相关信息")
    public static class Cpu {
        @Schema(description = "核心数")
        private Integer cpuNum;

        @Schema(description = "CPU总的使用率")
        private Double total;

        @Schema(description = "CPU系统使用率")
        private Double sys;

        @Schema(description = "CPU用户使用率")
        private Double used;

        @Schema(description = "CPU当前等待率")
        private Double wait;

        @Schema(description = "CPU当前空闲率")
        private Double free;
    }

    @Data
    @Schema(description = "内存相关信息")
    public static class Mem {
        @Schema(description = "内存总量")
        private Double total;

        @Schema(description = "已用内存")
        private Double used;

        @Schema(description = "剩余内存")
        private Double free;

        @Schema(description = "使用率")
        private Double usage;
    }

    @Data
    @Schema(description = "JVM相关信息")
    public static class Jvm {
        @Schema(description = "当前JVM占用的内存总数(M)")
        private Double total;

        @Schema(description = "JVM最大可用内存总数(M)")
        private Double max;

        @Schema(description = "JVM空闲内存(M)")
        private Double free;

        @Schema(description = "JVM使用率")
        private Double usage;

        @Schema(description = "JDK版本")
        private String version;

        @Schema(description = "JDK路径")
        private String home;
    }

    @Data
    @Schema(description = "系统相关信息")
    public static class Sys {
        @Schema(description = "服务器名称")
        private String computerName;

        @Schema(description = "服务器Ip")
        private String computerIp;

        @Schema(description = "项目路径")
        private String userDir;

        @Schema(description = "操作系统")
        private String osName;

        @Schema(description = "系统架构")
        private String osArch;
    }

    @Data
    @Schema(description = "磁盘相关信息")
    public static class Disk {
        @Schema(description = "磁盘总量")
        private Double total;

        @Schema(description = "已用磁盘")
        private Double used;

        @Schema(description = "剩余磁盘")
        private Double free;

        @Schema(description = "使用率")
        private Double usage;
    }
} 