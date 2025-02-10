package com.lawfirm.staff.model.request.finance.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "发票导入请求")
public class InvoiceImportRequest {
    
    @Schema(description = "Excel文件")
    private MultipartFile file;
    
    @Schema(description = "是否更新已存在的发票")
    private Boolean updateExisting;
} 