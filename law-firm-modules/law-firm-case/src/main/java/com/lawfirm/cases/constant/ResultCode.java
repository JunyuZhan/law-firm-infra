/**
 * 案件合同相关的结果码
 */
public enum ResultCode {

    /**
     * 案件合同无效
     */
    CASE_CONTRACT_INVALID(40010, "案件合同无效"),

    /**
     * 案件合同状态无效
     */
    CASE_CONTRACT_STATUS_INVALID(40011, "案件合同状态无效"),

    /**
     * 案件合同操作权限不足
     */
    CASE_CONTRACT_PERMISSION_DENIED(40012, "案件合同操作权限不足");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
} 