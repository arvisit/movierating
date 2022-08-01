package com.company.movierating.dao.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConfigurationManager {
    @Getter
    private final String url;
    @Getter
    private final String user;
    @Getter
    private final String password;
    @Getter
    private final String driver;
    private static final String PROPERTIES_FILE = "/application.properties";

    private static class ConfigurationManagerHolder {
        public static final ConfigurationManager HOLDER_INSTANCE = new ConfigurationManager();
    }

    private ConfigurationManager() {
        Properties properties = new Properties();
        try (InputStream is = ConfigurationManager.class.getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(is);
            if (System.getenv("USE_LOCAL_DB") != null) {
                url = properties.getProperty("db.test.url");
                user = properties.getProperty("db.test.user");
                password = properties.getProperty("db.test.password");
            } else {
                url = properties.getProperty("db.prod.url");
                user = properties.getProperty("db.prod.user");
                password = properties.getProperty("db.prod.password");
            }
            driver = properties.getProperty("db.driver");
            log.info("Configuration properties were loaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConfigurationManager getInstance() {
        return ConfigurationManagerHolder.HOLDER_INSTANCE;
    }
}
