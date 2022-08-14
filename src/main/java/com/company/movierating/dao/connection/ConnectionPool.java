package com.company.movierating.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConnectionPool {
    private final Integer poolSize;
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> inUseConnections;

    ConnectionPool(String driver, String url, String user, String password, Integer poolSize) {
        this.poolSize = poolSize;
        freeConnections = new LinkedBlockingDeque<>(poolSize);
        inUseConnections = new ArrayDeque<>();
        try {
            Class.forName(driver);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.offer(new ProxyConnection(connection));
                log.info("Connection was initialized");
            }
        } catch (SQLException | ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            inUseConnections.offer(connection);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.debug("Query to DB was performed");
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            inUseConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else {
            log.warn("Attempt to close invalid connection has occured");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < poolSize; i++) {
            try {
                ProxyConnection proxy = (ProxyConnection) freeConnections.take();
                proxy.reallyClose();
                log.info("Connection was closed");
            } catch (SQLException | InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
                log.info("{} was deregistered", driver);
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        });
    }
}
