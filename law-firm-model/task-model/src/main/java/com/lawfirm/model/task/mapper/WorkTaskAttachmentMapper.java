package com.lawfirm.model.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.task.constants.WorkTaskSqlConstants;
import com.lawfirm.model.task.entity.WorkTaskAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任务附件 Mapper 接口
 */
@Mapper
public interface WorkTaskAttachmentMapper extends BaseMapper<WorkTaskAttachment> {

    /**
     * 根据任务ID查询附件列表
     *
     * @param taskId 任务ID
     * @return 附件列表
     */
    @Select(WorkTaskSqlConstants.Attachment.SELECT_TASK_ATTACHMENTS)
    List<WorkTaskAttachment> selectByTaskId(Long taskId);

    /**
     * 统计任务的附件数量
     *
     * @param taskId 任务ID
     * @return 附件数量
     */
    @Select("SELECT COUNT(*) FROM work_task_attachment WHERE task_id = #{taskId}")
    Integer countByTaskId(Long taskId);

    /**
     * 根据MD5查询附件
     */
    @Select("SELECT * FROM work_task_attachment WHERE file_md5 = #{fileMd5}")
    WorkTaskAttachment selectByMd5(String fileMd5);

    /**
     * 查询最近上传的附件
     */
    @Select("SELECT * FROM work_task_attachment ORDER BY create_time DESC LIMIT #{limit}")
    List<WorkTaskAttachment> selectRecent(Integer limit);
} 