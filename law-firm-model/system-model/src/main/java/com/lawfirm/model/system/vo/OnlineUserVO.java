package com.lawfirm.model.system.vo;

import com.lawfirm.common.data.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 在线用户视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OnlineUserVO extends BaseVO {
    
    /**
     * 会话编号
     */
    private String tokenId;
    
    /**
     * 用户名称
     */
    private String username;
    
    /**
     * 登录IP地址
     */
    private String ipaddr;
    
    /**
     * 登录地点
     */
    private String loginLocation;
    
    /**
     * 浏览器类型
     */
    private String browser;
    
    /**
     * 操作系统
     */
    private String os;
    
    /**
     * 登录时间
     */
    private Long loginTime;
} 