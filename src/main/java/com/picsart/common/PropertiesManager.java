package com.picsart.common;

import java.util.Properties;

public class PropertiesManager {
    private static final ThreadLocal<Properties> PROPERTIES = new ThreadLocal<>() {
        @Override
        protected Properties initialValue() {
            return new Properties();
        }
    };

    public static void setProperty(String key, String value) {
        PROPERTIES.get().setProperty(key, value);
    }
    public static String getProperty(String key) {
        return PROPERTIES.get().getProperty(key);
    }
    public static String getProperty(String key, String defaultValue) {
        return PROPERTIES.get().getProperty(key, defaultValue);
    }
}
