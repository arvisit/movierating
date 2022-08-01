package com.company.movierating.dao.factory;

import java.util.HashMap;
import java.util.Map;

import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.connection.DataSource;
import com.company.movierating.dao.impl.UserDaoImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DaoFactory {
    private final Map<Class<?>, Object> daos;
    
    private static class DaoFactoryHolder {
        public static DaoFactory HOLDER_INSTANCE = new DaoFactory();
    }

    private DaoFactory() {
        DataSource dataSource = DataSource.getInstance();
        
        daos = new HashMap<>();
        daos.put(UserDao.class, new UserDaoImpl(dataSource));
    }

    public static DaoFactory getInstance() {
        return DaoFactoryHolder.HOLDER_INSTANCE;
    }

    public <T> T getDao(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        T dao = (T) daos.get(clazz);
        if (dao == null) {
            log.error("Attempt to get DaoObject for unsupported class {}", clazz);
            throw new IllegalArgumentException("Unsupported class " + clazz);
        }
        return dao;
    }
}
