package com.lawfirm.model.system.vo.monitor;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 网络信息视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class NetworkInfoVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主机名
     */
    private String hostName;

    /**
     * 域名
     */
    private String domainName;

    /**
     * 默认网关
     */
    private String defaultGateway;

    /**
     * DNS服务器
     */
    private transient List<String> dnsServers;

    /**
     * 网络接口列表
     */
    private transient List<NetworkInterfaceVO> interfaces;

    /**
     * 总接收字节数(MB)
     */
    private Double totalReceivedBytes;

    /**
     * 总发送字节数(MB)
     */
    private Double totalSentBytes;

    /**
     * 接收速率(KB/s)
     */
    private Double receiveRate;

    /**
     * 发送速率(KB/s)
     */
    private Double sendRate;

    /**
     * TCP连接数
     */
    private Integer tcpConnectionCount;

    /**
     * UDP连接数
     */
    private Integer udpConnectionCount;

    /**
     * 网络接口信息
     */
    @Data
    @Accessors(chain = true)
    public static class NetworkInterfaceVO implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 接口名称
         */
        private String name;

        /**
         * 接口显示名称
         */
        private String displayName;

        /**
         * MAC地址
         */
        private String macAddress;

        /**
         * IPv4地址
         */
        private transient List<String> ipv4Addresses;

        /**
         * IPv6地址
         */
        private transient List<String> ipv6Addresses;

        /**
         * 子网掩码
         */
        private String subnetMask;

        /**
         * 接口类型
         */
        private String type;

        /**
         * 是否启用
         */
        private Boolean enabled;

        /**
         * 是否回环接口
         */
        private Boolean loopback;

        /**
         * 是否点对点接口
         */
        private Boolean pointToPoint;

        /**
         * 是否虚拟接口
         */
        private Boolean virtual;

        /**
         * 接收字节数(MB)
         */
        private Double receivedBytes;

        /**
         * 发送字节数(MB)
         */
        private Double sentBytes;

        /**
         * 接收速率(KB/s)
         */
        private Double receiveRate;

        /**
         * 发送速率(KB/s)
         */
        private Double sendRate;

        /**
         * 接收错误数
         */
        private Long receiveErrors;

        /**
         * 发送错误数
         */
        private Long sendErrors;
    }
} 