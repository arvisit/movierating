package com.company.movierating.dao.connection;

import java.sql.Connection;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataSource implements AutoCloseable {
    private ConnectionPool connectionPool;
    private final String url;
    private final String user;
    private final String password;
    private final String driver;
    private final Integer poolSize;

    private static class DataSourceHolder {
        public static final DataSource INSTANCE_HOLDER = new DataSource();
    }

    private DataSource() {
        ConfigurationManager properties = ConfigurationManager.getInstance();
        url = properties.getUrl();
        user = properties.getUser();
        password = properties.getPassword();
        driver = properties.getDriver();
        poolSize = properties.getPoolSize();
    }

    public static DataSource getInstance() {
        return DataSourceHolder.INSTANCE_HOLDER;
    }

    public Connection getConnection() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool(driver, url, user, password, poolSize);
            log.info("Connection pool was initialized");
        }
        return connectionPool.getConnection();
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    @Override
    public void close() {
        if (connectionPool != null) {
            connectionPool.destroyPool();
            connectionPool = null;
            log.info("Connection pool was destroyed");
        }
    }
}
