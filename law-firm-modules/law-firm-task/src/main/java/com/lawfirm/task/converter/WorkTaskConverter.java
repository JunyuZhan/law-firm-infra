package com.lawfirm.task.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.util.json.JsonUtils;
import com.lawfirm.model.task.dto.WorkTaskDTO;
import com.lawfirm.model.task.dto.WorkTaskTagDTO;
import com.lawfirm.model.task.entity.WorkTask;
import com.lawfirm.model.task.entity.WorkTaskTag;
import com.lawfirm.model.task.enums.WorkTaskPriorityEnum;
import com.lawfirm.model.task.enums.WorkTaskStatusEnum;
import com.lawfirm.model.task.vo.WorkTaskVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作任务转换器
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WorkTaskConverter {

    WorkTaskConverter INSTANCE = Mappers.getMapper(WorkTaskConverter.class);
    
    // 使用日志记录器
    Logger LOGGER = LoggerFactory.getLogger(WorkTaskConverter.class);

    /**
     * 实体转DTO
     */
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "subTasks", ignore = true)
    @Mapping(target = "assigneeName", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "isRemind", ignore = true)
    @Mapping(target = "remindTime", ignore = true)
    @Mapping(target = "remindType", ignore = true)
    @Mapping(target = "progress", ignore = true)
    @Mapping(target = "estimatedHours", ignore = true)
    @Mapping(target = "actualHours", ignore = true)
    @Mapping(target = "contractId", ignore = true)
    @Mapping(target = "documentId", ignore = true)
    @Mapping(target = "documentIds", source = "documentIds", qualifiedByName = "stringToLongList")
    WorkTaskDTO entityToDto(WorkTask entity);

    /**
     * DTO转实体
     */
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "sort", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "documentIds", source = "documentIds", qualifiedByName = "longListToString")
    WorkTask dtoToEntity(WorkTaskDTO dto);

    /**
     * 实体转VO
     */
    @Mapping(target = "statusEnum", source = "status")
    @Mapping(target = "priority", source = "priority", qualifiedByName = "priorityToEnum")
    @Mapping(target = "priorityCode", source = "priority")
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "parentTitle", ignore = true)
    @Mapping(target = "commentCount", ignore = true)
    @Mapping(target = "attachmentCount", ignore = true)
    @Mapping(target = "subTaskCount", ignore = true)
    @Mapping(target = "completeTime", ignore = true)
    @Mapping(target = "cancelTime", ignore = true)
    @Mapping(target = "cancelReason", ignore = true)
    @Mapping(target = "assigneeName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    WorkTaskVO entityToVo(WorkTask entity);

    /**
     * 标签实体转标签DTO
     */
    WorkTaskTagDTO tagToTagDto(WorkTaskTag tag);

    /**
     * 合并任务属性
     */
    void mergeTaskProperties(WorkTask source, @MappingTarget WorkTask target);

    /**
     * Integer状态码转换为WorkTaskStatusEnum
     */
    default WorkTaskStatusEnum map(Integer value) {
        return WorkTaskStatusEnum.getByCode(value);
    }

    /**
     * Integer优先级码转换为WorkTaskPriorityEnum
     */
    @Named("priorityToEnum")
    default WorkTaskPriorityEnum priorityToEnum(Integer value) {
        return WorkTaskPriorityEnum.getByCode(value);
    }
    
    /**
     * JSON字符串转为Long列表
     */
    @Named("stringToLongList")
    default List<Long> stringToLongList(String json) {
        if (json == null || json.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        try {
            // 使用JsonUtils中的ObjectMapper
            return JsonUtils.parseArray(json, Long.class);
        } catch (Exception e) {
            LOGGER.error("转换documentIds失败: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Long列表转为JSON字符串
     */
    @Named("longListToString")
    default String longListToString(List<Long> list) {
        if (list == null || list.isEmpty()) {
            return "[]";
        }
        
        try {
            // 使用JsonUtils中的ObjectMapper
            return JsonUtils.toJsonString(list);
        } catch (Exception e) {
            LOGGER.error("转换documentIds失败: {}", e.getMessage());
            return "[]";
        }
    }
}