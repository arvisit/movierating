package com.company.movierating.service.factory;

import java.util.HashMap;
import java.util.Map;

import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.factory.DaoFactory;
import com.company.movierating.service.UserService;
import com.company.movierating.service.impl.UserServiceImpl;

public class ServiceFactory {
    private final Map<Class<?>, Object> services;

    private static class ServiceFactoryHolder {
        public static ServiceFactory HOLDER_INSTANCE = new ServiceFactory();
    }

    private ServiceFactory() {
        DaoFactory daoFactory = DaoFactory.getInstance();

        services = new HashMap<>();
        services.put(UserService.class, new UserServiceImpl(daoFactory.getDao(UserDao.class)));
    }

    public static ServiceFactory getInstance() {
        return ServiceFactoryHolder.HOLDER_INSTANCE;
    }

    public <T> T getService(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        T service = (T) services.get(clazz);
        if (service == null) {
            throw new IllegalArgumentException("Attempt to get ServiceObject for unsupported class " + clazz);
        }
        return service;
    }
}
