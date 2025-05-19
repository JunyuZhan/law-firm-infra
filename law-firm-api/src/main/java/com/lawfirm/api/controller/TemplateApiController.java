package com.lawfirm.api.controller;

import com.lawfirm.api.constant.ApiConstants;
import com.lawfirm.common.core.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * API文档注解示例模板
 * 仅在开发环境中使用，用于演示OpenAPI注解的使用方法
 */
@Tag(name = "API文档示例", description = "API文档注解使用示例，仅在开发环境中可用")
@RestController
@RequestMapping(ApiConstants.API_BASE + "/example")
@Profile("dev") // 仅在开发环境可用
@Slf4j
public class TemplateApiController extends BaseApiController {

    /**
     * 基础GET请求示例
     */
    @Operation(
            summary = "GET请求示例", 
            description = "展示GET请求的文档说明示例",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功"),
            @ApiResponse(responseCode = "401", description = "未认证"),
            @ApiResponse(responseCode = "500", description = "服务器错误")
    })
    @GetMapping("/get-example")
    public CommonResult<Map<String, Object>> getExample(
            @Parameter(description = "示例参数", required = true) @RequestParam String param) {
        Map<String, Object> result = new HashMap<>();
        result.put("param", param);
        result.put("timestamp", System.currentTimeMillis());
        
        return success(result, "GET请求示例");
    }
    
    /**
     * 路径参数示例
     */
    @Operation(
            summary = "路径参数示例", 
            description = "展示路径参数的文档说明示例"
    )
    @Parameters({
            @Parameter(name = "id", description = "记录ID", in = ParameterIn.PATH, required = true)
    })
    @GetMapping("/path/{id}")
    public CommonResult<Map<String, Object>> pathExample(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        
        return success(result, "路径参数示例");
    }
    
    /**
     * POST请求示例
     */
    @Operation(
            summary = "POST请求示例", 
            description = "展示POST请求的文档说明示例"
    )
    @PostMapping("/post-example")
    public CommonResult<ExampleDTO> postExample(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "请求体参数",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ExampleDTO.class)
                    )
            )
            @RequestBody ExampleDTO dto) {
        return success(dto, "POST请求示例");
    }
    
    /**
     * 示例DTO类
     */
    @Schema(description = "示例数据传输对象")
    public static class ExampleDTO {
        @Schema(description = "名称", example = "示例名称")
        private String name;
        
        @Schema(description = "描述", example = "这是一个示例描述")
        private String description;
        
        @Schema(description = "创建时间戳", example = "1681789342000")
        private Long timestamp = System.currentTimeMillis();

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }
} 