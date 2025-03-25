package com.lawfirm.model.auth.service;

import com.lawfirm.model.auth.entity.LoginHistory;

import java.util.List;

/**
 * 登录历史服务接口
 */
public interface LoginHistoryService {

    /**
     * 保存登录历史
     *
     * @param userId     用户ID
     * @param username   用户名
     * @param ip         IP地址
     * @param location   登录地点
     * @param browser    浏览器
     * @param os         操作系统
     * @param status     状态（0成功，1失败）
     * @param msg        消息
     * @return 操作结果
     */
    boolean saveLoginHistory(Long userId, String username, String ip, String location, 
                          String browser, String os, Integer status, String msg);

    /**
     * 获取用户登录历史
     *
     * @param userId 用户ID
     * @return 登录历史列表
     */
    List<LoginHistory> getLoginHistory(Long userId);

    /**
     * 获取用户最后一次登录记录
     *
     * @param userId 用户ID
     * @return 登录历史
     */
    LoginHistory getLastLogin(Long userId);
} 