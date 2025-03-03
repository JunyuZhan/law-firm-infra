package com.lawfirm.core.message.utils;

import com.lawfirm.common.util.id.IdGenerator;

public class MessageIdGenerator {
    
    public static String generateId() {
        return "MSG" + IdGenerator.nextIdStr();
    }
    
    public static String generateNotifyId() {
        return "NTF" + IdGenerator.nextIdStr();
    }
} 