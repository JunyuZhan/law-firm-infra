package com.lawfirm.common.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.log.domain.OperationLogDO;
import com.lawfirm.common.log.mapper.OperationLogMapper;
import com.lawfirm.common.log.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 操作日志服务实现类
 */
@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLogDO> implements OperationLogService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOperationLog(OperationLogDO operationLog) {
        save(operationLog);
    }

    @Override
    public OperationLogDO getOperationLogById(Long id) {
        return getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOperationLogById(Long id) {
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOperationLogByIds(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanOperationLog() {
        baseMapper.delete(null);
    }
} 