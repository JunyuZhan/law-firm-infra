package com.lawfirm.staff.model.request.finance.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "资产导入请求")
public class AssetImportRequest {
    
    @Schema(description = "Excel文件")
    private MultipartFile file;
    
    @Schema(description = "是否更新已存在的资产")
    private Boolean updateExisting;
} 