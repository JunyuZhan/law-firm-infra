// AI响应对象

public class AIResponseDTO {
    private String outputData; // 输出数据
    private String status; // 状态

    // 构造函数
    public AIResponseDTO(String outputData, String status) {
        this.outputData = outputData;
        this.status = status;
    }

    // Getter和Setter
    public String getOutputData() {
        return outputData;
    }

    public void setOutputData(String outputData) {
        this.outputData = outputData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 