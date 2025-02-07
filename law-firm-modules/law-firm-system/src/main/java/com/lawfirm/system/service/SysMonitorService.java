package com.lawfirm.system.service;

import com.lawfirm.model.system.vo.OnlineUserVO;
import com.lawfirm.model.system.vo.ServerInfoVO;
import java.util.List;

/**
 * 系统监控服务接口
 */
public interface SysMonitorService {
    
    /**
     * 获取服务器信息
     *
     * @return 服务器信息
     */
    ServerInfoVO getServerInfo();
    
    /**
     * 获取在线用户列表
     *
     * @param ipaddr IP地址
     * @param username 用户名
     * @return 在线用户列表
     */
    List<OnlineUserVO> getOnlineUserList(String ipaddr, String username);
    
    /**
     * 强退用户
     *
     * @param tokenId 会话编号
     */
    void forceLogout(String tokenId);
} 