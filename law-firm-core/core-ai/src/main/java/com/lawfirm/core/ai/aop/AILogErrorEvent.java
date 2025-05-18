package com.lawfirm.core.ai.aop;

import org.springframework.context.ApplicationEvent;
import java.util.Map;

public class AILogErrorEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final transient Map<String, Object> errorInfo;
    
    public AILogErrorEvent(Object source, Map<String, Object> errorInfo) {
        super(source);
        this.errorInfo = errorInfo;
    }
    
    public Map<String, Object> getErrorInfo() {
        return errorInfo;
    }
} 