// AI请求对象

public class AIRequestVO {
    private String inputData; // 输入数据
    private String modelVersion; // 模型版本

    // 构造函数
    public AIRequestVO(String inputData, String modelVersion) {
        this.inputData = inputData;
        this.modelVersion = modelVersion;
    }

    // Getter和Setter
    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }
} 