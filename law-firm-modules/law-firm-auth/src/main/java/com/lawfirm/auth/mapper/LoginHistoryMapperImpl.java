package com.lawfirm.auth.mapper;

import com.lawfirm.model.auth.entity.LoginHistory;
import com.lawfirm.model.auth.mapper.LoginHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 登录历史Mapper接口实现类
 * 用于扩展基础LoginHistoryMapper接口的功能
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LoginHistoryMapperImpl implements LoginHistoryMapper {

    // 注入实际的Mapper代理对象，用于调用基础功能
    private final LoginHistoryMapper loginHistoryMapper;
    
    @Override
    public LoginHistory selectById(Long id) {
        log.debug("自定义LoginHistoryMapperImpl.selectById，参数：{}", id);
        return loginHistoryMapper.selectById(id);
    }
    
    @Override
    public List<LoginHistory> selectByUserId(Long userId) {
        log.debug("自定义LoginHistoryMapperImpl.selectByUserId，参数：{}", userId);
        return loginHistoryMapper.selectByUserId(userId);
    }
    
    @Override
    public List<LoginHistory> selectRecentByUserId(Long userId, int limit) {
        log.debug("自定义LoginHistoryMapperImpl.selectRecentByUserId，参数：userId={}，limit={}", userId, limit);
        return loginHistoryMapper.selectRecentByUserId(userId, limit);
    }
    
    @Override
    public int insert(LoginHistory loginHistory) {
        log.debug("自定义LoginHistoryMapperImpl.insert，参数：{}", loginHistory);
        return loginHistoryMapper.insert(loginHistory);
    }
    
    // 实现其他接口方法...
}
