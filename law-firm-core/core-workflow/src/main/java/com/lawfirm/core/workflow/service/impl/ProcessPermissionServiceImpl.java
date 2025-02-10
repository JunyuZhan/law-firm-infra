package com.lawfirm.core.workflow.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.util.json.JsonUtils;
import com.lawfirm.core.workflow.entity.ProcessPermissionEntity;
import com.lawfirm.core.workflow.mapper.ProcessPermissionMapper;
import com.lawfirm.core.workflow.model.ProcessPermission;
import com.lawfirm.core.workflow.model.TaskNodePermission;
import com.lawfirm.core.workflow.repository.ProcessPermissionRepository;
import com.lawfirm.core.workflow.service.ProcessPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

/**
 * 流程权限服务实现
 */
@Slf4j
@Service
public class ProcessPermissionServiceImpl extends BaseServiceImpl<ProcessPermissionMapper, ProcessPermissionEntity, ProcessPermission> implements ProcessPermissionService {

    private final ProcessPermissionRepository processPermissionRepository;
    private final ObjectMapper objectMapper;

    public ProcessPermissionServiceImpl(ProcessPermissionMapper baseMapper, 
                                      ProcessPermissionRepository processPermissionRepository,
                                      ObjectMapper objectMapper) {
        this.processPermissionRepository = processPermissionRepository;
        this.objectMapper = objectMapper;
    }

    private String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("JSON序列化失败", e);
            throw new RuntimeException("JSON序列化失败", e);
        }
    }

    private <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.error("JSON反序列化失败", e);
            throw new RuntimeException("JSON反序列化失败", e);
        }
    }

    @Override
    protected ProcessPermissionEntity createEntity() {
        return new ProcessPermissionEntity();
    }

    @Override
    protected ProcessPermission createVO() {
        return new ProcessPermission();
    }

    @Override
    public ProcessPermissionEntity toEntity(ProcessPermission vo) {
        if (vo == null) {
            return null;
        }

        ProcessPermissionEntity entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        
        if (vo.getStartRoles() != null) {
            entity.setStartRoles(toJsonString(vo.getStartRoles()));
        }
        if (vo.getStartDepartments() != null) {
            entity.setStartDepartments(toJsonString(vo.getStartDepartments()));
        }
        if (vo.getTaskNodePermissions() != null) {
            entity.setTaskNodePermissions(toJsonString(vo.getTaskNodePermissions()));
        }
        if (vo.getAdminRoles() != null) {
            entity.setAdminRoles(toJsonString(vo.getAdminRoles()));
        }
        return entity;
    }

    @Override
    public ProcessPermission toDTO(ProcessPermissionEntity entity) {
        if (entity == null) {
            return null;
        }

        ProcessPermission vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        
        if (entity.getStartRoles() != null) {
            vo.setStartRoles(fromJson(entity.getStartRoles(), new TypeReference<List<String>>() {}));
        }
        if (entity.getStartDepartments() != null) {
            vo.setStartDepartments(fromJson(entity.getStartDepartments(), new TypeReference<List<String>>() {}));
        }
        if (entity.getTaskNodePermissions() != null) {
            vo.setTaskNodePermissions(fromJson(entity.getTaskNodePermissions(), new TypeReference<List<TaskNodePermission>>() {}));
        }
        if (entity.getAdminRoles() != null) {
            vo.setAdminRoles(fromJson(entity.getAdminRoles(), new TypeReference<List<String>>() {}));
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessPermission createPermission(ProcessPermission permission) {
        return create(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessPermission updatePermission(ProcessPermission permission) {
        return update(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermission(String processKey) {
        Optional<ProcessPermissionEntity> optional = processPermissionRepository.findByProcessKey(processKey);
        optional.ifPresent(entity -> delete(entity.getId()));
    }

    @Override
    public ProcessPermission getPermission(String processKey) {
        Optional<ProcessPermissionEntity> optional = processPermissionRepository.findByProcessKey(processKey);
        return optional.map(this::toDTO).orElse(null);
    }

    @Override
    public List<ProcessPermission> listPermissions(String category, Boolean enabled) {
        List<ProcessPermissionEntity> entities = processPermissionRepository.findByCategoryAndEnabled(category, enabled);
        return entityListToVOList(entities);
    }

    @Override
    public boolean checkStartPermission(String processKey, String userId) {
        ProcessPermission permission = getPermission(processKey);
        if (permission == null || !permission.isEnabled()) {
            return false;
        }

        // TODO: 实现角色和部门的权限检查
        return true;
    }

    @Override
    public boolean checkTaskPermission(String processKey, String taskKey, String userId) {
        ProcessPermission permission = getPermission(processKey);
        if (permission == null || !permission.isEnabled()) {
            return false;
        }

        // TODO: 实现任务节点的权限检查
        return true;
    }

    @Override
    public List<String> getStartableProcesses(String userId) {
        // TODO: 实现获取用户可发起的流程列表
        return List.of();
    }

    @Override
    public List<String> getTaskCandidates(String processKey, String taskKey) {
        // TODO: 实现获取任务候选人列表
        return List.of();
    }
} 