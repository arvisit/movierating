package com.company.movierating.service.converter.factory;

import java.util.HashMap;
import java.util.Map;

import com.company.movierating.service.converter.impl.BanConverter;
import com.company.movierating.service.converter.impl.UserConverter;

public class ConverterFactory {
    private final Map<Class<?>, Object> converters;

    private class ConverterFactoryHolder {
        public static final ConverterFactory HOLDER_INSTANCE = new ConverterFactory();
    }

    ConverterFactory() {
        converters = new HashMap<>();
        converters.put(UserConverter.class, new UserConverter());
        converters.put(BanConverter.class, new BanConverter(getConverter(UserConverter.class)));
    }

    public static ConverterFactory getInstance() {
        return ConverterFactoryHolder.HOLDER_INSTANCE;
    }

    public <T> T getConverter(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        T converter = (T) converters.get(clazz);
        if (converter == null) {
            throw new IllegalArgumentException("Attempt to get ConverterObject for unsupported class " + clazz);
        }
        return converter;
    }
}
