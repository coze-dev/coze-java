package com.coze.openapi.service.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthLogFactory {
    private static volatile Logger logger = LoggerFactory.getLogger("com.coze.openapi.auth");
    
    public static Logger getLogger() {
        return logger;
    }
    
    public static void setLogger(Logger customLogger) {
        if (customLogger != null) {
            logger = customLogger;
        }
    }
}