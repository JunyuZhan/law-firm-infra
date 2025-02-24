package com.lawfirm.model.log.dto;

import com.lawfirm.model.base.dto.BaseExportDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 日志导出DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogExportDTO extends BaseExportDTO {

    /**
     * 导出条件
     */
    private LogQueryDTO queryCondition;
}