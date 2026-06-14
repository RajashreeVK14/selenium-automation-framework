package com.ka.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApiConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ApiConfig.class.getClassLoader()
                .getResourceAsStream("config/application.properties")) {
            if (input == null) {
                throw new IllegalStateException("application.properties not found");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ApiConfig() {
    }

    public static String getBaseUri() {
        return PROPERTIES.getProperty("base.uri");
    }

    public static int getConnectionTimeout() {
        return Integer.parseInt(PROPERTIES.getProperty("connection.timeout", "10000"));
    }

    public static int getSocketTimeout() {
        return Integer.parseInt(PROPERTIES.getProperty("socket.timeout", "30000"));
    }
}
