package com.lawfirm.auth.service.impl;

import com.lawfirm.model.auth.entity.LoginHistory;
import com.lawfirm.model.auth.mapper.LoginHistoryMapper;
import com.lawfirm.model.auth.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 登录历史服务实现类
 */
@Slf4j
@Service("loginHistoryService")
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryMapper loginHistoryMapper;

    @Override
    public boolean saveLoginHistory(Long userId, String username, String ip, String location, 
                                  String browser, String os, Integer status, String msg) {
        try {
            LoginHistory history = new LoginHistory();
            history.setUserId(userId);
            history.setUsername(username);
            history.setIp(ip);
            history.setLocation(location);
            history.setBrowser(browser);
            history.setOs(os);
            history.setStatus(status);
            history.setMsg(msg);
            history.setLoginTime(LocalDateTime.now());
            
            int result = loginHistoryMapper.insert(history);
            return result > 0;
        } catch (Exception e) {
            log.error("保存登录历史失败", e);
            return false;
        }
    }

    @Override
    public List<LoginHistory> getLoginHistory(Long userId) {
        return loginHistoryMapper.selectByUserId(userId);
    }

    @Override
    public LoginHistory getLastLogin(Long userId) {
        return loginHistoryMapper.selectLastLoginByUserId(userId);
    }
} 